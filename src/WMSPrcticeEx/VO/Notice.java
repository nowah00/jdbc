package WMSPrcticeEx.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Notice {
    private int notice_id;
    private String notice_title;
    private String notice_content;
    private Date notice_date;
    private boolean notice_fixed;
    private String member_id;
}
