package com.mobileserver.domain;

public class Task {
    /*����ID*/
    private int taskId;
    public int getTaskId() {
        return taskId;
    }
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    /*���ID*/
    private int leaveInfoObj;
    public int getLeaveInfoObj() {
        return leaveInfoObj;
    }
    public void setLeaveInfoObj(int leaveInfoObj) {
        this.leaveInfoObj = leaveInfoObj;
    }

    /*��ǰ�ڵ�*/
    private int noteObj;
    public int getNoteObj() {
        return noteObj;
    }
    public void setNoteObj(int noteObj) {
        this.noteObj = noteObj;
    }

    /*��ǰ״̬*/
    private int state;
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }

    /*��ǰ������*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
        this.userObj = userObj;
    }

    /*����ʱ��*/
    private String taskTime;
    public String getTaskTime() {
        return taskTime;
    }
    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

}