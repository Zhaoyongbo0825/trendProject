package com.zhaoyongbo.trend.controller;

import com.zhaoyongbo.trend.pojo.Index;
import com.zhaoyongbo.trend.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/index")
public class IndexController {

    private final
    IndexService indexService;

    public IndexController(IndexService indexService) {
        this.indexService = indexService;
    }

    @RequestMapping(value = "/getList")
    public List<Index> getList(){
        return indexService.fetchIndexFromThirdPart();
    }
}
