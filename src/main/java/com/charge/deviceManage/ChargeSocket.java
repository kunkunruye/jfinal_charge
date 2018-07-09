package com.charge.deviceManage;

import com.alibaba.fastjson.JSONObject;
import com.charge.deviceInterface.Device;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.charge.protocol.ProtocolConstant.MSG_INUSE;

public class ChargeSocket implements Device {
    private Long chargeSocketId;
    private Long chargePileId;

    private boolean used;
    private Long startPower;//初始充电功率，单位mW
    private Long chargeIntensity;//电流，单位mA
    private Long chargeTime;//充电时长，单位s
    private Integer chargeState;//充电状态， 1：充电中  0：充电截止

    private Map<Integer, Alarm> alarmMap = new HashMap<>();//Tag， alarm   保存该网关的状态数据

    public ChargeSocket(Long chargePileId, Long chargeSocketId){
        this.chargePileId = chargePileId;
        this.chargeSocketId = chargeSocketId;
    }


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

        if (deviceObj.containsKey(MSG_INUSE)){
            if ("1".equals(deviceObj.getString(MSG_INUSE))){
                used = true;
            }else {
                used = false;
            }


        }else {
            startPower = 0L;
            chargeIntensity = 0L;
            chargeTime = 0L;
            chargeState = 0;
        }


        return true;
    }

    @Override
    public boolean updateStatus(Date updateTime, JSONObject deviceObj) {
        return false;
    }
}
