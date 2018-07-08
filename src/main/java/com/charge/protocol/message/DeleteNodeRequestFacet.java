package com.charge.protocol.message;

import static com.charge.protocol.ProtocolConstant.*;

/**
 * Created by zhengkun on 18-2-24.
 */
public class DeleteNodeRequestFacet extends RequestFacet {


    private String device;
    private Long pvsn;

    public DeleteNodeRequestFacet(String requestType,String device,Long pvsn){
        this.requestType = requestType;
        this.device = device;
        this.pvsn = pvsn;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Long getPvsn() {
        return pvsn;
    }

    public void setPvsn(Long pvsn) {
        this.pvsn = pvsn;
    }

    public String toString(){
        return MSG_REQUEST + MSG_COMPONENT_SEPARATOR + requestType + MSG_SEGMENT_SEPARATOR +
                MSG_DEVICE + MSG_COMPONENT_SEPARATOR + device + MSG_SEGMENT_SEPARATOR +
                MSG_PVSN + MSG_COMPONENT_SEPARATOR + String.format("%012d",pvsn) ;
    }

}
