package com.cardinal.tech.bzfx.dao;

import com.cardinal.tech.bzfx.entity.JkApi;
import com.cardinal.tech.bzfx.bean.dbo.page.*;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 接口(JkApi)表数据库访问层
 *
 * @author cadinal.tech
 * @since 2021-11-14 14:09:12
 */
public interface JkApiDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    JkApi queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<JkApi> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param jkApi 实例对象
     * @return 对象列表
     */
    List<JkApi> queryAll(JkApi jkApi);

    /**
     * 新增数据
     *
     * @param jkApi 实例对象
     * @return 影响行数
     */
    int insert(JkApi jkApi);

    /**
     * 修改数据
     *
     * @param jkApi 实例对象
     * @return 影响行数
     */
    int update(JkApi jkApi);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 通过字段进行分组统计
     *
     * @param field 主键
     * @return 统计详情
     */
    List<Map<String,Integer>> groupBy(String field);

     List<JkApi> queryPageJkApiList(PageQuery pq);
}