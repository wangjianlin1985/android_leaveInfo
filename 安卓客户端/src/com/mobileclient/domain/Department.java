package com.mobileclient.domain;

import java.io.Serializable;

public class Department implements Serializable {
    /*部门ID*/
    private int departmentId;
    public int getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    /*部门名称*/
    private String departmentName;
    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

}