package com.ssotest.SSO_TEST.model;

import java.util.List;

import lombok.Data;

@Data
public class ConfigUrlRequest {
    private ConfigUrlParent config_body;
    private List<ConfigUrlMap> config_mapping;
}
