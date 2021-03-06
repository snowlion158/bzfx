package com.cardinal.tech.bzfx.service.impl;

import com.cardinal.tech.bzfx.bean.bo.*;
import com.cardinal.tech.bzfx.bean.dbo.page.PageQuery;
import com.cardinal.tech.bzfx.entity.JcSpecial;
import com.cardinal.tech.bzfx.dao.JcSpecialDao;
import com.cardinal.tech.bzfx.enums.ErrEnumInterface;
import com.cardinal.tech.bzfx.service.JcSpecialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.cardinal.tech.bzfx.enums.err.SysErrEnum.*;
import static com.cardinal.tech.bzfx.util.DateUtil.now;

/**
 * 专项任务(JcSpecial)表服务实现类
 *
 * @author makejava
 * @since 2021-11-20 18:42:52
 */
@Slf4j
@RequiredArgsConstructor
@Service("jcSpecialService")
public class JcSpecialServiceImpl implements JcSpecialService {

    private final JcSpecialDao jcSpecialDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public JcSpecial queryById(Long id) {
        return this.jcSpecialDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<JcSpecial> queryAllByLimit(int offset, int limit) {
        return this.jcSpecialDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param jcSpecial 实例对象
     * @return 实例对象
     */
    @Override
    public JcSpecial insert(JcSpecial jcSpecial) {
        this.jcSpecialDao.insert(jcSpecial);
        return jcSpecial;
    }

    /**
     * 修改数据
     *
     * @param jcSpecial 实例对象
     * @return 实例对象
     */
    @Override
    public JcSpecial update(JcSpecial jcSpecial) {
        this.jcSpecialDao.update(jcSpecial);
        return this.queryById(jcSpecial.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.jcSpecialDao.deleteById(id) > 0;
    }

    /**
     * 通过字段进行数据统计
     *
     * @param field 字段名
     * @return 统计结果
     */
    @Override
    public List<Map<String, Integer>> groupBy(String field) {
        return this.jcSpecialDao.groupBy(field);
    }

    /**
     * 分页查询
     *
     * @param userQueryForm 查询对象
     * @return 分页结果
     */
    @Override
    public Page<JcSpecial> page(PageForm<JcSpecial> userQueryForm) {
        var pq = new PageQuery<>(userQueryForm);
        List<JcSpecial> entityList = this.jcSpecialDao.queryPageJcSpecialList(pq);
        Page<JcSpecial> p = new Page<>(pq.getTotalCount(), pq.getMax(), pq.getCurrentPage());
        p.setData(entityList);
        return p;
    }

    @Override
    public ErrEnumInterface statistics(Long id) {
        final var jcSpecial = this.jcSpecialDao.queryById(id);
        if (null != jcSpecial) {
            try {
                jcSpecial.setTjState(1);
                jcSpecialDao.update(jcSpecial);
                this.jcSpecialDao.callStatistics(id);
                jcSpecial.setTjState(2);
                jcSpecial.setTjDate(now());
                jcSpecialDao.update(jcSpecial);
                return SUCCESS;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            jcSpecial.setTjState(3);
            jcSpecial.setTjDate(now());
            jcSpecialDao.update(jcSpecial);
            return UNKOWN_ERR;
        }
        return DAT_FILE_NOT_EXIST;

    }
}