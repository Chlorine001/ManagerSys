package com.chlorine.water.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chlorine.water.common.core.PageResult;
import com.chlorine.water.common.core.R;
import com.chlorine.water.entity.LoginInfo;
import com.chlorine.water.service.LoginInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 登录记录
 */
@RestController
@RequestMapping("/login_info")
@RequiredArgsConstructor
public class LoginInfoController {

    private final LoginInfoService loginInfoService;

    @GetMapping("/page")
    public R<PageResult<LoginInfo>> page(@RequestParam(required = false) Integer userId,
                                         @RequestParam(defaultValue = "1") long current,
                                         @RequestParam(defaultValue = "10") long size) {
        LambdaQueryWrapper<LoginInfo> qw = new LambdaQueryWrapper<>();
        if (userId != null) qw.eq(LoginInfo::getUserId, userId);
        qw.orderByDesc(LoginInfo::getLoginTime);

        IPage<LoginInfo> page = loginInfoService.page(new Page<>(current, size), qw);
        return R.ok(new PageResult<>(page.getRecords(), page.getTotal(),
                page.getSize(), page.getCurrent(), page.getPages()));
    }
}
