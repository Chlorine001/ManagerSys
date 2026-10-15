package com.chlorine.water.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chlorine.water.common.core.PageResult;
import com.chlorine.water.common.core.R;
import com.chlorine.water.entity.Role;
import com.chlorine.water.entity.RoleMenu;
import com.chlorine.water.mapper.RoleMapper;
import com.chlorine.water.mapper.RoleMenuMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chlorine
 * @version 1.0
 * @Date 2026-10-15
 * @Description
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;

    /** 分页查询 */
    @GetMapping("/page")
    public R<IPage<Role>> page(@RequestParam(required = false) String name,
                               @RequestParam(defaultValue = "1") long current,
                               @RequestParam(defaultValue = "10") long size) {
        LambdaQueryWrapper<Role> qw = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(name)) {
            qw.like(Role::getName, name);
        }
        qw.orderByAsc(Role::getId);

        return R.ok(roleMapper.selectPage(new Page<>(current, size), qw));
    }

    /** 新增 */
    @PostMapping
    public Role add(@RequestBody Role role) {
        role.setId(null);
        if (!StringUtils.hasText(role.getState())) {
            role.setState("1"); // 默认启用
        }
        roleMapper.insert(role);
        return role;
    }

    /** 编辑 */
    @PutMapping
    public void update(@RequestBody Role role) {
        roleMapper.updateById(role);
    }

    /** 单个删除（同时清理 role_menu 关联） */
    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@PathVariable Integer id) {
        roleMenuMapper.delete(new LambdaQueryWrapper<RoleMenu>()
                .eq(RoleMenu::getRoleId, id));
        roleMapper.deleteById(id);
    }

    /** 批量禁用/启用 */
    @PutMapping("/state")
    public void changeState(@RequestBody Map<String, Object> body) {
        List<Integer> ids = (List<Integer>) body.get("ids");
        String state = (String) body.get("state");
        Role update = new Role();
        update.setState(state);
        roleMapper.update(update, new LambdaQueryWrapper<Role>().in(Role::getId, ids));
    }

    /** 查询角色已分配的菜单 id 列表 */
    @GetMapping("/{id}/menus")
    public List<Integer> getMenuIds(@PathVariable Integer id) {
        return roleMenuMapper.selectList(new LambdaQueryWrapper<RoleMenu>()
                        .eq(RoleMenu::getRoleId, id))
                .stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
    }

    /** 保存角色菜单权限（全量覆盖） */
    @PutMapping("/{id}/menus")
    @Transactional
    public void saveMenus(@PathVariable Integer id, @RequestBody List<Integer> menuIds) {
        roleMenuMapper.delete(new LambdaQueryWrapper<RoleMenu>()
                .eq(RoleMenu::getRoleId, id));
        if (menuIds != null) {
            menuIds.stream().distinct().forEach(menuId -> {
                RoleMenu rm = new RoleMenu();
                rm.setRoleId(id);
                rm.setMenuId(menuId);
                roleMenuMapper.insert(rm);
            });
        }
    }
}