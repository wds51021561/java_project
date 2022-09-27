package com.javasm.storage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javasm.common.page.PageResult;
import com.javasm.storage.entity.GoodStorage;
import com.javasm.storage.entity.StorageAudit;
import com.javasm.storage.entity.StorageForm;
import com.javasm.storage.entity.StorageGood;
import com.javasm.storage.mapper.StorageFormMapper;
import com.javasm.storage.myEnum.StorageEnum;
import com.javasm.storage.query.StorageFormQuery;
import com.javasm.storage.req.AddReturnGoodStorageForm;
import com.javasm.storage.service.IGoodStorageService;
import com.javasm.storage.service.IOrderService;
import com.javasm.storage.service.IStorageFormService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javasm.storage.service.IStorageGoodService;
import com.javasm.storage.utils.BaseUtil;
import com.javasm.storage.utils.FormCodeUtil;
import com.javasm.storage.utils.WrapperUtil;
import com.javasm.storage.utils.transfer.StorageFormToVO;
import com.javasm.storage.utils.transfer.base.BaseTransfer;
import com.javasm.storage.vo.StorageFormVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 出入库表 服务实现类
 * </p>
 *
 * @author yy
 * @since 2022-06-30
 */
@Service
@Transactional
public class StorageFormServiceImpl extends ServiceImpl<StorageFormMapper, StorageForm> implements IStorageFormService {

    @Autowired
    IGoodStorageService goodStorageService;

    @Autowired
    IStorageGoodService storageGoodService;

    @Autowired
    IOrderService orderService;

    @Override
    public Boolean updateStates(String storageCode, StorageAudit storageAudit) {
        //查询库单记录
        StorageForm storageForm = reqStorageFormByStorageFormCode(storageCode);
        //获取出入库类型, >5 出库, =<5 入库
        Integer storageType = storageForm.getStorageType();
        //标记商品更新是否成功
        boolean goodFlag = true;
        boolean orderFlag = true;

        //2级审核通过,入库,更新库单状态为入库, 更新商品状态为入库
        //2级审核通过,出库,更新库单状态为出库, 更新商品状态为出库
        //1级审核通过,出库,更新库单状态待出库
        //1级审核通过,入库,更新库单状态待入库
        //审核不通过,入库,更新库单状态为退回待入库
        //审核不通过,出库,更新库单状态为退回待出库
        if (storageAudit.getAuditState() == 1) {
            //2级审核通过
            if (storageAudit.getAuditLevel() == 2) {
                if (BaseUtil.isStorageStates(storageType)) {
                    storageForm.setStorageState(StorageEnum.IN_STORAGE.get());
                } else {
                    storageForm.setStorageState(StorageEnum.OUT_STORAGE.get());
                }
                //跟新商品状态
                goodFlag = goodStorageService.updateStates(storageForm);
                //更新订单状态
                orderFlag = orderService.updateOrderStates(storageForm);

            }
            //1级审核通过
            else {
                if (BaseUtil.isStorageStates(storageType)) {
                    storageForm.setStorageState(StorageEnum.WAIT_IN_STORAGE.get());
                } else {
                    storageForm.setStorageState(StorageEnum.WAIT_OUT_STORAGE.get());
                }
            }

        }
        //审核不通过
        else {
            if (BaseUtil.isStorageStates(storageType)) {
                storageForm.setStorageState(StorageEnum.EXIT_IN_STORAGE.get());
            } else {
                storageForm.setStorageState(StorageEnum.EXIT_OUT_STORAGE.get());
            }
        }
        storageForm.setModifyTime(LocalDateTime.now());
        //跟新数据库
        boolean formFlag = updateById(storageForm);
        return goodFlag && formFlag && orderFlag;


    }

