package com.javasm.domin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.javasm.domin.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_menu")
public class Menu extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;



    /**
     * 菜单标题
     */
    private String menuTitle;

    /**
     * 上级菜单id
     */
    private Long parentId;

    /**
     * 菜单分类 0：表示目录 1： 表示菜单  2：表示按钮
     */
    private Integer menuType;

    /**
     * 菜单排序
     */
    private Integer menuSort;

    /**
     * 路由地址
     */
    private String menuPath;

    /**
     * 菜单图标
     */
    private String menuIcon;

    /**
     * 组件地址
     */
    private String component;

    /**
     * 是否隐藏
     */
    private Boolean isHidden;

    /**
     * 权限标识
     */
    private String permission;




}
