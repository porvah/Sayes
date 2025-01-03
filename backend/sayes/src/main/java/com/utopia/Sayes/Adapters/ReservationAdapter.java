package com.utopia.Sayes.Adapters;

import com.utopia.Sayes.Models.Reservation;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ReservationAdapter implements IAdapter<Reservation> {

    @Override
    public Map<String, Object> toMap(Reservation reservation) {
        Map<String, Object> reservationMap = new HashMap<>();
        reservationMap.put("spot_id", reservation.getSpot_id());
        reservationMap.put("lot_id", reservation.getLot_id());
        reservationMap.put("start_time", reservation.getStart_time());
        reservationMap.put("end_time", reservation.getEnd_time());
        reservationMap.put("state", reservation.getState());
        reservationMap.put("driver_id", reservation.getDriver_id());
        return reservationMap;
    }

    @Override
    public Reservation fromMap(Map<String, Object> map) {
        long spot_id = (long) map.get("spot_id");
        long lot_id = (long) map.get("lot_id");
        LocalDateTime start_time = (LocalDateTime) map.get("start_time");
        LocalDateTime end_time = (LocalDateTime) map.get("end_time");
        String state = (String) map.get("state");
        long driver_id = (long) map.get("driver_id");
        double price = (double) map.get("price");

        Reservation reservation = new Reservation(spot_id, lot_id, start_time , end_time, state, driver_id, price);
        return reservation;
    }


}
