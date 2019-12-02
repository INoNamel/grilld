package com.grilled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ClientsRepository {

    @Autowired
    private JdbcTemplate jdbc;

    Login findClient(int id) {
        try {
            String query = ("SELECT * " +
                    "FROM client_list " +
                    "WHERE id = '"+id+"' ");
            SqlRowSet rs = jdbc.queryForRowSet(query);
            Login login = new Login();

            while (rs.next()) {
                login.setId(rs.getInt("id"));
                login.setTlf_type(rs.getString("tlf"));
                login.setName(rs.getString("name"));
            }

            return login;
        } catch (Exception e) {
            return null;
        }
    }

    List<Login> findAllClients() {
        try {
            String query = ("SELECT * FROM client_list "+
                    " ORDER BY id DESC");
            SqlRowSet rs = jdbc.queryForRowSet(query);

            List<Login> loginList = new ArrayList<>();

            while (rs.next()) {
                Login login = new Login();
                login.setId(rs.getInt("id"));
                login.setName(rs.getString("name"));
                login.setTlf_type(rs.getString("tlf"));
                loginList.add(login);
            }

            return loginList;

        } catch (Exception e) {
            return null;
        }
    }

    void deleteClient(int id) {
        jdbc.update("DELETE FROM client_list WHERE id = " +id+" ");
    }

    void updateClient(Login login) {
        jdbc.update("UPDATE client_list SET " +
                "name='" + login.getName() + "', " +
                "tlf='" + login.getTlf_type() + "', " +
                "drowssap='" + login.getPassword() + "', " +
                "WHERE id = " + login.getId()+ " ");
    }
}
