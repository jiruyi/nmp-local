
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/3/3 0003 14:16
 * @desc
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperateLog {
    private String id;

    private Boolean isSuccess;

    private Byte channelType;

    private String operModul;

    private String operUrl;

    private String operType;

    private String operDesc;

    private String operRequParam;

    private String operRespParam;

    private String operMethod;

    private String operUserId;

    private String operUserName;

    private String sourceIp;

    private String operLevel;

    private String operTime;

    private String remark;

    private String createTime;

    private Date updateTime;

}
