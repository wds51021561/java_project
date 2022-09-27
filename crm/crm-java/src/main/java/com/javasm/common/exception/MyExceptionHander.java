package com.javasm.common.exception;


import com.javasm.common.http.AxiosResult;
import com.javasm.common.http.EnumStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class MyExceptionHander {

    /**
     * 专门处理表单校验不成功的情况
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AxiosResult<Map<String, String>> handler(MethodArgumentNotValidException e) {

        Map<String, String> map = new HashMap<String, String>();
        //获取错误
        BindingResult bindingResult = e.getBindingResult();
        //遍历错误
        if (bindingResult.hasFieldErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (int i = 0; i < fieldErrors.size(); i++) {
                //添加错误信息
                map.put(fieldErrors.get(i).getField(), fieldErrors.get(i).getDefaultMessage());
            }
        }
        //返回错误
        return AxiosResult.error(EnumStatus.FORM_VALICATOR_ERROR, map);
    }

    /**
     * 处理无权限的异常
     */
    @ExceptionHandler(PermException.class)
    public AxiosResult<Void> handler(PermException e) {
        return AxiosResult.error(e.getEnumStatus());
    }


    @ExceptionHandler(NoLoginException.class)
    public AxiosResult<Void> handler(NoLoginException e) {
        return AxiosResult.error(e.getEnumStatus());
    }

    @ExceptionHandler(StorageException.class)
    public AxiosResult handler(StorageException e) {
        return AxiosResult.error(e.getEnumStatus());
    }
}
