package com.cache.bgc.controller;

import com.cache.bgc.model.ObjectEntry;
import com.cache.bgc.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@RestController
@EnableSwagger2
@RequestMapping("api/v1/")
public class CacheController {

    private CacheService cacheService;

    @Autowired
    public CacheController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @RequestMapping(value = "/cache", method = RequestMethod.GET)
    public List<ObjectEntry> cacheList() {
        return cacheService.getAllEntries();
    }

    @RequestMapping(value = "cache/{key}", method = RequestMethod.GET)
    public Object getCache(@PathVariable String key) {
        return cacheService.getValueFromCache(key);
    }

    @RequestMapping(value = "cache/{key}", method = RequestMethod.PUT)
    public ObjectEntry putCache(@PathVariable String key, @RequestBody Object value) {
        return cacheService.putValueInCache(key, value);
    }
}
