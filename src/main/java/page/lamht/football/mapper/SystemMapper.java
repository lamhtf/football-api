package page.lamht.football.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import page.lamht.football.entity.AlertMsg;
import page.lamht.football.entity.AppInfo;
import page.lamht.football.mo.SystemInfoResponse;

import java.util.List;

@Mapper
public interface SystemMapper {

    SystemMapper INSTANCE = Mappers.getMapper( SystemMapper.class );

    SystemInfoResponse.AppInfoMo appInfoToAppInfoMo(AppInfo info);
    List<SystemInfoResponse.AlertMsgMo> alertMsgsToAlertMsgs(List<AlertMsg> msgs);
    SystemInfoResponse.AlertMsgMo alertMsgToAlertMsg(AlertMsg msg);
}