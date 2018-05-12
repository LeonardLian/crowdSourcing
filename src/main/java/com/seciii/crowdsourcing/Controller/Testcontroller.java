package com.seciii.crowdsourcing.Controller;

import org.springframework.web.bind.annotation.*;

/**
 * @author: lian
 * @description: good good study
 * @date: create in 下午11:27 2018/3/21
 */
@CrossOrigin
@RestController
public class Testcontroller {
    @RequestMapping(value = "/hello",method = RequestMethod.POST)
    public String hello(@RequestBody String a){
        return a;
    }
}
