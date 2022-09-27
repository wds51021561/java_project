package com.javasm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.javasm.common.util.TreeUtils;
import com.javasm.domin.entity.Category;
import com.javasm.domin.vo.CategoryVo;
import com.javasm.mapper.CategoryMapper;
import com.javasm.service.CategoryService;
import com.javasm.service.base.impl.BaseServiceImpl;
import com.javasm.transfer.CategoryTransfer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl extends BaseServiceImpl<Category> implements CategoryService {

    private final CategoryTransfer categoryTransfer;

    private final CategoryMapper categoryMapper;

    /**
     * 根据 分类递归工具 循环遍历 分类列表
     * @return
     */
    @Override
    public List<CategoryVo> buildTree() {
        List<Category> list = this.list();
        List<CategoryVo> categoryVos = categoryTransfer.toVO(list);
        return TreeUtils.bulidTree(categoryVos);
    }



    //新增下拉框选择信息
    @Override
    public List<CategoryVo> getSelectTree() {
        //一级
        List<Category> first = categoryMapper.getByCategoryLevel(1);
        List<CategoryVo> categoryVos = categoryTransfer.toVO(first);
        //二级
        List<Category> second = categoryMapper.getByCategoryLevel(2);
        List<CategoryVo> categoryVos1 = categoryTransfer.toVO(second);
        categoryVos.forEach(categoryVo -> {
            List<CategoryVo> collect = categoryVos1.stream().filter(item -> item.getParentId().longValue() == categoryVo.getId().longValue()).collect(Collectors.toList());
            categoryVo.setChildren(collect);
        });
        return categoryVos;
    }

    /**
     *删除信息 以及删除子项
     */
    @Override
    public int deleSelfAndChildrenById(Long id) {
        List<Category> byparentId = categoryMapper.getByparentId(id);
        byparentId.forEach(category -> {
            //查询当前信息 删除本身
            deleteById(category.getId());
            //根据当前信息  查询第二级分类 删除
            List<Category> second = categoryMapper.getByparentId(category.getId());
            //删除第三级分类
            second.forEach(category1 -> {
                deleteById(category1.getId());
            });
        });
        return deleteById(id);
    }


    /**
     * 通过父id查 分类
     * @return
     */
    @Override
    public List<CategoryVo> getByParentId(long parentId) {

        List<Category> categories = categoryMapper.selectList(new QueryWrapper<Category>().lambda().eq(Category::getParentId, parentId));

        return categoryTransfer.toVO(categories);
    }
}
