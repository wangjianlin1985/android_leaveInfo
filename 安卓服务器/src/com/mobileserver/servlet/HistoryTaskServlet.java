package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.HistoryTaskDAO;
import com.mobileserver.domain.HistoryTask;

import org.json.JSONStringer;

public class HistoryTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*������ʷ����ҵ������*/
	private HistoryTaskDAO historyTaskDAO = new HistoryTaskDAO();

	/*Ĭ�Ϲ��캯��*/
	public HistoryTaskServlet() {
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
			/*��ȡ��ѯ��ʷ����Ĳ�����Ϣ*/
			int leaveObj = 0;
			if (request.getParameter("leaveObj") != null)
				leaveObj = Integer.parseInt(request.getParameter("leaveObj"));
			int noteObj = 0;
			if (request.getParameter("noteObj") != null)
				noteObj = Integer.parseInt(request.getParameter("noteObj"));
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");
			String taskTime = request.getParameter("taskTime");
			taskTime = taskTime == null ? "" : new String(request.getParameter(
					"taskTime").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ����ʷ�����ѯ*/
			List<HistoryTask> historyTaskList = historyTaskDAO.QueryHistoryTask(leaveObj,noteObj,userObj,taskTime);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<HistoryTasks>").append("\r\n");
			for (int i = 0; i < historyTaskList.size(); i++) {
				sb.append("	<HistoryTask>").append("\r\n")
				.append("		<historyTaskId>")
				.append(historyTaskList.get(i).getHistoryTaskId())
				.append("</historyTaskId>").append("\r\n")
				.append("		<leaveObj>")
				.append(historyTaskList.get(i).getLeaveObj())
				.append("</leaveObj>").append("\r\n")
				.append("		<noteObj>")
				.append(historyTaskList.get(i).getNoteObj())
				.append("</noteObj>").append("\r\n")
				.append("		<checkSuggest>")
				.append(historyTaskList.get(i).getCheckSuggest())
				.append("</checkSuggest>").append("\r\n")
				.append("		<userObj>")
				.append(historyTaskList.get(i).getUserObj())
				.append("</userObj>").append("\r\n")
				.append("		<taskTime>")
				.append(historyTaskList.get(i).getTaskTime())
				.append("</taskTime>").append("\r\n")
				.append("		<checkState>")
				.append(historyTaskList.get(i).getCheckState())
				.append("</checkState>").append("\r\n")
				.append("	</HistoryTask>").append("\r\n");
			}
			sb.append("</HistoryTasks>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(HistoryTask historyTask: historyTaskList) {
				  stringer.object();
			  stringer.key("historyTaskId").value(historyTask.getHistoryTaskId());
			  stringer.key("leaveObj").value(historyTask.getLeaveObj());
			  stringer.key("noteObj").value(historyTask.getNoteObj());
			  stringer.key("checkSuggest").value(historyTask.getCheckSuggest());
			  stringer.key("userObj").value(historyTask.getUserObj());
			  stringer.key("taskTime").value(historyTask.getTaskTime());
			  stringer.key("checkState").value(historyTask.getCheckState());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* �����ʷ���񣺻�ȡ��ʷ����������������浽�½�����ʷ������� */ 
			HistoryTask historyTask = new HistoryTask();
			int historyTaskId = Integer.parseInt(request.getParameter("historyTaskId"));
			historyTask.setHistoryTaskId(historyTaskId);
			int leaveObj = Integer.parseInt(request.getParameter("leaveObj"));
			historyTask.setLeaveObj(leaveObj);
			int noteObj = Integer.parseInt(request.getParameter("noteObj"));
			historyTask.setNoteObj(noteObj);
			String checkSuggest = new String(request.getParameter("checkSuggest").getBytes("iso-8859-1"), "UTF-8");
			historyTask.setCheckSuggest(checkSuggest);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			historyTask.setUserObj(userObj);
			String taskTime = new String(request.getParameter("taskTime").getBytes("iso-8859-1"), "UTF-8");
			historyTask.setTaskTime(taskTime);
			int checkState = Integer.parseInt(request.getParameter("checkState"));
			historyTask.setCheckState(checkState);

			/* ����ҵ���ִ����Ӳ��� */
			String result = historyTaskDAO.AddHistoryTask(historyTask);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����ʷ���񣺻�ȡ��ʷ�����������ʷ��¼Id*/
			int historyTaskId = Integer.parseInt(request.getParameter("historyTaskId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = historyTaskDAO.DeleteHistoryTask(historyTaskId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*������ʷ����֮ǰ�ȸ���historyTaskId��ѯĳ����ʷ����*/
			int historyTaskId = Integer.parseInt(request.getParameter("historyTaskId"));
			HistoryTask historyTask = historyTaskDAO.GetHistoryTask(historyTaskId);

			// �ͻ��˲�ѯ����ʷ������󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("historyTaskId").value(historyTask.getHistoryTaskId());
			  stringer.key("leaveObj").value(historyTask.getLeaveObj());
			  stringer.key("noteObj").value(historyTask.getNoteObj());
			  stringer.key("checkSuggest").value(historyTask.getCheckSuggest());
			  stringer.key("userObj").value(historyTask.getUserObj());
			  stringer.key("taskTime").value(historyTask.getTaskTime());
			  stringer.key("checkState").value(historyTask.getCheckState());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ������ʷ���񣺻�ȡ��ʷ����������������浽�½�����ʷ������� */ 
			HistoryTask historyTask = new HistoryTask();
			int historyTaskId = Integer.parseInt(request.getParameter("historyTaskId"));
			historyTask.setHistoryTaskId(historyTaskId);
			int leaveObj = Integer.parseInt(request.getParameter("leaveObj"));
			historyTask.setLeaveObj(leaveObj);
			int noteObj = Integer.parseInt(request.getParameter("noteObj"));
			historyTask.setNoteObj(noteObj);
			String checkSuggest = new String(request.getParameter("checkSuggest").getBytes("iso-8859-1"), "UTF-8");
			historyTask.setCheckSuggest(checkSuggest);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			historyTask.setUserObj(userObj);
			String taskTime = new String(request.getParameter("taskTime").getBytes("iso-8859-1"), "UTF-8");
			historyTask.setTaskTime(taskTime);
			int checkState = Integer.parseInt(request.getParameter("checkState"));
			historyTask.setCheckState(checkState);

			/* ����ҵ���ִ�и��²��� */
			String result = historyTaskDAO.UpdateHistoryTask(historyTask);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
