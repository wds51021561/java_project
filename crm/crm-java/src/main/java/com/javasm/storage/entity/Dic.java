package com.javasm.storage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 作者:yy
 * 日期:2022/6/30 15:12
 * 描述:
 */
@Data
public class Dic implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 字典类型id
     */
    private Integer typeId;
    /**
     * 字典类型名
     */
    private String typeName;
    /**
     * 字典状态id
     */
    private Integer statusId;
    /**
     * 字典状态值
     */
    private String statusName;
}
