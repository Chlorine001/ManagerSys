package com.chlorine.water.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chlorine.water.entity.Menu;
import com.chlorine.water.entity.User;
import com.chlorine.water.mapper.MenuMapper;
import com.chlorine.water.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, Object> body) {
        String username = body.get("name") == null ? null : String.valueOf(body.get("name"));
        String password = body.get("password") == null ? null : String.valueOf(body.get("password"));

        if (username == null || username.trim().isEmpty()) {
            throw new RuntimeException("账号不能为空");
        }
        if (password == null) {
            throw new RuntimeException("密码不能为空");
        }

        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("name", username).eq("password", password);
        User user = userMapper.selectOne(qw);
        if (user == null) {
            throw new RuntimeException("账号或密码错误");
        }

        List<Menu> menus;
        // 管理员：直接给全部菜单
        if (user.getRole() != null && user.getRole() == 0) {
            menus = menuMapper.selectList(null);
        } else {
            menus = menuMapper.selectMenusByUsername(username);
        }

        // 前端需要的 navRoot 结构：[{path, root:[], dicts:[]}]
        List<Map<String, Object>> navRoot = menus.stream()
                .map(m -> {
                    Map<String, Object> r = new HashMap<>();
                    r.put("id", m.getId());
                    r.put("url", m.getUrl());
                    r.put("name", m.getName());
                    r.put("icon", m.getIcon());
                    r.put("pid", m.getPid());
                    return r;
                })
                .collect(Collectors.toList());

        Map<String, Object> res = new HashMap<>();
        res.put("ok", true);
        res.put("token", UUID.randomUUID().toString().replace("-", ""));
        res.put("user", user);
        res.put("navroot", navRoot);
        return res;
    }

}

