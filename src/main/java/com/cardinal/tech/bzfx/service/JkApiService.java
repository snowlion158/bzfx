package com.cardinal.tech.bzfx.service;

import com.cardinal.tech.bzfx.bean.bo.*;
import com.cardinal.tech.bzfx.entity.JkApi;
import java.util.List;
import java.util.Map;

/**
 * 接口(JkApi)表服务接口
 *
 * @author cadinal.tech
 * @since 2021-11-14 14:09:12
 */
public interface JkApiService {

        /**
         * 通过ID查询单条数据
         *
         * @param id 主键
         * @return 实例对象
         */
        JkApi queryById(Long id);

        /**
         * 查询多条数据
         *
         * @param offset 查询起始位置
         * @param limit 查询条数
         * @return 对象列表
         */
        List<JkApi> queryAllByLimit(int offset, int limit);

        /**
         * 新增数据
         *
         * @param jkApi 实例对象
         * @return 实例对象
         */
        JkApi insert(JkApi jkApi);

        /**
         * 修改数据
         *
         * @param jkApi 实例对象
         * @return 实例对象
         */
        JkApi update(JkApi jkApi);

        /**
         * 通过主键删除数据
         *
         * @param id 主键
         * @return 是否成功
         */
        boolean deleteById(Long id);

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
        Page<JkApi> page(PageForm<JkApi> userQueryForm);

}