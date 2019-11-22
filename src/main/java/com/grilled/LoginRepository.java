package com.grilled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class LoginRepository {

    @Autowired
    private JdbcTemplate jdbc;

    boolean verifyPass(Login login) {
        String query = ("SELECT * FROM terces WHERE name = '"+login.getName()+"' ");
        SqlRowSet rs = jdbc.queryForRowSet(query);
        while (rs.next()) {
            if(login.getPassword().equals(rs.getString("drowssap"))) {
                login.setType(rs.getString("type"));
                return true;
            }
        }
        return false;
    }
}
