package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Task;
import com.mobileserver.util.DB;

public class TaskDAO {

	public List<Task> QueryTask(int leaveInfoObj,int noteObj,String userObj,String taskTime) {
		List<Task> taskList = new ArrayList<Task>();
		DB db = new DB();
		String sql = "select * from Task where 1=1";
		if (leaveInfoObj != 0)
			sql += " and leaveInfoObj=" + leaveInfoObj;
		if (noteObj != 0)
			sql += " and noteObj=" + noteObj;
		if (!userObj.equals(""))
			sql += " and userObj = '" + userObj + "'";
		if (!taskTime.equals(""))
			sql += " and taskTime like '%" + taskTime + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Task task = new Task();
				task.setTaskId(rs.getInt("taskId"));
				task.setLeaveInfoObj(rs.getInt("leaveInfoObj"));
				task.setNoteObj(rs.getInt("noteObj"));
				task.setState(rs.getInt("state"));
				task.setUserObj(rs.getString("userObj"));
				task.setTaskTime(rs.getString("taskTime"));
				taskList.add(task);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return taskList;
	}
	/* ����������Ϣ���󣬽���������Ϣ�����ҵ�� */
	public String AddTask(Task task) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����������Ϣ */
			String sqlString = "insert into Task(leaveInfoObj,noteObj,state,userObj,taskTime) values (";
			sqlString += task.getLeaveInfoObj() + ",";
			sqlString += task.getNoteObj() + ",";
			sqlString += task.getState() + ",";
			sqlString += "'" + task.getUserObj() + "',";
			sqlString += "'" + task.getTaskTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "������Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��������Ϣ */
	public String DeleteTask(int taskId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Task where taskId=" + taskId;
			db.executeUpdate(sqlString);
			result = "������Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ��������ID��ȡ��������Ϣ */
	public Task GetTask(int taskId) {
		Task task = null;
		DB db = new DB();
		String sql = "select * from Task where taskId=" + taskId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				task = new Task();
				task.setTaskId(rs.getInt("taskId"));
				task.setLeaveInfoObj(rs.getInt("leaveInfoObj"));
				task.setNoteObj(rs.getInt("noteObj"));
				task.setState(rs.getInt("state"));
				task.setUserObj(rs.getString("userObj"));
				task.setTaskTime(rs.getString("taskTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return task;
	}
	/* ����������Ϣ */
	public String UpdateTask(Task task) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Task set ";
			sql += "leaveInfoObj=" + task.getLeaveInfoObj() + ",";
			sql += "noteObj=" + task.getNoteObj() + ",";
			sql += "state=" + task.getState() + ",";
			sql += "userObj='" + task.getUserObj() + "',";
			sql += "taskTime='" + task.getTaskTime() + "'";
			sql += " where taskId=" + task.getTaskId();
			db.executeUpdate(sql);
			result = "������Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