    @Override
    public StorageForm reqStorageFormByStorageFormCode(String code) {
        LambdaQueryWrapper<StorageForm> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StorageForm::getStorageCode, code);
        return getOne(wrapper);
    }

    @Override
    public PageResult<StorageFormVO> pageStorageForm(StorageFormQuery queryForm) {

        IPage<StorageForm> ipage = new Page<>(queryForm.getCurrentPage(), queryForm.getPageSize());
        LambdaQueryWrapper<StorageForm> wrapper = WrapperUtil.getWrapper(StorageForm.class);
        //拼接查询条件
        spliceWrapper(wrapper, queryForm);
        IPage<StorageForm> page = page(ipage, wrapper);
        List<StorageForm> list = page.getRecords();
        List<StorageFormVO> listVO = BaseTransfer.toListVO(list, StorageFormVO.class, new StorageFormToVO());
        return new PageResult<>(page.getTotal(), listVO);
    }

    @Override
    public boolean isCreatedFormByCode(String returnGoodOrderCode) {
        LambdaQueryWrapper<StorageForm> wrapper = WrapperUtil.getWrapper(StorageForm.class);
        wrapper.eq(StorageForm::getOrderCode,returnGoodOrderCode);
        StorageForm one = getOne(wrapper);
        return one!=null;
    }

    @Override
    public boolean createReturnForm(AddReturnGoodStorageForm addStorageForm,String name) {
        //创建出入库订单
        String xsrk = FormCodeUtil.get(FormCodeUtil.FormCodePrefix.XSRK);
        StorageForm storageForm = new StorageForm(xsrk,2,name);
        storageForm.setOrderCode(addStorageForm.getReturnGoodOrderCode());
        storageForm.setStorageId(addStorageForm.getStorageId());

        boolean save = save(storageForm);

        //维护中间表
        List<StorageGood> list = new ArrayList<>();
        for (AddReturnGoodStorageForm.GoodInfo goodInfo : addStorageForm.getGoodInfos()) {
            GoodStorage byGoodInfo = goodStorageService.getByGoodInfo(goodInfo);
            StorageGood storageGood = new StorageGood();
            storageGood.setStorageCode(xsrk);
            storageGood.setGoodStorageId(byGoodInfo.getId());
            list.add(storageGood);
        }
        boolean b = storageGoodService.saveBatch(list);

        //更新商品状态
        Boolean aBoolean = goodStorageService.updateStates(storageForm);


        return save && b && aBoolean;
    }

    @Override
    public boolean createSellForm(AddReturnGoodStorageForm addStorageForm, String name) {
        //创建出入库订单
        String xsck = FormCodeUtil.get(FormCodeUtil.FormCodePrefix.XSCK);
        StorageForm storageForm = new StorageForm(xsck,7, name);
        storageForm.setOrderCode(addStorageForm.getReturnGoodOrderCode());
        storageForm.setStorageId(addStorageForm.getStorageId());

        boolean save = save(storageForm);

        //维护中间表
        List<StorageGood> list = new ArrayList<>();
        for (AddReturnGoodStorageForm.GoodInfo goodInfo : addStorageForm.getGoodInfos()) {
            GoodStorage byGoodInfo = goodStorageService.getByGoodInfo(goodInfo);
            StorageGood storageGood = new StorageGood();
            storageGood.setStorageCode(xsck);
            storageGood.setGoodStorageId(byGoodInfo.getId());
            list.add(storageGood);
        }
        boolean b = storageGoodService.saveBatch(list);

        //更新商品状态
        Boolean aBoolean = goodStorageService.updateStates(storageForm);


        //todo 回滚
        return save && b && aBoolean;
    }


    //拼接查询条件
    private void spliceWrapper(LambdaQueryWrapper<StorageForm> wrapper, StorageFormQuery queryForm) {
        //库单编码不为空
        if (!StringUtils.isEmpty(queryForm.getStorageFormCode())) {
            wrapper.likeLeft(StorageForm::getStorageCode, queryForm.getStorageFormCode());
        }
        //仓库类型不为空
        if (!StringUtils.isEmpty(queryForm.getStorageFormType())) {
            wrapper.eq(StorageForm::getStorageType, queryForm.getStorageFormType());
        }
        //库单状态
        switch (queryForm.getStates()) {
            //已通过
            case 1:
                wrapper.in(StorageForm::getStorageState, 2, 5);
                break;
            //驳回
            case 2:
                wrapper.in(StorageForm::getStorageState, 3, 6);
                break;
            //待审批
            default:
                wrapper.in(StorageForm::getStorageState, 1, 4, 7);
                break;
        }
        //判断是否添加时间范围
        if ((!StringUtils.isEmpty(queryForm.getStarTime())) && (!StringUtils.isEmpty(queryForm.getEndTime()))) {
            wrapper.between(StorageForm::getCreateTime, queryForm.getStarTime(), queryForm.getEndTime());
        }
    }

}
