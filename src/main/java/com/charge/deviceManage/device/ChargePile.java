package com.charge.deviceManage.device;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.charge.deviceInterface.Device;
import com.charge.deviceInterface.GateWay;
import com.charge.deviceManage.alarm.Alarm;
import com.charge.listener.ActiveMQMsgServer;
import com.charge.protocol.MqttMsgSender;
import com.charge.protocol.MsgConvertUtil;
import com.charge.protocol.message.GatewayFacet;
import com.charge.protocol.message.RequestChargePlieFacet;
import com.charge.protocol.message.RequestMsg;
import com.charge.protocol.message.RequestSocketFacet;
import com.charge.protocol.topic.GeneralTopic;
import com.charge.protocol.update.UpdateMsgHandle;
import com.charge.utils.*;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.charge.deviceManage.alarm.AlarmStatus.*;
import static com.charge.protocol.ProtocolConstant.*;

public class ChargePile implements GateWay {
    private Long chargePileId;//充电桩Id
    private String name;//充电桩名称

    private boolean isOnline;

    private Map<Integer, Alarm> alarmMap = new HashMap<>();//Tag， alarm   保存的状态数据

    private Map<Long, Device> chargeSocketMap = new HashMap<>();//该充电桩下的所有插座

    private static Logger _log = LoggerFactory.getLogger(ChargePile.class);
    private Map<Integer, String> requestSNAndCallBackQueueNameMap = new ConcurrentHashMap();

    public ChargePile(Long chargePileId){
        this.chargePileId = chargePileId;
        isOnline = false;//仅仅是初始化，未上线
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
    public void setName(String name) {
        this.name = name;

    }

    @Override
    public String getName() {
        return name;
    }


    @Override
    public Device getDevice(Long deviceId) {
        return null;
    }

    @Override
    public Vector<Device> getDevices() {
        return null;
    }

    public void dataMsgHandle(String message) {
        if (null == message || message.isEmpty()) {
            _log.error("the message is empty!");
            return;
        }

        long startTime=System.currentTimeMillis();//记录开始时间

        String messageIn = getPreMsgStr(message);
        JSONArray messageJsonArr = MsgConvertUtil.msg2Json(messageIn);
        updateDataAndAlarm(messageJsonArr);

        long endTime=System.currentTimeMillis();//记录结束时间
        float excTime=(float)(endTime-startTime)/1000;
        _log.info("PVStation dataMsgHandle convertDataValue2StandardUnit 执行时间："+excTime+"s");
    }

    private void updateDataAndAlarm(JSONArray msgJArray){

        JSONObject gwFacetObj = msgJArray.getJSONObject(0);
        Date dataTime= DateUtil.yyyyMMddHHmmssStrToDate(gwFacetObj.getString(MSG_TIME));

        updateAlarm(gwFacetObj, dataTime);

        for (int i=1; i<msgJArray.size(); i++){
            JSONObject chargeSocketObj = msgJArray.getJSONObject(i);

            Long socketSn = Long.parseLong(chargeSocketObj.getString(MSG_DEVICESN));

            if (!chargeSocketMap.containsKey(socketSn)){
                ChargeSocket chargeSocket = new ChargeSocket(chargePileId, socketSn);
                chargeSocketMap.put(socketSn, chargeSocket);
            }

            Device chargeSocket = chargeSocketMap.get(socketSn);

            chargeSocket.updateData(dataTime, chargeSocketObj);
            chargeSocket.updateStatus(dataTime, chargeSocketObj);

        }
    }

    private void updateAlarm(JSONObject gwFacetObj, Date dataTime) {
        List<Integer> gatewayStatusTagList = new ArrayList<>();
        if (gwFacetObj.containsKey(MSG_GW_STATUS) ){
            if(!gwFacetObj.getString(MSG_GW_STATUS).equals("")) {
                JSONArray gatewayStatusTagJsonArray = gwFacetObj.getJSONArray(MSG_GW_STATUS);
                for (int index=0; index<gatewayStatusTagJsonArray.size(); index++) {
                    Integer gatewayStatusTag = Integer.parseInt(gatewayStatusTagJsonArray.getString(index));
                    gatewayStatusTagList.add(gatewayStatusTag);
                }
            }
        }
        updateAllAlarm(gatewayStatusTagList, dataTime);//这里不在if中处理的原因：每次有新的数据都应该携带状态数据，如果没有携带，认为是状态改变
    }

    public void updateAllAlarm(List<Integer> alarmTagList, Date updateTime){
        //删除所有end的消息,删除一定要用迭代器
        Iterator<Map.Entry<Integer, Alarm>> it = alarmMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<Integer, Alarm> entry = it.next();
            if (entry.getValue().getStatus() == END) {
                it.remove();
            }
        }

        //在更新全部报警之前，将所有的报警设置为结束
        for (Alarm alarm : alarmMap.values()){
            alarm.setStatus(END);
        }

        //更新所有的报警
        for (Integer alarmTag : alarmTagList){
            updateSingleAlarm(alarmTag, updateTime);
        }

    }
    private void updateSingleAlarm(Integer alarmTag, Date updateTime) {
        if (alarmMap.containsKey(alarmTag)){
            //已存在的报警就更新时间，状态改为 CONTINUE
            Alarm alarm = alarmMap.get(alarmTag);
            alarm.setEndTime(updateTime);
            alarm.setStatus(CONTINUE);
        }else {
            //不存在的报警就新增，状态为 START
            AlarmInfoConfig alarmConfig = AlarmConfigManager.getInstance().getAlarmConfig("CHARGE_PILE");

            if (alarmConfig.containsTag(alarmTag.toString())){
                AlarmInfo alarmInfo = alarmConfig.getAlarmInfo(alarmTag.toString());
                String alarmMessage = alarmInfo.getAlarmMessage();
                Alarm alarm = new Alarm(alarmTag, alarmMessage, updateTime, updateTime);
                alarmMap.put(alarmTag, alarm);
            }else {
                _log.error("error, cannot find alarmInfo of " + alarmTag);
            }
        }
    }


