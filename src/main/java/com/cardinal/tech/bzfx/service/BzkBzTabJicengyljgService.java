package com.cardinal.tech.bzfx.service;

import com.cardinal.tech.bzfx.bean.bo.*;
import com.cardinal.tech.bzfx.entity.BzkBzTabJicengyljg;
import java.util.List;
import java.util.Map;

/**
 * 体系医院标准编码(BzkBzTabJicengyljg)表服务接口
 *
 * @author makejava
 * @since 2021-11-20 23:59:46
 */
public interface BzkBzTabJicengyljgService {

        /**
         * 通过ID查询单条数据
         *
         * @param id 主键
         * @return 实例对象
         */
        BzkBzTabJicengyljg queryById(String id);

        /**
         * 查询多条数据
         *
         * @param offset 查询起始位置
         * @param limit 查询条数
         * @return 对象列表
         */
        List<BzkBzTabJicengyljg> queryAllByLimit(int offset, int limit);

        /**
         * 新增数据
         *
         * @param bzkBzTabJicengyljg 实例对象
         * @return 实例对象
         */
        BzkBzTabJicengyljg insert(BzkBzTabJicengyljg bzkBzTabJicengyljg);

        /**
         * 修改数据
         *
         * @param bzkBzTabJicengyljg 实例对象
         * @return 实例对象
         */
        BzkBzTabJicengyljg update(BzkBzTabJicengyljg bzkBzTabJicengyljg);

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
        Page<BzkBzTabJicengyljg> page(PageForm<BzkBzTabJicengyljg> userQueryForm);

}