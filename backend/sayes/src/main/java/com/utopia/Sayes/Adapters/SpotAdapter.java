package com.utopia.Sayes.Adapters;

import com.utopia.Sayes.Models.Spot;
import java.util.HashMap;
import java.util.Map;

public class SpotAdapter implements IAdapter<Spot> {
    @Override
    public Map<String, Object> toMap(Spot spot) {
        Map<String, Object> spotMap = new HashMap<>();
        spotMap.put("spot_id", spot.getSpot_id());
        spotMap.put("lot_id", spot.getLot_id());
        spotMap.put("state", spot.getState());
        return spotMap;
    }

    @Override
    public Spot fromMap(Map<String, Object> map) {
        long spot_id = (long) map.get("spot_id");
        long lot_id = (long) map.get("lot_id");
        String state = (String) map.get("state");

        Spot spot = new Spot(spot_id, lot_id, state);
        return spot;
    }

}
