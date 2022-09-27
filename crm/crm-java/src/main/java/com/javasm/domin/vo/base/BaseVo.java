package com.javasm.domin.vo.base;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 给予前台显示的实体类 vo
 */
@Data
public class BaseVo {
    private Long id;

    /**
     * 创建者
     */
    @JsonIgnore
    private Long createBy;

    /**
     * 创建时间
     */
    @JsonIgnore
    private LocalDateTime createTime;

    /**
     * 修改者
     */
    @JsonIgnore
    private Long updateBy;

    /**
     * 修改时间
     */
    @JsonIgnore
    private LocalDateTime updateTime;
}
