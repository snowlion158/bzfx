package com.cardinal.tech.bzfx.api;

import com.cardinal.tech.bzfx.bean.bo.*;
import com.cardinal.tech.bzfx.entity.RhTaskFile;
import com.cardinal.tech.bzfx.enums.err.SysErrEnum;
import com.cardinal.tech.bzfx.service.RhTaskFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务数据-文件(RhTaskFile)表服务api接口
 *
 * @author cadinal.tech
 * @since 2021-11-14 14:09:12
 */
@Tag(name="任务数据-文件")
@RequestMapping("/rhTaskFile")
public interface RhTaskFileApi {

        RhTaskFileService getService();

        @PreAuthorize("permitAll()")
        @Operation(description = " get by id")
        @GetMapping("/{id}")
        default Response<RhTaskFile> queryById(@PathVariable("id") Long id){
            return new Response(getService().queryById(id));
        }

        @PreAuthorize("permitAll()")
        @Operation(description = " get list")
        @GetMapping("/list")
       default Response<List<RhTaskFile>> queryAllByLimit(@RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer limit){
            return new Response(getService().queryAllByLimit(offset,limit));
       }

        @PreAuthorize("permitAll()")
        @Operation(description = " add")
        @PostMapping("/add")
        default Response<RhTaskFile> insert(@RequestBody RhTaskFile rhTaskFile){
            return new Response(getService().insert(rhTaskFile));
        }

         @PreAuthorize("permitAll()")
         @Operation(description = " update")
         @PostMapping("/update")
        default Response<RhTaskFile> update(@RequestBody RhTaskFile rhTaskFile){
             return new Response(getService().update(rhTaskFile));
        }

        @PreAuthorize("permitAll()")
        @Operation(description = " delete by pk")
        @GetMapping("/delete")
       default Response<Boolean> deleteById(@RequestParam Long id){
            return new Response(getService().deleteById(id));
        }

        @PreAuthorize("permitAll()")
        @Operation(description = " group by field name")
        @GetMapping("/group")
       default Response<List<Map<String,Integer>>> deleteById(@RequestParam String field){
            return new Response(getService().groupBy(field));
        }

        @PreAuthorize("permitAll()")
        @Operation(description = " page list")
        @PostMapping("/page")
        default Response<Page<RhTaskFile>> page(@RequestBody PageForm<RhTaskFile> userQueryForm) {
           return new Response(getService().page(userQueryForm));
        }

        @PreAuthorize("permitAll()")
        @Operation(description = " sync data")
        @PostMapping("/task/sync")
        default Response<Boolean> syncData(@RequestBody HashMap<String,String> body){
            String url = body.get("url");
            String tableName = body.get("tableName");
            getService().syncData(tableName,url);
            return new Response();
        }
}