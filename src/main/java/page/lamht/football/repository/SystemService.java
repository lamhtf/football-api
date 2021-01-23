package page.lamht.football.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import page.lamht.football.entity.AlertMsg;
import page.lamht.football.entity.AppInfo;

import java.util.List;

@Service
@Transactional
public class SystemService {
    private final static Logger logger = LoggerFactory.getLogger(SystemService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<AlertMsg> findActiveAlertMsg() {
        try {
            String sql = "SELECT * FROM alert_msg am WHERE am.active = 'Y'";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<AlertMsg>(AlertMsg.class));
        } catch (Exception e) {
            logger.error(e.toString());
            return null;
        }
    }

    public AppInfo findAppInfo(String os) {
        try {
            String sql = "SELECT * FROM app_info ai WHERE ai.os = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{os}, new BeanPropertyRowMapper<AppInfo>(AppInfo.class));
        } catch (Exception e) {
            logger.error(e.toString());
            return null;
        }
    }
}
