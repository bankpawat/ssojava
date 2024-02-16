package com.ssotest.SSO_TEST.model;

import lombok.Data;

@Data
public class LoginSsoRequest {

    private ConfigUrlRequest request_body;
    private String sso_username;
    private String sso_password;
    
}
