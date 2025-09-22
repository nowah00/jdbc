package WMSPrcticeEx.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class inquiryVO {
    private String inquiry_id;
    private Date inquiry_date;
    private String inquiry_category_name;
    private String inquiry_content;
    private String inquiry_status;
    private Date reply_date;
    private String reply_content;
    private String member_id;
    private String manager_id;
}
