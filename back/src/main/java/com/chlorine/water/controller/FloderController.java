package com.chlorine.water.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chlorine.water.common.core.R;
import com.chlorine.water.entity.Floder;
import com.chlorine.water.service.FloderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 目录管理（树形）
 */
@RestController
@RequestMapping("/floder")
@RequiredArgsConstructor
public class FloderController {

    private final FloderService floderService;

    /** 目录树（全量，前端按 pid 组装） */
    @GetMapping("/list")
    public R<List<Floder>> list(@RequestParam(required = false) Integer userId) {
        LambdaQueryWrapper<Floder> qw = new LambdaQueryWrapper<>();
        if (userId != null) qw.eq(Floder::getUserId, userId);
        qw.orderByAsc(Floder::getId);
        return R.ok(floderService.list(qw));
    }

    @PostMapping
    public R<Floder> add(@RequestBody Floder entity) {
        entity.setId(null);
        if (entity.getCreateTime() == null) entity.setCreateTime(new Date());
        floderService.save(entity);
        return R.ok(entity);
    }

    @PutMapping
    public R<Void> update(@RequestBody Floder entity) {
        floderService.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        floderService.removeById(id);
        return R.ok();
    }
}
