package com.cardinal.tech.bzfx.controller;

import com.cardinal.tech.bzfx.bean.bo.Response;
import com.cardinal.tech.bzfx.service.RhTaskFileService;
import com.cardinal.tech.bzfx.api.RhTaskFileApi;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

/**
 * 任务数据-文件(RhTaskFile)表控制层
 *
 * @author cadinal.tech
 * @since 2021-11-14 14:09:12
 */
@RequiredArgsConstructor
@RestController
public class RhTaskFileController implements RhTaskFileApi{

    private final RhTaskFileService rhTaskFileService;

    @Override
    public RhTaskFileService getService(){
        return rhTaskFileService;
     }

}