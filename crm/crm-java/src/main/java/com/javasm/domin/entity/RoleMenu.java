package com.javasm.domin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_menu")
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 菜单id
     */
    private Long menuId;


}
