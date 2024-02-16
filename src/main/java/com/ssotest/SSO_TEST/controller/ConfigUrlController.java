package com.ssotest.SSO_TEST.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssotest.SSO_TEST.model.ConfigUrlGetter;
import com.ssotest.SSO_TEST.model.ConfigUrlRequest;
// import com.ssotest.SSO_TEST.model.LoginSsoRequest;
import com.ssotest.SSO_TEST.service.ConfigUrlService;
// import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/config")
public class ConfigUrlController {

    private final ConfigUrlService configUrlService;

    @Autowired
    public ConfigUrlController(ConfigUrlService configUrlService) {
        this.configUrlService = configUrlService;
    }
    // Testcase :{
    // "config_body" : {
    // "config_display_name" : "tes05",
    // "config_url" : "www.localhost:8080",
    // "config_user" : "username",
    // "config_pwd" : "password",
    // "config_method" : "POST"
    // },
    // "config_mapping" :
    // [{
    // "additional_name" : "tes01" ,
    // "additional_value" : "001",
    // "additional_display" : "tesja1"
    // },
    // {
    // "additional_name" : "tes02" ,
    // "additional_value" : "002",
    // "additional_display" : "tesja2"
    // }]
    // }

    @PostMapping("/add")
    public Boolean addConfigUrl(@RequestBody ConfigUrlRequest requestBody) {
        Boolean response = configUrlService.addConfigUrl(requestBody);
        return response;
    }

    @GetMapping("/get_url")
    public ConfigUrlGetter getAllUrl(){
        return configUrlService.getAllUrl();
    }    



}
