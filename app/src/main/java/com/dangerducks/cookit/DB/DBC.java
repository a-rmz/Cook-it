package com.dangerducks.cookit.DB;
import java.sql.*;

public class DBC {
    /*private static String url = "jdbc:mysql://danducks.heliohost.org/danducks_DB";
    private static String username = "danducks_DBadmin";
    private static String password = "pass1234";*/
    static String url = "jdbc:mysql://217.199.187.196:3306/cl55-danducks";
    private static String username = "cl55-danducks";
    private static String password = "pass1234";

    private static Connection GetConnection() throws Exception{
        String driver = "com.mysql.jdbc.Driver";
        Class.forName(driver).newInstance();
        return DriverManager.getConnection(url,username,password);
    }

    public boolean InsertQuery(String query){
        try{
            Connection conn = GetConnection();
            Statement st = conn.createStatement();
            st.executeUpdate(query);
            st.close();
            conn.close();
            return true;

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public ResultSet SearchQuery(String query){
        try{
            Connection conn = GetConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next())
                return rs;
            st.close();
            conn.close();


        }
        catch(Exception e){

            System.out.println(e.toString());
        }
        return null;
    }
}