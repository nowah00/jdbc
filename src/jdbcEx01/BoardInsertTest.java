package jdbcEx01;

import java.io.FileInputStream;
import java.sql.*;

public class BoardInsertTest {
    public static void main(String[] args) {
        String DriverName ="com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/bookmarketdb?serverTimezone=Asia/Seoul";
        String username = "root";
        String password = "mysql1234";

        try{
            Class.forName(DriverName);
            System.out.println("Driver loaded OK!");
        } catch (Exception e) {
            System.out.println("Driver loaded failed!");
        }
        try(Connection con = DriverManager.getConnection(url, username, password)){  //도로연결
            System.out.println("AutoCommit 상태: " + con.getAutoCommit());
            con.setAutoCommit(true);

            // 매개변수화된 SQL문
            String sql = "INSERT INTO boards(btitle,bcontent,bwriter,bdate,bfilename,bfiledata) values(?,?,?,now(),?,?)";

            //PreparedStatement 얻기
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //값 지정
            pstmt.setString(1, "봄이 오나 봄봄1111");
            pstmt.setString(2,"꽃이 동산에 가득하니 행복하네요1111");
            pstmt.setString(3,"신세계111");
            pstmt.setString(4,"spring.jpg");
            pstmt.setBlob(5,new FileInputStream("C:/Temp/spring.jpg"));

            //SQL문 실행
            int result = pstmt.executeUpdate();
            System.out.println("저장된 행의 수" + result);

            //bno 값 얻기
            if(result == 1){
                ResultSet rs = pstmt.getGeneratedKeys();
                if(rs.next()){
                    int bno = rs.getInt(1);
                    System.out.println("bno = " + bno);
                }
                rs.close();
            }
            if (result == 1) {
                System.out.println("Insert successful!");
            }
        } catch (Exception e) {
            System.out.println("Connection established!" + e);
        }
    }
}