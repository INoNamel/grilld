package com.grilled;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClientsRepository {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

    @Autowired
    private JdbcTemplate jdbc;

    Login findClient(String tlf) {
        try {
            String query = ("SELECT id, tlf, name, joined " +
                    "FROM client_list " +
                    "WHERE tlf = '"+tlf+"' ");
            SqlRowSet rs = jdbc.queryForRowSet(query);
            Login login = new Login();

            while (rs.next()) {
                login.setId(rs.getInt("id"));
                login.setName(rs.getString("name"));
                login.setTlf_type(rs.getString("tlf"));
                login.setJoined(LocalDate.parse(rs.getString("joined"), formatter));
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
                "drowssap='" + DigestUtils.sha256Hex(login.getPassword()) + "' " +
                "WHERE tlf = " + login.getTlf_type()+ " ");
    }
}
package com.grilled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ClientsRepository {

    @Autowired
    private JdbcTemplate jdbc;

    Client findClient(int id) {
        try {
            String query = ("SELECT * " +
                    "FROM client_list " +
                    "WHERE id = '"+id+"' ");
            SqlRowSet rs = jdbc.queryForRowSet(query);
            Client client = new Client();

            while (rs.next()) {
                client.setId(rs.getInt("id"));
                client.setTlf(rs.getString("tlf"));
                client.setName(rs.getString("name"));
            }

            return client;
        } catch (Exception e) {
            return null;
        }
    }

    List<Client> findAllClients() {
        try {
            String query = ("SELECT * FROM client_list "+
                    " ORDER BY id DESC");
            SqlRowSet rs = jdbc.queryForRowSet(query);

            List<Client> clientList = new ArrayList<>();

            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt("id"));
                client.setName(rs.getString("name"));
                client.setTlf(rs.getString("tlf"));
                clientList.add(client);
            }

            return clientList;

        } catch (Exception e) {
            return null;
        }
    }

    void deleteClient(int id) {
        jdbc.update("DELETE FROM client_list WHERE id = " +id +" ");
    }

    void updateClient(Client client) {
        jdbc.update("UPDATE client_list SET " +
                "name='" + client.getName() + "', " +
                "tlf=" + client.getTlf() + ", " +
                "drowssap=" + client.getPassword() + ", " +
                "WHERE id = " + client.getId()+ " ");
    }
}
