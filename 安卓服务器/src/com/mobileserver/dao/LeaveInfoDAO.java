package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.LeaveInfo;
import com.mobileserver.util.DB;

public class LeaveInfoDAO {

	public List<LeaveInfo> QueryLeaveInfo(String writeTime,int departmentObj,String userObj,int positionObj,int leaveTypeObj,Timestamp startDate,int startDayTimeType,Timestamp endDate,int endDayTimeType,String place,String reason) {
		List<LeaveInfo> leaveInfoList = new ArrayList<LeaveInfo>();
		DB db = new DB();
		String sql = "select * from LeaveInfo where 1=1";
		if (!writeTime.equals(""))
			sql += " and writeTime like '%" + writeTime + "%'";
		if (departmentObj != 0)
			sql += " and departmentObj=" + departmentObj;
		if (!userObj.equals(""))
			sql += " and userObj = '" + userObj + "'";
		if (positionObj != 0)
			sql += " and positionObj=" + positionObj;
		if (leaveTypeObj != 0)
			sql += " and leaveTypeObj=" + leaveTypeObj;
		if(startDate!=null)
			sql += " and startDate='" + startDate + "'";
		if (startDayTimeType != 0)
			sql += " and startDayTimeType=" + startDayTimeType;
		if(endDate!=null)
			sql += " and endDate='" + endDate + "'";
		if (endDayTimeType != 0)
			sql += " and endDayTimeType=" + endDayTimeType;
		if (!place.equals(""))
			sql += " and place like '%" + place + "%'";
		if (!reason.equals(""))
			sql += " and reason like '%" + reason + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				LeaveInfo leaveInfo = new LeaveInfo();
				leaveInfo.setLeaveId(rs.getInt("leaveId"));
				leaveInfo.setWriteTime(rs.getString("writeTime"));
				leaveInfo.setDepartmentObj(rs.getInt("departmentObj"));
				leaveInfo.setUserObj(rs.getString("userObj"));
				leaveInfo.setPositionObj(rs.getInt("positionObj"));
				leaveInfo.setLeaveTypeObj(rs.getInt("leaveTypeObj"));
				leaveInfo.setSfcj(rs.getString("sfcj"));
				leaveInfo.setStartDate(rs.getTimestamp("startDate"));
				leaveInfo.setStartDayTimeType(rs.getInt("startDayTimeType"));
				leaveInfo.setEndDate(rs.getTimestamp("endDate"));
				leaveInfo.setEndDayTimeType(rs.getInt("endDayTimeType"));
				leaveInfo.setLeaveDays(rs.getFloat("leaveDays"));
				leaveInfo.setWriteUserObj(rs.getString("writeUserObj"));
				leaveInfo.setPlace(rs.getString("place"));
				leaveInfo.setReason(rs.getString("reason"));
				leaveInfo.setMemo(rs.getString("memo"));
				leaveInfo.setState(rs.getInt("state"));
				leaveInfoList.add(leaveInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return leaveInfoList;
	}
	/* ���������Ϣ���󣬽��������Ϣ�����ҵ�� */
	public String AddLeaveInfo(LeaveInfo leaveInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����������Ϣ */
			String sqlString = "insert into LeaveInfo(writeTime,departmentObj,userObj,positionObj,leaveTypeObj,sfcj,startDate,startDayTimeType,endDate,endDayTimeType,leaveDays,writeUserObj,place,reason,memo,state) values (";
			sqlString += "'" + leaveInfo.getWriteTime() + "',";
			sqlString += leaveInfo.getDepartmentObj() + ",";
			sqlString += "'" + leaveInfo.getUserObj() + "',";
			sqlString += leaveInfo.getPositionObj() + ",";
			sqlString += leaveInfo.getLeaveTypeObj() + ",";
			sqlString += "'" + leaveInfo.getSfcj() + "',";
			sqlString += "'" + leaveInfo.getStartDate() + "',";
			sqlString += leaveInfo.getStartDayTimeType() + ",";
			sqlString += "'" + leaveInfo.getEndDate() + "',";
			sqlString += leaveInfo.getEndDayTimeType() + ",";
			sqlString += leaveInfo.getLeaveDays() + ",";
			sqlString += "'" + leaveInfo.getWriteUserObj() + "',";
			sqlString += "'" + leaveInfo.getPlace() + "',";
			sqlString += "'" + leaveInfo.getReason() + "',";
			sqlString += "'" + leaveInfo.getMemo() + "',";
			sqlString += leaveInfo.getState();
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "�����Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�����Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ�������Ϣ */
	public String DeleteLeaveInfo(int leaveId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from LeaveInfo where leaveId=" + leaveId;
			db.executeUpdate(sqlString);
			result = "�����Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�����Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ������ټ�¼ID��ȡ�������Ϣ */
	public LeaveInfo GetLeaveInfo(int leaveId) {
		LeaveInfo leaveInfo = null;
		DB db = new DB();
		String sql = "select * from LeaveInfo where leaveId=" + leaveId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				leaveInfo = new LeaveInfo();
				leaveInfo.setLeaveId(rs.getInt("leaveId"));
				leaveInfo.setWriteTime(rs.getString("writeTime"));
				leaveInfo.setDepartmentObj(rs.getInt("departmentObj"));
				leaveInfo.setUserObj(rs.getString("userObj"));
				leaveInfo.setPositionObj(rs.getInt("positionObj"));
				leaveInfo.setLeaveTypeObj(rs.getInt("leaveTypeObj"));
				leaveInfo.setSfcj(rs.getString("sfcj"));
				leaveInfo.setStartDate(rs.getTimestamp("startDate"));
				leaveInfo.setStartDayTimeType(rs.getInt("startDayTimeType"));
				leaveInfo.setEndDate(rs.getTimestamp("endDate"));
				leaveInfo.setEndDayTimeType(rs.getInt("endDayTimeType"));
				leaveInfo.setLeaveDays(rs.getFloat("leaveDays"));
				leaveInfo.setWriteUserObj(rs.getString("writeUserObj"));
				leaveInfo.setPlace(rs.getString("place"));
				leaveInfo.setReason(rs.getString("reason"));
				leaveInfo.setMemo(rs.getString("memo"));
				leaveInfo.setState(rs.getInt("state"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return leaveInfo;
	}
	/* ���������Ϣ */
	public String UpdateLeaveInfo(LeaveInfo leaveInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update LeaveInfo set ";
			sql += "writeTime='" + leaveInfo.getWriteTime() + "',";
			sql += "departmentObj=" + leaveInfo.getDepartmentObj() + ",";
			sql += "userObj='" + leaveInfo.getUserObj() + "',";
			sql += "positionObj=" + leaveInfo.getPositionObj() + ",";
			sql += "leaveTypeObj=" + leaveInfo.getLeaveTypeObj() + ",";
			sql += "sfcj='" + leaveInfo.getSfcj() + "',";
			sql += "startDate='" + leaveInfo.getStartDate() + "',";
			sql += "startDayTimeType=" + leaveInfo.getStartDayTimeType() + ",";
			sql += "endDate='" + leaveInfo.getEndDate() + "',";
			sql += "endDayTimeType=" + leaveInfo.getEndDayTimeType() + ",";
			sql += "leaveDays=" + leaveInfo.getLeaveDays() + ",";
			sql += "writeUserObj='" + leaveInfo.getWriteUserObj() + "',";
			sql += "place='" + leaveInfo.getPlace() + "',";
			sql += "reason='" + leaveInfo.getReason() + "',";
			sql += "memo='" + leaveInfo.getMemo() + "',";
			sql += "state=" + leaveInfo.getState();
			sql += " where leaveId=" + leaveInfo.getLeaveId();
			db.executeUpdate(sql);
			result = "�����Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�����Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
