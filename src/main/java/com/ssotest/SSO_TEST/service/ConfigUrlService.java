package com.ssotest.SSO_TEST.service;

import java.util.ArrayList;
import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// import org.springframework.web.client.RestTemplate;

import com.ssotest.SSO_TEST.dao.ConfigUrlDao;
import com.ssotest.SSO_TEST.model.ConfigUrlGetter;
import com.ssotest.SSO_TEST.model.ConfigUrlMap;
import com.ssotest.SSO_TEST.model.ConfigUrlParent;
import com.ssotest.SSO_TEST.model.ConfigUrlRequest;

@Service
public class ConfigUrlService {

    private final ConfigUrlDao configUrlDao;

    public ConfigUrlService(ConfigUrlDao configUrlDao) {
        this.configUrlDao = configUrlDao;
    }

    public Boolean addConfigUrl(ConfigUrlRequest requestBody) {

        ConfigUrlParent parent = new ConfigUrlParent();
        List<ConfigUrlMap> child = new ArrayList<>();

        try {

            parent = requestBody.getConfig_body();
            child = requestBody.getConfig_mapping();
            Boolean response = false;
            if(child.size() == 0){
                response = configUrlDao.addConfigUrlSingle(parent);
            }
            else{
                response = configUrlDao.addConfigUrl(parent, child);
            }

            return response;

        } catch (Exception err) {
            System.out.println(err);
            return false;
        }
    }

    public ConfigUrlGetter getAllUrl(){

        ConfigUrlGetter response = new ConfigUrlGetter();
        try{
            List<ConfigUrlRequest> result = new ArrayList<ConfigUrlRequest>();
            List<ConfigUrlParent> parents = configUrlDao.getConfigBody();
            List<ConfigUrlMap> childs = configUrlDao.getConfigMap();

            for (ConfigUrlParent i : parents){
                ConfigUrlRequest temp = new ConfigUrlRequest();
                temp.setConfig_body(i);

                List<ConfigUrlMap> configMap = new ArrayList<ConfigUrlMap>();
                for (ConfigUrlMap j : childs){
                    if(j.getAdditional_match_id() == i.getConfig_id()){
                        configMap.add(j);
                    }
                }
                // System.out.println(childs);

                temp.setConfig_mapping(configMap);
                result.add(temp);
            }

            response.setData(result);

            return response;
            
        } catch(Exception err){
            System.out.println(err);
            return null;
        }
    }
}
