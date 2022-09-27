package com.javasm.controller;

import com.javasm.common.http.AxiosResult;
import com.javasm.common.page.PageResult;
import com.javasm.common.perm.HasPerm;
import com.javasm.controller.base.BaseController;
import com.javasm.domin.criteria.BrandCriteria;
import com.javasm.domin.entity.Brand;

import com.javasm.domin.entity.Brand1;
import com.javasm.domin.vo.BrandVo;
import com.javasm.service.BrandService;
import com.javasm.transfer.BrandTransfer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("brand")
@RequiredArgsConstructor//和@Autowired效果一样
public class BrandController extends BaseController {


    private final BrandService brandService;
    private final BrandTransfer brandTransfer;


    /**
     * 分页条件查询
     */
    @GetMapping
    public AxiosResult<PageResult<BrandVo>> list(BrandCriteria brandCriteria){
        //分页条件查询
        return AxiosResult.success(brandService.searchPage(brandCriteria));

    }

    /**
     * 查询所有的品牌
     */
//    @PostMapping("findAll")
    @RequestMapping("findAll")
    public AxiosResult<List<BrandVo>> findAll(String key ,String area){
        //查询所有的品牌
        List<Brand> list = brandService.list();
        List<BrandVo> brandVos = brandTransfer.toVO(list);
        return AxiosResult.success(brandVos);

    }

    /**
     * 根据id查询
     */
    @GetMapping("{id}")
    public AxiosResult<Brand> findById(@PathVariable Long id){
        Brand byId = brandService.getById(id);
        return AxiosResult.success(byId);
    }
    /**
     *添加
     */
    @PostMapping("add")
    //@HasPerm(perm = "brand:add")
    public AxiosResult<Void> add(@RequestBody String key ,@RequestBody String area){
        System.out.println(key);
//        return toAxios(brandService.save(Brand));
        return null;
    }

    /**
     * 修改
     */
    @PutMapping
    @HasPerm(perm = "brand:edit")
    public AxiosResult<Void> update(@RequestBody  Brand Brand){
        return toAxios(brandService.update(Brand));
    }

    /**
     * 删除
     */
    @DeleteMapping("{id}")
    @HasPerm(perm = "brand:delete")
    public AxiosResult<Void> delete(@PathVariable  Long id){
        return toAxios(brandService.deleteById(id));
    }


    /**
     * 批量删除
     */

    @DeleteMapping("batch/{ids}")
    @HasPerm(perm = "brand:batch")
    public AxiosResult<Void> batchDelete(@PathVariable List<Long> ids){
        return toAxios(brandService.batchDeleteByIds(ids));
    }

}