    public void imageMsgHandle(String message){
        if (null == message || message.isEmpty()) {
            _log.error("the message is empty!");
            return;
        }

        //不需要转换


    }
    public void requestMsgHandle(String message){
        if (null == message || message.isEmpty()) {
            _log.error("the message is empty!");
            return;
        }
    }

    public void responseMsgHandle(String message){
        if (null == message || message.isEmpty()) {
            _log.error("the message is empty!");
            return;
        }

        long startTime=System.currentTimeMillis();//记录开始时间

        String messageIn = getPreMsgStr(message);
        JSONArray messageJsonArr = MsgConvertUtil.msg2Json(messageIn);

        JSONObject gwFacetObj = messageJsonArr.getJSONObject(0);

        String gwIdStr = gwFacetObj.getString(MSG_GWID);
        if (!chargePileId.equals(Long.parseLong(gwIdStr))){
            _log.error("gwid in message: " + gwIdStr + " is not equal to gwid in topic: " +  chargePileId.toString());
            return;
        }

        Integer responseSequenceNum = Integer.parseInt(gwFacetObj.getString(MSG_SERIALNUMBER));

        if (!requestSNAndCallBackQueueNameMap.containsKey(responseSequenceNum)){
            _log.error("response SequenceNum dose not exist in requestSNAndCallBackQueueNameMap:" + responseSequenceNum.toString());
            return;
        }

        JSONObject responseObj = messageJsonArr.getJSONObject(1);
        if (!responseObj.containsKey(MSG_RESPONSE)){
            _log.error("invalid response message : " + responseObj.toString());
            return;
        }

        String callBackQueueName = requestSNAndCallBackQueueNameMap.get(responseSequenceNum);

        String responseType = responseObj.getString(MSG_RESPONSE);

        switch (responseType){
            case MSG_RESPONCE_CODE_PERMISSIONONLINE:
                if (responseObj.getString(MSG_RESPONCE_RESULT).equals("1")){
                    isOnline = true;
                }
                DataSourceUtils.getInstance().pushToActiveMQ(messageJsonArr.toString(), callBackQueueName);
                break;
            case MSG_RESPONCE_CODE_SHUTDOWNALLSOCKETS:

            case MSG_RESPONCE_CODE_SHUTDOWNSOCKET:

            case MSG_RESPONCE_CODE_TESTSOCKETPOWER:

            case MSG_RESPONCE_CODE_SOCKETSTARTCHARGE:
                DataSourceUtils.getInstance().pushToActiveMQ(messageJsonArr.toString(), callBackQueueName);
                break;
            default:
                break;

        }


    }

    private String getPreMsgStr(String message) {
        String messageOut = message.replaceAll(MSG_FACET_SEPARATOR_INSIDE, MSG_FACET_SEPARATOR);
        return messageOut.replaceAll(MSG_FACET_SEPARATOR, MSG_FACET_SEPARATOR_INSIDE);
    }

    public void updateMsgHandle(String message){

        String messageJson = MsgConvertUtil.msg2Json(message).toString();
        //判断消息是否为空
        if (messageJson.equals("[]")){
            return;
        }

        //todo 这里需要重写
        String industryAndVersion = "CHARGE"+"/"+"1";
        ActiveMQMsgServer server = ActiveMQMsgServer.getInstance();
        UpdateMsgHandle.handle(server,industryAndVersion,messageJson);
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
