package com.ssotest.SSO_TEST.dao;

import java.util.List;

import com.ssotest.SSO_TEST.model.ConfigUrlMap;
import com.ssotest.SSO_TEST.model.ConfigUrlParent;

public interface ConfigUrlDao {
    public Boolean addConfigUrl(ConfigUrlParent parentRequest,List<ConfigUrlMap> childReqest);
    public List<ConfigUrlParent> getConfigBody();
    public List<ConfigUrlMap> getConfigMap();
    public Boolean addConfigUrlSingle(ConfigUrlParent parentRequest);

}
