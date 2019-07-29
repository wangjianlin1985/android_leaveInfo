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
	/* 传入节点信息对象，进行节点信息的添加业务 */
	public String AddNote(Note note) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新节点信息 */
			String sqlString = "insert into Note(noteName,departmentObj,positionObj,noteIndex) values (";
			sqlString += "'" + note.getNoteName() + "',";
			sqlString += note.getDepartmentObj() + ",";
			sqlString += note.getPositionObj() + ",";
			sqlString += note.getNoteIndex();
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "节点信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "节点信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除节点信息 */
	public String DeleteNote(int noteId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Note where noteId=" + noteId;
			db.executeUpdate(sqlString);
			result = "节点信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "节点信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据节点ID获取到节点信息 */
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
	/* 更新节点信息 */
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
			result = "节点信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "节点信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
