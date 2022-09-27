package com.javasm.domin.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.javasm.common.excel.LocalDateStringConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 员工信息的导出
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("base_admin")
@ContentRowHeight(35)
@HeadRowHeight(40)
@ColumnWidth(25)
// 头背景设置成红色 IndexedColors.RED.getIndex()
@HeadStyle(horizontalAlignment = HorizontalAlignment.CENTER, verticalAlignment = VerticalAlignment.CENTER, fillForegroundColor = 6)
// 头字体设置成20
@HeadFontStyle(fontHeightInPoints = 20, color = 1)
// 内容的背景设置成绿色 IndexedColors.GREEN.getIndex()
@ContentStyle(verticalAlignment = VerticalAlignment.CENTER, horizontalAlignment = HorizontalAlignment.CENTER)
// 内容字体设置成20
@ContentFontStyle(fontHeightInPoints = 18)
public class AdminExcel {

    /** 代码示范
     * @ExcelProperty("字符串标题")
     *
     *     private String string;
     *
     *     @ExcelProperty("日期标题")
     *
     *     private Date date;
     *
     *     @ExcelProperty("数字标题")
     *
     *     private Double doubleData;
     */


    /**
     * 管理员id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ExcelProperty(value = "员工id",index = 0)
    private Long id;



    /**
     * 管理员名称
     */
    @ExcelProperty("员工名称")
    private String adminName;

    /**
     * 管理员昵称
     */
    @ExcelProperty("员工昵称")
    private String nickName;

    /**
     * 管理员性别 0 : 男   1：女     2： 表示未知
     */
    @ExcelProperty("员工性别")
    private Integer gender;

    /**
     * 管理员手机
     */
    @ExcelProperty("员工手机")
    private String adminPhone;

    /**
     * 管理员邮箱
     */
    @ExcelProperty("员工邮箱")
    private String adminEmail;

    /**
     * 管理员家住地址
     */
    @ColumnWidth(40)//列宽
    @ExcelProperty("员工员工地址")
    private String adminAddress;

    /**
     * 管理员密码
     */
    @ExcelIgnore//忽略
    private String adminPwd;

    /**
     * 管理员头像
     */
    @ExcelIgnore
    private String adminAvatar;
    //导出头像   transient 不是列名 需要排除掉
    @ExcelProperty("员工头像")
    private transient URL url;

    /**
     * 账户是否可用
     */
    @ExcelProperty("是否可用")
    private Boolean isEnable;

    /**
     * 是否是超级管理员
     */
    @ExcelIgnore//忽略不导出
    private Boolean isAdmin;

    /**
     * 所在部门
     */
    @ExcelIgnore//忽略不导出
    private Long deptId;





    /**
     * 角色Ids
     */
    @ExcelIgnore//忽略不导出
    transient Set<Long> roleIds;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "入职时间",converter = LocalDateStringConverter.class)
    private LocalDateTime createTime;

}
