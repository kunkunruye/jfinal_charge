package com.charge.protocol.message;


import com.charge.utils.SEQGeneration;

import java.util.Date;

import static com.charge.protocol.ProtocolConstant.*;

/**
 * Created by zhengkun on 17-12-27.
 */
public class RequestMsg {
    private GatewayFacet gatewayFacet;

    private RequestFacet requestFacet;

    public RequestMsg(Long gatewayID, String requestType) {

        Integer sequenceNum = SEQGeneration.getInstance().getSEQ();
        Date utc = new Date();
        this.gatewayFacet = new GatewayFacet( sequenceNum, utc, String.format("%012d",gatewayID));
        this.requestFacet=new RequestFacet(requestType);
    }

    public RequestMsg(Long gatewayID, String requestType, Long pvsn) {

        Integer sequenceNum = SEQGeneration.getInstance().getSEQ();
        Date utc = new Date();
        this.gatewayFacet = new GatewayFacet(sequenceNum, utc, String.format("%012d",gatewayID));
        this.requestFacet=new DeletePVRequestFacet(requestType,pvsn);
    }


    public GatewayFacet getGatewayFacet() {
        return gatewayFacet;
    }

    public void setGatewayFacet(GatewayFacet gatewayFacet) {
        this.gatewayFacet = gatewayFacet;
    }

    public Integer getSequenceNum() {
        return gatewayFacet.getSequenceNum();
    }

    public void setSequenceNum(Integer sequenceNum) {
        gatewayFacet.setSequenceNum(sequenceNum);
    }

    public RequestFacet getRequestFacet() {
        return requestFacet;
    }

    public void setRequestFacet(RequestFacet requestFacet) {
        this.requestFacet = requestFacet;
    }

    public String toString(){
        String posFacetStr = gatewayFacet.toString();
        String requestFacetStr = requestFacet.toString();
        return posFacetStr + MSG_FACET_SEPARATOR_INSIDE + requestFacetStr;
    }

}
