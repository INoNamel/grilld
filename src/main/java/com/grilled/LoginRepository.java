package com.grilled;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class LoginRepository {

    @Autowired
    private JdbcTemplate jdbc;

    boolean verifyPass(Login login) {
        String query = ("SELECT name, tlf as type_or_tlf, drowssap FROM client_list " +
                "WHERE tlf = '"+ login.getTlf_type()+"'  " +
                "UNION SELECT name, type as type_or_tlf, drowssap FROM terces " +
                "WHERE name = '"+ login.getTlf_type()+"' ");
        SqlRowSet rs = jdbc.queryForRowSet(query);
        while (rs.next()) {
            if(DigestUtils.sha256Hex(login.getPassword()).equals(rs.getString("drowssap"))) {
                login.setTlf_type(rs.getString("type_or_tlf"));
                login.setName(rs.getString("name"));
                return true;
            }
        }
        return false;
    }

    boolean verifySingUp(Login login) {
        return (login.getTlf_type().isEmpty() || login.getTlf_type().equalsIgnoreCase("admin") || login.getTlf_type().equalsIgnoreCase("employee") || login.getName().isEmpty() || login.getName().length()<3 || login.getName().length()>20 || !login.getName().matches("[a-zA-Z]+") || login.getPassword().isEmpty() || login.getPassword().length()<5 || login.getPassword().length()>16 || !login.getTlf_type().matches("[0-9]+") || login.getTlf_type().length()<6 || login.getTlf_type().length()>10);
    }

    void signUp(Login login) {
        jdbc.update(
                "INSERT INTO client_list (name, tlf, drowssap) VALUES (?, ?, ?)",
                login.getName(), login.getTlf_type(), DigestUtils.sha256Hex(login.getPassword())
        );
    }
}
