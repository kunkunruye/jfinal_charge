package com.charge.protocol.message;

import static com.charge.protocol.ProtocolConstant.*;

/**
 * Created by zhengkun on 18-2-24.
 */
public class DataIntervalControlFacet extends ControlFacet {

    private String tickTime;

    public DataIntervalControlFacet(String controlType, String tickTime) {
        this.controlType = controlType;
        this.tickTime = tickTime;
    }

    public String getTickTime() {
        return tickTime;
    }

    public void setTickTime(String tickTime) {
        this.tickTime = tickTime;
    }

    public String toString(){
        return MSG_COMMAND + MSG_COMPONENT_SEPARATOR + controlType + MSG_SEGMENT_SEPARATOR + MSG_CONTROL_DATA_TICK + MSG_COMPONENT_SEPARATOR + tickTime;

    }

}
