package com.charge.deviceInterface;

import java.util.Set;

public interface GateWay extends Node {
    public void setName(String name);
    public String getName();
    public Device getDevice(Long deviceId);
    public Set<Device> getDevices();
}
