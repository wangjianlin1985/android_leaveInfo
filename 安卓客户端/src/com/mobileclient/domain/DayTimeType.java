package com.mobileclient.domain;

import java.io.Serializable;

public class DayTimeType implements Serializable {
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