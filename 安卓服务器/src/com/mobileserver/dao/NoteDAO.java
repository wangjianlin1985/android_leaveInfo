package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Note;
import com.mobileserver.util.DB;

public class NoteDAO {

	public List<Note> QueryNote(String noteName,int departmentObj,int positionObj) {
		List<Note> noteList = new ArrayList<Note>();
		DB db = new DB();
		String sql = "select * from Note where 1=1";
		if (!noteName.equals(""))
			sql += " and noteName like '%" + noteName + "%'";
		if (departmentObj != 0)
			sql += " and departmentObj=" + departmentObj;
		if (positionObj != 0)
			sql += " and positionObj=" + positionObj;
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Note note = new Note();
				note.setNoteId(rs.getInt("noteId"));
				note.setNoteName(rs.getString("noteName"));
				note.setDepartmentObj(rs.getInt("departmentObj"));
				note.setPositionObj(rs.getInt("positionObj"));
				note.setNoteIndex(rs.getInt("noteIndex"));
				noteList.add(note);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return noteList;
	}
	/* ����ڵ���Ϣ���󣬽��нڵ���Ϣ�����ҵ�� */
	public String AddNote(Note note) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����½ڵ���Ϣ */
			String sqlString = "insert into Note(noteName,departmentObj,positionObj,noteIndex) values (";
			sqlString += "'" + note.getNoteName() + "',";
			sqlString += note.getDepartmentObj() + ",";
			sqlString += note.getPositionObj() + ",";
			sqlString += note.getNoteIndex();
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "�ڵ���Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�ڵ���Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ���ڵ���Ϣ */
	public String DeleteNote(int noteId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Note where noteId=" + noteId;
			db.executeUpdate(sqlString);
			result = "�ڵ���Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�ڵ���Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݽڵ�ID��ȡ���ڵ���Ϣ */
	public Note GetNote(int noteId) {
		Note note = null;
		DB db = new DB();
		String sql = "select * from Note where noteId=" + noteId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				note = new Note();
				note.setNoteId(rs.getInt("noteId"));
				note.setNoteName(rs.getString("noteName"));
				note.setDepartmentObj(rs.getInt("departmentObj"));
				note.setPositionObj(rs.getInt("positionObj"));
				note.setNoteIndex(rs.getInt("noteIndex"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return note;
	}
	/* ���½ڵ���Ϣ */
	public String UpdateNote(Note note) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Note set ";
			sql += "noteName='" + note.getNoteName() + "',";
			sql += "departmentObj=" + note.getDepartmentObj() + ",";
			sql += "positionObj=" + note.getPositionObj() + ",";
			sql += "noteIndex=" + note.getNoteIndex();
			sql += " where noteId=" + note.getNoteId();
			db.executeUpdate(sql);
			result = "�ڵ���Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�ڵ���Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
