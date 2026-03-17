package com.chlorine.water.controller;

import com.chlorine.water.entity.Menu;
import com.chlorine.water.entity.User;
import com.chlorine.water.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuMapper menuMapper;

    @GetMapping("/list")
    public Map<String, Object> listAll() {
        List<Menu> list = menuMapper.selectList(null);
        Map<String, Object> res = new HashMap<>();
        res.put("ok", true);
        res.put("data", list);
        return res;
    }
    @PostMapping("/user")
    public Map<String, Object> listByUser(@RequestBody User user) {
        String userName = user.getName();
        Integer role = user.getRole();
        
        // 管理员：直接给全部菜单
        List<Menu> menus;
        if ("admin".equalsIgnoreCase(userName) || (role != null && role == 0)) {
            menus = menuMapper.selectList(null);
        } else {
            menus = menuMapper.selectMenusByUsername(userName);
        }
        Map<String, Object> res = new HashMap<>();
        res.put("ok", true);
        res.put("data", menus);
        return res;
    }

}

