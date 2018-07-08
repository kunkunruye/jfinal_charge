package com.charge.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;

/**
 * Created by zhengkun on 17-12-18.
 */
public class ReportCalculationUntil {

    private static Logger _log = LoggerFactory.getLogger(ReportCalculationUntil.class);
    /**
     * 获取结果为百分数形式
     * @param i 保留的小数位数
     */
    private static void getPercentInstance(int i){
        NumberFormat nt = NumberFormat.getPercentInstance();
        nt.setMinimumFractionDigits(i);
    }

    /**
     * 获得组串逆变日千瓦电量
     * 当日电量/组件容量
     * @param componentCapacity 组件容量
     * @param dailyGeneration 当日电量
     * @return
     */
    public static double getDailyGenerationPerKW(Double componentCapacity, Double dailyGeneration) {
        double dailyGenerationPerKW=0.0;
        try {
             dailyGenerationPerKW=dailyGeneration / componentCapacity;
        } catch (Exception e) {
           _log.error(e.getMessage());
        }
        return dailyGenerationPerKW;
    }

    /**
     * 获得损耗，结果百分比形式并保留一位小数
     * （电量累计-电表累计）/电量累计
     * @param sumElectric 当月电量累计
     * @param sumElectricMeter 当月发电表显示累计
     * @return 损耗比例
     */
    public static double getLoss(Double sumElectric , Double sumElectricMeter ){
        getPercentInstance(1);
        Double loss=0.0;
        try {
            loss=(sumElectric-sumElectricMeter)/sumElectric;
        } catch (Exception e) {
            _log.error(e.getMessage());
        }
        if(loss.isNaN() || loss.isInfinite()){
            loss = 0.0;
        }
        return loss;
    }
    /**
     * 计算当月/当年完成率，结果百分比形式并保留一位小数
     * 月累计电量/月目标电量
     * @param cumulativeEletricity 累计电量
     * @param targetElectricity 目标电量
     * @return
     */
    public static double getCompletionRateOfMonthOrYear(Double cumulativeEletricity,Double targetElectricity){

        getPercentInstance(1);
        double completionRate=0.0;
        try {
            completionRate=cumulativeEletricity/targetElectricity;
        } catch (Exception e) {
            _log.error(e.getMessage());
        }
        return completionRate;
    }

    /**
     * 获得完成率
     * @param consumptionElectricity 消纳电量
     * @param internetElectricity  入网电量
     * @param targetElectricity  目标电量
     * @return
     */
    public static double getCompletionRateOfMonthOrYear(Double consumptionElectricity,Double internetElectricity,Double targetElectricity){
     getPercentInstance(1);
        double completionRate= 0;
        try {
            completionRate = (consumptionElectricity+internetElectricity)/targetElectricity;
        } catch (Exception e) {
            _log.error(e.getMessage());
        }
        return completionRate;
    }

    /**
     * 日KW电量偏差,结果百分比形式，没有小数
     * （最大日kw电量-日kw电量）/最大日kw电量
     * @param inverterDayKwElectricity 日kw电量
     * @param maxInverterDayKwElectricity 最大日kw电量
     * @return
     */
    public static double getInverterDeviation(Double inverterDayKwElectricity,Double maxInverterDayKwElectricity){
       getPercentInstance(1);
        double inverterDeviation=0.0;
        try {
            inverterDeviation=(maxInverterDayKwElectricity-inverterDayKwElectricity)/maxInverterDayKwElectricity;
        } catch (Exception e) {
            _log.error(e.getMessage());
        }
        return inverterDeviation;
    }


    /**
     * 获得平铺参考
     * @param dipReference  倾角参考
     * @param horizontalIrradiation  水平辐照
     * @param obliqueIrradiation  斜面辐照
     * @param environmentalCoeffidient  环境系数
     * @return
     */
    public static double getTileReference(Double dipReference,Double horizontalIrradiation,Double obliqueIrradiation,Double environmentalCoeffidient){
        double tileReference=0.0;
        try {
            tileReference=dipReference*horizontalIrradiation/obliqueIrradiation*environmentalCoeffidient;
        } catch (Exception e) {
            _log.error(e.getMessage());
        }
        return tileReference;
    }

