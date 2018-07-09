package com.charge.deviceManage;

import com.charge.protocol.TopicAndMsgStruct;
import com.charge.protocol.topic.GeneralTopic;

import java.util.Vector;

import static com.charge.protocol.ProtocolConstant.*;

public class MsgProcessor {
    private static MsgProcessor ourInstance = new MsgProcessor();

    public static MsgProcessor getInstance() {
        return ourInstance;
    }

    private MsgProcessor() {
    }

    public void processIncomeMsg(String topicAndMsg) {

        TopicAndMsgStruct topicAndMsgStruct = new TopicAndMsgStruct(topicAndMsg);

        String message = topicAndMsgStruct.getMessage();
        String topicStr = topicAndMsgStruct.getTopic();

        GeneralTopic generalTopic = new GeneralTopic(topicStr);
        Long gwId = Long.parseLong(generalTopic.getGwId());
        String messageType = generalTopic.getMessageType();

        if (TOPIC_NOTIFY.equals(messageType)){

            UnregisteredChargePileManager instance = UnregisteredChargePileManager.getInstance();
            if (!instance.hasUnregisteredChargePile(gwId)){
                instance.addUnregisteredChargePile(gwId);
            }

        }else {

            ChargePile chargePile = ChargePileManager.getInstance().getChargePile(gwId);

            switch (messageType) {
                case TOPIC_DATA:
                    chargePile.dataMsgHandle(message);
                    break;
                case TOPIC_IMAGE:
                    chargePile.imageMsgHandle(message);
                    break;
                case TOPIC_REQUEST:
                    chargePile.requestMsgHandle(message);
                    break;
                case TOPIC_RESPONSE:
                    chargePile.responseMsgHandle(message);
                    break;
                case TOPIC_UPDATE:
                    chargePile.updateMsgHandle(message);
                    break;
                default:
                    break;
            }

        }



    }

    //请求允许上线
    public void permissionOnLine(Long gwId, String callBackQueueName){
        ChargePile chargePile = ChargePileManager.getInstance().getChargePile(gwId);

        chargePile.permissionOnLine(callBackQueueName);

    }

    //请求关闭所有插座
    public void shutDownAllSockets(Long gwId, String callBackQueueName){
        ChargePile chargePile = ChargePileManager.getInstance().getChargePile(gwId);

        chargePile.shutDownAllSockets(callBackQueueName);

    }

    //请求关闭插座
    public void shutDownChargeSocket(Long gwId, Vector<Long> socketIds, String callBackQueueName){
        ChargePile chargePile = ChargePileManager.getInstance().getChargePile(gwId);

        chargePile.shutDownChargeSocket(socketIds, callBackQueueName);

    }

    //请求测试充电功率
    public void requestTestPower(Long gwId, Vector<Long> socketIds, String callBackQueueName){
        ChargePile chargePile = ChargePileManager.getInstance().getChargePile(gwId);

        chargePile.requestTestPower(socketIds, callBackQueueName);
    }

    //请求插座开始充电
    public void startCharge(Long gwId, Vector<Long> socketIds, String callBackQueueName){
        ChargePile chargePile = ChargePileManager.getInstance().getChargePile(gwId);

        chargePile.startCharge(socketIds, callBackQueueName);

    }




}
