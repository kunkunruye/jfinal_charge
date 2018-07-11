package com.charge.deviceManage;

import com.charge.deviceManage.device.ChargePile;
import com.charge.protocol.TopicAndMsgStruct;
import com.charge.protocol.topic.GeneralTopic;

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

            //待注册设备
            if (!ChargePileManager.getInstance().hasChargePile(gwId)){
                ChargePileManager.getInstance().addChargePile(gwId);
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

}
