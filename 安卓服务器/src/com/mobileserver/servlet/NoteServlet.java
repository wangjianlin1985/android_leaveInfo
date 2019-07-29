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

	/*构造节点信息业务层对象*/
	private NoteDAO noteDAO = new NoteDAO();

	/*默认构造函数*/
	public NoteServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*获取action参数，根据action的值执行不同的业务处理*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*获取查询节点信息的参数信息*/
			String noteName = request.getParameter("noteName");
			noteName = noteName == null ? "" : new String(request.getParameter(
					"noteName").getBytes("iso-8859-1"), "UTF-8");
			int departmentObj = 0;
			if (request.getParameter("departmentObj") != null)
				departmentObj = Integer.parseInt(request.getParameter("departmentObj"));
			int positionObj = 0;
			if (request.getParameter("positionObj") != null)
				positionObj = Integer.parseInt(request.getParameter("positionObj"));

			/*调用业务逻辑层执行节点信息查询*/
			List<Note> noteList = noteDAO.QueryNote(noteName,departmentObj,positionObj);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加节点信息：获取节点信息参数，参数保存到新建的节点信息对象 */ 
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

			/* 调用业务层执行添加操作 */
			String result = noteDAO.AddNote(note);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除节点信息：获取节点信息的节点ID*/
			int noteId = Integer.parseInt(request.getParameter("noteId"));
			/*调用业务逻辑层执行删除操作*/
			String result = noteDAO.DeleteNote(noteId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新节点信息之前先根据noteId查询某个节点信息*/
			int noteId = Integer.parseInt(request.getParameter("noteId"));
			Note note = noteDAO.GetNote(noteId);

			// 客户端查询的节点信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新节点信息：获取节点信息参数，参数保存到新建的节点信息对象 */ 
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

			/* 调用业务层执行更新操作 */
			String result = noteDAO.UpdateNote(note);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
