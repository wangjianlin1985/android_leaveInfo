package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.LeaveType;
import com.mobileserver.util.DB;

public class LeaveTypeDAO {

	public List<LeaveType> QueryLeaveType() {
		List<LeaveType> leaveTypeList = new ArrayList<LeaveType>();
		DB db = new DB();
		String sql = "select * from LeaveType where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				LeaveType leaveType = new LeaveType();
				leaveType.setLeaveTypeId(rs.getInt("leaveTypeId"));
				leaveType.setLeaveTypeName(rs.getString("leaveTypeName"));
				leaveTypeList.add(leaveType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return leaveTypeList;
	}
	/* ����������Ͷ��󣬽���������͵����ҵ�� */
	public String AddLeaveType(LeaveType leaveType) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в������������ */
			String sqlString = "insert into LeaveType(leaveTypeName) values (";
			sqlString += "'" + leaveType.getLeaveTypeName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "���������ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��������� */
	public String DeleteLeaveType(int leaveTypeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from LeaveType where leaveTypeId=" + leaveTypeId;
			db.executeUpdate(sqlString);
			result = "�������ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�������ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* �����������ID��ȡ��������� */
	public LeaveType GetLeaveType(int leaveTypeId) {
		LeaveType leaveType = null;
		DB db = new DB();
		String sql = "select * from LeaveType where leaveTypeId=" + leaveTypeId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				leaveType = new LeaveType();
				leaveType.setLeaveTypeId(rs.getInt("leaveTypeId"));
				leaveType.setLeaveTypeName(rs.getString("leaveTypeName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return leaveType;
	}
	/* ����������� */
	public String UpdateLeaveType(LeaveType leaveType) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update LeaveType set ";
			sql += "leaveTypeName='" + leaveType.getLeaveTypeName() + "'";
			sql += " where leaveTypeId=" + leaveType.getLeaveTypeId();
			db.executeUpdate(sql);
			result = "������͸��³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������͸���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
