package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Note {
    /*�ڵ�ID*/
    private int noteId;
    public int getNoteId() {
        return noteId;
    }
    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    /*�ڵ�����*/
    private String noteName;
    public String getNoteName() {
        return noteName;
    }
    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    /*������*/
    private Department departmentObj;
    public Department getDepartmentObj() {
        return departmentObj;
    }
    public void setDepartmentObj(Department departmentObj) {
        this.departmentObj = departmentObj;
    }

    /*��Ҫְ��*/
    private Position positionObj;
    public Position getPositionObj() {
        return positionObj;
    }
    public void setPositionObj(Position positionObj) {
        this.positionObj = positionObj;
    }

    /*�ڵ���*/
    private int noteIndex;
    public int getNoteIndex() {
        return noteIndex;
    }
    public void setNoteIndex(int noteIndex) {
        this.noteIndex = noteIndex;
    }

}