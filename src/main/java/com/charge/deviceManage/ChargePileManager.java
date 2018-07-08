package com.charge.deviceManage;

import java.util.HashMap;
import java.util.Map;

public class ChargePileManager {
    private static ChargePileManager ourInstance = new ChargePileManager();

    private Map<Long, ChargePile> chargePileMap = new HashMap<>();


    public static ChargePileManager getInstance() {
        return ourInstance;
    }

    private ChargePileManager() {
    }

    public ChargePile getChargePile(Long chargePileId){
        if (chargePileMap.containsKey(chargePileId)){
            return chargePileMap.get(chargePileId);
        }

        return null;
    }


}
