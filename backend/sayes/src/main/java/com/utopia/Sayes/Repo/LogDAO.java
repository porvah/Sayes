package com.utopia.Sayes.Repo;

import com.utopia.Sayes.Adapters.LogAdapter;
import com.utopia.Sayes.Models.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class LogDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    LogAdapter logAdapter = new LogAdapter();
    public void addlog(long spot_id, long lot_id, Date start_time , Date end_time,  long driver_id) {
        String query = "INSERT INTO logs"  +
                "(driver_id, reservation_time, departure_time, spot_id , lot_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        int rows = jdbcTemplate.update(query, driver_id ,start_time,end_time,spot_id,lot_id);
        if(rows == 0){
            throw new RuntimeException("can't add this log");
        }
    }
    public List<Log> getlogs() {
        String query = "SELECT * FROM logs";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        List<Log> logs = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Log log = logAdapter.fromMap(row);
            logs.add(log);
        }
        return logs;
    }
    public List<Map<String , Object>> getTopUsers(){
        String query = "SELECT u.username , COUNT(l.driver_id) AS total_reservations " +
                "FROM logs l JOIN Users u " +
                "ON u.user_id = l.driver_id " +
                "GROUP BY l.driver_id , u.username " +
                "ORDER BY total_reservations DESC " +
                "LIMIT 20";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        return rows;
    }
    public List<Map<String, Object>> getViolations() {
        String query = "SELECT u.username, combined.lot_id, " +
                "COALESCE(SUM(combined.fee), 0) AS total_fee, " +
                "COALESCE(SUM(combined.penalty_amount), 0) AS total_penalty " +
                "FROM Users u " +
                "LEFT JOIN (" +
                "    SELECT f.driver_id, f.lot_id, f.fee, 0 AS penalty_amount " +
                "    FROM fees f " +
                "    UNION ALL " +
                "    SELECT p.driver_id, p.lot_id, 0 AS fee, p.penalty_amount " +
                "    FROM penalties p" +
                ") combined ON u.user_id = combined.driver_id " +
                "WHERE combined.lot_id IS NOT NULL " +
                "GROUP BY u.user_id, u.username, combined.lot_id";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        return rows;
    }
}
