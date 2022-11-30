package com.itheima.pinda.zuul.filter;

import cn.hutool.core.util.StrUtil;
import com.itheima.pinda.base.R;
import com.itheima.pinda.common.adapter.IgnoreTokenConfig;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public abstract class BaseFilter extends ZuulFilter {
    @Value("${server.servlet.context-path}")
    protected String zuulPrefix;

    /**
     *
     * @return
     */
    protected boolean isIgnoreToken(){
        //获取请求url
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        String uri = request.getRequestURI();

        uri = StrUtil.subSuf(uri,zuulPrefix.length());
        uri = StrUtil.subSuf(uri,uri.indexOf("/",1));
        return IgnoreTokenConfig.isIgnoreToken(uri);
    }

    protected void errorResponse(String errMsg, int errCode, int httpStatusCode){
        R tokenError = R.fail(errCode,errMsg);
        RequestContext currentContext = RequestContext.getCurrentContext();
        currentContext.setResponseStatusCode(httpStatusCode);
        currentContext.addZuulRequestHeader("Content-Type","application/json;charset=UTF-8");
        if(currentContext.getResponseBody() == null){
            currentContext.setResponseBody(tokenError.toString());
            currentContext.setSendZuulResponse(false);
        }
    }

}
