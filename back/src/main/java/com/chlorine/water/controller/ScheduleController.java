package com.chlorine.water.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chlorine.water.common.core.PageResult;
import com.chlorine.water.common.core.R;
import com.chlorine.water.entity.Schedule;
import com.chlorine.water.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程安排关系管理
 */
@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/page")
    public R<PageResult<Schedule>> page(@RequestParam(required = false) Integer classId,
                                        @RequestParam(required = false) Integer courseId,
                                        @RequestParam(required = false) Integer userId,
                                        @RequestParam(defaultValue = "1") long current,
                                        @RequestParam(defaultValue = "10") long size) {
        LambdaQueryWrapper<Schedule> qw = new LambdaQueryWrapper<>();
        if (classId != null) qw.eq(Schedule::getClassId, classId);
        if (courseId != null) qw.eq(Schedule::getCourseId, courseId);
        if (userId != null) qw.eq(Schedule::getUserId, userId);
        qw.orderByAsc(Schedule::getId);

        IPage<Schedule> page = scheduleService.page(new Page<>(current, size), qw);
        return R.ok(new PageResult<>(page.getRecords(), page.getTotal(),
                page.getSize(), page.getCurrent(), page.getPages()));
    }

    @GetMapping("/list")
    public R<List<Schedule>> list(@RequestParam(required = false) Integer classId) {
        LambdaQueryWrapper<Schedule> qw = new LambdaQueryWrapper<>();
        if (classId != null) qw.eq(Schedule::getClassId, classId);
        return R.ok(scheduleService.list(qw));
    }

    @PostMapping
    public R<Schedule> add(@RequestBody Schedule entity) {
        entity.setId(null);
        scheduleService.save(entity);
        return R.ok(entity);
    }

    @PutMapping
    public R<Void> update(@RequestBody Schedule entity) {
        scheduleService.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        scheduleService.removeById(id);
        return R.ok();
    }
}
