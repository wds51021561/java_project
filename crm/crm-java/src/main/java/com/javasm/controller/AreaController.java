package com.javasm.controller;


import com.javasm.common.http.AreaItem;
import com.javasm.common.http.AxiosResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.javasm.common.http.AreaResult;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("area")
public class AreaController {


    private final RestTemplate restTemplate;


    /**
     * 获取省级联动  省 市  区  聚合数据信息
     */
    @GetMapping("{id}/children")
    public AxiosResult<List<AreaItem>> getChildById(@PathVariable String id){
        //聚合数据的key  访问路径
        /*"http://apis.juhe.cn/xzqh/query?key=59db7e044462bff7394d4eb67541e7af&fid=" + id;
        * "http://apis.juhe.cn/xzqh/query?key=b0f65d6bd4b0ff717363861555f8b01c&fid=" + id;*/
        String path = "http://apis.juhe.cn/xzqh/query?key=59db7e044462bff7394d4eb67541e7af&fid=" + id;
        //restTemplate.getForObject  可以访问外置资源
        AreaResult areaResult = restTemplate.getForObject(path, AreaResult.class);
        return AxiosResult.success(areaResult.getResult());
    }



}
