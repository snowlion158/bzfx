package com.cardinal.tech.bzfx.api;

import com.cardinal.tech.bzfx.bean.bo.*;
import com.cardinal.tech.bzfx.entity.BzkTabDanweijbxx;
import com.cardinal.tech.bzfx.service.BzkTabDanweijbxxService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 单位基本信息(BzkTabDanweijbxx)表服务api接口
 *
 * @author makejava
 * @since 2021-11-20 16:06:13
 */
@Tag(name = "单位基本信息")
@RequestMapping("/bzkTabDanweijbxx")
public interface BzkTabDanweijbxxApi extends BaseApi {

    BzkTabDanweijbxxService getService();

    int API_ID = 7;

    @PreAuthorize("permitAll()")
    @Operation(description = " get by id")
    @GetMapping("/{id}")
    default Response<BzkTabDanweijbxx> queryById(@PathVariable("id") String id) {
        return new Response(getService().queryById(id));
    }

    @PreAuthorize("permitAll()")
    @Operation(description = " get list")
    @GetMapping("/list")
    default Response<List<BzkTabDanweijbxx>> queryAllByLimit(@RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer limit) {
        return new Response(getService().queryAllByLimit(offset, limit));
    }

    @PreAuthorize("permitAll()")
    @Operation(description = " add")
    @PostMapping("/add")
    default Response<BzkTabDanweijbxx> insert(@RequestBody BzkTabDanweijbxx bzkTabDanweijbxx) {
        return new Response(getService().insert(bzkTabDanweijbxx));
    }

    @PreAuthorize("permitAll()")
    @Operation(description = " update")
    @PostMapping("/update")
    default Response<BzkTabDanweijbxx> update(@RequestBody BzkTabDanweijbxx bzkTabDanweijbxx) {
        return new Response(getService().update(bzkTabDanweijbxx));
    }

    @PreAuthorize("permitAll()")
    @Operation(description = " delete by pk")
    @GetMapping("/delete")
    default Response<Boolean> deleteById(@RequestParam String id) {
        return new Response(getService().deleteById(id));
    }

    @PreAuthorize("permitAll()")
    @Operation(description = " group by field name")
    @GetMapping("/group")
    default Response<List<Map<String, Integer>>> groupById(@RequestParam String field) {
        return new Response(getService().groupBy(field));
    }

    @PreAuthorize("permitAll()")
    @Operation(description = " page list")
    @PostMapping("/page")
    default Response<Page<BzkTabDanweijbxx>> page(@RequestBody PageForm<BzkTabDanweijbxx> userQueryForm) {
        return new Response(getService().page(userQueryForm));
    }

    @PreAuthorize("permitAll()")
    @Operation(description = " page list")
    @PostMapping("/page_dict")
    default Response<Page<BzkTabDanweijbxx>> pageDict(@RequestBody PageForm<BzkTabDanweijbxx> userQueryForm) {
        return new Response(getService().pageDict(userQueryForm));
    }

    @PreAuthorize("hasRole('access_api')")
    @Operation(description = " page list")
    @PostMapping("/pageList")
    default Response<Page<BzkTabDanweijbxx>> pageList(@RequestBody PageForm<BzkTabDanweijbxx> userQueryForm) {
        if (!checkApiAccess(API_ID)) {
            throw new AccessDeniedException("不允许访问");
        }
        return new Response(getService().page(userQueryForm));
    }
}