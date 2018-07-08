package com.charge.deviceInterface;

public interface Device extends Node {

    public void setCollectorId(Long collectorId);
    public Long getCollectorId();

    public void setGWId(Long gwId);
    public Long getGWId();

}
