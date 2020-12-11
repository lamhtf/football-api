package page.lamht.football.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import page.lamht.football.entity.AlertMsg;
import page.lamht.football.entity.AppInfo;
import page.lamht.football.mapper.SystemMapper;
import page.lamht.football.mo.SystemInfoResponse;
import page.lamht.football.repository.SystemService;

import java.util.List;

@RestController
public class SystemController {

    private final static Logger logger = LoggerFactory.getLogger(SystemController.class);

    @Autowired
    private SystemService service;

    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping(value="/system/info/{os}", produces= MediaType.APPLICATION_JSON_VALUE)
    String getSystemInfo(@PathVariable String os) throws JsonProcessingException {

        List<AlertMsg> msgs = service.findActiveAlertMsg();
        AppInfo info = service.findAppInfo(os);

        List<SystemInfoResponse.AlertMsgMo> msgMos = SystemMapper.INSTANCE.alertMsgsToAlertMsgs(msgs);
        SystemInfoResponse.AppInfoMo infoMo = SystemMapper.INSTANCE.appInfoToAppInfoMo(info);

        SystemInfoResponse response = new SystemInfoResponse(msgMos, infoMo);

        String result = objectMapper.writeValueAsString(response);
        return result;
    }
}
