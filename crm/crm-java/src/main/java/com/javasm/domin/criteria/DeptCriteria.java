package com.javasm.domin.criteria;

import com.javasm.domin.criteria.base.BaseQueryCriteria;

import lombok.Data;
import org.springframework.util.StringUtils;


@Data
public class DeptCriteria extends BaseQueryCriteria {


    private String deptName;


    /**
     * 判断是否是查询
     * @return
     */
    public boolean isQuery() {
        return !StringUtils.isEmpty(this.deptName) || (!StringUtils.isEmpty(this.getStartTime()) && !StringUtils.isEmpty(this.getEndTime()));
    }


}
