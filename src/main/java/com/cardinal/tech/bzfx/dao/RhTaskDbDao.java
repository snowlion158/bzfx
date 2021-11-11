package com.cardinal.tech.bzfx.dao;

import com.cardinal.tech.bzfx.entity.RhTaskDb;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 任务数据-数据库(RhTaskDb)表数据库访问层
 *
 * @author cadinal.tech
 * @since 2021-11-11 20:18:33
 */
public interface RhTaskDbDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    RhTaskDb queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<RhTaskDb> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param rhTaskDb 实例对象
     * @return 对象列表
     */
    List<RhTaskDb> queryAll(RhTaskDb rhTaskDb);

    /**
     * 新增数据
     *
     * @param rhTaskDb 实例对象
     * @return 影响行数
     */
    int insert(RhTaskDb rhTaskDb);

    /**
     * 修改数据
     *
     * @param rhTaskDb 实例对象
     * @return 影响行数
     */
    int update(RhTaskDb rhTaskDb);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}