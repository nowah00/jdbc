package WMSPrcticeEx;

import WMSPrcticeEx.VO.Notice;
import jdbc_boards.util.DBUtil;
import jdbc_boards.vo.Board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CSDAO {
    private Connection conn;

    List<Notice> noticeList = new ArrayList<>();

    public boolean createNotice(Notice notice) {
        // 1. Connection 필요
        conn = DBUtil.getConnection();

        // 2.쿼리 생성
        String sql = "INSERT INTO notice(notice_title, notice_date, notice_fixed) VALUES(?,NOW(),?)";

        // 3.Connection 쿼리를 담아 DB 서버로 request 할 객체 PrepareStatement 생성
        try (PreparedStatement pstmt = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS)){ // 쿼리를 보내는 동시에 PK를 가져오기
            // 4. 값을 생성
            pstmt.setString(1, notice.getNotice_title());
            pstmt.setBoolean(2, notice.getNotice_fixed);
            // 5. 서버로 전송 후 결과값 (int 성공 1 실패 0)
            int affected = pstmt.executeUpdate();
            boolean ok = affected > 0;
            // 생성된 PK를 Board 객체에 반영
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int newNotice_id = rs.getInt(1);
                    notice.setNotice_id(newNotice_id);
                    noticeList.add(notice);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return ok;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
