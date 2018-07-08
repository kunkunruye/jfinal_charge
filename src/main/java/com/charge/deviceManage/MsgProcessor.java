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

    public void permissionOnLine(Long gwId){


    }

    public void shutDownAllSockets(Long gwId){


    }

    public void shutDownChargeSocket(Long gwId, Vector<Long> socketIds){


    }

    public void requestTestPower(Long gwId, Vector<Long> socketIds){


    }

    public void startCharge(Long gwId, Vector<Long> socketIds){


    }




}
