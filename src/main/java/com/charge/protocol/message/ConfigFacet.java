package com.charge.protocol.message;


import static com.charge.protocol.ProtocolConstant.*;

public class ConfigFacet {
    private Integer loraF;

    private Integer sf;

    private Integer bf;

    private Integer cr;

    private Integer pvIn;

    private String device;

    private String deviceType;

    private Integer modbusAddr;

    private Integer deviceNum;

    private String stationName;

    private String province;

    private String city;

    private String pvSn;

    public ConfigFacet(Integer loraF, Integer sf, Integer bf, Integer cr,
                       Integer pvIn, String device,String deviceType,
                       Integer modbusAddr,Integer deviceNum,String stationName, String province,String city,String pvSn){
        this.loraF=loraF;
        this.sf=sf;
        this.bf=bf;
        this.cr=cr;
        this.pvIn=pvIn;
        this.device=device;
        this.deviceType=deviceType;
        this.modbusAddr=modbusAddr;
        this.deviceNum=deviceNum;
        this.stationName=stationName;
        this.province=province;
        this.city=city;
        this.pvSn=pvSn;
    }

    public ConfigFacet(Integer loraF, Integer sf, Integer bf, Integer cr, Integer pvIn,String stationName,String province,String city){
        this.loraF=loraF;
        this.sf=sf;
        this.bf=bf;
        this.cr=cr;
        this.pvIn=pvIn;
        this.stationName=stationName;
        this.province=province;
        this.city=city;
    }

    public Integer getLoraF() {
        return loraF;
    }

    public Integer getSf() {
        return sf;
    }

    public Integer getBf() {
        return bf;
    }

    public Integer getCr() {
        return cr;
    }

    public Integer getPvIn() {
        return pvIn;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public Integer getModbusAddr() {
        return modbusAddr;
    }

    public Integer getDeviceNum() {
        return deviceNum;
    }

    public String getStationName() {
        return stationName;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {

        switch (pvIn){
            case 0:
                return  MSG_CONFIG + MSG_COMPONENT_SEPARATOR + "1" + MSG_SEGMENT_SEPARATOR +
                        MSG_CONG_LORA_F + MSG_COMPONENT_SEPARATOR + loraF + MSG_SEGMENT_SEPARATOR +
                        MSG_CONG_SF + MSG_COMPONENT_SEPARATOR + sf + MSG_SEGMENT_SEPARATOR +
                        MSG_CONG_BF + MSG_COMPONENT_SEPARATOR + bf + MSG_SEGMENT_SEPARATOR +
                        MSG_CONG_CR + MSG_COMPONENT_SEPARATOR + cr + MSG_SEGMENT_SEPARATOR +
                        MSG_CONG_PV_IN + MSG_COMPONENT_SEPARATOR + pvIn + MSG_SEGMENT_SEPARATOR+
                        MSG_CONG_NAME + MSG_COMPONENT_SEPARATOR + stationName + MSG_SEGMENT_SEPARATOR +
                        MSG_CONG_PROV + MSG_COMPONENT_SEPARATOR + province + MSG_SEGMENT_SEPARATOR +
                        MSG_CONG_CITY + MSG_COMPONENT_SEPARATOR + city + MSG_SEGMENT_SEPARATOR;
            case 1:
                return  MSG_CONFIG + MSG_COMPONENT_SEPARATOR + "1" + MSG_SEGMENT_SEPARATOR +
                        MSG_CONG_LORA_F + MSG_COMPONENT_SEPARATOR + loraF + MSG_SEGMENT_SEPARATOR +
                        MSG_CONG_SF + MSG_COMPONENT_SEPARATOR + sf + MSG_SEGMENT_SEPARATOR +
                        MSG_CONG_BF + MSG_COMPONENT_SEPARATOR + bf + MSG_SEGMENT_SEPARATOR +
                        MSG_CONG_CR + MSG_COMPONENT_SEPARATOR + cr + MSG_SEGMENT_SEPARATOR +
                        MSG_CONG_PV_IN + MSG_COMPONENT_SEPARATOR + pvIn + MSG_SEGMENT_SEPARATOR +
                        MSG_DEVICE + MSG_COMPONENT_SEPARATOR + device + MSG_SEGMENT_SEPARATOR +
                        MSG_DEVICETYPE + MSG_COMPONENT_SEPARATOR + deviceType + MSG_SEGMENT_SEPARATOR +
                        MSG_CONG_MODBUSADDR + MSG_COMPONENT_SEPARATOR + modbusAddr + MSG_SEGMENT_SEPARATOR +
                        MSG_CONG_DEVICE_NUM + MSG_COMPONENT_SEPARATOR + deviceNum + MSG_SEGMENT_SEPARATOR +
                        MSG_PVSN + MSG_COMPONENT_SEPARATOR + pvSn + MSG_SEGMENT_SEPARATOR +
                        MSG_CONG_NAME + MSG_COMPONENT_SEPARATOR + stationName + MSG_SEGMENT_SEPARATOR +
                        MSG_CONG_PROV + MSG_COMPONENT_SEPARATOR + province + MSG_SEGMENT_SEPARATOR +
                        MSG_CONG_CITY + MSG_COMPONENT_SEPARATOR + city + MSG_SEGMENT_SEPARATOR;
            default:
                return "";
        }



    }
}
