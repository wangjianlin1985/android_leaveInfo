package com.mobileclient.domain;

import java.io.Serializable;

public class Note implements Serializable {
    /*节点ID*/
    private int noteId;
    public int getNoteId() {
        return noteId;
    }
    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    /*节点名称*/
    private String noteName;
    public String getNoteName() {
        return noteName;
    }
    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    /*处理部门*/
    private int departmentObj;
    public int getDepartmentObj() {
        return departmentObj;
    }
    public void setDepartmentObj(int departmentObj) {
        this.departmentObj = departmentObj;
    }

    /*需要职级*/
    private int positionObj;
    public int getPositionObj() {
        return positionObj;
    }
    public void setPositionObj(int positionObj) {
        this.positionObj = positionObj;
    }

    /*节点编号*/
    private int noteIndex;
    public int getNoteIndex() {
        return noteIndex;
    }
    public void setNoteIndex(int noteIndex) {
        this.noteIndex = noteIndex;
    }

}