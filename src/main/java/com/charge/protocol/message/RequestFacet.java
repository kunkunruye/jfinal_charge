package com.charge.protocol.message;

import static com.charge.protocol.ProtocolConstant.*;

/**
 * Created by zhengkun on 17-12-27.
 */
public class RequestFacet {

    protected String requestType;

    public RequestFacet(){
    }

    public RequestFacet(String requestType){
        this.requestType = requestType;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String toString(){
        return MSG_REQUEST + MSG_COMPONENT_SEPARATOR + requestType;
    }

}
