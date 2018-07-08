package com.charge.protocol;

/**
 * Created by wukai on 17-7-12.
 */
public class ProtocolConstant {
    public static final String MQTT_BROKER_IP = "tcp://139.199.78.128:1883";

    public static final int MSG_UPDATE_WEB_PORT =   7777;       //update的web管理端口
    public static final int MSG_UPDATE_PROCESS_PORT =   8888;       //update的数据管理端口

    public static final String MQTT_TOPIC_SEPARATOR = "/";

    public static final String MQTT_TOPIC_TO_POINT = "P";
    public static final String MQTT_TOPIC_TO_CENTER = "C";

    public static final String MQTT_TOPIC_GW_INIT = "GW_INIT";

    public static final String TOPIC_DATA = "data";
    public static final String TOPIC_IMAGE = "image";
    public static final String TOPIC_RESPONSE = "response";
    public static final String TOPIC_REQUEST = "request";
    public static final String TOPIC_CONTROL = "control";
    public static final String TOPIC_UPDATE = "update";
    public static final String TOPIC_NOTIFY = "notify";
    public static final String TOPIC_CONFIG = "config";
    public static final String TOPIC_CONFIRM = "confirm";


    public static final String MSG_FACET_SEPARATOR = "\r";              //数据段分隔符
    public static final String MSG_FACET_SEPARATOR_INSIDE = "\r\n";     //内部使用的数据段分隔符
    public static final String MSG_SEGMENT_SEPARATOR = ";";             //字段分隔符
    public static final String MSG_COMPONENT_SEPARATOR = ":";           //组件分隔符
    public static final String MSG_REPEAT_SEPARATOR = ",";              //重复分隔符


    public static final String MSG_STATIONNAME = "POS";         //电站名称
    public static final String MSG_SERIALNUMBER                             = "SEQ";         //信息序号／信息唯一标示号
    public static final String MSG_TIME                                     = "TIME";        //时间
    public static final String MSG_DEVICETYPE                               = "DEVICETYPE";  //机型种类
    public static final String MSG_GWID                                     = "GWID";        //网关ID
    public static final String MSG_LZOFLAG                                  = "LZOFLAG";     //判断是否压缩0:未压缩,1:已压缩
    public static final String MSG_LZOLEN                                   = "LZOLEN";        //数据压缩前长度
    public static final String MSG_GW_STATUS                                = "STATUS";        //网关硬件状态


    public static final String MSG_REQUEST                                  = "REQUEST";     //REQUEST主题，请求
    public static final String MSG_RESPONSE                                 = "RESPONSE";    //响应
    public static final String MSG_COMMAND                                  = "COMMAND";     //控制
    public static final String MSG_CONFIRM                                  = "CONFIRM";     //确认网关配置
    public static final String MSG_CONFIG                                   = "CONFIG";      //GW入网请求

    public static final String MSG_DEVICE_INV = "INV";//逆变器
    public static final String MSG_DEVICE_COMB = "COMB";//汇流箱
    public static final String MSG_DEVICE_WES = "WES";//气象站

    public static final String MSG_DEVICE                                   = "DEVICE";      //设备名称
    public static final String MSG_PVSN                                     = "PVSN";      //采集设备序列号
    public static final String MSG_PV_SN                                    = "PV_SN";      //配置网关序列号
    public static final String MSG_ALARM                                    = "WARN";        //设备告警标志
    public static final String MSG_STATUS                                   = "STATUS";      //采集设备故障标志
    public static final String MSG_LNG                                      = "LNG";        //经度
    public static final String MSG_LAT                                      = "LAT";        //纬度
    public static final String MSG_LAC                                      = "LAC";        //位置信息编码
    public static final String MSG_CI                                       = "CI";        //区域信息编码
    public static final String MSG_STREET                                   = "STREET";    //街道信息

    //2017.10.19况发志新增
    public static final String MSG_IMAGE_DEVICE                             =   "DEVICE";   //发送图像的设备名
    public static final String MSG_IMAGE_FRAME                              =   "FRAME";    //图像分割后的数据数量
    public static final String MSG_IMAGE_INDEX                              =   "INDEX";    //数据在图像的位置
    public static final String MSG_IMAGE_IMAGE                              =   "IMAGE";    //带有图像数据的十六进制码


    public static final String MSG_REQUEST_CODE_REVERSE                     =   "0";        //0:保留
    public static final String MSG_REQUEST_CODE_INVERTERNUMBER              =   "1";        //1:请求传输逆变器编号及种类
    public static final String MSG_REQUEST_CODE_COMBBOX                     =   "2";        //2:请求传输汇流箱编号及种类
    public static final String MSG_REQUEST_CODE_GEOGRAPHYINFO               =   "3";        //3:请求传输地理位置信息
    public static final String MSG_REQUEST_CODE_ONLINECONFIRMATION          =   "4";        //4:请求传输在线确认
    public static final String MSG_REQUEST_CODE_TRANSFERDATACONFIG          =   "5";        //5:请求传输数据配置,需要在 addInfo:data 项中增加相应设备型号,
    public static final String MSG_REQUEST_CODE_TRANSFERALARMCONFIG         =   "6";        //6:请求传输报警配置,需要在 addInfo:data 项中增加相应设备型号,
    public static final String MSG_REQUEST_CODE_NODEINFO                    =   "7";        //7:请求传输相应的node信息
    public static final String MSG_REQUEST_CODE_ADDNODE                     =   "8";        //8:请求加入某个节点
    public static final String MSG_REQUEST_CODE_DELETENODE                  =   "9";        //9:请求删除某个节点
    public static final String MSG_REQUEST_CODE_CHANGE_GW_INFO              =   "10";       //10:请求改变GW配置信息
    public static final String MSG_REQUEST_CODE_DELETEPV                    =   "11";       //11:请求删除采集器
    public static final String MSG_REQUEST_CODE_DELETEGW                    =   "12";       //12:请求删除网关

