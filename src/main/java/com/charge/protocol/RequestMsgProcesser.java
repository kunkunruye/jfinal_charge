package com.charge.protocol;


import com.charge.protocol.message.POSFacet;
import com.alibaba.fastjson.JSONArray;

import java.util.Date;
import java.util.Map;

import static com.charge.protocol.ProtocolConstant.*;


/**
 * Created by zhengkun on 17-9-13.
 */
public class RequestMsgProcesser {
    //private String requestMsgStr;

    private String powerStationName;
    private String sequenceNum;
    private String receptTimeStr;
    private String gwid;
    private String deviceType;
    private String requestType;

    public RequestMsgProcesser (JSONArray requestMsg){
        parseRequestMsg(requestMsg);
    }

    private void parseRequestMsg(JSONArray myJsonArray){

        Map mapFirst = myJsonArray.getJSONObject(0);
        powerStationName= (String) mapFirst.get(MSG_STATIONNAME);
        sequenceNum= (String) mapFirst.get(MSG_SERIALNUMBER);
        receptTimeStr = (String) mapFirst.get(MSG_TIME);
        if (mapFirst.containsKey(MSG_GWID)){
            gwid = (String) mapFirst.get(MSG_GWID);
        }

        Map mapSecond = myJsonArray.getJSONObject(1);
        requestType = (String) mapSecond.get(MSG_REQUEST);
        deviceType = (String) mapSecond.get(MSG_DEVICETYPE);
    }


    public String getResponseMsgStr(){

        String dataConfigStr = null;
        String alarmConfigStr = null;

        POSFacet posFacet = new POSFacet(powerStationName, Integer.valueOf(sequenceNum), new Date(), gwid);
        String firstFacetMsg = posFacet.toString();

        String secondFacetPrefix = null;
        String thirdFacetPrefix = null;
        switch (requestType) {
            case MSG_REQUEST_CODE_REVERSE:
            case MSG_REQUEST_CODE_INVERTERNUMBER:
            case MSG_REQUEST_CODE_COMBBOX:
            case MSG_REQUEST_CODE_GEOGRAPHYINFO:
            case MSG_REQUEST_CODE_ONLINECONFIRMATION:
                return null;


            case MSG_REQUEST_CODE_TRANSFERDATACONFIG:

            case MSG_REQUEST_CODE_TRANSFERALARMCONFIG:


                secondFacetPrefix =  MSG_FACET_SEPARATOR_INSIDE +MSG_RESPONSE+ MSG_COMPONENT_SEPARATOR +MSG_RESPONCE_CODE_TRANSFERDATACONFIG+ MSG_SEGMENT_SEPARATOR
                        + MSG_DEVICETYPE+ MSG_COMPONENT_SEPARATOR + deviceType + MSG_SEGMENT_SEPARATOR;



                thirdFacetPrefix =  MSG_FACET_SEPARATOR_INSIDE +MSG_RESPONSE+ MSG_COMPONENT_SEPARATOR +MSG_RESPONCE_CODE_TRANSFERALARMCONFIG+ MSG_SEGMENT_SEPARATOR
                        + MSG_DEVICETYPE+ MSG_COMPONENT_SEPARATOR + deviceType + MSG_SEGMENT_SEPARATOR;



                break;


            default:
                break;
        }

        return firstFacetMsg + secondFacetPrefix + dataConfigStr + thirdFacetPrefix + alarmConfigStr;


    }




}
