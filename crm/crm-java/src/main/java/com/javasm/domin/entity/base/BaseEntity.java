package com.javasm.domin.entity.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javasm.common.valid.addGroup;
import com.javasm.common.valid.updateGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class BaseEntity implements Serializable  {

    /**
     * 管理员id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Null(groups = addGroup.class,message = "添加时不能有id ")
    @NotNull(groups = updateGroup.class,message = "修改id必须有数值")
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


    /**
     * 创建时间和修改时间的调用
     */
    public void setData() {
        if (id == null) {
            //添加功能
            this.createBy = 1L;//创建人
            this.createTime = LocalDateTime.now();//当前创建时间
        } else {
            this.updateBy = 2L;//修改人
            this.updateTime = LocalDateTime.now();//当前修改时间
        }
    }
}
