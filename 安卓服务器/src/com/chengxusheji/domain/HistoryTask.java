package com.chengxusheji.domain;

import java.sql.Timestamp;
public class HistoryTask {
    /*������ʷ��¼Id*/
    private int historyTaskId;
    public int getHistoryTaskId() {
        return historyTaskId;
    }
    public void setHistoryTaskId(int historyTaskId) {
        this.historyTaskId = historyTaskId;
    }

    /*��ټ�¼ID*/
    private LeaveInfo leaveObj;
    public LeaveInfo getLeaveObj() {
        return leaveObj;
    }
    public void setLeaveObj(LeaveInfo leaveObj) {
        this.leaveObj = leaveObj;
    }

    /*�ڵ�*/
    private Note noteObj;
    public Note getNoteObj() {
        return noteObj;
    }
    public void setNoteObj(Note noteObj) {
        this.noteObj = noteObj;
    }

    /*�������*/
    private String checkSuggest;
    public String getCheckSuggest() {
        return checkSuggest;
    }
    public void setCheckSuggest(String checkSuggest) {
        this.checkSuggest = checkSuggest;
    }

    /*������*/
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

    /*����״̬*/
    private int checkState;
    public int getCheckState() {
        return checkState;
    }
    public void setCheckState(int checkState) {
        this.checkState = checkState;
    }

}