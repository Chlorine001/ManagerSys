package com.chlorine.water.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chlorine.water.common.core.PageResult;
import com.chlorine.water.common.core.R;
import com.chlorine.water.entity.Download;
import com.chlorine.water.service.DownloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 下载记录
 */
@RestController
@RequestMapping("/download")
@RequiredArgsConstructor
public class DownloadController {

    private final DownloadService downloadService;

    @GetMapping("/page")
    public R<PageResult<Download>> page(@RequestParam(required = false) Integer fileId,
                                        @RequestParam(required = false) Integer userId,
                                        @RequestParam(defaultValue = "1") long current,
                                        @RequestParam(defaultValue = "10") long size) {
        LambdaQueryWrapper<Download> qw = new LambdaQueryWrapper<>();
        if (fileId != null) qw.eq(Download::getFileId, fileId);
        if (userId != null) qw.eq(Download::getUserId, userId);
        qw.orderByDesc(Download::getTime);

        IPage<Download> page = downloadService.page(new Page<>(current, size), qw);
        return R.ok(new PageResult<>(page.getRecords(), page.getTotal(),
                page.getSize(), page.getCurrent(), page.getPages()));
    }

    @PostMapping
    public R<Download> add(@RequestBody Download entity) {
        entity.setId(null);
        if (entity.getTime() == null) entity.setTime(new Date());
        downloadService.save(entity);
        return R.ok(entity);
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        downloadService.removeById(id);
        return R.ok();
    }
}
