package com.mobileclient.domain;

import java.io.Serializable;

public class HistoryTask implements Serializable {
    /*任务历史记录Id*/
    private int historyTaskId;
    public int getHistoryTaskId() {
        return historyTaskId;
    }
    public void setHistoryTaskId(int historyTaskId) {
        this.historyTaskId = historyTaskId;
    }

    /*请假记录ID*/
    private int leaveObj;
    public int getLeaveObj() {
        return leaveObj;
    }
    public void setLeaveObj(int leaveObj) {
        this.leaveObj = leaveObj;
    }

    /*节点*/
    private int noteObj;
    public int getNoteObj() {
        return noteObj;
    }
    public void setNoteObj(int noteObj) {
        this.noteObj = noteObj;
    }

    /*审批意见*/
    private String checkSuggest;
    public String getCheckSuggest() {
        return checkSuggest;
    }
    public void setCheckSuggest(String checkSuggest) {
        this.checkSuggest = checkSuggest;
    }

    /*处理人*/
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

    /*审批状态*/
    private int checkState;
    public int getCheckState() {
        return checkState;
    }
    public void setCheckState(int checkState) {
        this.checkState = checkState;
    }

}