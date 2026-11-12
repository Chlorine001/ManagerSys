package com.chlorine.water.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chlorine.water.common.core.R;
import com.chlorine.water.entity.FileStar;
import com.chlorine.water.service.FileStarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文件评分管理
 */
@RestController
@RequestMapping("/file_star")
@RequiredArgsConstructor
public class FileStarController {

    private final FileStarService fileStarService;

    /** 某文件下所有评分 */
    @GetMapping("/list")
    public R<List<FileStar>> list(@RequestParam Integer fileId) {
        return R.ok(fileStarService.list(new LambdaQueryWrapper<FileStar>()
                .eq(FileStar::getFileId, fileId)));
    }

    /**
     * 提交/更新评分：同一用户对同一文件只保留一条（配合 DB 唯一约束 user_id+file_id）
     * 已存在则更新，不存在则新增
     */
    @PostMapping
    public R<FileStar> save(@RequestBody FileStar entity) {
        FileStar exist = fileStarService.getOne(new LambdaQueryWrapper<FileStar>()
                .eq(FileStar::getUserId, entity.getUserId())
                .eq(FileStar::getFileId, entity.getFileId()));
        if (exist != null) {
            exist.setStar(entity.getStar());
            fileStarService.updateById(exist);
            return R.ok(exist);
        }
        entity.setId(null);
        fileStarService.save(entity);
        return R.ok(entity);
    }

    /** 某文件的平均评分 */
    @GetMapping("/avg")
    public R<Double> avg(@RequestParam Integer fileId) {
        List<FileStar> list = fileStarService.list(new LambdaQueryWrapper<FileStar>()
                .eq(FileStar::getFileId, fileId));
        if (list.isEmpty()) return R.ok(0.0);
        double avg = list.stream().mapToDouble(s -> s.getStar() == null ? 0 : s.getStar()).average().orElse(0);
        return R.ok(avg);
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        fileStarService.removeById(id);
        return R.ok();
    }
}
