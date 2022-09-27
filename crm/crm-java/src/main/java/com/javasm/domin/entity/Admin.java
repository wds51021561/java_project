package com.javasm.domin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.javasm.common.valid.SexList;
import com.javasm.domin.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("base_admin")
public class Admin extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;



    /**
     * 管理员名称
     */
    @NotBlank(message = "不能为空知道吗") //判断 null 和 "" 和 "  "
    private String adminName;

    /**
     * 管理员昵称
     */
    @NotBlank(message = "不能为空知道吗") //判断 null 和 "" 和 "  "
    private String nickName;

    /**
     * 管理员性别 0 : 男   1：女     2： 表示未知
     */
    @SexList(sex = {0,1,2},message = "请输入正确的数字 男女")
    private Integer gender;

    /**
     * 管理员手机
     */
    @NotBlank(message = "不能为空知道吗") //判断 null 和 "" 和 "  "
    @Pattern(regexp = "^[1][3,5,7,8][0-9]\\\\d{8}$",message = "请输入正确手机号")
    private String adminPhone;

    /**
     * 管理员邮箱
     */
    @NotBlank(message = "不能为空知道吗") //判断 null 和 "" 和 "  "
    @Email(message = "你输入的不是一个邮箱格式")
    private String adminEmail;

    /**
     * 管理员家住地址
     */
    private String adminAddress;

    /**
     * 管理员密码
     */
    private String adminPwd;

    /**
     * 管理员头像
     */
    @URL(message = "上传的头像必须是URL地址")
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
    private Long deptId;



    /**
     * 重置密码时间
     */
    private LocalDateTime pwdResetTime;

    /**
     * 角色Ids
     */
    transient Set<Long> roleIds;


}
