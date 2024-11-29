package com.utopia.Sayes.Repo;

import com.utopia.Sayes.Adapters.ReservationAdapter;
import com.utopia.Sayes.Models.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class ReservationDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ReservationAdapter reservationAdapter = new ReservationAdapter();
    public void addReservation(long spot_id, long lot_id, String reservation_time, String state, long driver_id) {
        String query = "INSERT INTO reserved_spots"  +
        "(spot_id, lot_id, reservation_time, state, driver_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        int rows = jdbcTemplate.update(query, spot_id, lot_id, reservation_time, state, driver_id);
        if(rows == 0){
            throw new RuntimeException("can't add this reservation");
        }
    }
    public void deleteReservation(long spot_id, long lot_id) {
        String query = "DELETE FROM reserved_spots WHERE spot_id = ? AND lot_id = ?";
        int rows = jdbcTemplate.update(query, spot_id, lot_id);
        if (rows == 0){
            throw new RuntimeException("can't delete this reservation");
        }
    }
    public Reservation getReservation(long spot_id, long lot_id,long driver_id) throws Exception {
        String query = "SELECT FROM reserved_spots WHERE spot_id = ? AND lot_id = ? AND driver_id = ?";
        Map<String, Object> resultMap = jdbcTemplate.queryForMap(query, spot_id, lot_id,driver_id);
        if(resultMap.isEmpty()){
            throw new Exception("There is no spot with this id");
        }
        return reservationAdapter.fromMap(resultMap);
    }

}
