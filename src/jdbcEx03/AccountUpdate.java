package jdbcEx03;

import jdbcEx01.vo.Person;
import jdbcEx03.vo.Accounts;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountUpdate {
    public static void main(String[] args) {
        Connection connection = DBUtil.getConnection();

        //매개변수화된 UPDATE SQL문 작성
        String sql = new StringBuilder()
                .append(" UPDATE accounts SET ")
                .append("ano = ? ,")
                .append("owner = ? ,")
                .append("balance = ? ")
                .append(" WHERE ano = ?").toString();


        //PreparedStatement 객체 생성 하고 값 지정
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){

            pstmt.setString(1, "111-111-1111");
            pstmt.setString(2,"나겨울");
            pstmt.setInt(3, 1000000);
            pstmt.setString(4, "111-111-1111");

            //SQL 문 실행
            int rows = pstmt.executeUpdate();
            System.out.println(rows + " rows updated!");

            String selectSql = "select * from accounts";
            ResultSet rs = pstmt.executeQuery(selectSql);
            while(rs.next()) {
                jdbcEx03.vo.Accounts accounts = new Accounts();
                accounts.setAno(rs.getString(1));
                accounts.setOwner(rs.getString(2));
                accounts.setBalance(rs.getInt(3));
                System.out.println(accounts.getAno() + " " + accounts.getOwner() + " " + accounts.getBalance());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
