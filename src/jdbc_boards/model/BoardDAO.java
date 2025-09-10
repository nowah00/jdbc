package jdbc_boards.model;

import jdbc_boards.util.DBUtil;
import jdbc_boards.vo.Board;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {

    private Connection conn;
    List<Board> boardList = new ArrayList<>();

    public boolean createBoard(Board board) {

            // 1. Connection 필요
            conn = DBUtil.getConnection();
            // 2.쿼리 생성
            String sql = "INSERT INTO BoardTable(btitle, bcontent, bwriter, bdate) VALUES(?,?,?,NOW())";
            // 3.Connection 쿼리를 담아 DB 서버로 request 할 객체 PrepareStatement 생성
            try (PreparedStatement pstmt = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS)){ // 쿼리를 보내는 동시에 PK를 가져오기
            // 4. 값을 생성
                pstmt.setString(1, board.getBtitle());
                pstmt.setString(2, board.getBcontent());
                pstmt.setString(3, board.getBwriter());
            // 5. 서버로 전송 후 결과값 (int 성공 1 실패 0)
                int affected = pstmt.executeUpdate();
                boolean ok = affected > 0;
            // 생성된 PK를 Board 객체에 반영
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int newbno = rs.getInt(1);
                        board.setBno(newbno);
                        boardList.add(board);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return ok;
            } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
    }

    public List<Board> searchAll(){
        // 1.Connection 연결
        conn = DBUtil.getConnection();
        // 2.쿼리 작성
        String sql = "SELECT * FROM BoardTable";
        // 3. DB 서버로 request 할 객체 생성
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Board board = new Board();
                board.setBno(rs.getInt(1));
                board.setBtitle(rs.getString(2));
                board.setBcontent(rs.getString(3));
                board.setBwriter(rs.getString(4));
                board.setBdate(rs.getDate(5));
                boardList.add(board);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return boardList;
    }

    public Board searchOne(int bno){
        // 1.Connection 연결
        conn = DBUtil.getConnection();
        // 2.Return 할 객체 생성
        Board oneBoard = new Board();
        // 3.쿼리 작성
        String sql = "SELECT * FROM BoardTable WHERE bno = ?";
        // 4. DB 서버에 request 할 객체 생성
        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
        // 5. 값(PK) 입력
            pstmt.setInt(1, bno);
        // 6. 값을 받
            ResultSet rs = pstmt.executeQuery(); // 반환된 결과를 rs 객체에 저장
            if (rs.next()) { // 결과 테이블의 첫번쨰 행이 있으면 oneBoard 객체에 저장
                oneBoard.setBno(rs.getInt(1));
                oneBoard.setBtitle(rs.getString(2));
                oneBoard.setBcontent(rs.getString(3));
                oneBoard.setBwriter(rs.getString(4));
                oneBoard.setBdate(rs.getDate(5));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return oneBoard; // 선택한 보드를 리턴
    }

    public Board updateBoard(int bno){

        conn = DBUtil.getConnection();

        String sql = "UPDATE BoardTable SET (btitle, bcontent, bwriter, bdate) = (?,?,?,NOW()) WHERE bno = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(4, bno);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

//    public boolean deleteBoard(int bno){}
}
