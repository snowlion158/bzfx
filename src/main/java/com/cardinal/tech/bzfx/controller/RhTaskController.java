package com.cardinal.tech.bzfx.controller;

import com.cardinal.tech.bzfx.service.RhTaskService;
import com.cardinal.tech.bzfx.api.RhTaskApi;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

/**
 * 任务(RhTask)表控制层
 *
 * @author cadinal.tech
 * @since 2021-11-13 20:00:37
 */
@RequiredArgsConstructor
@RestController
public class RhTaskController implements RhTaskApi{

    private final RhTaskService rhTaskService;

    @Override
    public RhTaskService getService(){
        return rhTaskService;
     }
}