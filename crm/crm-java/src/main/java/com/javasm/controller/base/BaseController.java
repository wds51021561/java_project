package com.javasm.controller.base;

import com.javasm.common.http.AxiosResult;

public class BaseController {

    protected AxiosResult<Void> toAxios(int row){
        return row>0?AxiosResult.success():AxiosResult.error();

    }
}
