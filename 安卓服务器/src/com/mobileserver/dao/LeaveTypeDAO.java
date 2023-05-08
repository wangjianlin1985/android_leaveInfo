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
	/* 传入请假类型对象，进行请假类型的添加业务 */
	public String AddLeaveType(LeaveType leaveType) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新请假类型 */
			String sqlString = "insert into LeaveType(leaveTypeName) values (";
			sqlString += "'" + leaveType.getLeaveTypeName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "请假类型添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "请假类型添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除请假类型 */
	public String DeleteLeaveType(int leaveTypeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from LeaveType where leaveTypeId=" + leaveTypeId;
			db.executeUpdate(sqlString);
			result = "请假类型删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "请假类型删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据请假类型ID获取到请假类型 */
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
	/* 更新请假类型 */
	public String UpdateLeaveType(LeaveType leaveType) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update LeaveType set ";
			sql += "leaveTypeName='" + leaveType.getLeaveTypeName() + "'";
			sql += " where leaveTypeId=" + leaveType.getLeaveTypeId();
			db.executeUpdate(sql);
			result = "请假类型更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "请假类型更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
