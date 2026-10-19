package com.chlorine.water.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chlorine.water.common.core.R;
import com.chlorine.water.common.exception.BusinessException;
import com.chlorine.water.common.exception.ErrorCodes;
import com.chlorine.water.entity.Menu;
import com.chlorine.water.entity.User;
import com.chlorine.water.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuMapper menuMapper;

    @GetMapping("/list")
    public R<List<Menu>> listAll() {
        return R.ok(menuMapper.selectList(null));
    }

    /** 全部菜单（前端转树） */
    @GetMapping("/all")
    public List<Menu> allMenus() {
        return menuMapper.selectList(new LambdaQueryWrapper<Menu>().orderByAsc(Menu::getPid).orderByAsc(Menu::getId));
    }

    @PostMapping("/user")
    public R<List<Menu>> listByUser(@RequestBody User user) {
        Integer role = user.getRoleId();

        // 管理员：直接给全部菜单
        List<Menu> menus;
        if (role != null && role == 1) {
            menus = menuMapper.selectList(null);
        } else {
            menus = menuMapper.selectMenusByUsername(user.getName());
        }
        return R.ok(menus);
    }

    // ============ 新增 ============
    @PostMapping("/add")
    public R<Map<String, Object>> add(@RequestBody Menu menu) {
        if (isBlank(menu.getName()))
            throw new BusinessException(ErrorCodes.PARAM_INVALID, "标题不能为空");
        if (isBlank(menu.getUrl())||menu.getUrl().equals("/"))
            throw new BusinessException(ErrorCodes.PARAM_INVALID, "路径不能为空");


        if (menu.getPid() == null) menu.setPid(0);

        if (menu.getPid() != 0) {
            Menu parent = menuMapper.selectById(menu.getPid());
            if (parent == null)   throw new BusinessException(ErrorCodes.MENU_PARENT_NOT_FOUND);
        }

        Long count = menuMapper.countByPidAndUrl(menu.getPid(), menu.getUrl(), null);
        if (count != null && count > 0) {
            throw new BusinessException(ErrorCodes.MENU_URL_DUPLICATE,
                    "同级下已存在相同的路径：" + menu.getUrl());
        }

        if (menu.getIsHide() == null) menu.setIsHide(false);
        if (menu.getIsLeaf() == null) menu.setIsLeaf(true);

        menuMapper.insert(menu);

        updateParentLeaf(menu.getPid());
        return R.ok(null);
    }

    // ============ 编辑 ============
    @PostMapping("/update")
    public R<Map<String, Object>> update(@RequestBody Menu menu) {
        if (menu.getId() == null)
            throw new BusinessException(ErrorCodes.PARAM_INVALID, "缺少 id");

        Menu exist = menuMapper.selectById(menu.getId());
        if (exist == null)
            throw new BusinessException(ErrorCodes.MENU_NOT_FOUND);

        if (isBlank(menu.getName()))
            throw new BusinessException(ErrorCodes.PARAM_INVALID, "标题不能为空");
        if (isBlank(menu.getUrl())||menu.getUrl().equals("/"))
            throw new BusinessException(ErrorCodes.PARAM_INVALID, "路径不能为空");

        if (menu.getPid() == null) menu.setPid(0);

        if (menu.getId().equals(menu.getPid()))
            throw new BusinessException(ErrorCodes.MENU_SELF_AS_PARENT);

        if (menu.getPid() != null && isDescendant(menu.getId(), menu.getPid()))
            throw new BusinessException(ErrorCodes.MENU_PARENT_IS_DESCENDANT);

        Long count = menuMapper.countByPidAndUrl(menu.getPid(), menu.getUrl(), menu.getId());
        if (count != null && count > 0)
            throw new BusinessException(ErrorCodes.MENU_URL_DUPLICATE,
                    "同级下已存在相同的路径：" + menu.getUrl());
        menuMapper.updateById(menu);

        if (!Objects.equals(menu.getPid(), exist.getPid())) {
            updateParentLeaf(exist.getPid());
        }
        updateParentLeaf(menu.getPid());

        return R.ok(null);
    }

    // ============ 删除单个 ============
    @PostMapping("/delete")
    public R<Map<String, Object>> delete(@RequestBody Menu menu) {
        if (menu.getId() == null)
            throw new BusinessException(ErrorCodes.PARAM_INVALID, "缺少 id");

        Menu exist = menuMapper.selectById(menu.getId());
        if (exist == null)
            throw new BusinessException(ErrorCodes.MENU_NOT_FOUND);

        Long childCount = menuMapper.countByPid(menu.getId());
        if (childCount != null && childCount > 0)
            throw new BusinessException(ErrorCodes.MENU_HAS_CHILDREN);

        menuMapper.deleteById(menu.getId());
        updateParentLeaf(exist.getPid());
        return R.ok(null);
    }

    // ============ 批量删除 ============
    @PostMapping("/batchDelete")
    public R<Map<String, Object>> batchDelete(@RequestBody Map<String, Object> body) {
        Object idsObj = body.get("ids");
        if (!(idsObj instanceof List) || ((List<?>) idsObj).isEmpty())
            throw new BusinessException(ErrorCodes.PARAM_INVALID, "请选择要删除的菜单");

        @SuppressWarnings("unchecked")
        List<Object> rawIds = (List<Object>) idsObj;

        Set<Integer> pidSet = new HashSet<>();
        List<Integer> idList = new ArrayList<>();

        for (Object o : rawIds) {
            Integer id;
            try {
                id = Integer.valueOf(String.valueOf(o));
            } catch (NumberFormatException e) {
                continue;
            }
            idList.add(id);

            Menu exist = menuMapper.selectById(id);
            if (exist == null) continue;

            Long childCount = menuMapper.countByPid(id);
            if (childCount != null && childCount > 0)
                throw new BusinessException(ErrorCodes.MENU_HAS_CHILDREN,
                        "菜单「" + exist.getName() + "」下有子菜单，请先删除");

            if (exist.getPid() != null) pidSet.add(exist.getPid());
        }

        if (idList.isEmpty())
            throw new BusinessException(ErrorCodes.PARAM_INVALID, "没有可删除的菜单");

        menuMapper.deleteBatchIds(idList);

        for (Integer pid : pidSet) {
            updateParentLeaf(pid);
        }
        return R.ok(null);
    }

    // ============ 工具 ============

    private void updateParentLeaf(Integer pid) {
        //更新叶子节点
        if (pid == null || pid == 0) return;
        Menu parent = menuMapper.selectById(pid);
        if (parent == null) return;

        Long childCount = menuMapper.countByPid(pid);
        parent.setIsLeaf(childCount == null || childCount == 0);
        menuMapper.updateById(parent);
    }

    private boolean isDescendant(Integer id, Integer candidatePid) {
        //不能把父级设为自己的下级
        Integer cur = candidatePid;
        int depth = 0;
        while (cur != null && cur != 0 && depth < 100) {
            if (cur.equals(id)) return true;
            Menu parent = menuMapper.selectById(cur);
            if (parent == null) return false;
            cur = parent.getPid();
            depth++;
        }
        return false;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
    
}