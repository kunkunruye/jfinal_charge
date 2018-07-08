package com.charge.deviceInterface;

import java.util.Set;

public interface GateWay extends Node {

    public void setName(String name);
    public String getName();
    public void addCollector(Collector newCollector);
    public void delCollector(Long collectorId);
    public Collector getCollector(Long collectorId);
    public Set<Collector> getCollectors();
    public Device getDevice(Long deviceId);
    public Set<Device> getDevices();
}
