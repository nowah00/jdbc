package callableStatementEX;

import callableStatementEX.vo.Member;
import util.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberSearchOne {
    static Connection conn = DBUtil.getConnection();

    public static void main(String[] args) {
        List<Member> memberList = new ArrayList<>();

        String sql = "{CALL SP_MEMBER_SEARCH_ONE(?)}";
        try (CallableStatement call = conn.prepareCall(sql)){
            call.setInt(1,1);
            ResultSet rs = call.executeQuery();
                if (rs.next()){
                    Member member = new Member();
                    member.setUserid(rs.getString("m_userid"));
                    member.setEmail(rs.getString("m_email"));
                    member.setHp(rs.getString("m_hp"));
                    memberList.add(member);
                }
            memberList.forEach(System.out::println);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
