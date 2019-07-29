package com.mobileserver.domain;

public class DayTimeType {
    /*记录编号*/
    private int dayTimeTypeId;
    public int getDayTimeTypeId() {
        return dayTimeTypeId;
    }
    public void setDayTimeTypeId(int dayTimeTypeId) {
        this.dayTimeTypeId = dayTimeTypeId;
    }

    /*时间段名称*/
    private String dayTimeTypeName;
    public String getDayTimeTypeName() {
        return dayTimeTypeName;
    }
    public void setDayTimeTypeName(String dayTimeTypeName) {
        this.dayTimeTypeName = dayTimeTypeName;
    }

}