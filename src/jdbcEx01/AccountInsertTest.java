package jdbcEx01;

import jdbcEx01.vo.Person;

import java.sql.*;

public class AccountInsertTest {
    public static void main(String[] args) {
        String DriverName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/bookmarketdb?serverTimezone=Asia/Seoul";
        String username = "root";
        String password = "mysql1234";

        try {
            Class.forName(DriverName);
            System.out.println("Driver loaded OK!");
        } catch (Exception e) {
            System.out.println("Driver loaded failed!");
        }

        try (Connection con = DriverManager.getConnection(url, username, password)) {
            System.out.println("AutoCommit 상태: " + con.getAutoCommit());
            con.setAutoCommit(true);

            // INSERT SQL
            String sql = "INSERT INTO accounts(ano, owner, balance) VALUES (?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // 값 지정
            pstmt.setString(1, "1111-2222-3333-4445");
            pstmt.setString(2, "고하원");
            pstmt.setInt(3, 500000);

            // SQL 실행
            int result = pstmt.executeUpdate();
            System.out.println("저장된 행의 수" + result);

            String selectSql = "select ano, owner, balance from accounts";
            ResultSet rs = pstmt.executeQuery(selectSql);
            while(rs.next()) {
                jdbcEx01.vo.Person person = new Person();
                person.setId(rs.getInt(1));
                person.setName(rs.getString(2));
                System.out.println(person.toString());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}