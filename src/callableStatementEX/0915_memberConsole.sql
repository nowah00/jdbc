-- 샘플테이블 생성
create table CODE1 (
    CID int,
    cName VARCHAR(50)
);
DESC CODE1;

INSERT INTO CODE1(cid, cname)
select ifnull(max(cid),0)+1 as cld2,'TEST' as cName2
FROM CODE1;
select * from code1;

truncate code1;

-- 프로시져 생셩 P_INSERTCODE()
SET @result = '';

DELIMITER $$
CREATE PROCEDURE P_INSERTCODES(IN cData VARCHAR(255),
                              IN cTname VARCHAR(255),
                              OUT resultMsg VARCHAR(255))
BEGIN
    SET @strsql = CONCAT(
                  'INSERT INTO ',cTname,'(cid,cname)',
                  'SELECT COALESCE(MAX(cid),0)+1,? FROM ', cTname
                  );
    -- 바인딩할 변수 설정
    SET @cData = cData;
    SET @resultMsg = 'Insert Sucess!';

    PREPARE stmt FROM @strsql;
    EXECUTE stmt USING @cData;

    DEALLOCATE PREPARE stmt;
    COMMIT;
END $$
DELIMITER ;

CALL P_INSERTCODES('프론트디자이너','CODE1',@result);





CREATE TABLE TB_MEMBER (
    m_seq INT AUTO_INCREMENT PRIMARY KEY,  -- 자동 증가 시퀀스
    m_userid VARCHAR(20) NOT NULL,
    m_pwd VARCHAR(20) NOT NULL,
    m_email VARCHAR(50) NULL,
    m_hp VARCHAR(20) NULL,
    m_registdate DATETIME DEFAULT NOW(),  -- 기본값: 현재 날짜와 시간
    m_point INT DEFAULT 0
);

DELIMITER @@
    CREATE PROCEDURE SP_MEMBER_INSERT(
        IN V_USERID VARCHAR(20),
        IN V_PWD VARCHAR(20),
        IN V_EMAIL VARCHAR(50),
        IN V_HP VARCHAR(20),
        OUT RTN_CODE INT
    )
    BEGIN
        DECLARE v_count int;
        -- 입력한 USER_ID 가 테이블에 같은 USER_ID 있다면 카운트됌
        SELECT COUNT(m_seq) into v_count FROM TB_MEMBER WHERE M_USERID = V_USERID;
        -- 카운트가 되었을 때 100을, 안됐을때 200을 RTN_CODE 에 입력
        IF v_count > 0 THEN
            SET RTN_CODE = 100;
        ELSE
            INSERT INTO TB_MEMBER (M_USERID, M_pwd, M_EMAIL,M_HP)
            VALUES (V_USERID,V_PWD,V_EMAIL,V_HP);
            SET RTN_CODE = 200;
        END IF;
        COMMIT;
    END  @@
DELIMITER ;

DELIMITER @@
    CREATE PROCEDURE SP_MEMBER_LIST()
    BEGIN
        SELECT m_userid , m_email, m_hp FROM TB_MEMBER;
    end @@
DELIMITER ;

DELIMITER @@
    CREATE PROCEDURE SP_MEMBER_SEARCH_ONE(IN SEQ INT)
    BEGIN
        SELECT m_userid , m_email, m_hp FROM TB_MEMBER WHERE m_seq = SEQ;
    end @@
DELIMITER ;

DELIMITER @@
CREATE PROCEDURE SP_MEMBER_UPDATE(IN UD_PWD VARCHAR(20), IN SEQ INT)
    BEGIN
        UPDATE TB_MEMBER SET m_pwd = UD_PWD WHERE m_seq = SEQ;
    end @@
DELIMITER ;

DELIMITER @@
CREATE PROCEDURE SP_MEMBER_DELETE(IN SEQ INT)
BEGIN
    DELETE FROM TB_MEMBER WHERE m_seq = SEQ;
end @@
DELIMITER ;