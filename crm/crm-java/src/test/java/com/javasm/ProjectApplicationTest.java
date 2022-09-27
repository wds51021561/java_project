package com.javasm;

import com.javasm.common.http.AxiosResult;
import com.javasm.storage.controller.DicController;
import com.javasm.storage.entity.GoodStorage;
import com.javasm.storage.mapper.GoodStorageMapper;
import com.javasm.storage.mapper.OrderMapper;
import com.javasm.storage.query.ReturnGoodOrderQuery;
import com.javasm.storage.vo.Options;
import com.javasm.storage.vo.ReturnGoodOrderVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectApplicationTest {

    @Autowired
    GoodStorageMapper goodStorageMapper;

    @Autowired
    DicController dicController;


    @Autowired
    OrderMapper orderMapper;

    @Test
    public void demo() {
        List<GoodStorage> goodStorages = goodStorageMapper.queryByStorageFormCode("123");
    }

    @Test
    public void demo1() {
        AxiosResult storageType = dicController.getStorageType();
        List<Options> list = (List<Options>) storageType.getData();
        list.forEach(o -> {
            System.out.println(o.getLabel());
        });
    }

    @Test
    public void demo2() {
        orderMapper.updateSellOrder(512023,8);
    }
}
