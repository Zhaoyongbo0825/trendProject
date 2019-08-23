package com.zhaoyongbo.trend.service;

import cn.hutool.core.collection.CollectionUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.zhaoyongbo.trend.pojo.Index;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class IndexService {

    private final
    RestTemplate restTemplate;

    public IndexService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //@HystrixCommand(fallbackMethod = "thirdPartNotConnected")该注解表示该方法链接其他模块失败后访问某个方法进行
    //相对应的操作，从而在前端不会出现报错，提高了的使用舒适度
    @HystrixCommand(fallbackMethod = "thirdPartNotConnected")
    public List<Index> fetchIndexFromThirdPart() {
        List<Map<String, Object>> list = restTemplate.getForObject("http://localhost:8200/index/codes.json", List.class);
        if (list == null) {
            return null;
        }
        return map2Index(list);
    }

    private List<Index> map2Index(List<Map<String, Object>> temp) {
        List<Index> indexes = new ArrayList<>();
        for (Map map : temp) {
            String code = map.get("code").toString();
            String name = map.get("name").toString();
            Index index = new Index();
            index.setCode(code);
            index.setName(name);
            indexes.add(index);
        }

        return indexes;
    }
    public List<Index> thirdPartNotConnected(){
        System.out.println("third_part_not_connected()");
        Index index= new Index();
        index.setCode("000000");
        index.setName("无效指数代码");
        return CollectionUtil.toList(index);
    }
}
