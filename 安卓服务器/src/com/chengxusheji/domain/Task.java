package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Task {
    /*任务ID*/
    private int taskId;
    public int getTaskId() {
        return taskId;
    }
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    /*请假ID*/
    private LeaveInfo leaveInfoObj;
    public LeaveInfo getLeaveInfoObj() {
        return leaveInfoObj;
    }
    public void setLeaveInfoObj(LeaveInfo leaveInfoObj) {
        this.leaveInfoObj = leaveInfoObj;
    }

    /*当前节点*/
    private Note noteObj;
    public Note getNoteObj() {
        return noteObj;
    }
    public void setNoteObj(Note noteObj) {
        this.noteObj = noteObj;
    }

    /*当前状态*/
    private int state;
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }

    /*当前处理人*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*创建时间*/
    private String taskTime;
    public String getTaskTime() {
        return taskTime;
    }
    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

}