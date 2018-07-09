package com.charge.deviceManage;

import java.util.HashMap;
import java.util.Map;

public class UnregisteredChargePileManager {
    private static UnregisteredChargePileManager ourInstance = new UnregisteredChargePileManager();

    private Map<Long, ChargePile> unregisteredChargePileMap = new HashMap<>();

    public static UnregisteredChargePileManager getInstance() {
        return ourInstance;
    }

    private UnregisteredChargePileManager() {
    }

    public ChargePile getUnregisteredChargePile(Long chargePileId){
        if (unregisteredChargePileMap.containsKey(chargePileId)){
            return unregisteredChargePileMap.get(chargePileId);
        }

        return null;
    }

    public boolean hasUnregisteredChargePile(Long chargePileId){
        return unregisteredChargePileMap.containsKey(chargePileId);
    }

    public void addUnregisteredChargePile(Long chargePileId){
        unregisteredChargePileMap.put(chargePileId, new ChargePile(chargePileId));
    }

    public void deleteUnregisteredChargePile(Long chargePileId){
        unregisteredChargePileMap.remove(chargePileId);
    }


}
