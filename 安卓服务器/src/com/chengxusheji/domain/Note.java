package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Note {
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
    private Department departmentObj;
    public Department getDepartmentObj() {
        return departmentObj;
    }
    public void setDepartmentObj(Department departmentObj) {
        this.departmentObj = departmentObj;
    }

    /*需要职级*/
    private Position positionObj;
    public Position getPositionObj() {
        return positionObj;
    }
    public void setPositionObj(Position positionObj) {
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