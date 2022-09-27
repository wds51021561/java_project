package com.javasm.storage.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javasm.storage.entity.Dic;
import com.javasm.storage.mapper.DicMapper;
import com.javasm.storage.myEnum.StorageEnum;
import com.javasm.storage.service.IDicService;
import com.javasm.storage.utils.BaseUtil;
import com.javasm.storage.utils.DicUtils;
import com.javasm.storage.utils.WrapperUtil;
import com.javasm.storage.vo.Options;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者:yy
 * 日期:2022/7/1 20:54
 * 描述:
 */
@Service
@Transactional
public class DicServiceImpl extends ServiceImpl<DicMapper, Dic> implements IDicService {

    @Override
    public List<Options> getStorageType() {
        LambdaQueryWrapper<Dic> wrapper = WrapperUtil.getWrapper(Dic.class);
        wrapper.eq(Dic::getTypeId, 1);
        List<Dic> list = list(wrapper);
        return BaseUtil.dicToOptions(list);
    }

    @Override
    public List<Options> getStorage() {
        return getListOptions(StorageEnum.STORAGE_TYPE.get());

    }

    @Override
    public List<Options> getOrderStates(Integer id) {
        //销售入库订单状态选项
        List<Options> listOptions = getListOptions(8);
        //销售出库订单状态选项
        List<Options> sellOptions = new ArrayList<>();
        int size = listOptions.size();

        for (int i = 0; i < size; i++) {
            int index = 0;
            Options options = listOptions.get(index);
            if(options.getValue()<7){
              sellOptions.add(listOptions.remove(index));
            }else {
                index++;
            }
        }
        if(id == 0){
            return listOptions;
        }
        return sellOptions;

    }

    @Override
    public List<Options> getPayType() {
       return getListOptions(4);
    }

    @Override
    public List<Options> getOrderType() {
        return getListOptions(3);
    }

    private List<Options> getListOptions(Integer id){
        Map<Integer, String> dicList = DicUtils.getDicList(id);
        List<Options> list = new ArrayList<>();
        dicList.entrySet().forEach(entry -> {
            Options options = new Options();
            options.setValue(entry.getKey());
            options.setLabel(entry.getValue());
            list.add(options);
        });
        return list;
    }
}
