package page.lamht.football.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import page.lamht.football.dto.PersonDto;
import page.lamht.football.entity.AlertMsg;
import page.lamht.football.entity.AppInfo;
import page.lamht.football.entity.Player;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class SystemService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<AlertMsg> findActiveAlertMsg() {
        String sql = "SELECT * FROM alert_msg am WHERE am.active = 'Y'";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<AlertMsg>(AlertMsg.class));
    }

    public AppInfo findAppInfo(String os) {
        try {
            String sql = "SELECT * FROM app_info ai WHERE ai.os = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{os}, new BeanPropertyRowMapper<AppInfo>(AppInfo.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
