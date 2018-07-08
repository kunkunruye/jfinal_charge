package com.charge.protocol.message;



import com.charge.utils.SEQGeneration;

import java.util.Date;

import static com.charge.protocol.ProtocolConstant.*;

public class ControlMsg {
    private POSFacet posFacet;

    private ControlFacet controlFacet;

    public ControlMsg(String stationName, Long gatewayID, String controlType) {

        Integer sequenceNum = SEQGeneration.getInstance().getSEQ();
        Date utc = new Date();
        this.posFacet = new POSFacet(stationName, sequenceNum, utc, gatewayID.toString());
        this.controlFacet=new ControlFacet(controlType);
    }
    public ControlMsg(String stationName, Long gatewayID, String controlType,String tickTime) {

        Integer sequenceNum = SEQGeneration.getInstance().getSEQ();
        Date utc = new Date();
        this.posFacet = new POSFacet(stationName, sequenceNum, utc, gatewayID.toString());
        this.controlFacet=new DataIntervalControlFacet(controlType,tickTime);
    }


    public ControlMsg(String stationName, Long gatewayID, String controlType,String riseTime,String fallTime) {

        Integer sequenceNum = SEQGeneration.getInstance().getSEQ();
        Date utc = new Date();
        this.posFacet = new POSFacet(stationName, sequenceNum, utc, gatewayID.toString());
        this.controlFacet=new SunsetControlFacet(controlType,riseTime,fallTime);
    }

    public POSFacet getPosFacet() {
        return posFacet;
    }

    public void setPosFacet(POSFacet posFacet) {
        this.posFacet = posFacet;
    }

    public ControlFacet getControlFacet() {
        return controlFacet;
    }

    public void setControlFacet(ControlFacet controlFacet) {
        this.controlFacet = controlFacet;
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

    @Override
    public String toString() {
        String posFacetStr = posFacet.toString();
        String controlFacetStr = controlFacet.toString();
        return posFacetStr+ MSG_FACET_SEPARATOR_INSIDE +controlFacetStr;

    }
}
