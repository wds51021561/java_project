package com.itheima.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.goods.dao.GoodsInfoMapper;
import com.itheima.goods.service.GoodsInfoService;
import com.itheima.pind.goods.entity.GoodsInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GoodsInfoServiceImpl extends ServiceImpl<GoodsInfoMapper, GoodsInfo> implements GoodsInfoService {
}
