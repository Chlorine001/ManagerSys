package com.chlorine.water.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chlorine.water.common.core.PageResult;
import com.chlorine.water.common.core.R;
import com.chlorine.water.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 班级管理
 */
@RestController
@RequestMapping("/class")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;

    /** 分页查询（可按班级号/备注模糊查询） */
    @GetMapping("/page")
    public R<PageResult<com.chlorine.water.entity.Class>> page(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String info,
            @RequestParam(defaultValue = "1") long current,
            @RequestParam(defaultValue = "10") long size) {
        LambdaQueryWrapper<com.chlorine.water.entity.Class> qw = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(name)) qw.like(com.chlorine.water.entity.Class::getName, name);
        if (StringUtils.hasText(info)) qw.like(com.chlorine.water.entity.Class::getInfo, info);
        qw.orderByAsc(com.chlorine.water.entity.Class::getId);

        IPage<com.chlorine.water.entity.Class> page = classService.page(new Page<>(current, size), qw);
        return R.ok(new PageResult<>(page.getRecords(), page.getTotal(),
                page.getSize(), page.getCurrent(), page.getPages()));
    }

    /** 列表（下拉用） */
    @GetMapping("/list")
    public R<java.util.List<com.chlorine.water.entity.Class>> list() {
        return R.ok(classService.list(new LambdaQueryWrapper<com.chlorine.water.entity.Class>()
                .orderByAsc(com.chlorine.water.entity.Class::getId)));
    }

    /** 新增 */
    @PostMapping
    public R<com.chlorine.water.entity.Class> add(@RequestBody com.chlorine.water.entity.Class entity) {
        entity.setId(null);
        classService.save(entity);
        return R.ok(entity);
    }

    /** 编辑 */
    @PutMapping
    public R<Void> update(@RequestBody com.chlorine.water.entity.Class entity) {
        classService.updateById(entity);
        return R.ok();
    }

    /** 删除 */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        classService.removeById(id);
        return R.ok();
    }
}
