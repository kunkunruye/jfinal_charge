package com.charge.deviceManage;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.charge.deviceInterface.Collector;
import com.charge.deviceInterface.Device;
import com.charge.deviceInterface.GateWay;
import com.charge.listener.ActiveMQMsgServer;
import com.charge.protocol.MqttMsgSender;
import com.charge.protocol.MsgConvertUtil;
import com.charge.protocol.message.GatewayFacet;
import com.charge.protocol.message.RequestChargePlieFacet;
import com.charge.protocol.message.RequestMsg;
import com.charge.protocol.message.RequestSocketFacet;
import com.charge.protocol.topic.GeneralTopic;
import com.charge.protocol.update.UpdateMsgHandle;
import com.charge.utils.DateUtil;
import com.charge.utils.SEQGeneration;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.charge.protocol.ProtocolConstant.*;

public class ChargePile implements GateWay {
    private Long chargePileId;//充电桩Id
    private String name;//充电桩名称

    private Map<Integer, Alarm> alarmMap = new HashMap<>();//Tag， alarm   保存的状态数据

    private Map<Long, Device> chargeSocketMap = new HashMap<>();//该充电桩下的所有插座

    private static Logger _log = LoggerFactory.getLogger(ChargePile.class);
    private Map<Integer, String> requestSNAndCallBackQueueNameMap = new ConcurrentHashMap();

    public ChargePile(Long chargePileId){
        this.chargePileId = chargePileId;
    }

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

        JSONArray messageJsonArr = MsgConvertUtil.msg2Json(messageIn);

        long endTime=System.currentTimeMillis();//记录结束时间
        float excTime=(float)(endTime-startTime)/1000;
        _log.info("PVStation dataMsgHandle convertDataValue2StandardUnit 执行时间："+excTime+"s");

