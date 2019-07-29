package com.chengxusheji.domain;

import java.sql.Timestamp;
public class LeaveInfo {
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
    private Department departmentObj;
    public Department getDepartmentObj() {
        return departmentObj;
    }
    public void setDepartmentObj(Department departmentObj) {
        this.departmentObj = departmentObj;
    }

    /*请假人*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*职级*/
    private Position positionObj;
    public Position getPositionObj() {
        return positionObj;
    }
    public void setPositionObj(Position positionObj) {
        this.positionObj = positionObj;
    }

    /*请假类别*/
    private LeaveType leaveTypeObj;
    public LeaveType getLeaveTypeObj() {
        return leaveTypeObj;
    }
    public void setLeaveTypeObj(LeaveType leaveTypeObj) {
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
    private Timestamp startDate;
    public Timestamp getStartDate() {
        return startDate;
    }
    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    /*开始时间段*/
    private DayTimeType startDayTimeType;
    public DayTimeType getStartDayTimeType() {
        return startDayTimeType;
    }
    public void setStartDayTimeType(DayTimeType startDayTimeType) {
        this.startDayTimeType = startDayTimeType;
    }

    /*结束时间*/
    private Timestamp endDate;
    public Timestamp getEndDate() {
        return endDate;
    }
    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    /*结束时间段*/
    private DayTimeType endDayTimeType;
    public DayTimeType getEndDayTimeType() {
        return endDayTimeType;
    }
    public void setEndDayTimeType(DayTimeType endDayTimeType) {
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
    private UserInfo writeUserObj;
    public UserInfo getWriteUserObj() {
        return writeUserObj;
    }
    public void setWriteUserObj(UserInfo writeUserObj) {
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