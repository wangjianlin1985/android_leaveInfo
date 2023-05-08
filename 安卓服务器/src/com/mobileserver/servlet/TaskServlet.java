package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.TaskDAO;
import com.mobileserver.domain.Task;

import org.json.JSONStringer;

public class TaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����������Ϣҵ������*/
	private TaskDAO taskDAO = new TaskDAO();

	/*Ĭ�Ϲ��캯��*/
	public TaskServlet() {
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
			/*��ȡ��ѯ������Ϣ�Ĳ�����Ϣ*/
			int leaveInfoObj = 0;
			if (request.getParameter("leaveInfoObj") != null)
				leaveInfoObj = Integer.parseInt(request.getParameter("leaveInfoObj"));
			int noteObj = 0;
			if (request.getParameter("noteObj") != null)
				noteObj = Integer.parseInt(request.getParameter("noteObj"));
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");
			String taskTime = request.getParameter("taskTime");
			taskTime = taskTime == null ? "" : new String(request.getParameter(
					"taskTime").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ��������Ϣ��ѯ*/
			List<Task> taskList = taskDAO.QueryTask(leaveInfoObj,noteObj,userObj,taskTime);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Tasks>").append("\r\n");
			for (int i = 0; i < taskList.size(); i++) {
				sb.append("	<Task>").append("\r\n")
				.append("		<taskId>")
				.append(taskList.get(i).getTaskId())
				.append("</taskId>").append("\r\n")
				.append("		<leaveInfoObj>")
				.append(taskList.get(i).getLeaveInfoObj())
				.append("</leaveInfoObj>").append("\r\n")
				.append("		<noteObj>")
				.append(taskList.get(i).getNoteObj())
				.append("</noteObj>").append("\r\n")
				.append("		<state>")
				.append(taskList.get(i).getState())
				.append("</state>").append("\r\n")
				.append("		<userObj>")
				.append(taskList.get(i).getUserObj())
				.append("</userObj>").append("\r\n")
				.append("		<taskTime>")
				.append(taskList.get(i).getTaskTime())
				.append("</taskTime>").append("\r\n")
				.append("	</Task>").append("\r\n");
			}
			sb.append("</Tasks>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Task task: taskList) {
				  stringer.object();
			  stringer.key("taskId").value(task.getTaskId());
			  stringer.key("leaveInfoObj").value(task.getLeaveInfoObj());
			  stringer.key("noteObj").value(task.getNoteObj());
			  stringer.key("state").value(task.getState());
			  stringer.key("userObj").value(task.getUserObj());
			  stringer.key("taskTime").value(task.getTaskTime());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���������Ϣ����ȡ������Ϣ�������������浽�½���������Ϣ���� */ 
			Task task = new Task();
			int taskId = Integer.parseInt(request.getParameter("taskId"));
			task.setTaskId(taskId);
			int leaveInfoObj = Integer.parseInt(request.getParameter("leaveInfoObj"));
			task.setLeaveInfoObj(leaveInfoObj);
			int noteObj = Integer.parseInt(request.getParameter("noteObj"));
			task.setNoteObj(noteObj);
			int state = Integer.parseInt(request.getParameter("state"));
			task.setState(state);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			task.setUserObj(userObj);
			String taskTime = new String(request.getParameter("taskTime").getBytes("iso-8859-1"), "UTF-8");
			task.setTaskTime(taskTime);

			/* ����ҵ���ִ����Ӳ��� */
			String result = taskDAO.AddTask(task);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��������Ϣ����ȡ������Ϣ������ID*/
			int taskId = Integer.parseInt(request.getParameter("taskId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = taskDAO.DeleteTask(taskId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����������Ϣ֮ǰ�ȸ���taskId��ѯĳ��������Ϣ*/
			int taskId = Integer.parseInt(request.getParameter("taskId"));
			Task task = taskDAO.GetTask(taskId);

			// �ͻ��˲�ѯ��������Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("taskId").value(task.getTaskId());
			  stringer.key("leaveInfoObj").value(task.getLeaveInfoObj());
			  stringer.key("noteObj").value(task.getNoteObj());
			  stringer.key("state").value(task.getState());
			  stringer.key("userObj").value(task.getUserObj());
			  stringer.key("taskTime").value(task.getTaskTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����������Ϣ����ȡ������Ϣ�������������浽�½���������Ϣ���� */ 
			Task task = new Task();
			int taskId = Integer.parseInt(request.getParameter("taskId"));
			task.setTaskId(taskId);
			int leaveInfoObj = Integer.parseInt(request.getParameter("leaveInfoObj"));
			task.setLeaveInfoObj(leaveInfoObj);
			int noteObj = Integer.parseInt(request.getParameter("noteObj"));
			task.setNoteObj(noteObj);
			int state = Integer.parseInt(request.getParameter("state"));
			task.setState(state);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			task.setUserObj(userObj);
			String taskTime = new String(request.getParameter("taskTime").getBytes("iso-8859-1"), "UTF-8");
			task.setTaskTime(taskTime);

			/* ����ҵ���ִ�и��²��� */
			String result = taskDAO.UpdateTask(task);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
