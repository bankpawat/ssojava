package com.ssotest.SSO_TEST.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.servlet.view.RedirectView;

import com.ssotest.SSO_TEST.model.LoginSsoRequest;
import com.ssotest.SSO_TEST.service.LoginSsoService;

@RestController
@RequestMapping("/login")
public class LoginSsoController {

    private final LoginSsoService loginSsoService;

    @Autowired
    public LoginSsoController(LoginSsoService loginSsoService) {
        this.loginSsoService = loginSsoService;
    }

    @PostMapping("/sso")
    public String testUrl(@RequestBody LoginSsoRequest loginSsoRequest) {

        String response = loginSsoService.checkLoginSso(loginSsoRequest.getRequest_body(),
                loginSsoRequest.getSso_username(), loginSsoRequest.getSso_password());

        if (response == null) {
            return null;
        } else {
            return response;
        }
    }

}
