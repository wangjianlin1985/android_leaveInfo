package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Position {
    /*职级ID*/
    private int positionId;
    public int getPositionId() {
        return positionId;
    }
    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    /*职级名称*/
    private String positionName;
    public String getPositionName() {
        return positionName;
    }
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

}