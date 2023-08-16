
import com.matrictime.network.request.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/3/3 0003 17:58
 * @desc
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDetail extends BaseRequest {

    private String id;

    private String userId;

    private String userName;

    private String loginAccount;

    private String loginIp;

    private String loginAddr;

    private Byte loginType;

    private Boolean isSuccess;

    private String failCause;

    private String loginTime;
}
