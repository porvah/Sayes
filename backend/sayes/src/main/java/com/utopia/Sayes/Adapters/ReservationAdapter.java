package com.utopia.Sayes.Adapters;

import com.google.gson.Gson;
import com.utopia.Sayes.Models.Reservation;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

public class ReservationAdapter implements IAdapter<Reservation> {

    @Override
    public Map<String, Object> toMap(Reservation reservation) {
        Map<String, Object> reservationMap = new HashMap<>();
        reservationMap.put("spot_id", reservation.getSpot_id());
        reservationMap.put("lot_id", reservation.getLot_id());
        reservationMap.put("reservation_time", reservation.getReservation_time());
        reservationMap.put("state", reservation.getState());
        reservationMap.put("driver_id", reservation.getDriver_id());
        return reservationMap;
    }

    @Override
    public Reservation fromMap(Map<String, Object> map) {
        long spot_id = (long) map.get("spot_id");
        long lot_id = (long) map.get("lot_id");
        Time reservation_time = (Time) map.get("reservation_time");
        String state = (String) map.get("state");
        long driver_id = (long) map.get("driver_id");

        Reservation reservation = new Reservation(spot_id, lot_id, reservation_time, state, driver_id);
        return reservation;
    }

    @Override
    public String toJson(Reservation reservation) {
        Gson gson = new Gson();
        return gson.toJson(reservation);
    }
}
