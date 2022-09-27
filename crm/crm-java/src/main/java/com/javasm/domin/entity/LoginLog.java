package com.javasm.domin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@TableName("log_login_log")
public class LoginLog  {

    /**
     * 日志文件
     */

    private static final long serialVersionUID = 1L;

    /**
     * 登录日志id
     */
    @TableId(value = "login_id", type = IdType.AUTO)
    private Long loginId;

    /**
     * 谁登录的
     */
    private String adminName;

    /**
     * 登录者使用电脑的ip
     */
    private String requestIp;

    /**
     * 登录地点
     */
    private String loginAddress;

    /**
     * 登录使用的浏览器名称
     */
    private String broswerName;

    /**
     * 登录使用电脑的系统名称
     */
    private String osName;

    /**
     * 登录状态0： 表示登录成功 1： 表示登录失败
     */
    private Integer loginStatus;

    /**
     * 登录提示信息
     */
    private String message;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;


}
