package jdbcEx01;

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
            PreparedStatement pstmt = con.prepareStatement(sql);

            // 값 지정
            pstmt.setString(1, "1111-1212-3232-12121");
            pstmt.setString(2, "홍길동");
            pstmt.setInt(3, 100000);

            // SQL 실행
            int result = pstmt.executeUpdate();
            System.out.println("저장된 행의 수: " + result);

            // ---- 여기서 SELECT 실행해서 출력 ----
            String selectSql = "SELECT ano, owner, balance FROM accounts";
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(selectSql)) {

                while (rs.next()) {
                    String ano = rs.getString("ano");
                    String owner = rs.getString("owner");
                    int balance = rs.getInt("balance");
                    System.out.println("계좌번호: " + ano + ", 소유주: " + owner + ", 잔액: " + balance);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}