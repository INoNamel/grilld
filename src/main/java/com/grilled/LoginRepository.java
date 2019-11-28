package com.grilled;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class LoginRepository {

    @Autowired
    private JdbcTemplate jdbc;

    boolean verifyPass(Login login) {
        String query = ("SELECT name, tlf as type_or_tlf, drowssap FROM client_list WHERE name = '"+login.getName()+"' " +
                "UNION SELECT name, type as type_or_tlf, drowssap FROM terces " +
                "WHERE name = '"+login.getName()+"' ");
        SqlRowSet rs = jdbc.queryForRowSet(query);
        while (rs.next()) {
            if(DigestUtils.sha256Hex(login.getPassword()).equals(rs.getString("drowssap"))) {
                login.setType(rs.getString("type_or_tlf"));
                return true;
            }
        }
        return false;
    }
}
