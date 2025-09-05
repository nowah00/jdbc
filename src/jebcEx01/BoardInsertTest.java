package jebcEx01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BoardInsertTest {
    public static void main(String[] args) {
        String DriverName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/bookmarketdb?serverTimezone=Asia/Seoul";
        String username = "root";
        String password = "mysql1234";

        try{
            Class.forName(DriverName);
            System.out.println("Driver loaded Successfully!");
        } catch (Exception e) {
            System.out.println("Driver loaded failed.");
        }

        try {
            Connection con = DriverManager.getConnection(url,username,password);

            String sql = "insert into users values(?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1,"20");
            ps.setString(2,"고하원");
            ps.setString(3,"hawon");
            ps.setString(4,"26");
            ps.setString(5,"hw@gmail.com");

            int result = ps.executeUpdate();
            System.out.println("저장된 행의 수 " + result);

            if (result == 0) {
                System.out.println("Insert Successful!.");


            }
        } catch (SQLException e) {
            System.out.println("Connection established.");
        }
    }
}
