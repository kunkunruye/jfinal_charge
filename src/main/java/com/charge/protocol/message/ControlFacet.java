package com.charge.protocol.message;


import static com.charge.protocol.ProtocolConstant.*;

public class ControlFacet {
    protected String controlType;


    public ControlFacet() {
    }

    public ControlFacet(String controlType) {
        this.controlType = controlType;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }


    public String toString() {
        return MSG_COMMAND + MSG_COMPONENT_SEPARATOR + controlType ;


    }
}
