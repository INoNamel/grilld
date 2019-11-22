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
