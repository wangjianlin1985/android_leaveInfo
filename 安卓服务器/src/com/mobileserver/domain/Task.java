package com.mobileserver.domain;

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
    private int leaveInfoObj;
    public int getLeaveInfoObj() {
        return leaveInfoObj;
    }
    public void setLeaveInfoObj(int leaveInfoObj) {
        this.leaveInfoObj = leaveInfoObj;
    }

    /*当前节点*/
    private int noteObj;
    public int getNoteObj() {
        return noteObj;
    }
    public void setNoteObj(int noteObj) {
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
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
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