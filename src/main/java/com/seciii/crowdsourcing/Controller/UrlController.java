package com.seciii.crowdsourcing.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 上午11:49 2018/5/14
 */

@Controller
public class UrlController {

    @RequestMapping(value="/login")
    public String login(){
        return "login";
    }
}
