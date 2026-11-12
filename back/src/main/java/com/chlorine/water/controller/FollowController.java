package com.chlorine.water.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chlorine.water.common.core.R;
import com.chlorine.water.entity.Follow;
import com.chlorine.water.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 关注管理
 */
@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    /** 我关注的人 id 列表 */
    @GetMapping("/following")
    public R<List<Integer>> following(@RequestParam Integer followerId) {
        List<Integer> ids = followService.list(new LambdaQueryWrapper<Follow>()
                        .eq(Follow::getFollowerId, followerId))
                .stream().map(Follow::getFollowingId).collect(Collectors.toList());
        return R.ok(ids);
    }

    /** 关注我的人 id 列表 */
    @GetMapping("/followers")
    public R<List<Integer>> followers(@RequestParam Integer followingId) {
        List<Integer> ids = followService.list(new LambdaQueryWrapper<Follow>()
                        .eq(Follow::getFollowingId, followingId))
                .stream().map(Follow::getFollowerId).collect(Collectors.toList());
        return R.ok(ids);
    }

    /** 是否已关注 */
    @GetMapping("/exists")
    public R<Boolean> exists(@RequestParam Integer followerId, @RequestParam Integer followingId) {
        long count = followService.count(new LambdaQueryWrapper<Follow>()
                .eq(Follow::getFollowerId, followerId)
                .eq(Follow::getFollowingId, followingId));
        return R.ok(count > 0);
    }

    /** 关注（幂等：已存在则不重复插入，配合 DB 唯一约束） */
    @PostMapping
    public R<Follow> follow(@RequestBody Follow entity) {
        long count = followService.count(new LambdaQueryWrapper<Follow>()
                .eq(Follow::getFollowerId, entity.getFollowerId())
                .eq(Follow::getFollowingId, entity.getFollowingId()));
        if (count > 0) {
            Follow exist = followService.getOne(new LambdaQueryWrapper<Follow>()
                    .eq(Follow::getFollowerId, entity.getFollowerId())
                    .eq(Follow::getFollowingId, entity.getFollowingId()));
            return R.ok(exist);
        }
        entity.setId(null);
        if (entity.getTime() == null) entity.setTime(new Date());
        followService.save(entity);
        return R.ok(entity);
    }

    /** 取消关注 */
    @DeleteMapping
    public R<Void> unfollow(@RequestParam Integer followerId, @RequestParam Integer followingId) {
        followService.remove(new LambdaQueryWrapper<Follow>()
                .eq(Follow::getFollowerId, followerId)
                .eq(Follow::getFollowingId, followingId));
        return R.ok();
    }
}
