package jebcEx01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserInsertEx {
    public static void main(String[] args) {
        Connection con = null;
        try {
            // 1. DB의 드라이버를 찾아서 로드해야 한다.  MYSQL JDBC 드라이버 등록
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully!");

            // 2. 드라이버 로드가 OK 됐다면, 연결 connection 객체를 생성한다.
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookmarketdb?serverTimezone=Asia/Seoul", "root", "mysql1234");
            System.out.println("Connection established!" + con);

            // 3. Connection 객체가 생성되었다면, 퀴리문을 담아 Statements 객체에 담아 DB에 전송한다.
            String sql = " insert into users(userid,username,userpassword,userage,useremail) " +
                         " values('10','신세계','1234','20','shin@gmail.com')";

            // 4. 전송한 결과를 받아서 처리한다.
            int result = con.createStatement().executeUpdate(sql);
            if (result == 1) {
                System.out.println("Insert successful!");
            } else {
                System.out.println("Insert failed!");
            }

        } catch (Exception e) {
            System.out.println("Driver loaded failed..");
        } finally {
            // 5. 다 사용한 Statements 와 Connection 객체를 닫는다.
            if (con != null) {
                try{
                    con.close();
                    System.out.println("Connection closed!");
                } catch (SQLException e) {
                    throw new RuntimeException();
                }
            }
        }
    }
}
