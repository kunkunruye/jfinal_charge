package com.charge.protocol.message;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.charge.protocol.ProtocolConstant.*;

/**
 * Created by zhengkun on 17-12-27.
 */
public class POSFacet {
    private String powerstationName;
    private Integer sequenceNum;
    private Date utc;
    private String gatewayID;

    public POSFacet(String powerstationName, Integer sequenceNum, Date utc, String gatewayID) {
        this.powerstationName = powerstationName;
        this.sequenceNum = sequenceNum;
        this.utc = utc;
        this.gatewayID = gatewayID;
    }

    public String getPowerstationName() {
        return powerstationName;
    }

    public void setPowerstationName(String powerstationName) {
        this.powerstationName = powerstationName;
    }

    public Integer getSequenceNum() {
        return sequenceNum;
    }

    public void setSequenceNum(Integer sequenceNum) {
        this.sequenceNum = sequenceNum;
    }

    public Date getUtc() {
        return utc;
    }

    public void setUtc(Date utc) {
        this.utc = utc;
    }

    public String getGatewayID() {
        return gatewayID;
    }

    public void setGatewayID(String gatewayID) {
        this.gatewayID = gatewayID;
    }

    public String toString(){

        String currentTimeStr;
        SimpleDateFormat currentTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        currentTimeStr=currentTimeFormat.format(utc);

        return MSG_STATIONNAME + MSG_COMPONENT_SEPARATOR +powerstationName+ MSG_SEGMENT_SEPARATOR +MSG_SERIALNUMBER
                + MSG_COMPONENT_SEPARATOR +sequenceNum+ MSG_SEGMENT_SEPARATOR + MSG_TIME + MSG_COMPONENT_SEPARATOR +currentTimeStr
                + MSG_SEGMENT_SEPARATOR + MSG_GWID + MSG_COMPONENT_SEPARATOR + gatewayID;

    }
}
