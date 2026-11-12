package com.chlorine.water.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chlorine.water.common.core.PageResult;
import com.chlorine.water.common.core.R;
import com.chlorine.water.entity.Flie;
import com.chlorine.water.service.FlieService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 文件管理
 */
@RestController
@RequestMapping("/flie")
@RequiredArgsConstructor
public class FlieController {

    private final FlieService flieService;

    @GetMapping("/page")
    public R<PageResult<Flie>> page(@RequestParam(required = false) Integer userId,
                                    @RequestParam(required = false) Integer floderId,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) String tag,
                                    @RequestParam(defaultValue = "1") long current,
                                    @RequestParam(defaultValue = "10") long size) {
        LambdaQueryWrapper<Flie> qw = new LambdaQueryWrapper<>();
        if (userId != null) qw.eq(Flie::getUserId, userId);
        if (floderId != null) qw.eq(Flie::getFloderId, floderId);
        if (StringUtils.hasText(name)) qw.like(Flie::getName, name);
        if (StringUtils.hasText(tag)) qw.like(Flie::getTag, tag);
        qw.orderByDesc(Flie::getUploadTime);

        IPage<Flie> page = flieService.page(new Page<>(current, size), qw);
        return R.ok(new PageResult<>(page.getRecords(), page.getTotal(),
                page.getSize(), page.getCurrent(), page.getPages()));
    }

    @GetMapping("/list")
    public R<List<Flie>> list(@RequestParam(required = false) Integer userId,
                              @RequestParam(required = false) Integer floderId) {
        LambdaQueryWrapper<Flie> qw = new LambdaQueryWrapper<>();
        if (userId != null) qw.eq(Flie::getUserId, userId);
        if (floderId != null) qw.eq(Flie::getFloderId, floderId);
        qw.orderByDesc(Flie::getUploadTime);
        return R.ok(flieService.list(qw));
    }

    @PostMapping
    public R<Flie> add(@RequestBody Flie entity) {
        entity.setId(null);
        if (entity.getUploadTime() == null) entity.setUploadTime(new Date());
        flieService.save(entity);
        return R.ok(entity);
    }

    @PutMapping
    public R<Void> update(@RequestBody Flie entity) {
        flieService.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        flieService.removeById(id);
        return R.ok();
    }
}
