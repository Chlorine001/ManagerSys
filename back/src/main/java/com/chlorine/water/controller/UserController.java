package com.chlorine.water.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chlorine.water.common.core.PageResult;
import com.chlorine.water.common.core.R;
import com.chlorine.water.entity.User;
import com.chlorine.water.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 查询所有数据（脱敏：password/idNumber 不输出）
    @GetMapping
    public R<List<User>> findAll() {
        return R.ok(userService.list());
    }

    @PostMapping("/register")
    public R<User> register(@RequestBody User user) {
        return R.ok(userService.registerUser(user));
    }

    // 删除
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        userService.removeById(id);
        return R.ok();
    }

    // 更新（脱敏：password/idNumber 不参与输出；为空不覆盖密码）
    @PutMapping
    public R<User> update(@RequestBody User user) {
        User exist = userService.getById(user.getId());
        if (exist == null) {
            return R.fail(1001, "用户未找到");
        }
        // 密码留空表示不修改
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            user.setPassword(exist.getPassword());
        }
        userService.updateById(user);
        return R.ok(userService.getById(user.getId()));
    }

    // 分页查询
    @GetMapping("/page")
    public R<PageResult<User>> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                        @RequestParam(defaultValue = "10") Integer pageSize) {
        IPage<User> page = userService.page(new Page<>(pageNum, pageSize));
        PageResult<User> result = new PageResult<>(
                page.getRecords(), page.getTotal(),
                page.getSize(), page.getCurrent(), page.getPages()
        );
        return R.ok(result);
    }

    // 单个查询
    @GetMapping("/{id}")
    public R<User> getById(@PathVariable Integer id) {
        return R.ok(userService.getById(id));
    }
}
