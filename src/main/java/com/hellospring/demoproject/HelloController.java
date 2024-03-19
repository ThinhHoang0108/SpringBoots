package com.hellospring.demoproject;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
public class HelloController {
    @RequestMapping(value = "/")
    public String index(){
        return "My First Spring boot";
    }
}
