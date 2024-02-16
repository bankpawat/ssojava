package com.ssotest.SSO_TEST.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public abstract class AbstractDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String sqlCommand;
    private Object[] parameters;
    private int [] type;

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public String getSqlCommand() {
        return sqlCommand;
    }

    public void setSqlCommand(String sqlCommand) {
        this.sqlCommand = sqlCommand;
    }

    public int[] getType() {
        return type;
    }

    public void setType(int[] type) {
        this.type = type;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public String setRowNumSQL(String sql, int pageIndex, int pageSize) {
//        int startRow = getStartRow(pageIndex, pageSize);
        int offset = (pageIndex) * pageSize;
        StringBuilder bf = new StringBuilder(sql);

        if(pageIndex!=0 || pageSize!=0){
            bf.append(" LIMIT ").append(pageSize);
            bf.append(" OFFSET ").append(offset);
        }

        return bf.toString();
    }
    
    public int getStartRow(int pageIndex, int pageSize) {
        return ((pageIndex - 1) * pageSize) + 1;
    }

    private static final String PRE_COUNT_SQL = "select count(*) from (";
    private static final String SUF_COUNT_SQL = ") c ";

    public int countTotal(JdbcTemplate jdbcTemplate, String sql) {
        String sqlSelect = PRE_COUNT_SQL + sql + SUF_COUNT_SQL;
        int count = jdbcTemplate.queryForObject(sqlSelect,Integer.class);
        
        return count;
    }
    
    public int countTotal(JdbcTemplate jdbcTemplate, String sql,ArrayList param) {
        String sqlSelect = PRE_COUNT_SQL + sql + SUF_COUNT_SQL;
        int count = jdbcTemplate.queryForObject(sqlSelect ,Integer.class, param.toArray());
        return count;
    }
    
    public int countTotal(JdbcTemplate jdbcTemplate,String sql, Object[] params) {
        int count = 0;
        if (params != null && params.length > 0) {
        String sqlSelect = PRE_COUNT_SQL + sql + SUF_COUNT_SQL;
            count = jdbcTemplate.queryForObject(sqlSelect ,Integer.class, params);
        } else {
            count = countTotal(jdbcTemplate,sql);
        }
        return count;
    }
    
    public Long getNextSeq(String seqName) {
        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        sql.append(seqName);
        sql.append(".nextval from dual");
        Long nextVal = jdbcTemplate.queryForObject(sql.toString(),Long.class);
        return nextVal;
    }
    
    public Long getNextSeqTemp(String dsName) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT NVL(MAX(SEQ),0)+1 FROM ").append(dsName);
        Long nextVal = jdbcTemplate.queryForObject(sql.toString(),Long.class);
        return nextVal;
    }
    
    public Long  lastInsertSeq(String dsName) {  
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT NVL(MAX(SEQ),0) FROM ").append(dsName);
        Long val = jdbcTemplate.queryForObject(sql.toString(),Long.class);


       return val;
    }

    
}
