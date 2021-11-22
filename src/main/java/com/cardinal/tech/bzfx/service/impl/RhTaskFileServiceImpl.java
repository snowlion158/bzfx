package com.cardinal.tech.bzfx.service.impl;

import com.cardinal.tech.bzfx.bean.bo.*;
import com.cardinal.tech.bzfx.bean.dbo.page.PageQuery;
import com.cardinal.tech.bzfx.dao.*;
import com.cardinal.tech.bzfx.entity.*;
import com.cardinal.tech.bzfx.enums.biz.SyncResultEnum;
import com.cardinal.tech.bzfx.enums.biz.SyncStateEnum;
import com.cardinal.tech.bzfx.etl.EtlUtil;
import com.cardinal.tech.bzfx.service.RhTaskFileService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 任务数据-文件(RhTaskFile)表服务实现类
 *
 * @author cadinal.tech
 * @since 2021-11-14 14:09:12
 */
@RequiredArgsConstructor
@Service("rhTaskFileService")
public class RhTaskFileServiceImpl implements RhTaskFileService {

    private final RhTaskFileDao rhTaskFileDao;

    private final SlSyncLogsDao slSyncLogsDao;

    private final EtlUtil etlUtil;

    private SqlSessionTemplate sqlSessionTemplate;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public RhTaskFile queryById(Long id) {
        return this.rhTaskFileDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<RhTaskFile> queryAllByLimit(int offset, int limit) {
        return this.rhTaskFileDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param rhTaskFile 实例对象
     * @return 实例对象
     */
    @Override
    public RhTaskFile insert(RhTaskFile rhTaskFile) {
        this.rhTaskFileDao.insert(rhTaskFile);
        return rhTaskFile;
    }

    /**
     * 修改数据
     *
     * @param rhTaskFile 实例对象
     * @return 实例对象
     */
    @Override
    public RhTaskFile update(RhTaskFile rhTaskFile) {
        this.rhTaskFileDao.update(rhTaskFile);
        return this.queryById(rhTaskFile.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.rhTaskFileDao.deleteById(id) > 0;
    }

    /**
     * 通过字段进行数据统计
     *
     * @param field 字段名
     * @return 统计结果
     */
    @Override
    public List<Map<String,Integer>> groupBy(String field) {
        return this.rhTaskFileDao.groupBy(field);
    }

    /**
     *  分页查询
     *
     * @param userQueryForm 查询对象
     * @return 分页结果
     */
     @Override
     public Page<RhTaskFile> page(PageForm<RhTaskFile> userQueryForm){
        var pq = new PageQuery<>(userQueryForm);
        List<RhTaskFile> entityList = this.rhTaskFileDao.queryPageRhTaskFileList(pq);
        Page<RhTaskFile> p = new Page<>(pq.getTotalCount(), pq.getMax(), pq.getCurrentPage());
        p.setData(entityList);
        return p;
    }

    /**
     * 同步数据
     *
     * @param taskId 字段名
     * @return
     */
    @Override
    public boolean syncData(Long taskId) {
        RhTaskFile rhTaskFile = new RhTaskFile();
        rhTaskFile.setTaskId(taskId);
        List<RhTaskFile> rhTaskFiles = this.rhTaskFileDao.queryAll(rhTaskFile);
        long cont = rhTaskFiles.stream().filter(e-> Objects.nonNull(e.getState()) && e.getState().equals(SyncStateEnum.SYNC_PROGRESS.value())).count();
        if (cont>0){ return false; }
        syncData(rhTaskFiles);
        return true;
    }

    @Async
    void syncData(List<RhTaskFile> rhTaskFiles) {
        etlUtil.truncateTable();
        for (RhTaskFile file:rhTaskFiles){
            long count = 0;
            Date syncAt = new Date();
            Date syncEnd = null;
            Integer result = SyncResultEnum.SYNC_SUCCESS.value();
            String remark = SyncResultEnum.SYNC_SUCCESS.desc();
            try {
                file.setState(SyncStateEnum.SYNC_PROGRESS.value());
                file.setSyncAt(syncAt);
                this.rhTaskFileDao.update(file);
                count = this.batchProcessing(file);
                file.setState(SyncStateEnum.SYNC_FINISHED.value());
                file.setResult(result);
                syncEnd = new Date();
                file.setSyncEnd(syncEnd);
                this.rhTaskFileDao.update(file);
            }catch (Exception e){
                e.printStackTrace();
                remark = e.getMessage();
                file.setState(SyncStateEnum.SYNC_FINISHED.value());
                result = SyncResultEnum.SYNC_FAIL.value();
                file.setResult(result);
                syncEnd = new Date();
                file.setSyncEnd(syncEnd);
                this.rhTaskFileDao.update(file);
            }finally {
                SlSyncLogs slSyncLogs = new SlSyncLogs();
                slSyncLogs.setCreatAt(new Date());
                slSyncLogs.setTaskDbId(file.getId().intValue());
                slSyncLogs.setDataTotal(count);
                slSyncLogs.setDbHost(file.getTableName());
                slSyncLogs.setSyncAt(syncAt);
                slSyncLogs.setSyncEnd(syncEnd);
                slSyncLogs.setResult(result);
                slSyncLogs.setRemark(remark);
                slSyncLogs.setCreatAt(new Date());
                slSyncLogsDao.insert(slSyncLogs);
            }
        }
    }

    private long batchProcessing(RhTaskFile file) throws FileNotFoundException {
        String filePath = String.format("%s%s%s",file.getUrl(),"/",file.getFilename());
        long count = 0;
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH,false);
        if (file.getTableName().equals("BZK_TAB_DANWEIJBXX")){
            List<BzkTabDanweijbxx> list = etlUtil.parseCsvToBean(BzkTabDanweijbxx.class,filePath,',',1);
            BzkTabDanweijbxxDao bzkTabDanweijbxxDao = sqlSession.getMapper(BzkTabDanweijbxxDao.class);
            list.forEach(e->{
                bzkTabDanweijbxxDao.insert(e);
            });
            count = list.size();
        }else if (file.getTableName().equals("BZK_SLGX_BZ_BZDAXX")){
            List<BzkSlgxBzBzdaxx> list = etlUtil.parseCsvToBean(BzkSlgxBzBzdaxx.class,filePath,',',1);
            BzkSlgxBzBzdaxxDao bzkSlgxBzBzdaxxDao = sqlSession.getMapper(BzkSlgxBzBzdaxxDao.class);
            list.forEach(e->{
                bzkSlgxBzBzdaxxDao.insert(e);
            });
            count = list.size();
        }else if (file.getTableName().equals("BZK_SLGX_BZ_BZFFJL")){
            List<BzkSlgxBzBzffjl> list = etlUtil.parseCsvToBean(BzkSlgxBzBzffjl.class,filePath,',',1);
            BzkSlgxBzBzffjlDao bzkSlgxBzBzffjlDao = sqlSession.getMapper(BzkSlgxBzBzffjlDao.class);
            list.forEach(e->{
                bzkSlgxBzBzffjlDao.insert(e);
            });
            count = list.size();
        }else if (file.getTableName().equals("BZK_SLGX_CW_CWBZSJ")){
            List<BzkSlgxCwCwbzsj> list = etlUtil.parseCsvToBean(BzkSlgxCwCwbzsj.class,filePath,',',1);
            BzkSlgxCwCwbzsjDao bzkSlgxCwCwbzsjDao = sqlSession.getMapper(BzkSlgxCwCwbzsjDao.class);
            list.forEach(e->{
                bzkSlgxCwCwbzsjDao.insert(e);
            });
            count = list.size();
        }else if (file.getTableName().equals("BZK_SLGX_YF_RYZFQK")){
            List<BzkSlgxYfRyzfqk> list = etlUtil.parseCsvToBean(BzkSlgxYfRyzfqk.class,filePath,',',1);
            BzkSlgxYfRyzfqkDao bzkSlgxYfRyzfqkDao = sqlSession.getMapper(BzkSlgxYfRyzfqkDao.class);
            list.forEach(e->{
                bzkSlgxYfRyzfqkDao.insert(e);
            });
            count = list.size();
        }else if (file.getTableName().equals("BZK_TAB_DANWEIBCLRXX")){
            List<BzkTabDanweibclrxx> list = etlUtil.parseCsvToBean(BzkTabDanweibclrxx.class,filePath,',',1);
            BzkTabDanweibclrxxDao bzkTabDanweibclrxxDao = sqlSession.getMapper(BzkTabDanweibclrxxDao.class);
            list.forEach(e->{
                bzkTabDanweibclrxxDao.insert(e);
            });
            count = list.size();
        }else if (file.getTableName().equals("BZK_TAB_RENYUANJBXX")){
            List<BzkTabRenyuanjbxx> list = etlUtil.parseCsvToBean(BzkTabRenyuanjbxx.class,filePath,',',1);
            BzkTabRenyuanjbxxDao bzkTabRenyuanjbxxDao = sqlSession.getMapper(BzkTabRenyuanjbxxDao.class);
            list.forEach(e->{
                bzkTabRenyuanjbxxDao.insert(e);
            });
            count = list.size();
        }else if (file.getTableName().equals("BZK_TAB_BAOZHANGKJBXX")){
            List<BzkTabBaozhangkjbxx> list = etlUtil.parseCsvToBean(BzkTabBaozhangkjbxx.class,filePath,',',1);
            BzkTabBaozhangkjbxxDao bzkTabBaozhangkjbxxDao = sqlSession.getMapper(BzkTabBaozhangkjbxxDao.class);
            list.forEach(e->{
                bzkTabBaozhangkjbxxDao.insert(e);
            });
            count = list.size();
        }
        sqlSession.commit();
        return count;
    }
}