        updateData(messageJsonArr);

    }

    private void updateData(JSONArray msgJArray){
        //todo 具体功能待实现

        JSONObject gwFacetObj = msgJArray.getJSONObject(0);

        Date dataTime= DateUtil.yyyyMMddHHmmssStrToDate(gwFacetObj.getString(MSG_TIME));

        for (int i=1; i<msgJArray.size(); i++){
            JSONObject chargeSocketObj = msgJArray.getJSONObject(i);

            Long socketSn = Long.parseLong(chargeSocketObj.getString(MSG_DEVICESN));

            if (!chargeSocketMap.containsKey(socketSn)){
                ChargeSocket chargeSocket = new ChargeSocket(chargePileId, socketSn);
                chargeSocketMap.put(socketSn, chargeSocket);
            }

            Device chargeSocket = chargeSocketMap.get(socketSn);

            chargeSocket.updateData(dataTime, chargeSocketObj);

        }



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

    //通过mqtt向硬件推送数据
    private void sendMqttMsg(String topicType, String msg) {
        try {
            //以后改成response
            GeneralTopic stationInfoSend = new GeneralTopic(MQTT_TOPIC_INDUSTRY_CHARGE, getGatewayIdStr(), topicType);//发送主题的前部分和接受到的主题相同
            String msgReplace = msg.replaceAll(MSG_FACET_SEPARATOR_INSIDE, MSG_FACET_SEPARATOR);
            MqttMsgSender.getInstance().Send(stationInfoSend.getTopic(), msgReplace,0);
        } catch (MqttException e) {
            _log.error("向mqtt推送消息错误!",e.getMessage());
        }
    }


    //请求允许上线
    public void permissionOnLine(String callBackQueueName){

        RequestMsg requestMsg = new RequestMsg();

        Integer sequenceNum = SEQGeneration.getInstance().getSEQ();
        Date utc = new Date();
        GatewayFacet gatewayFacet = new GatewayFacet(sequenceNum, utc, getGatewayIdStr());
        requestMsg.setGatewayFacet(gatewayFacet);
        RequestChargePlieFacet requestFacet = new RequestChargePlieFacet(MSG_REQUEST_CODE_PERMISSIONONLINE);
        requestMsg.addRequestFacet(requestFacet);

        String requestMsgStr = requestMsg.toString();

        System.out.println("permissionOnLine: \n\r" + requestMsgStr);

        sendMqttMsg(TOPIC_REQUEST, requestMsgStr);
        //将sn存储起来，等待接受response消息使用
        requestSNAndCallBackQueueNameMap.put(gatewayFacet.getSequenceNum(), callBackQueueName);


    }

    private String getGatewayIdStr() {
        return String.format("%012d",chargePileId);
    }

    //请求关闭所有插座
    public void shutDownAllSockets(String callBackQueueName){
        RequestMsg requestMsg = new RequestMsg();

        Integer sequenceNum = SEQGeneration.getInstance().getSEQ();
        Date utc = new Date();
        requestMsg.setGatewayFacet(new GatewayFacet(sequenceNum, utc, getGatewayIdStr()));
        requestMsg.addRequestFacet(new RequestChargePlieFacet(MSG_REQUEST_CODE_SHUTDOWNALLSOCKETS));

        String requestMsgStr = requestMsg.toString();

        System.out.println("shutDownAllSockets: \n\r" + requestMsgStr);

        sendMqttMsg(TOPIC_REQUEST, requestMsgStr);
        //将sn存储起来，等待接受response消息使用
        requestSNAndCallBackQueueNameMap.put(sequenceNum, callBackQueueName);

    }

    //请求关闭插座
    public void shutDownChargeSocket(Vector<Long> socketIds, String callBackQueueName){
        RequestMsg requestMsg = new RequestMsg();

        Integer sequenceNum = SEQGeneration.getInstance().getSEQ();
        Date utc = new Date();
        GatewayFacet gatewayFacet = new GatewayFacet(sequenceNum, utc, getGatewayIdStr());
        requestMsg.setGatewayFacet(gatewayFacet);
        for (Long socketId : socketIds){
            requestMsg.addRequestFacet(new RequestSocketFacet(MSG_REQUEST_CODE_SHUTDOWNSOCKET, socketId.toString()));
        }

        String requestMsgStr = requestMsg.toString();

        System.out.println("shutDownChargeSocket: \n\r" + requestMsgStr);

        sendMqttMsg(TOPIC_REQUEST, requestMsgStr);
        //将sn存储起来，等待接受response消息使用
        requestSNAndCallBackQueueNameMap.put(gatewayFacet.getSequenceNum(), callBackQueueName);

    }

    //请求测试充电功率
    public void requestTestPower(Vector<Long> socketIds, String callBackQueueName){
        RequestMsg requestMsg = new RequestMsg();

        Integer sequenceNum = SEQGeneration.getInstance().getSEQ();
        Date utc = new Date();
        requestMsg.setGatewayFacet(new GatewayFacet(sequenceNum, utc, getGatewayIdStr()));
        for (Long socketId : socketIds){
            requestMsg.addRequestFacet(new RequestSocketFacet(MSG_REQUEST_CODE_TESTSOCKETPOWER, socketId.toString()));
        }

        String requestMsgStr = requestMsg.toString();

        System.out.println("requestTestPower: \n\r" + requestMsgStr);

        sendMqttMsg(TOPIC_REQUEST, requestMsgStr);
        //将sn存储起来，等待接受response消息使用
        requestSNAndCallBackQueueNameMap.put(sequenceNum, callBackQueueName);

    }

    //请求插座开始充电
    public void startCharge(Vector<Long> socketIds, String callBackQueueName){
        RequestMsg requestMsg = new RequestMsg();

        Integer sequenceNum = SEQGeneration.getInstance().getSEQ();
        Date utc = new Date();
        requestMsg.setGatewayFacet(new GatewayFacet(sequenceNum, utc, getGatewayIdStr()));
        for (Long socketId : socketIds){
            requestMsg.addRequestFacet(new RequestSocketFacet(MSG_REQUEST_CODE_SOCKETSTARTCHARGE, socketId.toString()));
        }

        String requestMsgStr = requestMsg.toString();

        System.out.println("startCharge: \n\r" + requestMsgStr);

        sendMqttMsg(TOPIC_REQUEST, requestMsgStr);
        //将sn存储起来，等待接受response消息使用
        requestSNAndCallBackQueueNameMap.put(sequenceNum, callBackQueueName);

    }


    public static void main(String[] args){


        ChargePile chargePile = new ChargePile(1000L);

        String testQueue = "testqueue";

        chargePile.permissionOnLine(testQueue);

        chargePile.shutDownAllSockets(testQueue);

        Vector<Long> sockets = new Vector<>();
        sockets.add(1L);
        sockets.add(2L);
        sockets.add(4L);

        chargePile.shutDownChargeSocket(sockets, testQueue);

        chargePile.requestTestPower(sockets, testQueue);

        chargePile.startCharge(sockets, testQueue);

    }



}
