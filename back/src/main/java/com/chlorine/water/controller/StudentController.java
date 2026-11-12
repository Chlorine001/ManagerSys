package com.chlorine.water.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chlorine.water.common.core.PageResult;
import com.chlorine.water.common.core.R;
import com.chlorine.water.entity.Student;
import com.chlorine.water.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学生-班级关联管理
 */
@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/page")
    public R<PageResult<Student>> page(@RequestParam(required = false) Integer classId,
                                       @RequestParam(defaultValue = "1") long current,
                                       @RequestParam(defaultValue = "10") long size) {
        LambdaQueryWrapper<Student> qw = new LambdaQueryWrapper<>();
        if (classId != null) qw.eq(Student::getClassId, classId);
        qw.orderByAsc(Student::getId);

        IPage<Student> page = studentService.page(new Page<>(current, size), qw);
        return R.ok(new PageResult<>(page.getRecords(), page.getTotal(),
                page.getSize(), page.getCurrent(), page.getPages()));
    }

    @GetMapping("/list")
    public R<List<Student>> list(@RequestParam(required = false) Integer classId) {
        LambdaQueryWrapper<Student> qw = new LambdaQueryWrapper<>();
        if (classId != null) qw.eq(Student::getClassId, classId);
        return R.ok(studentService.list(qw));
    }

    @PostMapping
    public R<Student> add(@RequestBody Student entity) {
        entity.setId(null);
        studentService.save(entity);
        return R.ok(entity);
    }

    @PutMapping
    public R<Void> update(@RequestBody Student entity) {
        studentService.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        studentService.removeById(id);
        return R.ok();
    }
}
