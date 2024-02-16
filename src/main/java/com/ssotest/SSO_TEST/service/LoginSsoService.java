package com.ssotest.SSO_TEST.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.ssotest.SSO_TEST.model.ConfigUrlMap;
import com.ssotest.SSO_TEST.model.ConfigUrlParent;
import com.ssotest.SSO_TEST.model.ConfigUrlRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Service
public class LoginSsoService {
    private final RestTemplate restTemplate;

    public LoginSsoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String checkLoginSso(ConfigUrlRequest ConfigBody, String Username, String Password) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> requestEntity = new HttpEntity<>(null, headers);// BODY , PARAMS

            ConfigUrlParent temp_body = ConfigBody.getConfig_body();
            List<ConfigUrlMap> temp_mapping = ConfigBody.getConfig_mapping();

            String UrlString = temp_body.getConfig_url();
            String MethodString = temp_body.getConfig_method();
            String UsernameFiled = temp_body.getConfig_user();
            String PasswordFiled = temp_body.getConfig_pwd();

            UrlString = UrlString + "?" + UsernameFiled + "=" + Username + "&" + PasswordFiled + "=" + Password;
            if (temp_mapping.size() > 0) {
                for (ConfigUrlMap i : temp_mapping) {
                    UrlString = UrlString + "&" + i.getAdditional_name() + "=" + i.getAdditional_value();
                }
            }
            if (MethodString.equals("POST")) {
                String response = restTemplate.postForObject(UrlString, requestEntity, String.class);
                String responseRedirect = extractRedirectUrl(response);
                System.out.println(responseRedirect);
                return responseRedirect;
            } else {
                String response = restTemplate.getForObject(UrlString, String.class);
                String responseRedirect = extractRedirectUrl(response);

                return responseRedirect;
            }

        } catch (Exception err) {
            System.out.println(err);
            return "false";
        }
    }

    public static String extractRedirectUrl(String htmlResponse) {
        String regex = "<a\\s+href=\"([^\"]+)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(htmlResponse);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }

}
