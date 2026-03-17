package com.chlorine.water.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chlorine.water.entity.User;
import com.chlorine.water.mapper.UserMapper;
import com.chlorine.water.service.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Getter
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    //查询所有数据
    @GetMapping
    public List<User> findAll() {
        return userService.list();
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    //删除
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return userService.removeById(id);
    }

    //分页查询
    //接口：/user/page
    // @RequestParam接收 ?pageNum=1&pageSize=3

//    @GetMapping("/page")
//    public Map<String, Object> findPage (@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
//        pageNum = (pageNum - 1) * pageSize;
//        List<User> data = userMapper.selectPage(pageNum, pageSize);
//        Integer total = userMapper.selectTotal();
//        Map<String, Object> res = new HashMap<>();
//        res.put("data", data);
//        res.put("total", total);
//        return res;
//    }

    //分页查询-MybatisPlus
    @GetMapping("/page")
    public IPage<User> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize) {
        IPage<User> page = new Page<>(pageNum, pageSize);
        return userService.page(page);
    }

}

//
//// UserController.java
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/users")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/register")
//    public User register(@RequestBody User user) {
//        return userService.registerUser(user);
//    }
//
//    @GetMapping("/{id}")
//    public User getUser(@PathVariable Long id) {
//        return userService.getUserById(id);
//    }
//
//    @PutMapping("/{id}/email")
//    public String updateEmail(@PathVariable Long id, @RequestParam String email) {
//        boolean success = userService.updateUserEmail(id, email);
//        return success ? "更新成功" : "用户不存在";
//    }
//}