    /**
     * 获取朝向折减
     * @param installationAngle 安装角度
     * @return
     */
    public static double getOrientationReduce( double installationAngle){
        double orientationReduce=0.0;
        try {
            orientationReduce=1-(1/Math.cos(installationAngle*Math.PI/180)-1);
        } catch (Exception e) {
            _log.error(e.getMessage());
        }
        return orientationReduce;
    }

    /**
     * 获取灰尘极限
     * @param dipReference 倾角参考
     * @param mwCleaningCost mw清洗成本
     * @return
     */
    public static double getDustLimit(Double dipReference,Double mwCleaningCost){
       getPercentInstance(1);
       double dustlimit=0.0;
        try {
            dustlimit=1-mwCleaningCost/1000/10/dipReference*365;
        } catch (Exception e) {
            _log.error(e.getMessage());
        }
        return dustlimit;
    }

    /**
     * 计算组串或者集中效率 结果一位小数百分数。
     * @param componentMismatch 组件失配
     * @param dcLine 直流线路
     * @param groupStringOrCentralizedInversion 组串或者集中逆变
     * @param groupStringOrCentralizedmpptMismatch 组串或者集中mppt失配
     * @param temperatureCoefficient 温度系数
     * @param acSide 交流侧
     * @param transformmer  变压器
     * @param radiationLoss  辐照损失
     * @param dustLimit  灰尘极限
     * @param annualDecay  首年衰减
     * @return 组串或者集中效率
     */
    public static double getGroupStringOrCentralizedEffect(Double componentMismatch,
                                              Double dcLine,
                                              Double groupStringOrCentralizedInversion,
                                              Double groupStringOrCentralizedmpptMismatch,
                                              Double temperatureCoefficient,
                                              Double acSide,
                                              Double transformmer,
                                              Double radiationLoss,
                                              Double dustLimit,
                                              Double annualDecay
                                              ){
        getPercentInstance(1);
        double groupStringOrcentralizedEffict=0.0;
        try {
            groupStringOrcentralizedEffict=componentMismatch*dcLine*groupStringOrCentralizedInversion*groupStringOrCentralizedmpptMismatch*temperatureCoefficient*acSide*transformmer*radiationLoss*(1-(1-dustLimit)/2)*(1-(1-annualDecay)/2);
        } catch (Exception e) {
            _log.error(e.getMessage());
        }
        return groupStringOrcentralizedEffict;
    }

    /**
     * 正常停电值，给的表里的计算公式如下。
     *    1-(2+0.5+5)/365;
     * @return 一位小数的百分数
     */
    public  static double getPowerFailure(){
        getPercentInstance(1);
        double powerFailure=1-(2+0.5+5)/365;
        return powerFailure;
    }

    /**
     *获得公司超发收益
     * @param checkCoeffidient 考核系数
     * @param projectPfice  项目电价
     * @param theoryElectric 理论电量
     * @return
     */
    public static double getSuperIncome(Double checkCoeffidient,Double projectPfice,Double theoryElectric){
                 double superIncome=0.0;
        try {
            superIncome=theoryElectric*(1-checkCoeffidient)*projectPfice;
        } catch (Exception e) {
            _log.error(e.getMessage());
        }
        return superIncome;
    }

    /**
     * 获得理论电量
     * @param horizontalIrradiation 水平辐照
     * @param projectCapacity 项目容量
     * @param environmentalCoeffidient 环境系数
     * @param powerFailure  正常停电
     * @param groupStringEffect  组串效率
     * @return
     */
    public  static double getTheoryElectric(Double horizontalIrradiation,Double projectCapacity,Double environmentalCoeffidient,Double powerFailure, Double groupStringEffect){
              double theoryElectric=0.0;
        try {
            theoryElectric=horizontalIrradiation*projectCapacity*environmentalCoeffidient*powerFailure*groupStringEffect;
        } catch (Exception e) {
            _log.error(e.getMessage());
        }
        return theoryElectric;
    }

