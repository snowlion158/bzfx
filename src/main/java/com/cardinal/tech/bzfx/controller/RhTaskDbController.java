package com.cardinal.tech.bzfx.controller;

import com.cardinal.tech.bzfx.bean.bo.Response;
import com.cardinal.tech.bzfx.service.RhTaskDbService;
import com.cardinal.tech.bzfx.api.RhTaskDbApi;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

/**
 * 任务数据-数据库(RhTaskDb)表控制层
 *
 * @author cadinal.tech
 * @since 2021-11-13 20:00:37
 */
@RequiredArgsConstructor
@RestController
public class RhTaskDbController implements RhTaskDbApi{

    private final RhTaskDbService rhTaskDbService;

    @Override
    public RhTaskDbService getService(){
        return rhTaskDbService;
     }

}