package com.charge.deviceInterface;

import java.util.Set;

public interface Collector extends Node {

    public void setGWId(Long GWId);
    public Long getGWId();
    public void addDevice(Device newDevice);
    public void delDevcie(Long deviceId);
    public Device getDevice(Long deviceId);
    public Set<Device> getDevices();

}
