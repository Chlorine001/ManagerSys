package com.chlorine.water.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chlorine.water.common.core.PageResult;
import com.chlorine.water.common.core.R;
import com.chlorine.water.entity.View;
import com.chlorine.water.service.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 文件查看记录
 */
@RestController
@RequestMapping("/view")
@RequiredArgsConstructor
public class ViewController {

    private final ViewService viewService;

    @GetMapping("/page")
    public R<PageResult<View>> page(@RequestParam(required = false) Integer fileId,
                                    @RequestParam(required = false) Integer userId,
                                    @RequestParam(defaultValue = "1") long current,
                                    @RequestParam(defaultValue = "10") long size) {
        LambdaQueryWrapper<View> qw = new LambdaQueryWrapper<>();
        if (fileId != null) qw.eq(View::getFileId, fileId);
        if (userId != null) qw.eq(View::getUserId, userId);
        qw.orderByDesc(View::getTime);

        IPage<View> page = viewService.page(new Page<>(current, size), qw);
        return R.ok(new PageResult<>(page.getRecords(), page.getTotal(),
                page.getSize(), page.getCurrent(), page.getPages()));
    }

    /** 记录一次查看 */
    @PostMapping
    public R<View> add(@RequestBody View entity) {
        entity.setId(null);
        if (entity.getTime() == null) entity.setTime(new Date());
        viewService.save(entity);
        return R.ok(entity);
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        viewService.removeById(id);
        return R.ok();
    }
}
