package com.dangerducks.cookit.DB;
import java.sql.ResultSet;

public class DBFunct {
    private DBC con = new DBC();

    public boolean Login(String name, String pass) {
        String query = "SELECT Id,username,password FROM Users WHERE username='" + name + "'";
        ResultSet rs = con.SearchQuery(query);
        boolean u = false, p = false;

        try {
            u = rs.getString("username").equals(name);
            p = rs.getString("password").equals(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (u && p) {
            return true;
        }
        return false;
    }

    public boolean RegisterUser(String name, String pass, String email, String Alergias) {
        String query = "INSERT INTO Users (username,password,email,alergias) VALUES ('" + name + "','" + pass + "','" + email + "','" + Alergias + "')";
        return con.InsertQuery(query);
    }


}