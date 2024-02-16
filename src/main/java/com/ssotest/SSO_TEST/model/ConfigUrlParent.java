package com.ssotest.SSO_TEST.model;

import lombok.Data;

@Data
public class ConfigUrlParent {
    private int config_id;
    private String config_display_name;
    private String config_url;
    private String config_user;
    private String config_pwd;
    private String config_method;
    private String config_send_location;
}
