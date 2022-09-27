package com.javasm.controller;

import com.github.pagehelper.PageInfo;
import com.javasm.common.http.AxiosResult;
import com.javasm.common.page.PageResult;
import com.javasm.common.perm.HasPerm;
import com.javasm.controller.base.BaseController;
import com.javasm.domin.criteria.GoodCriteria;
import com.javasm.domin.entity.Good;
import com.javasm.domin.vo.GoodVo;
import com.javasm.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("good")
@RequiredArgsConstructor//和@Autowired效果一样
public class GoodController extends BaseController {


    private final GoodService goodService;

    /**
     *查询所有
     */
    @GetMapping
    public AxiosResult<PageResult<GoodVo>> list(GoodCriteria goodCriteria){


        PageResult<GoodVo> goodVoPageResult = goodService.searchPage(goodCriteria);

        return AxiosResult.success(goodVoPageResult);
    }

    /**
     * 根据id查询
     */
    @GetMapping("{id}")
    public AxiosResult<Good> findById(@PathVariable Long id){
        Good byId = goodService.getById(id);
        return AxiosResult.success(byId);
    }
    /**
     *添加
     */
    @PostMapping
    @HasPerm(perm = "good:add")
    public AxiosResult<Void> add(@RequestBody  Good Good){
        return toAxios(goodService.save(Good));
    }

    /**
     * 修改
     */
    @PutMapping
    @HasPerm(perm = "good:edit")
    public AxiosResult<Void> update(@RequestBody  Good Good){
        return toAxios(goodService.update(Good));
    }

    /**
     * 删除
     */
    @DeleteMapping("{id}")
    @HasPerm(perm = "good:delete")
    public AxiosResult<Void> delete(@PathVariable  Long id){
        return toAxios(goodService.deleteById(id));
    }


    @DeleteMapping("batch/{ids}")
    @HasPerm(perm = "good:batch")
    public AxiosResult<Void> batchDeleteByIds(@PathVariable List<Long> ids) {
        return toAxios(goodService.batchDeleteByIds(ids));
    }


}
