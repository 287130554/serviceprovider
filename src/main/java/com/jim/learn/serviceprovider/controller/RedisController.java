package com.jim.learn.serviceprovider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(@RequestParam(value="key")String key, @RequestParam(value = "value") String value){
        ValueOperations ops=stringRedisTemplate.opsForValue();

        ops.set(key,value);
        return "success";
    }

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public String get(@RequestParam(value = "key")String key){
        ValueOperations ops=stringRedisTemplate.opsForValue();
        return (String) ops.get(key);
    }
}
