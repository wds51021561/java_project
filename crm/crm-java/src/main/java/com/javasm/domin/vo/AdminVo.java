package com.javasm.domin.vo;


import com.javasm.domin.vo.base.BaseVo;
import lombok.Data;


@Data
public class AdminVo extends BaseVo {
    /**
     * 管理员名称
     */
    private String adminName;

    /**
     * 管理员昵称
     */
    private String nickName;

    /**
     * 管理员性别 0 : 男   1：女     2： 表示未知
     */
    private String sex;

    /**
     * 管理员手机
     */
    private String adminPhone;

    /**
     * 管理员邮箱
     */
    private String adminEmail;

    /**
     * 管理员家住地址
     */
    private String adminAddress;

    /**
     * 管理员头像
     */
    private String adminAvatar;

    /**
     * 账户是否可用
     */
    private Boolean isEnable;

    /**
     * 是否是超级管理员
     */
    private Boolean isAdmin;

    /**
     * 所在部门
     */
    private String deptName;

}
