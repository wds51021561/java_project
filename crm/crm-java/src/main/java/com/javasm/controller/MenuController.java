package com.javasm.controller;

import com.javasm.common.http.AxiosResult;
import com.javasm.common.page.PageResult;
import com.javasm.common.perm.HasPerm;
import com.javasm.controller.base.BaseController;
import com.javasm.domin.criteria.MenuCriteria;
import com.javasm.domin.entity.Menu;

import com.javasm.domin.vo.MenuVo;
import com.javasm.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("menu")
@RequiredArgsConstructor
public class MenuController  extends BaseController {

    private final MenuService menuService;



    /**
     * 分页条件查询
     */
    @GetMapping
    public AxiosResult<PageResult<MenuVo>> list(MenuCriteria menuCriteria) {
        return AxiosResult.success(menuService.getMenuTree(menuCriteria));
    }

    /**
     * 查询所有菜单的Tree
     *
     * @return
     */
    @GetMapping("tree")
    public AxiosResult<List<MenuVo>> getMenuTree() {
        List<MenuVo> rootfolder = new ArrayList<>();
        List<MenuVo> list = menuService.getAllMenuTree();
        MenuVo menuVo = new MenuVo();
        menuVo.setId(0L);
        menuVo.setMenuTitle("主目录");
        menuVo.setChildren(list);
        rootfolder.add(menuVo);
        return AxiosResult.success(rootfolder);
    }




    /**
     * 添加功能
     */

    @PostMapping
    @HasPerm(perm = "menu:add")
    public AxiosResult<Void> add(@RequestBody Menu menu) {
        System.out.println(menu);
        return toAxios(menuService.save(menu));
    }


    /**
     * 修改功能
     */

    @PutMapping
    @HasPerm(perm = "menu:edit")
    public AxiosResult<Void> update(@RequestBody Menu menu) {
        return toAxios(menuService.update(menu));
    }


    @GetMapping("{id}")
    public AxiosResult<Menu> findById(@PathVariable Long id) {
        return AxiosResult.success(menuService.getById(id));
    }


    @DeleteMapping("{id}")
    @HasPerm(perm = "dept:delete")
    public AxiosResult<Void> deleteById(@PathVariable Long id) {
        return toAxios(menuService.deleteById(id));
    }

}
