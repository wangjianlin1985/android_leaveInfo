package com.chengxusheji.domain;

import java.sql.Timestamp;
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
    private LeaveInfo leaveInfoObj;
    public LeaveInfo getLeaveInfoObj() {
        return leaveInfoObj;
    }
    public void setLeaveInfoObj(LeaveInfo leaveInfoObj) {
        this.leaveInfoObj = leaveInfoObj;
    }

    /*��ǰ�ڵ�*/
    private Note noteObj;
    public Note getNoteObj() {
        return noteObj;
    }
    public void setNoteObj(Note noteObj) {
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
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
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