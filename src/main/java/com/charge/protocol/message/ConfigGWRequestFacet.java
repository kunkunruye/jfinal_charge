package com.charge.protocol.message;

import static com.charge.protocol.ProtocolConstant.*;

/**
 * Created by zhengkun on 18-2-24.
 */
public class ConfigGWRequestFacet extends RequestFacet {
    private String device;

    private String deviceType;

    private Integer modBusAddr;

    private Long pvsn;

    private Integer lora_F;

    private Integer sf;

    private Integer bf;

    private Integer cr;

    private Integer pv_In = 0;

    private Integer deviceNum;

    private String name;

    private String prov;

    private String city;

    public ConfigGWRequestFacet(String requestType,Integer lora_F, Integer sf,Integer bf,Integer cr, Integer pv_In){
        this.requestType = requestType;
        this.lora_F = lora_F;
        this.sf = sf;
        this.bf = bf;
        this.cr = cr;
        this.pv_In = pv_In;
    }

    public ConfigGWRequestFacet(String requestType,Integer lora_F, Integer sf,Integer bf,Integer cr, Integer pv_In,
                        String device,String deviceType,Integer modBusAddr,Integer deviceNum,
                        Long pvsn,String name,String prov,String city){
        this.requestType = requestType;
        this.lora_F = lora_F;
        this.sf = sf;
        this.bf = bf;
        this.cr = cr;
        this.pv_In = pv_In;
        this.device = device;
        this.deviceType = deviceType;
        this.modBusAddr = modBusAddr;
        this.deviceNum = deviceNum;
        this.pvsn = pvsn;
        this.name = name;
        this.prov = prov;
        this.city = city;
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

    public Integer getLora_F() {
        return lora_F;
    }

    public void setLora_F(Integer lora_F) {
        this.lora_F = lora_F;
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

    public Integer getPv_In() {
        return pv_In;
    }

    public void setPv_In(Integer pv_In) {
        this.pv_In = pv_In;
    }

    public Integer getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Integer deviceNum) {
        this.deviceNum = deviceNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String toString(){
        if (pv_In == 0){
            return MSG_REQUEST + MSG_COMPONENT_SEPARATOR + requestType + MSG_SEGMENT_SEPARATOR +
                    MSG_CONG_LORA_F + MSG_COMPONENT_SEPARATOR + lora_F + MSG_SEGMENT_SEPARATOR +
                    MSG_CONG_SF + MSG_COMPONENT_SEPARATOR + sf + MSG_SEGMENT_SEPARATOR +
                    MSG_CONG_BF + MSG_COMPONENT_SEPARATOR + bf + MSG_SEGMENT_SEPARATOR +
                    MSG_CONG_CR + MSG_COMPONENT_SEPARATOR + cr + MSG_SEGMENT_SEPARATOR +
                    MSG_CONG_PV_IN + MSG_COMPONENT_SEPARATOR + pv_In ;

        } else if (pv_In == 1){
            return MSG_REQUEST + MSG_COMPONENT_SEPARATOR + requestType + MSG_SEGMENT_SEPARATOR +
                    MSG_CONG_LORA_F + MSG_COMPONENT_SEPARATOR + lora_F + MSG_SEGMENT_SEPARATOR +
                    MSG_CONG_SF + MSG_COMPONENT_SEPARATOR + sf + MSG_SEGMENT_SEPARATOR +
                    MSG_CONG_BF + MSG_COMPONENT_SEPARATOR + bf + MSG_SEGMENT_SEPARATOR +
                    MSG_CONG_CR + MSG_COMPONENT_SEPARATOR + cr + MSG_SEGMENT_SEPARATOR +
                    MSG_CONG_PV_IN + MSG_COMPONENT_SEPARATOR + pv_In + MSG_SEGMENT_SEPARATOR +
                    MSG_DEVICE + MSG_COMPONENT_SEPARATOR + device + MSG_SEGMENT_SEPARATOR +
                    MSG_DEVICETYPE + MSG_COMPONENT_SEPARATOR + deviceType + MSG_SEGMENT_SEPARATOR +
                    MSG_CONG_MODBUSADDR + MSG_COMPONENT_SEPARATOR + modBusAddr + MSG_SEGMENT_SEPARATOR +
                    MSG_CONG_DEVICE_NUM + MSG_COMPONENT_SEPARATOR + deviceNum + MSG_SEGMENT_SEPARATOR +
                    MSG_PVSN + MSG_COMPONENT_SEPARATOR + String.format("%012d",pvsn) ;
        }else{
            return MSG_REQUEST + MSG_COMPONENT_SEPARATOR + requestType + MSG_SEGMENT_SEPARATOR +
                    MSG_CONG_LORA_F + MSG_COMPONENT_SEPARATOR + lora_F + MSG_SEGMENT_SEPARATOR +
                    MSG_CONG_SF + MSG_COMPONENT_SEPARATOR + sf + MSG_SEGMENT_SEPARATOR +
                    MSG_CONG_BF + MSG_COMPONENT_SEPARATOR + bf + MSG_SEGMENT_SEPARATOR +
                    MSG_CONG_CR + MSG_COMPONENT_SEPARATOR + cr ;
        }
    }
}
