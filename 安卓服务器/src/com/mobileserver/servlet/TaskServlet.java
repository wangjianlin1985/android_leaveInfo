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

	/*构造任务信息业务层对象*/
	private TaskDAO taskDAO = new TaskDAO();

	/*默认构造函数*/
	public TaskServlet() {
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
			/*获取查询任务信息的参数信息*/
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

			/*调用业务逻辑层执行任务信息查询*/
			List<Task> taskList = taskDAO.QueryTask(leaveInfoObj,noteObj,userObj,taskTime);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加任务信息：获取任务信息参数，参数保存到新建的任务信息对象 */ 
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

			/* 调用业务层执行添加操作 */
			String result = taskDAO.AddTask(task);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除任务信息：获取任务信息的任务ID*/
			int taskId = Integer.parseInt(request.getParameter("taskId"));
			/*调用业务逻辑层执行删除操作*/
			String result = taskDAO.DeleteTask(taskId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新任务信息之前先根据taskId查询某个任务信息*/
			int taskId = Integer.parseInt(request.getParameter("taskId"));
			Task task = taskDAO.GetTask(taskId);

			// 客户端查询的任务信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新任务信息：获取任务信息参数，参数保存到新建的任务信息对象 */ 
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

			/* 调用业务层执行更新操作 */
			String result = taskDAO.UpdateTask(task);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