    /**
     * 获取理论/KW年或者考核KW/年
     * @param theoryOrAssessmentElectric 理论或者考核电量
     * @param projectCapacity 项目容量
     * @return
     */
    public static double theoryOrassessmentKWEveryYear(Double theoryOrAssessmentElectric,Double projectCapacity){
        double theoryOrassessmentKWEveryYear=0.0;
        try {
            theoryOrassessmentKWEveryYear=theoryOrAssessmentElectric/projectCapacity;
        } catch (Exception e) {
            _log.error(e.getMessage());
        }
        return theoryOrassessmentKWEveryYear;
    }

    /**
     * 表中上边的考核电量现在改成考核偏差，获得考核偏差。
     * @param theoryElectric  理论电量
     * @param assessmentElectric 考核电量
     * @return 考核偏差
     */
   public static double getAssessmentDeviation(Double theoryElectric,Double assessmentElectric){
        double assessmentDeviation=0.0;
       try {
           assessmentDeviation=theoryElectric-assessmentElectric;
       } catch (Exception e) {
           _log.error(e.getMessage());
       }
       return assessmentDeviation;
   }

    /**
     * 获得考核电量
     * @param theoryElectric 理论电量
     * @param assessmentCoefficient  考核系数
     * @return
     */
   public static double getAssessmentElectric(Double theoryElectric,Double assessmentCoefficient){
       double assessmentElectric=0.0;
       try {
           assessmentCoefficient=theoryElectric*assessmentCoefficient;
       } catch (Exception e) {
           _log.error(e.getMessage());
       }
       return assessmentCoefficient;
   }

    /**
     * 获得累计应收
     * 在表中第一个参数是年累计电量，觉得有问题，改成月累计电量。
     * @param monthlyCumulativeElectricity 月累计电量
     * @param monthlyCumulativeElectricityPrice  年累计电量政府补助价格
     * @param monthlyElectricityConsumption   月消纳电量
     * @param monthlyElectricityConsumptionPrice  月消纳电量价格
     * @param monthlyInternetPower  月入网电量
     * @param monthlyInternetPowerPrice
     * @return
     */
   public static double getCumulativeReceive(Double monthlyCumulativeElectricity,Double monthlyCumulativeElectricityPrice,
                                             Double monthlyElectricityConsumption,Double monthlyElectricityConsumptionPrice,
                                             Double monthlyInternetPower,Double monthlyInternetPowerPrice){
       double cumulativeReceive=0.0;
       try {
           cumulativeReceive=monthlyCumulativeElectricity*monthlyCumulativeElectricityPrice+monthlyElectricityConsumption*monthlyElectricityConsumptionPrice+monthlyInternetPower*monthlyInternetPowerPrice;
       } catch (Exception e) {
           _log.error(e.getMessage());
       }
       return cumulativeReceive;
   }

    /**
     * 获取KW日均电量
     * @param days 当月天数
     * @param capacity 容量
     * @param monthlyInternetElectricity  月上网电量
     * @return
     */
    public static double getDayKwelectricity(int days,Double capacity,Double monthlyInternetElectricity){
       Double dayKwElectricity=0.0;
       if(monthlyInternetElectricity == 0 || capacity == 0){
           dayKwElectricity=0.0;
       }else {
           try {
               dayKwElectricity = monthlyInternetElectricity / capacity / days;
           } catch (Exception e) {
               _log.error(e.getMessage());
           }
           if (dayKwElectricity.isNaN() || dayKwElectricity.isInfinite()) {
               dayKwElectricity = 0.0;
           }
       }
        return dayKwElectricity;

    }

    /**
     * 获取当年电量
     * @param assessmentElectric 当年考核电量
     * @param sumOfProportion  计划生产比例的总和
     * @return
     */
    public static double getCurrentYearElectric(Double assessmentElectric,Double sumOfProportion){
     double currentYearElectric=0.0;
        try {
            currentYearElectric=assessmentElectric*sumOfProportion;
        } catch (Exception e) {
            _log.error(e.getMessage());
        }
        return currentYearElectric;
    }

























}
