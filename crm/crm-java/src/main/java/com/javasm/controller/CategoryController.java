package com.javasm.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javasm.common.http.AxiosResult;
import com.javasm.common.perm.HasPerm;
import com.javasm.controller.base.BaseController;
import com.javasm.domin.entity.Category;

import com.javasm.domin.vo.CategoryVo;
import com.javasm.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor//和@Autowired效果一样
public class CategoryController extends BaseController {


    private final CategoryService categoryService;

    /**
     *查询所有 树状列表
     */
    @GetMapping
    public AxiosResult<List<CategoryVo>> list(@RequestParam(defaultValue = "1") int currentPage,@RequestParam(defaultValue = "5") int pageSize){
        PageHelper.startPage(currentPage,pageSize);
        List<CategoryVo> list = categoryService.buildTree();
        PageInfo pageInfo = new PageInfo(list);
        long total = pageInfo.getTotal();
        System.out.println(list);
        System.out.println(total);
        return AxiosResult.success(list);
    }

    /**
     * 根据id查询
     */
    @GetMapping("{id}")
    public AxiosResult<Category> findById(@PathVariable Long id){
        Category byId = categoryService.getById(id);
        return AxiosResult.success(byId);
    }
    /**
     *添加
     */
    @PostMapping
    @HasPerm(perm = "category:add")
    public AxiosResult<Void> add(@RequestBody  Category Category){
        return toAxios(categoryService.save(Category));
    }

    /**
     * 修改
     */
    @PutMapping
    @HasPerm(perm = "category:edit")
    public AxiosResult<Void> update(@RequestBody  Category Category){
        return toAxios(categoryService.update(Category));
    }

    /**
     * 删除  以及删除下面的子项
     */
    @DeleteMapping("{id}")
    public AxiosResult<Void> delete(@PathVariable  Long id){

        int row=categoryService.deleSelfAndChildrenById(id);
        return toAxios(row);
    }


    /**
     * 返回联机选择框    信息
     */
    @GetMapping("selectTree")
    public AxiosResult<List<CategoryVo>> getSelectTree(){
        List<CategoryVo> list= categoryService.getSelectTree();
        return AxiosResult.success(list);
    }

    /**
     * 通过父id拿到 分类
     */
    @GetMapping("findByPid")
    public AxiosResult<List<CategoryVo>> findByPid(long parentId){
        List<CategoryVo> list=categoryService.getByParentId(parentId);
        return AxiosResult.success(list);
    }


}
