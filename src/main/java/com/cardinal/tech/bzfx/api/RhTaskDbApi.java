package com.cardinal.tech.bzfx.api;

import com.cardinal.tech.bzfx.bean.bo.*;
import com.cardinal.tech.bzfx.entity.RhTaskDb;
import com.cardinal.tech.bzfx.enums.ErrEnumInterface;
import com.cardinal.tech.bzfx.enums.err.SysErrEnum;
import com.cardinal.tech.bzfx.service.RhTaskDbService;
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
 * 任务数据-数据库(RhTaskDb)表服务api接口
 *
 * @author makejava
 * @since 2021-11-25 17:01:32
 */
@Tag(name="任务数据-数据库")
@RequestMapping("/rhTaskDb")
public interface RhTaskDbApi {

        RhTaskDbService getService();

        @PreAuthorize("permitAll()")
        @Operation(description = " get by id")
        @GetMapping("/{id}")
        default Response<RhTaskDb> queryById(@PathVariable("id") Long id){
            return new Response(getService().queryById(id));
        }

        @PreAuthorize("permitAll()")
        @Operation(description = " get list")
        @GetMapping("/list")
       default Response<List<RhTaskDb>> queryAllByLimit(@RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer limit){
            return new Response(getService().queryAllByLimit(offset,limit));
       }

        @PreAuthorize("permitAll()")
        @Operation(description = " add")
        @PostMapping("/add")
        default Response<RhTaskDb> insert(@RequestBody RhTaskDb rhTaskDb){
            return new Response(getService().insert(rhTaskDb));
        }

         @PreAuthorize("permitAll()")
         @Operation(description = " update")
         @PostMapping("/update")
        default Response<RhTaskDb> update(@RequestBody RhTaskDb rhTaskDb){
             return new Response(getService().update(rhTaskDb));
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
       default Response<List<Map<String,Integer>>> groupById(@RequestParam String field){
            return new Response(getService().groupBy(field));
        }

        @PreAuthorize("permitAll()")
        @Operation(description = " page list")
        @PostMapping("/page")
        default Response<Page<RhTaskDb>> page(@RequestBody PageForm<RhTaskDb> userQueryForm) {
           return new Response(getService().page(userQueryForm));
        }


        @PreAuthorize("permitAll()")
        @Operation(description = "sync data")
        @PostMapping("/task/sync")
        default Response<Boolean> syncData(@RequestBody HashMap<String,Long> body){
            Long taskId = body.get("id");
            Boolean flag = getService().syncData(taskId);
            if (flag){
                return new Response(SysErrEnum.SUCCESS);
            }
            return new Response(SysErrEnum.SYNC_PROGRESS);
        }


    @PreAuthorize("permitAll()")
    @Operation(description = "statistics data")
    @GetMapping("/statistics")
    default Response<Boolean> statistics(){
        getService().statistics();

        return new Response(SysErrEnum.SUCCESS);
    }

    @PreAuthorize("permitAll()")
    @Operation(description = "CONNECTION TEST")
    @GetMapping("/isConnection")
    default Response<Boolean> isConnection(@RequestParam Long id){
        Boolean flag = getService().isConnection(id);
        if (flag){
            return new Response(SysErrEnum.SUCCESS);
        }
        return new Response(SysErrEnum.DB_CONNECTION_FAIL);
    }
}