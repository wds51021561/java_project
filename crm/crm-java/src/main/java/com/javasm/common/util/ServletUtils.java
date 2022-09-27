package com.javasm.common.util;

import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


public class ServletUtils {


    /**
     * 获取全局Request
     */

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes ServletRequestAttributes = (org.springframework.web.context.request.ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return ServletRequestAttributes.getRequest();

    }

    /**
     * 获取全局getResponse
     */

    public static HttpServletResponse getResponse() {
        ServletRequestAttributes ServletRequestAttributes = (org.springframework.web.context.request.ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return ServletRequestAttributes.getResponse();

    }



    /**
     * 获取UserAgent
     *
     * @param httpServletRequest
     * @return
     */
    public static UserAgent getUserAgent(HttpServletRequest httpServletRequest) {
        return UserAgent.parseUserAgentString(httpServletRequest.getHeader("user-agent"));
    }


    /**
     * 获取ip地址
     */

    public static String getRequestIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equalsIgnoreCase(ip)) {
            return "127.0.0.1";
        }
        return ip;
    }


    /**
     * 获取地理位置
     */

    public static String getLoginAddress(HttpServletRequest request) {
        String requestIp = getRequestIp(request);
        //获取远程访问RestTemplate
        RestTemplate bean = SpringContainerUtils.getBean(RestTemplate.class);
        //聚合数据访问ip 以及key
        String url = "http://apis.juhe.cn/ip/ipNew?ip=" + requestIp + "&key=0b0122d5b3055bc2dea21770a8df3f4d";
        //省市区归属地
        Map forObject = bean.getForObject(url, Map.class);
        Map<String, String> map = (Map<String, String>) forObject.get("result");
        String country = map.get("Country");
        String province = map.get("Province");
        String city = map.get("City");
        return country + province + city;
    }

}
