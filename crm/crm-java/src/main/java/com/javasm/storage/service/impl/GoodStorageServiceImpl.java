package com.javasm.storage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.javasm.storage.entity.GoodStorage;
import com.javasm.storage.entity.StorageForm;
import com.javasm.storage.mapper.GoodStorageMapper;
import com.javasm.storage.myEnum.StorageEnum;
import com.javasm.storage.req.AddReturnGoodStorageForm;
import com.javasm.storage.service.IGoodStorageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javasm.storage.utils.BaseUtil;
import com.javasm.storage.utils.WrapperUtil;
import com.javasm.storage.vo.AuditGoodVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yy
 * @since 2022-06-30
 */
@Service
@Transactional
public class GoodStorageServiceImpl extends ServiceImpl<GoodStorageMapper, GoodStorage> implements IGoodStorageService {

    @Autowired
    GoodStorageMapper goodStorageMapper;

    @Override
    public Boolean updateStates(StorageForm storageForm) {
        //查询库单关联的库存
        List<GoodStorage> list = goodStorageMapper.queryByStorageFormCode(storageForm.getStorageCode());
        if(list.size()==0){
            return true;
        }

        list.forEach(goodStorage -> {
            //更新库存状态
            updateStates(goodStorage,storageForm);
            //更新业务状态
            goodStorage.setGoodBusiness(storageForm.getStorageType());
            //跟新所在仓库
            goodStorage.setStorageId(storageForm.getStorageId());
            //更新时间
            goodStorage.setModifyTime(LocalDateTime.now());
        });

        //更新商品库存数据
        return updateBatchById(list);
    }

    @Override
    public List<GoodStorage> queryByStorageFormCode(String code) {
        return goodStorageMapper.queryByStorageFormCode(code);
    }

    @Override
    public List<AuditGoodVO> queryAuditGoodVoByStorageFormCode(String code) {
        return goodStorageMapper.queryAuditGoodVoByStorageFormCode(code);
    }

    @Override
    public boolean isReturnGoodStates(List<AddReturnGoodStorageForm.GoodInfo> goodInfos) {
        for (AddReturnGoodStorageForm.GoodInfo goodInfo : goodInfos) {
            GoodStorage goodStorage = getByGoodInfo(goodInfo);
            if(goodStorage==null){
                return false;
            }
            //判断商品是否是出库状态
            if (goodStorage.getGoodState() != 3 ) {
                return false;
            }
            //判断商品是不是售后出库或销售出库
            if(goodStorage.getGoodBusiness() != 7 && goodStorage.getGoodBusiness() != 8){
                return false;
            }
        }
        return true;
    }

    /**
     * 根据GoodInfo获取商品库存对象
     * @param goodInfo
     * @return
     */
    @Override
    public GoodStorage getByGoodInfo(AddReturnGoodStorageForm.GoodInfo goodInfo) {
        LambdaQueryWrapper<GoodStorage> wrapper = WrapperUtil.getWrapper(GoodStorage.class);
        wrapper.eq(GoodStorage::getGoodId, goodInfo.getGoodId());
        wrapper.eq(GoodStorage::getGoodSerial, goodInfo.getGoodSerial());
        return getOne(wrapper);
    }

    @Override
    public boolean isSellGoodStates(List<AddReturnGoodStorageForm.GoodInfo> goodInfos,Integer storageId) {
        for (AddReturnGoodStorageForm.GoodInfo goodInfo : goodInfos) {
            GoodStorage goodStorage = getByGoodInfo(goodInfo);
            if(goodStorage==null){
                return false;
            }
            //判断商品是否是出库状态
            if (goodStorage.getGoodState() != 1 ) {
                return false;
            }
            //判断商品是否在此仓库
            if(goodStorage.getStorageId() != storageId){
                return false;
            }
        }
        return true;
    }


    /**
     * 更新库存商品状态
     * @param goodStorage
     * @param storageForm
     * 库单状态: 录入完成, 更新商品商品为待出入库
     * 库单状态: 已入库 / 已出库 ,更新商品状态已入库 / 已出库
     * 库单状态: 待出库 / 待出库 ,更新商品信息为待出入库
     * 库单状态: 退回 ,更新商品信息为待出入库
     */
    private void updateStates(GoodStorage goodStorage,StorageForm storageForm){
        switch (storageForm.getStorageState()){
            case 1:
            case 3:
                goodStorage.setGoodState(0);
                break;
            case 2:
                goodStorage.setGoodState(1);
                break;
            case 4:
            case 6:
                goodStorage.setGoodState(2);
                break;
            case 5:
                goodStorage.setGoodState(3);
                break;
            case 7:
                if(BaseUtil.isStorageStates(storageForm.getStorageType())){
                    goodStorage.setGoodState(0);
                }else {
                    goodStorage.setGoodState(2);
                }
                break;
        }

    }
}
