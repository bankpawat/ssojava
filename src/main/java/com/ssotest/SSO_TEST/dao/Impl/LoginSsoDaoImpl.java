package com.ssotest.SSO_TEST.dao.Impl;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.ssotest.SSO_TEST.dao.AbstractDao;
import com.ssotest.SSO_TEST.dao.LoginSsoDao;


@Repository
public class LoginSsoDaoImpl extends AbstractDao implements LoginSsoDao {

    @Override
    public Boolean CheckLoginFirst(String username, String password) {
        try{

            StringBuilder sql = new StringBuilder();
            ArrayList param = new ArrayList();

            sql.append("SELECT COUNT(*) FROM user_table WHERE username_test = ? AND password_test = ?");

            param.add(username);
            param.add(password);

            int count = getJdbcTemplate().queryForObject(sql.toString(), Integer.class , param.toArray());

            if(count > 0){
                return true;
            }
            else{
                return false;
            }

        }catch(Exception err){
            System.out.println(err);
            return false;
        }

    }
    
}
