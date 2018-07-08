package com.charge.protocol.message;

import static com.charge.protocol.ProtocolConstant.*;

/**
 * Created by zhengkun on 18-2-24.
 */
public class DeleteGWRequestFacet extends RequestFacet {

    private String cry;

    public DeleteGWRequestFacet(String requestType, String cry){
        this.requestType = requestType;
        this.cry = cry;
    }

    public String toString(){
        return MSG_REQUEST + MSG_COMPONENT_SEPARATOR + requestType + MSG_SEGMENT_SEPARATOR + MSG_CONG_CRY + MSG_COMPONENT_SEPARATOR + cry;
    }

}
