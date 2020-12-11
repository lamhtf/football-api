package page.lamht.football.mo;

import lombok.Data;
import page.lamht.football.entity.AlertMsg;

import java.io.Serializable;
import java.util.List;

@Data
public class SystemInfoResponse {

    @Data
    public static class AlertMsgMo {
        private String message;
        private String active;
    }

    @Data
    public static class AppInfoMo {
        private String os;
        private String version;
        private String url;
    }

    public SystemInfoResponse(List<AlertMsgMo> alertMsgs, AppInfoMo appInfo){
        this.setAlertMsgs(alertMsgs);
        this.setAppInfo(appInfo);
    };

    List<AlertMsgMo> alertMsgs;
    AppInfoMo appInfo;
}
