package com.mobileserver.domain;

public class UserInfo {
    /*�û���*/
    private String user_name;
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /*����*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*����*/
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*�Ա�*/
    private String sex;
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    /*��������*/
    private java.sql.Timestamp birthday;
    public java.sql.Timestamp getBirthday() {
        return birthday;
    }
    public void setBirthday(java.sql.Timestamp birthday) {
        this.birthday = birthday;
    }

    /*���ڲ���*/
    private int departmentObj;
    public int getDepartmentObj() {
        return departmentObj;
    }
    public void setDepartmentObj(int departmentObj) {
        this.departmentObj = departmentObj;
    }

    /*�ֻ���*/
    private String mobileNumber;
    public String getMobileNumber() {
        return mobileNumber;
    }
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /*ְ��*/
    private int positionName;
    public int getPositionName() {
        return positionName;
    }
    public void setPositionName(int positionName) {
        this.positionName = positionName;
    }

}