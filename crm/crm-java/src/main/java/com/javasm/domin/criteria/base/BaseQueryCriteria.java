package com.javasm.domin.criteria.base;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


/**
 * 通用条件查询
 */
@Data
public class BaseQueryCriteria {

    private Integer currentPage=1;//当前所在页

    private Integer pageSize=5;//当前页显示

    //开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    //结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}
