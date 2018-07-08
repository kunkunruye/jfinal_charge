package com.charge.protocol.message;

import static com.charge.protocol.ProtocolConstant.*;

/**
 * Created by zhengkun on 18-2-24.
 */
public class SunsetControlFacet extends ControlFacet {
    private String riseTime;

    private String fallTime;

    public SunsetControlFacet(String controlType, String riseTime, String fallTime) {
        this.controlType = controlType;
        this.riseTime = riseTime;
        this.fallTime = fallTime;
    }

    public String getRiseTime() {
        return riseTime;
    }

    public void setRiseTime(String riseTime) {
        this.riseTime = riseTime;
    }

    public String getFallTime() {
        return fallTime;
    }

    public void setFallTime(String fallTime) {
        this.fallTime = fallTime;
    }

    public String toString() {
        return MSG_COMMAND + MSG_COMPONENT_SEPARATOR + controlType + MSG_SEGMENT_SEPARATOR + MSG_CONTROL_RISETIME + MSG_COMPONENT_SEPARATOR + riseTime
                + MSG_SEGMENT_SEPARATOR + MSG_CONTROL_FALLTIME + MSG_COMPONENT_SEPARATOR + fallTime;
    }
}
