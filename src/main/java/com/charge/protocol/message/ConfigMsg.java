package com.charge.protocol.message;



import com.charge.utils.SEQGeneration;

import java.util.Date;

import static com.charge.protocol.ProtocolConstant.*;

public class ConfigMsg {

    private POSFacet posFacet;

    private ConfigFacet configFacet;

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

    public POSFacet getPosFacet() {
        return posFacet;
    }

    public void setPosFacet(POSFacet posFacet) {
        this.posFacet = posFacet;
    }

    public ConfigFacet getConfigFacet() {
        return configFacet;
    }

    public void setConfigFacet(ConfigFacet configFacet) {
        this.configFacet = configFacet;
    }

    //不启用内部PV
    public ConfigMsg(String stationName,Long gatewayID,Integer loraF, Integer sf, Integer bf, Integer cr, Integer pvIn,String province,String city){
        Integer sequenceNum = SEQGeneration.getInstance().getSEQ();
        Date utc = new Date();
        this.posFacet = new POSFacet(stationName, sequenceNum, utc, String.format("%012d",gatewayID));
        this.configFacet = new ConfigFacet(loraF,sf,bf,cr,pvIn,stationName,province,city);
    }

    //启用内部PV
    public ConfigMsg(String stationName,Long gatewayID,Integer loraF, Integer sf, Integer bf, Integer cr, Integer pvIn,
                     String device,String deviceType, Integer modbusAddr,Integer deviceNum,String province,String city,Long pvSn){
        Integer sequenceNum = SEQGeneration.getInstance().getSEQ();
        Date utc = new Date();
        this.posFacet = new POSFacet(stationName, sequenceNum, utc, String.format("%012d",gatewayID));
        this.configFacet = new ConfigFacet(loraF,sf,bf,cr,pvIn,device,deviceType,modbusAddr,deviceNum,stationName,province,city,String.format("%012d",pvSn));
    }

    @Override
    public String toString() {
        String posFacetStr = posFacet.toString();
        String configFacetStr = configFacet.toString();
        return posFacetStr+ MSG_FACET_SEPARATOR_INSIDE +configFacetStr;

    }

}
