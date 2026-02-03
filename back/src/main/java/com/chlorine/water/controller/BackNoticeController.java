package com.chlorine.water.controller;

import com.chlorine.water.entity.BackNotice;
import com.chlorine.water.mapper.BackNoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/back_notice", produces = "text/plain;charset=UTF-8")
public class BackNoticeController {

    @Autowired
    private BackNoticeMapper backNoticeMapper;


    @GetMapping("/text")
    public String getBackNotice() {
        List<BackNotice> backNotice = backNoticeMapper.getBackNotice();
        return backNotice.get(0).getReport().toString();
    }


}
