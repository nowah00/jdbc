package callableStatementEX;

import util.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

public class MemberInsert {
    static Connection conn = DBUtil.getConnection();

    public static void main(String[] args) {

        String m_userid = "blackpink";
        String m_pwd = "blackpink123";
        String m_email = "blackpink@gmail.com";
        String m_hp = "010-1234-1234";

        String sql = "{CALL SP_MEMBER_INSERT(?,?,?,?,?)}";
        try (CallableStatement call = conn.prepareCall(sql)){
            // IN 파라미터
            call.setString(1,m_userid);
            call.setString(2,m_pwd);
            call.setString(3,m_email);
            call.setString(4,m_hp);
            // OUT 파라미터
            call.registerOutParameter(5, Types.INTEGER);
            // 실행
            call.execute();

            int rtn = call.getInt(5);

            if (rtn == 100) {
                System.out.println("이미 가입된 사용자입니다");
            } else {
                System.out.println("성공적으로 가입되었습니다.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
