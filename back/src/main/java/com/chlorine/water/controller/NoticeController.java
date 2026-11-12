package com.chlorine.water.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chlorine.water.common.core.PageResult;
import com.chlorine.water.common.core.R;
import com.chlorine.water.entity.Notice;
import com.chlorine.water.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公告管理
 */
@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/page")
    public R<PageResult<Notice>> page(@RequestParam(required = false) String title,
                                      @RequestParam(defaultValue = "1") long current,
                                      @RequestParam(defaultValue = "10") long size) {
        LambdaQueryWrapper<Notice> qw = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(title)) qw.like(Notice::getTitle, title);
        qw.orderByDesc(Notice::getPublishTime);

        IPage<Notice> page = noticeService.page(new Page<>(current, size), qw);
        return R.ok(new PageResult<>(page.getRecords(), page.getTotal(),
                page.getSize(), page.getCurrent(), page.getPages()));
    }

    /** 有效公告列表（未过期） */
    @GetMapping("/list")
    public R<List<Notice>> list() {
        java.util.Date now = new java.util.Date();
        return R.ok(noticeService.list(new LambdaQueryWrapper<Notice>()
                .and(w -> w.isNull(Notice::getExpirationTime)
                        .or().ge(Notice::getExpirationTime, now))
                .orderByDesc(Notice::getPublishTime)));
    }

    @PostMapping
    public R<Notice> add(@RequestBody Notice entity) {
        entity.setId(null);
        if (entity.getPublishTime() == null) entity.setPublishTime(new java.util.Date());
        noticeService.save(entity);
        return R.ok(entity);
    }

    @PutMapping
    public R<Void> update(@RequestBody Notice entity) {
        noticeService.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        noticeService.removeById(id);
        return R.ok();
    }
}
