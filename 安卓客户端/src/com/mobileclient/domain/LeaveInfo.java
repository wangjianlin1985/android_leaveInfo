package com.mobileclient.domain;

import java.io.Serializable;

public class LeaveInfo implements Serializable {
    /*请假记录ID*/
    private int leaveId;
    public int getLeaveId() {
        return leaveId;
    }
    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }

    /*填表时间*/
    private String writeTime;
    public String getWriteTime() {
        return writeTime;
    }
    public void setWriteTime(String writeTime) {
        this.writeTime = writeTime;
    }

    /*部门*/
    private int departmentObj;
    public int getDepartmentObj() {
        return departmentObj;
    }
    public void setDepartmentObj(int departmentObj) {
        this.departmentObj = departmentObj;
    }

    /*请假人*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
        this.userObj = userObj;
    }

    /*职级*/
    private int positionObj;
    public int getPositionObj() {
        return positionObj;
    }
    public void setPositionObj(int positionObj) {
        this.positionObj = positionObj;
    }

    /*请假类别*/
    private int leaveTypeObj;
    public int getLeaveTypeObj() {
        return leaveTypeObj;
    }
    public void setLeaveTypeObj(int leaveTypeObj) {
        this.leaveTypeObj = leaveTypeObj;
    }

    /*是否长假*/
    private String sfcj;
    public String getSfcj() {
        return sfcj;
    }
    public void setSfcj(String sfcj) {
        this.sfcj = sfcj;
    }

    /*开始时间*/
    private java.sql.Timestamp startDate;
    public java.sql.Timestamp getStartDate() {
        return startDate;
    }
    public void setStartDate(java.sql.Timestamp startDate) {
        this.startDate = startDate;
    }

    /*开始时间段*/
    private int startDayTimeType;
    public int getStartDayTimeType() {
        return startDayTimeType;
    }
    public void setStartDayTimeType(int startDayTimeType) {
        this.startDayTimeType = startDayTimeType;
    }

    /*结束时间*/
    private java.sql.Timestamp endDate;
    public java.sql.Timestamp getEndDate() {
        return endDate;
    }
    public void setEndDate(java.sql.Timestamp endDate) {
        this.endDate = endDate;
    }

    /*结束时间段*/
    private int endDayTimeType;
    public int getEndDayTimeType() {
        return endDayTimeType;
    }
    public void setEndDayTimeType(int endDayTimeType) {
        this.endDayTimeType = endDayTimeType;
    }

    /*请假天数*/
    private float leaveDays;
    public float getLeaveDays() {
        return leaveDays;
    }
    public void setLeaveDays(float leaveDays) {
        this.leaveDays = leaveDays;
    }

    /*填写人*/
    private String writeUserObj;
    public String getWriteUserObj() {
        return writeUserObj;
    }
    public void setWriteUserObj(String writeUserObj) {
        this.writeUserObj = writeUserObj;
    }

    /*前往地点*/
    private String place;
    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }

    /*请假事由*/
    private String reason;
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }

    /*备注*/
    private String memo;
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /*状态*/
    private int state;
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }

}