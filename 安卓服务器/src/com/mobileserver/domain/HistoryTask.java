package com.mobileserver.domain;

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
    private int leaveObj;
    public int getLeaveObj() {
        return leaveObj;
    }
    public void setLeaveObj(int leaveObj) {
        this.leaveObj = leaveObj;
    }

    /*�ڵ�*/
    private int noteObj;
    public int getNoteObj() {
        return noteObj;
    }
    public void setNoteObj(int noteObj) {
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

    /*����״̬*/
    private int checkState;
    public int getCheckState() {
        return checkState;
    }
    public void setCheckState(int checkState) {
        this.checkState = checkState;
    }

}