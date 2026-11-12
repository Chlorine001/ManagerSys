package com.chlorine.water.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chlorine.water.common.core.R;
import com.chlorine.water.entity.FileComment;
import com.chlorine.water.service.FileCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 文件评论管理
 */
@RestController
@RequestMapping("/file_comment")
@RequiredArgsConstructor
public class FileCommentController {

    private final FileCommentService fileCommentService;

    /** 某文件下的评论列表（按时间正序，前端按 pid 组树） */
    @GetMapping("/list")
    public R<List<FileComment>> list(@RequestParam Integer fileId) {
        return R.ok(fileCommentService.list(new LambdaQueryWrapper<FileComment>()
                .eq(FileComment::getFileId, fileId)
                .orderByAsc(FileComment::getTime)));
    }

    /** 新增评论 */
    @PostMapping
    public R<FileComment> add(@RequestBody FileComment entity) {
        entity.setId(null);
        if (entity.getTime() == null) entity.setTime(new Date());
        if (entity.getLikes() == null) entity.setLikes(0);
        fileCommentService.save(entity);
        return R.ok(entity);
    }

    /** 点赞（likes + 1），并发下用原子更新避免丢赞 */
    @PostMapping("/{id}/like")
    @Transactional
    public R<Void> like(@PathVariable Integer id) {
        FileComment c = fileCommentService.getById(id);
        if (c == null) {
            return R.fail(4004, "评论不存在");
        }
        c.setLikes((c.getLikes() == null ? 0 : c.getLikes()) + 1);
        fileCommentService.updateById(c);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        fileCommentService.removeById(id);
        return R.ok();
    }
}
