package jdbc_boards.Controller;

import jdbc_boards.model.BoardDAO;
import jdbc_boards.vo.Board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;

public class BoardMenu {
    BoardDAO dao;
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    public BoardMenu(){
        dao = new BoardDAO();
    }

    public void boardMenu() throws IOException {
        KIKI:
        while(true) {
            System.out.println("\n메인 메뉴: 1.Create | 2.Read | 3.Update | 4.Delete | 5.Exit");
            System.out.print("메뉴 선택: ");
            int choice = 0;
            try {
                choice = Integer.parseInt(input.readLine());
            } catch (IOException e) {
                System.out.println("입력도중 에러 발생");
            } catch (NumberFormatException e1) {
                System.out.println("숫자만 입력하라니까");
            } catch (Exception e2) {
                System.out.println("꿰엑 에라 모르겠다.");
            }
            switch (choice) {
                case 1:
                    //사용자에게 title,content를 입력받아서 Board 구성하여 createBoard()넘겨주자
                    Board row = boardDataInput();
                    boolean ack = dao.createBoard(row);
                    if (ack == true) System.out.println("글이 성공적으로 입력되었습니다.");
                    else {
                        System.out.println("입력 실패, 다시 시도 부탁드립니다. ");
                    } break;
                case 2:
                    readOption();
                    break;
                case 3:
                    Board udBoard = boardDataUpdate();
                    boolean ok = dao.updateBoard(udBoard);
                    if (ok == true) System.out.println("글이 성공적으로 수정되었습니다.");
                    else {
                        System.out.println("수정 실패, 다시 시도 부탁드립니다. ");
                    }
                    break;
                case 4:
                    System.out.println("\n[삭제할 글 번호 입력]");
                    System.out.print("> ");
                    int bno = Integer.parseInt(input.readLine());
                    boolean ko = dao.deleteBoard(bno);
                    if (ko == true) System.out.println("글이 성공적으로 삭제되었습니다.");
                    else {
                        System.out.println("삭제 실패, 다시 시도 부탁드립니다. ");
                    }
                    break;
                case 5:
                    System.out.println("[종료]");
                    break KIKI;
            }
        }
    }

    public Board boardDataInput() throws IOException{
        Board board = new Board();
        System.out.println("\n[새로운 글 입력]");
        System.out.println("제목 입력");
        System.out.print("> ");
        String title =input.readLine();
        board.setBtitle(title);
        System.out.println("내용 입력");
        System.out.print("> ");
        String content = input.readLine();
        board.setBcontent(content);
        System.out.println("글쓴이 입력");
        System.out.print("> ");
        String writer = input.readLine();
        board.setBwriter(writer);
        return board;
    }

    public void readOption() throws IOException {
        System.out.println("\n메인 메뉴: 1.Select All | 2.Select One");
        System.out.print("> ");
        int readChoice = 0;
        try{
            readChoice = Integer.parseInt(input.readLine());
        }catch (IOException e){
            System.out.println("입력도중 에러 발생");
        }catch (NumberFormatException e1){
            System.out.println("숫자만 입력하라니까");
        }catch (Exception e2){
            System.out.println("꿰엑 에라 모르겠다.");
        }
        switch (readChoice) {
            case 1:
                List<Board> boardList = dao.searchAll();
                boardList.stream().sorted(Comparator.comparing(Board::getBno).reversed()).forEach(System.out::println);
                break;
            case 2:
                System.out.println("\n[보드 번호를 입력하세요.]");
                System.out.print("> ");
                int bno = Integer.parseInt(input.readLine());
                Board oneBoard = dao.searchOne(bno);
                if (oneBoard == null) {
                    System.out.println("[해당 글이 없습니다.]");
                } else {System.out.println(oneBoard);}
                break;
        }
    }

    public Board boardDataUpdate() throws IOException{
        Board board = new Board();
        System.out.println("\n[수정할 글 번호 입력]");
        System.out.print("> ");
        int bno = Integer.parseInt(input.readLine());
        board.setBno(bno);
        System.out.println("수정할 제목");
        System.out.print("> ");
        String title = input.readLine();
        board.setBtitle(title);
        System.out.println("수정할 내용");
        System.out.print("> ");
        String content = input.readLine();
        board.setBcontent(content);
        return board;
    }
}
