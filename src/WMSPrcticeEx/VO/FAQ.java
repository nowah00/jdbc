package WMSPrcticeEx.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class FAQ {
    private String FAQ_id;
    private String FAQ_category_name;
    private String FAQ_content;
    private String member_id;
    private String manager_id;
}
