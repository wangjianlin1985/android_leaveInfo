package com.mobileclient.domain;

import java.io.Serializable;

public class LeaveType implements Serializable {
    /*请假类型ID*/
    private int leaveTypeId;
    public int getLeaveTypeId() {
        return leaveTypeId;
    }
    public void setLeaveTypeId(int leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    /*请假类型名称*/
    private String leaveTypeName;
    public String getLeaveTypeName() {
        return leaveTypeName;
    }
    public void setLeaveTypeName(String leaveTypeName) {
        this.leaveTypeName = leaveTypeName;
    }

}