package com.javasm.domin.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.javasm.domin.vo.base.BaseVo;
import lombok.Data;

import java.util.List;


@Data
public class MenuVo extends BaseVo {


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


    /**
     * 找孩子
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<MenuVo> children;

}
