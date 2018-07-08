package com.charge.protocol.message;


import com.charge.utils.SEQGeneration;

import java.util.Date;
import java.util.Vector;

import static com.charge.protocol.ProtocolConstant.*;

/**
 * Created by zhengkun on 17-12-27.
 */
public class RequestMsg {
    private GatewayFacet gatewayFacet;

    private Vector<RequestFacet> requestFacetVec = new Vector<>();

    public RequestMsg() {

    }

    public void setGatewayFacet(GatewayFacet gatewayFacet) {
        this.gatewayFacet = gatewayFacet;
    }


    public void addRequestFacet(RequestFacet requestFacet){
        requestFacetVec.add(requestFacet);
    }

    public String toString(){
        String posFacetStr = gatewayFacet.toString();
        StringBuilder msg = new StringBuilder();

        msg.append(posFacetStr).append(MSG_FACET_SEPARATOR_INSIDE);

        for (RequestFacet requestFacet : requestFacetVec){
            msg.append(requestFacet.toString()).append(MSG_FACET_SEPARATOR_INSIDE);
        }
        msg.deleteCharAt(msg.length()-2);

        return msg.toString();
    }

}
