package com.zhaoyongbo.trend.service;

import com.zhaoyongbo.trend.pojo.Index;
import org.springframework.beans.factory.annotation.Autowired;
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
}
