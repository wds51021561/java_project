package com.javasm.common.valid;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = { SexConstraintValidator.class})//指定校验器
public @interface SexList {

    /**
     * 自定义对于性别的注解
     * @return
     */

    String message() default "{javax.validation.constraints.AssertTrue.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    //定义数组描述可以取值的内容
    int[] sex() default {};
}
