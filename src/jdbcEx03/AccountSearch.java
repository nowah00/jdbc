package jdbcEx03;

import jdbcEx03.vo.Accounts;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountSearch {
    public static void main(String[] args) {
        Connection connection = DBUtil.getConnection();

        String sql = "select * from accounts WHERE ano = ?";

        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "111-111-2222");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                jdbcEx03.vo.Accounts accounts = new Accounts();
                accounts.setAno(rs.getString(1));
                accounts.setOwner(rs.getString(2));
                accounts.setBalance(rs.getInt(3));
                System.out.println("계좌번호: " + accounts.getAno() + " 계좌주: " + accounts.getOwner() + " 계좌잔액: " + accounts.getBalance());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
