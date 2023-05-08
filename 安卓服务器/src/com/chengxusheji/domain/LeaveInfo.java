package com.chengxusheji.domain;

import java.sql.Timestamp;
public class LeaveInfo {
    /*��ټ�¼ID*/
    private int leaveId;
    public int getLeaveId() {
        return leaveId;
    }
    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }

    /*���ʱ��*/
    private String writeTime;
    public String getWriteTime() {
        return writeTime;
    }
    public void setWriteTime(String writeTime) {
        this.writeTime = writeTime;
    }

    /*����*/
    private Department departmentObj;
    public Department getDepartmentObj() {
        return departmentObj;
    }
    public void setDepartmentObj(Department departmentObj) {
        this.departmentObj = departmentObj;
    }

    /*�����*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*ְ��*/
    private Position positionObj;
    public Position getPositionObj() {
        return positionObj;
    }
    public void setPositionObj(Position positionObj) {
        this.positionObj = positionObj;
    }

    /*������*/
    private LeaveType leaveTypeObj;
    public LeaveType getLeaveTypeObj() {
        return leaveTypeObj;
    }
    public void setLeaveTypeObj(LeaveType leaveTypeObj) {
        this.leaveTypeObj = leaveTypeObj;
    }

    /*�Ƿ񳤼�*/
    private String sfcj;
    public String getSfcj() {
        return sfcj;
    }
    public void setSfcj(String sfcj) {
        this.sfcj = sfcj;
    }

    /*��ʼʱ��*/
    private Timestamp startDate;
    public Timestamp getStartDate() {
        return startDate;
    }
    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    /*��ʼʱ���*/
    private DayTimeType startDayTimeType;
    public DayTimeType getStartDayTimeType() {
        return startDayTimeType;
    }
    public void setStartDayTimeType(DayTimeType startDayTimeType) {
        this.startDayTimeType = startDayTimeType;
    }

    /*����ʱ��*/
    private Timestamp endDate;
    public Timestamp getEndDate() {
        return endDate;
    }
    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    /*����ʱ���*/
    private DayTimeType endDayTimeType;
    public DayTimeType getEndDayTimeType() {
        return endDayTimeType;
    }
    public void setEndDayTimeType(DayTimeType endDayTimeType) {
        this.endDayTimeType = endDayTimeType;
    }

    /*�������*/
    private float leaveDays;
    public float getLeaveDays() {
        return leaveDays;
    }
    public void setLeaveDays(float leaveDays) {
        this.leaveDays = leaveDays;
    }

    /*��д��*/
    private UserInfo writeUserObj;
    public UserInfo getWriteUserObj() {
        return writeUserObj;
    }
    public void setWriteUserObj(UserInfo writeUserObj) {
        this.writeUserObj = writeUserObj;
    }

    /*ǰ���ص�*/
    private String place;
    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }

    /*�������*/
    private String reason;
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }

    /*��ע*/
    private String memo;
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /*״̬*/
    private int state;
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }

}