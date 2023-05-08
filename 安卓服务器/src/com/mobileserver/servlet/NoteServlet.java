package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.NoteDAO;
import com.mobileserver.domain.Note;

import org.json.JSONStringer;

public class NoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����ڵ���Ϣҵ������*/
	private NoteDAO noteDAO = new NoteDAO();

	/*Ĭ�Ϲ��캯��*/
	public NoteServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*��ȡaction����������action��ִֵ�в�ͬ��ҵ����*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*��ȡ��ѯ�ڵ���Ϣ�Ĳ�����Ϣ*/
			String noteName = request.getParameter("noteName");
			noteName = noteName == null ? "" : new String(request.getParameter(
					"noteName").getBytes("iso-8859-1"), "UTF-8");
			int departmentObj = 0;
			if (request.getParameter("departmentObj") != null)
				departmentObj = Integer.parseInt(request.getParameter("departmentObj"));
			int positionObj = 0;
			if (request.getParameter("positionObj") != null)
				positionObj = Integer.parseInt(request.getParameter("positionObj"));

			/*����ҵ���߼���ִ�нڵ���Ϣ��ѯ*/
			List<Note> noteList = noteDAO.QueryNote(noteName,departmentObj,positionObj);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Notes>").append("\r\n");
			for (int i = 0; i < noteList.size(); i++) {
				sb.append("	<Note>").append("\r\n")
				.append("		<noteId>")
				.append(noteList.get(i).getNoteId())
				.append("</noteId>").append("\r\n")
				.append("		<noteName>")
				.append(noteList.get(i).getNoteName())
				.append("</noteName>").append("\r\n")
				.append("		<departmentObj>")
				.append(noteList.get(i).getDepartmentObj())
				.append("</departmentObj>").append("\r\n")
				.append("		<positionObj>")
				.append(noteList.get(i).getPositionObj())
				.append("</positionObj>").append("\r\n")
				.append("		<noteIndex>")
				.append(noteList.get(i).getNoteIndex())
				.append("</noteIndex>").append("\r\n")
				.append("	</Note>").append("\r\n");
			}
			sb.append("</Notes>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Note note: noteList) {
				  stringer.object();
			  stringer.key("noteId").value(note.getNoteId());
			  stringer.key("noteName").value(note.getNoteName());
			  stringer.key("departmentObj").value(note.getDepartmentObj());
			  stringer.key("positionObj").value(note.getPositionObj());
			  stringer.key("noteIndex").value(note.getNoteIndex());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӽڵ���Ϣ����ȡ�ڵ���Ϣ�������������浽�½��Ľڵ���Ϣ���� */ 
			Note note = new Note();
			int noteId = Integer.parseInt(request.getParameter("noteId"));
			note.setNoteId(noteId);
			String noteName = new String(request.getParameter("noteName").getBytes("iso-8859-1"), "UTF-8");
			note.setNoteName(noteName);
			int departmentObj = Integer.parseInt(request.getParameter("departmentObj"));
			note.setDepartmentObj(departmentObj);
			int positionObj = Integer.parseInt(request.getParameter("positionObj"));
			note.setPositionObj(positionObj);
			int noteIndex = Integer.parseInt(request.getParameter("noteIndex"));
			note.setNoteIndex(noteIndex);

			/* ����ҵ���ִ����Ӳ��� */
			String result = noteDAO.AddNote(note);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ���ڵ���Ϣ����ȡ�ڵ���Ϣ�Ľڵ�ID*/
			int noteId = Integer.parseInt(request.getParameter("noteId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = noteDAO.DeleteNote(noteId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���½ڵ���Ϣ֮ǰ�ȸ���noteId��ѯĳ���ڵ���Ϣ*/
			int noteId = Integer.parseInt(request.getParameter("noteId"));
			Note note = noteDAO.GetNote(noteId);

			// �ͻ��˲�ѯ�Ľڵ���Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("noteId").value(note.getNoteId());
			  stringer.key("noteName").value(note.getNoteName());
			  stringer.key("departmentObj").value(note.getDepartmentObj());
			  stringer.key("positionObj").value(note.getPositionObj());
			  stringer.key("noteIndex").value(note.getNoteIndex());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���½ڵ���Ϣ����ȡ�ڵ���Ϣ�������������浽�½��Ľڵ���Ϣ���� */ 
			Note note = new Note();
			int noteId = Integer.parseInt(request.getParameter("noteId"));
			note.setNoteId(noteId);
			String noteName = new String(request.getParameter("noteName").getBytes("iso-8859-1"), "UTF-8");
			note.setNoteName(noteName);
			int departmentObj = Integer.parseInt(request.getParameter("departmentObj"));
			note.setDepartmentObj(departmentObj);
			int positionObj = Integer.parseInt(request.getParameter("positionObj"));
			note.setPositionObj(positionObj);
			int noteIndex = Integer.parseInt(request.getParameter("noteIndex"));
			note.setNoteIndex(noteIndex);

			/* ����ҵ���ִ�и��²��� */
			String result = noteDAO.UpdateNote(note);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
