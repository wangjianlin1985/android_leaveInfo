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
	/* 传入任务信息对象，进行任务信息的添加业务 */
	public String AddTask(Task task) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新任务信息 */
			String sqlString = "insert into Task(leaveInfoObj,noteObj,state,userObj,taskTime) values (";
			sqlString += task.getLeaveInfoObj() + ",";
			sqlString += task.getNoteObj() + ",";
			sqlString += task.getState() + ",";
			sqlString += "'" + task.getUserObj() + "',";
			sqlString += "'" + task.getTaskTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "任务信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "任务信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除任务信息 */
	public String DeleteTask(int taskId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Task where taskId=" + taskId;
			db.executeUpdate(sqlString);
			result = "任务信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "任务信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据任务ID获取到任务信息 */
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
	/* 更新任务信息 */
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
			result = "任务信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "任务信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
