package com.dangerducks.cookit.DB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DBFunct {
    private DBC con = new DBC();

    public boolean Login(String name, String pass) throws SQLException {
        String query = "SELECT Id,username,password FROM Users WHERE username='" + name + "'";
        ResultSet rs = con.SearchQuery(query);
        if (rs.getString("username").equals(name) && rs.getString("password").equals(pass)) {
            return true;
        }
        return false;
    }

    public boolean RegisterUser(String name, String pass, String email, String Alergias) {
        String query = "INSERT INTO Users (username,password,email,alergias) VALUES ('" + name + "','" + pass + "','" + email + "','" + Alergias + "')";
        return con.InsertQuery(query);
    }


}