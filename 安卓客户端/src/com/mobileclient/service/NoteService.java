package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Note;
import com.mobileclient.util.HttpUtil;

/*节点信息管理业务逻辑层*/
public class NoteService {
	/* 添加节点信息 */
	public String AddNote(Note note) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("noteId", note.getNoteId() + "");
		params.put("noteName", note.getNoteName());
		params.put("departmentObj", note.getDepartmentObj() + "");
		params.put("positionObj", note.getPositionObj() + "");
		params.put("noteIndex", note.getNoteIndex() + "");
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NoteServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询节点信息 */
	public List<Note> QueryNote(Note queryConditionNote) throws Exception {
		String urlString = HttpUtil.BASE_URL + "NoteServlet?action=query";
		if(queryConditionNote != null) {
			urlString += "&noteName=" + URLEncoder.encode(queryConditionNote.getNoteName(), "UTF-8") + "";
			urlString += "&departmentObj=" + queryConditionNote.getDepartmentObj();
			urlString += "&positionObj=" + queryConditionNote.getPositionObj();
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		NoteListHandler noteListHander = new NoteListHandler();
		xr.setContentHandler(noteListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Note> noteList = noteListHander.getNoteList();
		return noteList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Note> noteList = new ArrayList<Note>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Note note = new Note();
				note.setNoteId(object.getInt("noteId"));
				note.setNoteName(object.getString("noteName"));
				note.setDepartmentObj(object.getInt("departmentObj"));
				note.setPositionObj(object.getInt("positionObj"));
				note.setNoteIndex(object.getInt("noteIndex"));
				noteList.add(note);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return noteList;
	}

	/* 更新节点信息 */
	public String UpdateNote(Note note) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("noteId", note.getNoteId() + "");
		params.put("noteName", note.getNoteName());
		params.put("departmentObj", note.getDepartmentObj() + "");
		params.put("positionObj", note.getPositionObj() + "");
		params.put("noteIndex", note.getNoteIndex() + "");
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NoteServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除节点信息 */
	public String DeleteNote(int noteId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("noteId", noteId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NoteServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "节点信息信息删除失败!";
		}
	}

	/* 根据节点ID获取节点信息对象 */
	public Note GetNote(int noteId)  {
		List<Note> noteList = new ArrayList<Note>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("noteId", noteId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NoteServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Note note = new Note();
				note.setNoteId(object.getInt("noteId"));
				note.setNoteName(object.getString("noteName"));
				note.setDepartmentObj(object.getInt("departmentObj"));
				note.setPositionObj(object.getInt("positionObj"));
				note.setNoteIndex(object.getInt("noteIndex"));
				noteList.add(note);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = noteList.size();
		if(size>0) return noteList.get(0); 
		else return null; 
	}
}
