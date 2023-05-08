package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.HistoryTask;
import com.mobileserver.util.DB;

public class HistoryTaskDAO {

	public List<HistoryTask> QueryHistoryTask(int leaveObj,int noteObj,String userObj,String taskTime) {
		List<HistoryTask> historyTaskList = new ArrayList<HistoryTask>();
		DB db = new DB();
		String sql = "select * from HistoryTask where 1=1";
		if (leaveObj != 0)
			sql += " and leaveObj=" + leaveObj;
		if (noteObj != 0)
			sql += " and noteObj=" + noteObj;
		if (!userObj.equals(""))
			sql += " and userObj = '" + userObj + "'";
		if (!taskTime.equals(""))
			sql += " and taskTime like '%" + taskTime + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				HistoryTask historyTask = new HistoryTask();
				historyTask.setHistoryTaskId(rs.getInt("historyTaskId"));
				historyTask.setLeaveObj(rs.getInt("leaveObj"));
				historyTask.setNoteObj(rs.getInt("noteObj"));
				historyTask.setCheckSuggest(rs.getString("checkSuggest"));
				historyTask.setUserObj(rs.getString("userObj"));
				historyTask.setTaskTime(rs.getString("taskTime"));
				historyTask.setCheckState(rs.getInt("checkState"));
				historyTaskList.add(historyTask);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return historyTaskList;
	}
	/* ������ʷ������󣬽�����ʷ��������ҵ�� */
	public String AddHistoryTask(HistoryTask historyTask) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�������ʷ���� */
			String sqlString = "insert into HistoryTask(leaveObj,noteObj,checkSuggest,userObj,taskTime,checkState) values (";
			sqlString += historyTask.getLeaveObj() + ",";
			sqlString += historyTask.getNoteObj() + ",";
			sqlString += "'" + historyTask.getCheckSuggest() + "',";
			sqlString += "'" + historyTask.getUserObj() + "',";
			sqlString += "'" + historyTask.getTaskTime() + "',";
			sqlString += historyTask.getCheckState();
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "��ʷ������ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ʷ�������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ����ʷ���� */
	public String DeleteHistoryTask(int historyTaskId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from HistoryTask where historyTaskId=" + historyTaskId;
			db.executeUpdate(sqlString);
			result = "��ʷ����ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ʷ����ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ����������ʷ��¼Id��ȡ����ʷ���� */
	public HistoryTask GetHistoryTask(int historyTaskId) {
		HistoryTask historyTask = null;
		DB db = new DB();
		String sql = "select * from HistoryTask where historyTaskId=" + historyTaskId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				historyTask = new HistoryTask();
				historyTask.setHistoryTaskId(rs.getInt("historyTaskId"));
				historyTask.setLeaveObj(rs.getInt("leaveObj"));
				historyTask.setNoteObj(rs.getInt("noteObj"));
				historyTask.setCheckSuggest(rs.getString("checkSuggest"));
				historyTask.setUserObj(rs.getString("userObj"));
				historyTask.setTaskTime(rs.getString("taskTime"));
				historyTask.setCheckState(rs.getInt("checkState"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return historyTask;
	}
	/* ������ʷ���� */
	public String UpdateHistoryTask(HistoryTask historyTask) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update HistoryTask set ";
			sql += "leaveObj=" + historyTask.getLeaveObj() + ",";
			sql += "noteObj=" + historyTask.getNoteObj() + ",";
			sql += "checkSuggest='" + historyTask.getCheckSuggest() + "',";
			sql += "userObj='" + historyTask.getUserObj() + "',";
			sql += "taskTime='" + historyTask.getTaskTime() + "',";
			sql += "checkState=" + historyTask.getCheckState();
			sql += " where historyTaskId=" + historyTask.getHistoryTaskId();
			db.executeUpdate(sql);
			result = "��ʷ������³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ʷ�������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
