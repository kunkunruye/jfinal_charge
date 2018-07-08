package com.charge.protocol.message;


import com.charge.utils.SEQGeneration;

import java.util.Date;

import static com.charge.protocol.ProtocolConstant.*;

/**
 * Created by zhengkun on 17-12-27.
 */
public class RequestMsg {
    private POSFacet posFacet;

    private RequestFacet requestFacet;

    public RequestMsg(String stationName, Long gatewayID, String requestType) {

        Integer sequenceNum = SEQGeneration.getInstance().getSEQ();
        Date utc = new Date();
        this.posFacet = new POSFacet(stationName, sequenceNum, utc, String.format("%012d",gatewayID));
        this.requestFacet=new RequestFacet(requestType);
    }

    public RequestMsg(String stationName, Long gatewayID, String requestType, Long pvsn) {

        Integer sequenceNum = SEQGeneration.getInstance().getSEQ();
        Date utc = new Date();
        this.posFacet = new POSFacet(stationName, sequenceNum, utc, String.format("%012d",gatewayID));
        this.requestFacet=new DeletePVRequestFacet(requestType,pvsn);
    }

    public RequestMsg(String stationName, Long gatewayID, String requestType, String cry) {

        Integer sequenceNum = SEQGeneration.getInstance().getSEQ();
        Date utc = new Date();
        this.posFacet = new POSFacet(stationName, sequenceNum, utc, String.format("%012d",gatewayID));
        this.requestFacet=new DeleteGWRequestFacet(requestType,cry);
    }

    public RequestMsg(String stationName, Long gatewayID, String requestType,String device,String deviceType,
                      Integer modBusAddr,Long pvsn,Integer deviceNum,Integer loraF,Integer sf,Integer bf,Integer cr) {

        Integer sequenceNum = SEQGeneration.getInstance().getSEQ();
        Date utc = new Date();
        this.posFacet = new POSFacet(stationName, sequenceNum, utc, String.format("%012d",gatewayID));
        this.requestFacet=new AddNodeRequestFacet(requestType,device,deviceType,modBusAddr,pvsn,deviceNum,loraF,sf,bf,cr);
    }

    public RequestMsg(String stationName, Long gatewayID, String requestType,String device,Long pvsn) {

        Integer sequenceNum = SEQGeneration.getInstance().getSEQ();
        Date utc = new Date();
        this.posFacet = new POSFacet(stationName, sequenceNum, utc, String.format("%012d",gatewayID));
        this.requestFacet=new DeleteNodeRequestFacet(requestType,device,pvsn);
    }

    public RequestMsg(String stationName, Long gatewayID, String requestType,Integer lora_F, Integer sf,Integer bf,Integer cr, Integer pv_In) {

        Integer sequenceNum = SEQGeneration.getInstance().getSEQ();
        Date utc = new Date();
        this.posFacet = new POSFacet(stationName, sequenceNum, utc, String.format("%012d",gatewayID));
        this.requestFacet=new ConfigGWRequestFacet(requestType,lora_F,sf,bf,cr,pv_In);
    }
    //(Gateway,Collector)
    public RequestMsg(String stationName, Long gatewayID, String requestType,Integer lora_F, Integer sf,Integer bf,Integer cr, Integer pv_In,
                      String device,String deviceType,Integer modBusAddr,Integer deviceNum,
                      Long pvsn,String name,String prov,String city) {

        Integer sequenceNum = SEQGeneration.getInstance().getSEQ();
        Date utc = new Date();
        this.posFacet = new POSFacet(stationName, sequenceNum, utc, String.format("%012d",gatewayID));
        this.requestFacet=new ConfigGWRequestFacet(requestType,lora_F,sf,bf,cr,pv_In,device,deviceType,modBusAddr,deviceNum,pvsn,name,prov,city);
    }

    public POSFacet getPosFacet() {
        return posFacet;
    }

    public void setPosFacet(POSFacet posFacet) {
        this.posFacet = posFacet;
    }

    public String getStationName() {
        return posFacet.getPowerstationName();
    }

    public void setStationName(String stationName) {
        posFacet.setPowerstationName(stationName);
    }

    public Integer getSequenceNum() {
        return posFacet.getSequenceNum();
    }

    public void setSequenceNum(Integer sequenceNum) {
        posFacet.setSequenceNum(sequenceNum);
    }

    public RequestFacet getRequestFacet() {
        return requestFacet;
    }

    public void setRequestFacet(RequestFacet requestFacet) {
        this.requestFacet = requestFacet;
    }

    public String toString(){
        String posFacetStr = posFacet.toString();
        String requestFacetStr = requestFacet.toString();
        return posFacetStr + MSG_FACET_SEPARATOR_INSIDE + requestFacetStr;
    }

}
