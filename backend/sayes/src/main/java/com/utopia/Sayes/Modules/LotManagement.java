package com.utopia.Sayes.Modules;

import com.utopia.Sayes.DTOs.UpdateLotDTO;
import com.utopia.Sayes.DTOs.UpdateLotManagerLotSpotsDTO;
import com.utopia.Sayes.Models.Lot;
import com.utopia.Sayes.Modules.DynamicPricing.DynamicPricing;
import com.utopia.Sayes.Modules.WebSocket.NotificationService;
import com.utopia.Sayes.Repo.LotDAO;
import com.utopia.Sayes.Repo.LotManagerDAO;
import com.utopia.Sayes.Repo.SpotDAO;
import com.utopia.Sayes.enums.SpotStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LotManagement {
    @Autowired
    LotDAO lotDAO;
    @Autowired
    SpotDAO spotDAO;

    @Autowired
    LotManagerDAO lotManagerDAO;

    @Autowired
    DynamicPricing dynamicPricing;

    @Autowired
    NotificationService notificationService;

    public long createLot(long manager_id , double longitude,double latitude,long revenue, long price, String lot_type
            , double penalty , double fee, Duration time , int numOfSpots) throws Exception{
        try {
            if (! lotManagerDAO.doesManagerExist(manager_id))
                throw new Exception("lot manager is not exist");
            Lot lot = new Lot(manager_id,longitude,latitude
                    ,revenue,price,lot_type, penalty,fee, time);
            long lotId =  lotDAO.addLot(lot);
            addSpots(lotId , numOfSpots);
            return lotId;
        }
        catch (Exception e){
           throw new Exception(e.getMessage());
        }
    }
    public void addSpots(long lot_id, int count) throws Exception {
        try {
            Lot lot = lotDAO.getLotById(lot_id);
            System.out.println(lot.getLot_id());
            if (lot == null){
                throw new Exception("there is no lot with this id");
            }
            List<Long> spotIds = new ArrayList<>(count);
            for(int i = 0;i < count;i++){
                spotIds.add(spotDAO.addSpot(lot_id,"Available"));
            }
            notificationService.notifyLotUpdate(new UpdateLotDTO(
                    lot_id,
                    lotDAO.getLotTotalSpots(lot_id),
                    lot.getLongitude(),
                    lot.getLatitude(),
                    lot.getPrice(),
                    lot.getLot_type()
            ));

            notificationService.notifyLotManager(new UpdateLotManagerLotSpotsDTO(
                    -1L, // flag for batch update
                    lot_id,
                    lot.getManager_id(),
                    lot.getRevenue(),
                    SpotStatus.Available,
                    spotIds
            ));
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public List<Lot> getLots() throws Exception {
        try {
            return lotDAO.getLots();
        }
        catch (Exception e){
            throw  new Exception(e.getMessage());
        }
    }
    public double getLotDynamicPrice(long lotId , Time duration) throws Exception {
        try {
            java.sql.Timestamp startTimestamp = new java.sql.Timestamp(new Date().getTime());
            return dynamicPricing.getPrice(lotId, new Time(startTimestamp.getTime()), duration , 0);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
