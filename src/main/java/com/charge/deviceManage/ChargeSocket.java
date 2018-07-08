package com.charge.deviceManage;

import com.alibaba.fastjson.JSONObject;
import com.charge.deviceInterface.Device;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChargeSocket implements Device {
    private Long chargeSocketId;
    private Long chargePileId;

    private Map<Integer, Alarm> alarmMap = new HashMap<>();//Tag， alarm   保存该网关的状态数据


    @Override
    public void setCollectorId(Long collectorId) {
        return;

    }

    @Override
    public Long getCollectorId() {
        return chargePileId;
    }

    @Override
    public void setGWId(Long gwId) {
        chargePileId = gwId;

    }

    @Override
    public Long getGWId() {
        return chargePileId;
    }

    @Override
    public void setID(Long id) {

        chargeSocketId = id;
    }

    @Override
    public Long getID() {
        return chargeSocketId;
    }

    @Override
    public boolean updateData(Date updateTime, JSONObject deviceObj) {
        return false;
    }

    @Override
    public boolean updateStatus(Date updateTime, JSONObject deviceObj) {
        return false;
    }
}
