package com.cardinal.tech.bzfx.service;

import com.cardinal.tech.bzfx.bean.bo.*;
import com.cardinal.tech.bzfx.entity.BzkTabDanweijbxx;
import java.util.List;
import java.util.Map;

/**
 * 单位基本信息(BzkTabDanweijbxx)表服务接口
 *
 * @author makejava
 * @since 2021-11-20 16:06:13
 */
public interface BzkTabDanweijbxxService {

        /**
         * 通过ID查询单条数据
         *
         * @param id 主键
         * @return 实例对象
         */
        BzkTabDanweijbxx queryById(String id);

        /**
         * 查询多条数据
         *
         * @param offset 查询起始位置
         * @param limit 查询条数
         * @return 对象列表
         */
        List<BzkTabDanweijbxx> queryAllByLimit(int offset, int limit);

        /**
         * 新增数据
         *
         * @param bzkTabDanweijbxx 实例对象
         * @return 实例对象
         */
        BzkTabDanweijbxx insert(BzkTabDanweijbxx bzkTabDanweijbxx);

        /**
         * 修改数据
         *
         * @param bzkTabDanweijbxx 实例对象
         * @return 实例对象
         */
        BzkTabDanweijbxx update(BzkTabDanweijbxx bzkTabDanweijbxx);

        /**
         * 通过主键删除数据
         *
         * @param id 主键
         * @return 是否成功
         */
        boolean deleteById(String id);

        /**
         * 通过字段统计
         *
         * @param field 字段名
         * @return 统计结果
         */
        List<Map<String,Integer>> groupBy(String field);

        /**
         *  分页查询
         *
         * @param userQueryForm 查询对象
         * @return 分页结果
         */
        Page<BzkTabDanweijbxx> page(PageForm<BzkTabDanweijbxx> userQueryForm);

        /**
         *  分页查询（字典）
         *
         * @param userQueryForm 查询对象
         * @return 分页结果
         */
        Page<BzkTabDanweijbxx> pageDict(PageForm<BzkTabDanweijbxx> userQueryForm);
}