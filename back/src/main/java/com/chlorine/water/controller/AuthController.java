package com.chlorine.water.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chlorine.water.common.core.R;
import com.chlorine.water.common.exception.BusinessException;
import com.chlorine.water.common.exception.ErrorCodes;
import com.chlorine.water.controller.dto.LoginRequest;
import com.chlorine.water.entity.Menu;
import com.chlorine.water.entity.User;
import com.chlorine.water.mapper.MenuMapper;
import com.chlorine.water.mapper.UserMapper;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final int ROLE_ADMIN = 1;

    private final UserMapper userMapper;
    private final MenuMapper menuMapper;

    @PostMapping("/login")
    public R<Map<String, Object>> login(@Valid @RequestBody LoginRequest req) {

        // ① 按用户名查用户（不拿密码一起查，避免密码进 SQL 日志 / 时序暴露）
        User user = userMapper.selectOne(
                new LambdaQueryWrapper<User>().eq(User::getName, req.getName())
        );

        // ② 统一提示"账号或密码错误"，不要分别提示"账号不存在"/"密码错误"（防撞库）
        if (user == null || !passwordMatches(req.getPassword(), user.getPassword())) {
            log.warn("登录失败 username={}", req.getName());
            throw new BusinessException(ErrorCodes.LOGIN_FAILED);
        }

        // ③ 查菜单
        List<Menu> menus = Objects.equals(user.getRoleId(), ROLE_ADMIN)
                ? menuMapper.selectList(null)
                : menuMapper.selectMenusByUsername(user.getName());

        // ④ 组装返回
        Map<String, Object> res = new HashMap<>();
        res.put("token", issueToken(user));
        res.put("user", toSafeUser(user));          // 脱敏！绝不含 password
        res.put("navroot", menus.stream()
                .map(this::toNavItem)
                .collect(Collectors.toList()));

        log.info("登录成功 userId={} username={}", user.getId(), user.getName());
        return R.ok(res);
    }

    /** 密码比对（当前按明文，改 BCrypt 时只动这里） */
    private boolean passwordMatches(String raw, String stored) {
        if (stored == null) return false;
        // TODO 生产环境改为: return BCrypt.checkpw(raw, stored);
        return Objects.equals(raw, stored);
    }

    /** 签发 token（当前 UUID，需配套存储/校验，见文末） */
    private String issueToken(User user) {

        return UUID.randomUUID().toString().replace("-", "");
//        redisTemplate.opsForValue().set("login:token:" + token, user.getId(), Duration.ofHours(24));
    }

    /** 用户脱敏，只暴露必要字段 */
    private Map<String, Object> toSafeUser(User user) {
        Map<String, Object> u = new LinkedHashMap<>();
        u.put("id", user.getId());
        u.put("name", user.getName());
        u.put("roleId", user.getRoleId());
        // 你的 User 还有什么非敏感字段（nickname、avatar…）按需补，但绝不能放 password
        return u;
    }

    /** 菜单转前端 navroot 结构 */
    private Map<String, Object> toNavItem(Menu m) {
        Map<String, Object> r = new LinkedHashMap<>();
        r.put("id", m.getId());
        r.put("url", m.getUrl());
        r.put("name", m.getName());
        r.put("icon", m.getIcon());
        r.put("pid", m.getPid());
        r.put("ishide", m.getIsHide());
        return r;
    }
}