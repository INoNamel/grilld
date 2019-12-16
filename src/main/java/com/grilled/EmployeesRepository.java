package com.grilled;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeesRepository {

    @Autowired
    private JdbcTemplate jdbc;

    Login findEmployee(String name) {
        try {
            String query = ("SELECT * " +
                    "FROM terces " +
                    "WHERE type = 'employee' AND name = '"+name+"' ");
            SqlRowSet rs = jdbc.queryForRowSet(query);
            Login employee = new Login();

            while (rs.next()) {
                employee.setName(rs.getString("name"));
                employee.setPassword(rs.getString("drowssap"));
            }

            return employee;
        } catch (Exception e) {
            return null;
        }
    }

    List<Login> findAllEmployees() {
        try {
            String query = ("SELECT name FROM terces "+
                    "WHERE type = 'employee' ORDER BY name");
            SqlRowSet rs = jdbc.queryForRowSet(query);

            List<Login> employeeList = new ArrayList<>();

            while (rs.next()) {
                Login login = new Login();
                login.setName(rs.getString("name"));
                employeeList.add(login);
            }

            return employeeList;

        } catch (Exception e) {
            return null;
        }
    }

    void deleteEmployee(){}

    void updateEmployee(Login login) {
        jdbc.update("UPDATE terces SET " +
                "name='" + login.getName() + "', " +
                "drowssap='" + login.getPassword() + "', " +
                "WHERE name = '" + login.getName()+ "' ");
    }

    void addEmployee(Login login) {
        jdbc.update(
                "INSERT INTO terces (name, drowssap) VALUES (?, ?)",
                login.getName(), DigestUtils.sha256Hex(login.getPassword())
        );
    }
}
