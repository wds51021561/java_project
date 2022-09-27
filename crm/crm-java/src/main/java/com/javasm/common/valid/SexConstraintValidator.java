package com.javasm.common.valid;

import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class SexConstraintValidator implements ConstraintValidator<SexList,Integer> {


    List<Integer> list;
    @Override
    public void initialize(SexList constraintAnnotation) {
        //拿到了 我们在代码的制定的 0 1 2  如果代码制定了 4 5 6 这里也是 4 5 6
        int [] sex=constraintAnnotation.sex();
        list= CollectionUtils.arrayToList(sex);

    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        //value 表示的是前段传过来的数据  返回是个boolean
        return list.contains(value);
    }
}
