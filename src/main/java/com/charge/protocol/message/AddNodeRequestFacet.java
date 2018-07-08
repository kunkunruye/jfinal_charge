package com.charge.protocol.message;


import static com.charge.protocol.ProtocolConstant.*;

/**
 * Created by zhengkun on 18-2-24.
 */
public class AddNodeRequestFacet extends RequestFacet {

    private String device;

    private String deviceType;

    private Integer modBusAddr;

    private Long pvsn;

    private Integer deviceNum;

    private Integer loraF;

    private Integer sf;

    private Integer bf;

    private Integer cr;

    public AddNodeRequestFacet(String requestType,String device,String deviceType,Integer modBusAddr,Long pvsn,Integer deviceNum,Integer loraF,Integer sf,Integer bf,Integer cr){
        this.requestType = requestType;
        this.device = device;
        this.deviceType = deviceType;
        this.modBusAddr = modBusAddr;
        this.pvsn = pvsn;
        this.deviceNum = deviceNum;
        this.loraF = loraF;
        this.sf = sf;
        this.bf = bf;
        this.cr = cr;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getModBusAddr() {
        return modBusAddr;
    }

    public void setModBusAddr(Integer modBusAddr) {
        this.modBusAddr = modBusAddr;
    }

    public Long getPvsn() {
        return pvsn;
    }

    public void setPvsn(Long pvsn) {
        this.pvsn = pvsn;
    }

    public Integer getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Integer deviceNum) {
        this.deviceNum = deviceNum;
    }

    public Integer getLoraF() {
        return loraF;
    }

    public void setLoraF(Integer loraF) {
        this.loraF = loraF;
    }

    public Integer getSf() {
        return sf;
    }

    public void setSf(Integer sf) {
        this.sf = sf;
    }

    public Integer getBf() {
        return bf;
    }

    public void setBf(Integer bf) {
        this.bf = bf;
    }

    public Integer getCr() {
        return cr;
    }

    public void setCr(Integer cr) {
        this.cr = cr;
    }

    public String toString(){
        StringBuilder msg = new StringBuilder();
        for(int addr=modBusAddr;addr<modBusAddr+deviceNum;addr++){
            String deviceName = device+"_"+String.format("%02d",addr);
            msg.append(MSG_REQUEST).append(MSG_COMPONENT_SEPARATOR).append(requestType).append(MSG_SEGMENT_SEPARATOR).
                    append(MSG_DEVICE).append(MSG_COMPONENT_SEPARATOR).append(deviceName).append(MSG_SEGMENT_SEPARATOR);
                    if(device.equals("INV")){
                        msg.append(MSG_DEVICETYPE).append(MSG_COMPONENT_SEPARATOR).append(deviceType).append(MSG_SEGMENT_SEPARATOR).
                            append(MSG_CONG_MODBUSADDR).append(MSG_COMPONENT_SEPARATOR).append(addr).append(MSG_SEGMENT_SEPARATOR);
                    }
                    msg.append(MSG_PVSN).append(MSG_COMPONENT_SEPARATOR).append(String.format("%012d", pvsn)).append(MSG_SEGMENT_SEPARATOR).
                    append(MSG_CONG_LORA_F).append(MSG_COMPONENT_SEPARATOR).append(loraF).append(MSG_SEGMENT_SEPARATOR).
                    append(MSG_CONG_SF).append(MSG_COMPONENT_SEPARATOR).append(sf).append(MSG_SEGMENT_SEPARATOR).
                    append(MSG_CONG_BF).append(MSG_COMPONENT_SEPARATOR).append(bf).append(MSG_SEGMENT_SEPARATOR).
                    append(MSG_CONG_CR).append(MSG_COMPONENT_SEPARATOR).append(cr).
                    append(MSG_FACET_SEPARATOR_INSIDE);
        }
        msg.deleteCharAt(msg.length()-1);
        return msg.toString();
    }

}
