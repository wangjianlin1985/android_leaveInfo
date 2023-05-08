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

	/*构造历史任务业务层对象*/
	private HistoryTaskDAO historyTaskDAO = new HistoryTaskDAO();

	/*默认构造函数*/
	public HistoryTaskServlet() {
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
			/*获取查询历史任务的参数信息*/
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

			/*调用业务逻辑层执行历史任务查询*/
			List<HistoryTask> historyTaskList = historyTaskDAO.QueryHistoryTask(leaveObj,noteObj,userObj,taskTime);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加历史任务：获取历史任务参数，参数保存到新建的历史任务对象 */ 
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

			/* 调用业务层执行添加操作 */
			String result = historyTaskDAO.AddHistoryTask(historyTask);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除历史任务：获取历史任务的任务历史记录Id*/
			int historyTaskId = Integer.parseInt(request.getParameter("historyTaskId"));
			/*调用业务逻辑层执行删除操作*/
			String result = historyTaskDAO.DeleteHistoryTask(historyTaskId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新历史任务之前先根据historyTaskId查询某个历史任务*/
			int historyTaskId = Integer.parseInt(request.getParameter("historyTaskId"));
			HistoryTask historyTask = historyTaskDAO.GetHistoryTask(historyTaskId);

			// 客户端查询的历史任务对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新历史任务：获取历史任务参数，参数保存到新建的历史任务对象 */ 
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

			/* 调用业务层执行更新操作 */
			String result = historyTaskDAO.UpdateHistoryTask(historyTask);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
