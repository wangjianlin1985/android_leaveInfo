package com.mobileserver.domain;

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
    private int departmentObj;
    public int getDepartmentObj() {
        return departmentObj;
    }
    public void setDepartmentObj(int departmentObj) {
        this.departmentObj = departmentObj;
    }

    /*��Ҫְ��*/
    private int positionObj;
    public int getPositionObj() {
        return positionObj;
    }
    public void setPositionObj(int positionObj) {
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