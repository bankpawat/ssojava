package com.ssotest.SSO_TEST.dao.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ssotest.SSO_TEST.dao.AbstractDao;
import com.ssotest.SSO_TEST.dao.ConfigUrlDao;
import com.ssotest.SSO_TEST.model.ConfigUrlMap;
import com.ssotest.SSO_TEST.model.ConfigUrlParent;

@Repository
public class ConfigUrlDaoImpl extends AbstractDao implements ConfigUrlDao {

    @Override
    public Boolean addConfigUrl(ConfigUrlParent parentRequest, List<ConfigUrlMap> childReqest) {
        try {

            StringBuilder sql = new StringBuilder();
            ArrayList param = new ArrayList();

            sql.append(
                    "WITH inserted_row AS (INSERT INTO config_table (config_display_name, config_url, config_user, config_pwd, config_method , config_send_location)");
            sql.append("VALUES (?,?,?,?,?,?) RETURNING config_id )");
            param.add(parentRequest.getConfig_display_name());
            param.add(parentRequest.getConfig_url());
            param.add(parentRequest.getConfig_user());
            param.add(parentRequest.getConfig_pwd());
            param.add(parentRequest.getConfig_method());
            param.add(parentRequest.getConfig_send_location());
            int count = 0;
            sql.append(
                    "INSERT INTO additional_table (additional_match_id, additional_name, additional_value, additional_display)");
            for (ConfigUrlMap i : childReqest) {
                sql.append("SELECT config_id, ? , ? , ?");
                sql.append(" FROM (SELECT config_id FROM inserted_row) AS subquery");
                param.add(i.getAdditional_name());
                param.add(i.getAdditional_value());
                param.add(i.getAdditional_display());
                count++;
                if (count != childReqest.size()) {
                    sql.append(" UNION ALL ");
                }
            }
            sql.append(";");

            System.out.println(sql.toString() + "\n");
            int result = getJdbcTemplate().update(sql.toString(), param.toArray());
            if (result > 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception err) {
            System.out.println(err);
            return false;
        }

    }

    @Override
    public List<ConfigUrlParent> getConfigBody() {
        List<ConfigUrlParent> response = new ArrayList<ConfigUrlParent>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM config_table");

            response = getJdbcTemplate().query(sql.toString(), new RowMapper<ConfigUrlParent>() {
                @Override
                public ConfigUrlParent mapRow(ResultSet rs, int rowNum) throws SQLException {

                    ConfigUrlParent obj = new ConfigUrlParent();
                    obj.setConfig_id(rs.getInt("config_id"));
                    obj.setConfig_display_name(rs.getString("config_display_name"));
                    obj.setConfig_url(rs.getString("config_url"));
                    obj.setConfig_user(rs.getString("config_user"));
                    obj.setConfig_pwd(rs.getString("config_pwd"));
                    obj.setConfig_method(rs.getString("config_method"));
                    obj.setConfig_send_location(rs.getString("config_send_location"));
                    return obj;
                }
            });

            return response;

        } catch (Exception err) {
            System.out.println(err);
            return null;
        }

    }

    @Override
    public List<ConfigUrlMap> getConfigMap() {
        List<ConfigUrlMap> response = new ArrayList<ConfigUrlMap>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM additional_table");
            response = getJdbcTemplate().query(sql.toString(), new RowMapper<ConfigUrlMap>() {
                @Override
                public ConfigUrlMap mapRow(ResultSet rs, int rowNum) throws SQLException {

                    ConfigUrlMap obj = new ConfigUrlMap();
                    obj.setAdditional_id(rs.getInt("additional_id"));
                    obj.setAdditional_display(rs.getString("additional_display"));
                    obj.setAdditional_value(rs.getString("additional_value"));
                    obj.setAdditional_name(rs.getString("additional_name"));
                    obj.setAdditional_match_id(rs.getInt("additional_match_id"));
                    return obj;
                }
            });
            return response;
        } catch (Exception err) {
            System.out.println(err);
            return null;
        }
    }

    @Override
    public Boolean addConfigUrlSingle(ConfigUrlParent parentRequest) {
        try {

            StringBuilder sql = new StringBuilder();
            ArrayList param = new ArrayList();

            sql.append(
                    "INSERT INTO config_table (config_display_name, config_url, config_user, config_pwd, config_method , config_send_location)");
            sql.append("VALUES (?,?,?,?,?,?);");
            param.add(parentRequest.getConfig_display_name());
            param.add(parentRequest.getConfig_url());
            param.add(parentRequest.getConfig_user());
            param.add(parentRequest.getConfig_pwd());
            param.add(parentRequest.getConfig_method());
            param.add(parentRequest.getConfig_send_location());
            int result = getJdbcTemplate().update(sql.toString(), param.toArray());
            if (result > 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception err) {
            System.out.println(err);
            return false;
        }
    }

}
