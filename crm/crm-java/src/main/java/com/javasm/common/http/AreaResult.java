package com.javasm.common.http;

import lombok.Data;

import java.util.List;

//聚合数据
@Data
public class AreaResult {

    private String reason;

    private List<AreaItem> result;

}
