package com.mobileserver.domain;

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
    private int departmentObj;
    public int getDepartmentObj() {
        return departmentObj;
    }
    public void setDepartmentObj(int departmentObj) {
        this.departmentObj = departmentObj;
    }

    /*�����*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
        this.userObj = userObj;
    }

    /*ְ��*/
    private int positionObj;
    public int getPositionObj() {
        return positionObj;
    }
    public void setPositionObj(int positionObj) {
        this.positionObj = positionObj;
    }

    /*������*/
    private int leaveTypeObj;
    public int getLeaveTypeObj() {
        return leaveTypeObj;
    }
    public void setLeaveTypeObj(int leaveTypeObj) {
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
    private java.sql.Timestamp startDate;
    public java.sql.Timestamp getStartDate() {
        return startDate;
    }
    public void setStartDate(java.sql.Timestamp startDate) {
        this.startDate = startDate;
    }

    /*��ʼʱ���*/
    private int startDayTimeType;
    public int getStartDayTimeType() {
        return startDayTimeType;
    }
    public void setStartDayTimeType(int startDayTimeType) {
        this.startDayTimeType = startDayTimeType;
    }

    /*����ʱ��*/
    private java.sql.Timestamp endDate;
    public java.sql.Timestamp getEndDate() {
        return endDate;
    }
    public void setEndDate(java.sql.Timestamp endDate) {
        this.endDate = endDate;
    }

    /*����ʱ���*/
    private int endDayTimeType;
    public int getEndDayTimeType() {
        return endDayTimeType;
    }
    public void setEndDayTimeType(int endDayTimeType) {
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
    private String writeUserObj;
    public String getWriteUserObj() {
        return writeUserObj;
    }
    public void setWriteUserObj(String writeUserObj) {
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