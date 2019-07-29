package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.LeaveTypeDAO;
import com.mobileserver.domain.LeaveType;

import org.json.JSONStringer;

public class LeaveTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造请假类型业务层对象*/
	private LeaveTypeDAO leaveTypeDAO = new LeaveTypeDAO();

	/*默认构造函数*/
	public LeaveTypeServlet() {
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
			/*获取查询请假类型的参数信息*/

			/*调用业务逻辑层执行请假类型查询*/
			List<LeaveType> leaveTypeList = leaveTypeDAO.QueryLeaveType();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<LeaveTypes>").append("\r\n");
			for (int i = 0; i < leaveTypeList.size(); i++) {
				sb.append("	<LeaveType>").append("\r\n")
				.append("		<leaveTypeId>")
				.append(leaveTypeList.get(i).getLeaveTypeId())
				.append("</leaveTypeId>").append("\r\n")
				.append("		<leaveTypeName>")
				.append(leaveTypeList.get(i).getLeaveTypeName())
				.append("</leaveTypeName>").append("\r\n")
				.append("	</LeaveType>").append("\r\n");
			}
			sb.append("</LeaveTypes>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(LeaveType leaveType: leaveTypeList) {
				  stringer.object();
			  stringer.key("leaveTypeId").value(leaveType.getLeaveTypeId());
			  stringer.key("leaveTypeName").value(leaveType.getLeaveTypeName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加请假类型：获取请假类型参数，参数保存到新建的请假类型对象 */ 
			LeaveType leaveType = new LeaveType();
			int leaveTypeId = Integer.parseInt(request.getParameter("leaveTypeId"));
			leaveType.setLeaveTypeId(leaveTypeId);
			String leaveTypeName = new String(request.getParameter("leaveTypeName").getBytes("iso-8859-1"), "UTF-8");
			leaveType.setLeaveTypeName(leaveTypeName);

			/* 调用业务层执行添加操作 */
			String result = leaveTypeDAO.AddLeaveType(leaveType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除请假类型：获取请假类型的请假类型ID*/
			int leaveTypeId = Integer.parseInt(request.getParameter("leaveTypeId"));
			/*调用业务逻辑层执行删除操作*/
			String result = leaveTypeDAO.DeleteLeaveType(leaveTypeId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新请假类型之前先根据leaveTypeId查询某个请假类型*/
			int leaveTypeId = Integer.parseInt(request.getParameter("leaveTypeId"));
			LeaveType leaveType = leaveTypeDAO.GetLeaveType(leaveTypeId);

			// 客户端查询的请假类型对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("leaveTypeId").value(leaveType.getLeaveTypeId());
			  stringer.key("leaveTypeName").value(leaveType.getLeaveTypeName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新请假类型：获取请假类型参数，参数保存到新建的请假类型对象 */ 
			LeaveType leaveType = new LeaveType();
			int leaveTypeId = Integer.parseInt(request.getParameter("leaveTypeId"));
			leaveType.setLeaveTypeId(leaveTypeId);
			String leaveTypeName = new String(request.getParameter("leaveTypeName").getBytes("iso-8859-1"), "UTF-8");
			leaveType.setLeaveTypeName(leaveTypeName);

			/* 调用业务层执行更新操作 */
			String result = leaveTypeDAO.UpdateLeaveType(leaveType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