    public static final String MSG_RESPONCE_CODE_REVERSE                    =   "0";        //0:保留
    public static final String MSG_RESPONCE_CODE_INVERTERNUMBER             =   "1";        //1:响应传输逆变器编号及种类
    public static final String MSG_RESPONCE_CODE_COMBBOX                    =   "2";        //2:响应传输汇流箱编号及种类
    public static final String MSG_RESPONCE_CODE_GEOGRAPHYINFO              =   "3";        //3:响应传输地理位置信息
    public static final String MSG_RESPONCE_CODE_ONLINECONFIRMATION         =   "4";        //4:响应传输在线确认
    public static final String MSG_RESPONCE_CODE_TRANSFERDATACONFIG         =   "5";        //5:响应传输数据配置
    public static final String MSG_RESPONCE_CODE_TRANSFERALARMCONFIG        =   "6";        //6:响应传输报警配置
    public static final String MSG_RESPONCE_CODE_NODEINFO                   =   "7";        //7:响应传输相应的node信息
    public static final String MSG_RESPONCE_CODE_ADDNODE                    =   "8";        //8:响应加入某个节点
    public static final String MSG_RESPONCE_CODE_DELETENODE                 =   "9";        //9:响应删除某个节点
    public static final String MSG_RESPONCE_CODE_CHANGE_GW_INFO             =   "10";       //10:响应改变GW配置信息
    public static final String MSG_RESPONCE_CODE_DELETEPV                   =   "11";       //11:响应删除采集器
    public static final String MSG_RESPONCE_CODE_DELETEGW                   =   "12";       //12:响应删除网关

    public static final String MSG_RESPONCE_DELETE =   "DELETE";  //删除节点成功与否标志
    public static final String MSG_RESPONCE_ADD =   "ADD";  //添加节点成功与否标志
    public static final String MSG_RESPONCE_RESULT =   "RESULT";  //改变GW后的结果标志


    public static final String MSG_CONTROL_CODE_REVERSE                     =   "0";        //0:保留
    public static final String MSG_CONTROL_CODE_INTERVAL                    =   "1";        //1:设定数据传输间隔
    public static final String MSG_CONTROL_CODE_TRANSMISSION                =   "2";        //2:加速传输,10s间隔
    public static final String MSG_CONTROL_CODE_STARTIMAGES                 =   "3";        //3:开始传输图像
    public static final String MSG_CONTROL_CODE_STOPIMAGES                  =   "4";        //4:停止传输图像
    public static final String MSG_CONTROL_CODE_SETSUNTIME                  =   "5";        //5:设定日出日落时间

    //2017.11.14况发志新增
    public static final String MSG_UPDATE_UPDATE                           =   "UPDATE";   //升级文件的版本号
    public static final String MSG_UPDATE_OFFSET                           =   "OFFSET";   //本次发送的数据在总的升级文件中的偏移量,单位为字节
    public static final String MSG_UPDATE_LEN                              =   "LEN";      //本次发送的数据长度,单位为字节
    public static final String MSG_UPDATE_LEN_ALL                          =   "LEN_ALL";  //升级文件的总的长度
    public static final String MSG_UPDATE_CRC                              =   "CRC";      //校验,一个字节,对本次所发数据按字节异或操作所得到的结果
    public static final String MSG_UPDATE_STATUS                           =   "STATUS";   //升级状态,可选值为 0、1、3、4、5、10,0 代表该段数据校验有误;1 代表校验成功;3 代表子站网关处于电池供电,升级有可能会中断;4 代表内部 flash 损坏,不可升级;5代表已最新无需升级;10升级成功


    //2018年1月3日
    public static final String MSG_CONTROL_DATA_TICK                        =   "TICK";        //传输间隔
    public static final String MSG_CONTROL_RISETIME                        =   "RISETIME";        //日出时间
    public static final String MSG_CONTROL_FALLTIME                        =   "FALLTIME";        //日落时间


    public static final String MSG_CONG_LORA_F =   "LORA_F";  //信道
    public static final String MSG_CONG_SF =   "SF";  //扩频因子
    public static final String MSG_CONG_BF =   "BF";  //带宽
    public static final String MSG_CONG_CR =   "CR";  //纠错编码
    public static final String MSG_CONG_PV_IN =   "PV_IN";  //是否启用内部PV
    public static final String MSG_CONG_DEVICE_NUM =   "DEVICENUM";  //设备的个数
    public static final String MSG_CONG_MODBUSADDR =   "MODBUSADDR";  //modbusaddr
    public static final String MSG_CONG_NAME =   "NAME";  //改变后的电站名
    public static final String MSG_CONG_PROV =   "PROV";  //改变后的省名称
    public static final String MSG_CONG_CITY =   "CITY";  //改变后的市名称
    public static final String MSG_CONG_CRY =   "CRY";  //内部参数

}





























