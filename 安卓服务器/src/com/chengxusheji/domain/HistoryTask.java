package com.chengxusheji.domain;

import java.sql.Timestamp;
public class HistoryTask {
    /*任务历史记录Id*/
    private int historyTaskId;
    public int getHistoryTaskId() {
        return historyTaskId;
    }
    public void setHistoryTaskId(int historyTaskId) {
        this.historyTaskId = historyTaskId;
    }

    /*请假记录ID*/
    private LeaveInfo leaveObj;
    public LeaveInfo getLeaveObj() {
        return leaveObj;
    }
    public void setLeaveObj(LeaveInfo leaveObj) {
        this.leaveObj = leaveObj;
    }

    /*节点*/
    private Note noteObj;
    public Note getNoteObj() {
        return noteObj;
    }
    public void setNoteObj(Note noteObj) {
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

    /*审批状态*/
    private int checkState;
    public int getCheckState() {
        return checkState;
    }
    public void setCheckState(int checkState) {
        this.checkState = checkState;
    }

}