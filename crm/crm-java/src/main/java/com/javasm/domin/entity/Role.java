package com.javasm.domin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.javasm.domin.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role")
public class Role extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;



    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String roleDesc;




}
