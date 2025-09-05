package jdbcEx01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConnectionTest {
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
        try(Connection con = DriverManager.getConnection(url, username, password)){ //도로연결
            System.out.println("AutoCommit 상태: " + con.getAutoCommit());
            con.setAutoCommit(false);
            Statement stmt = con.createStatement(); //자동차 생성
            int result = stmt.executeUpdate("insert into person(id,name) values(100,'홍길동')");
            if(result == 1){
                System.out.println("Insert Successfully!");
            }
        } catch (Exception e) {
            System.out.println("Connection established!" + e.getMessage());
        }

//        Connection con = null;
//        try {
//            Class.forName(DriverName);
//            System.out.println("Driver loaded successfully!");
//
//            con = DriverManager.getConnection(url, username, password);
//            System.out.println("Connection established!" + con);
//
//            String sql = " insert into person(id,name) " +
//                    " values('28','김현지')";
//
//            int result = con.createStatement().executeUpdate(sql);
//            if (result == 1) {
//                System.out.println("Insert successful!");
//            } else {
//                System.out.println("Insert failed!");
//            }
//
//        } catch (Exception e) {
//            System.out.println("Driver loaded failed..");
//        } finally {
//            if (con != null) {
//                try{
//                    con.close();
//                    System.out.println("Connection closed!");
//                } catch (SQLException e) {
//                    throw new RuntimeException();
//                }
//            }
//        }
    }
}
