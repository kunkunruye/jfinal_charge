package com.charge.deviceInterface;


import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public interface Node {

    /*节点包括传输节点和设备节点
    传输节点：网关和采集器
    设备节点：具体的被采集对象，如逆变器，汇流箱，气象站等

    */

    void setID(Long id);//

    Long getID();

    //更新数据
    boolean updateData(Date updateTime, JSONObject deviceObj);

    //更新状态，（报警）
    boolean updateStatus(Date updateTime, JSONObject deviceObj);
}
