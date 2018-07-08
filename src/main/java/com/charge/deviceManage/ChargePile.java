package com.charge.deviceManage;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.charge.deviceInterface.Collector;
import com.charge.deviceInterface.Device;
import com.charge.deviceInterface.GateWay;
import com.charge.listener.ActiveMQMsgServer;
import com.charge.protocol.MsgConvertUtil;
import com.charge.protocol.update.UpdateMsgHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.charge.protocol.ProtocolConstant.*;

public class ChargePile implements GateWay {
    private Long chargePileId;//充电桩Id
    private String name;//充电桩名称

    private Map<Integer, Alarm> alarmMap = new HashMap<>();//Tag， alarm   保存的状态数据

    private Map<Long, Device> chargeSocketIds = new HashMap<>();//该充电桩下的所有插座

    private static Logger _log = LoggerFactory.getLogger(ChargePile.class);

    @Override
    public void setID(Long id) {
        chargePileId = id;
    }

    @Override
    public Long getID() {
        return chargePileId;
    }

    @Override
    public boolean updateData(Date updateTime, JSONObject deviceObj) {
        return false;
    }

    @Override
    public boolean updateStatus(Date updateTime, JSONObject deviceObj) {
        return false;
    }

    @Override
    public void setName(String name) {
        this.name = name;

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addCollector(Collector newCollector) {

    }

    @Override
    public void delCollector(Long collectorId) {

    }

    @Override
    public Collector getCollector(Long collectorId) {
        return null;
    }

    @Override
    public Set<Collector> getCollectors() {
        return null;
    }

    @Override
    public Device getDevice(Long deviceId) {
        return null;
    }

    @Override
    public Set<Device> getDevices() {
        return null;
    }

    public void dataMsgHandle(String message) {
        if (null == message || message.isEmpty()) {
            return;
        }

        String messageOut = message.replaceAll(MSG_FACET_SEPARATOR_INSIDE, MSG_FACET_SEPARATOR);
        String messageIn = messageOut.replaceAll(MSG_FACET_SEPARATOR, MSG_FACET_SEPARATOR_INSIDE);

        long startTime=System.currentTimeMillis();//记录开始时间

        JSONArray messageJson = MsgConvertUtil.msg2Json(messageIn);

        long endTime=System.currentTimeMillis();//记录结束时间
        float excTime=(float)(endTime-startTime)/1000;
        _log.info("PVStation dataMsgHandle convertDataValue2StandardUnit 执行时间："+excTime+"s");

        updateData(messageJson);

    }

    private void updateData(JSONArray msgJArray){
        //todo 具体功能待实现
    }

    public void imageMsgHandle(String message){

    }
    public void requestMsgHandle(String message){

    }

    public void responseMsgHandle(String message){

    }

    public void updateMsgHandle(String message){

        String messageJson = MsgConvertUtil.msg2Json(message).toString();
        //判断消息是否为空
        if (messageJson.equals("[]")){
            return;
        }

        //todo 这里需要重写
        //String location = getProvince()+"/"+getCity();
        String location = "";
        ActiveMQMsgServer server = ActiveMQMsgServer.getInstance();
        UpdateMsgHandle.handle(server,location,messageJson);
    }



}
