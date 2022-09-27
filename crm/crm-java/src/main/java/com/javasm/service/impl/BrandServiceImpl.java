package com.javasm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javasm.common.page.PageResult;
import com.javasm.domin.criteria.BrandCriteria;
import com.javasm.domin.entity.Brand;
import com.javasm.domin.vo.BrandVo;
import com.javasm.mapper.BrandMapper;
import com.javasm.service.BrandService;
import com.javasm.service.base.impl.BaseServiceImpl;
import com.javasm.transfer.BrandTransfer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor//和 @Autowired效果一样
@Transactional
public class BrandServiceImpl extends BaseServiceImpl<Brand> implements BrandService {

    private final BrandMapper brandMapper;//map
    private final BrandTransfer brandTransfer;//转换entity 和vo对象


    @Override
    public PageResult<BrandVo> searchPage(BrandCriteria brandCriteria) {
        //开启分页
        PageHelper.startPage(brandCriteria.getCurrentPage(), brandCriteria.getPageSize());
        //条件创建
        LambdaQueryWrapper<Brand> lambda = new QueryWrapper<Brand>().lambda();
        //判断品牌名称
        if (!StringUtils.isEmpty(brandCriteria.getBrandName())) {
            lambda.like(Brand::getBrandName, brandCriteria.getBrandName());
        }
        //判断开始时间以及结束时间是否为空
        LocalDateTime startTime = brandCriteria.getStartTime();
        LocalDateTime endTime = brandCriteria.getEndTime();
        if (startTime != null && endTime != null) {
            //根据between进行判断
            lambda.between(Brand::getCreateTime, startTime, endTime);
        }
        List<Brand> brands = brandMapper.selectList(lambda);
        /*获取总条数*/
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        /*返回总条数以及 转换成Vo对象*/
        return new PageResult<BrandVo>(pageInfo.getTotal(), brandTransfer.toVO(brands));

    }
}
