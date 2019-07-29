package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.DayTimeTypeDAO;
import com.mobileserver.domain.DayTimeType;

import org.json.JSONStringer;

public class DayTimeTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造时间段类型业务层对象*/
	private DayTimeTypeDAO dayTimeTypeDAO = new DayTimeTypeDAO();

	/*默认构造函数*/
	public DayTimeTypeServlet() {
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
			/*获取查询时间段类型的参数信息*/

			/*调用业务逻辑层执行时间段类型查询*/
			List<DayTimeType> dayTimeTypeList = dayTimeTypeDAO.QueryDayTimeType();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<DayTimeTypes>").append("\r\n");
			for (int i = 0; i < dayTimeTypeList.size(); i++) {
				sb.append("	<DayTimeType>").append("\r\n")
				.append("		<dayTimeTypeId>")
				.append(dayTimeTypeList.get(i).getDayTimeTypeId())
				.append("</dayTimeTypeId>").append("\r\n")
				.append("		<dayTimeTypeName>")
				.append(dayTimeTypeList.get(i).getDayTimeTypeName())
				.append("</dayTimeTypeName>").append("\r\n")
				.append("	</DayTimeType>").append("\r\n");
			}
			sb.append("</DayTimeTypes>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(DayTimeType dayTimeType: dayTimeTypeList) {
				  stringer.object();
			  stringer.key("dayTimeTypeId").value(dayTimeType.getDayTimeTypeId());
			  stringer.key("dayTimeTypeName").value(dayTimeType.getDayTimeTypeName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加时间段类型：获取时间段类型参数，参数保存到新建的时间段类型对象 */ 
			DayTimeType dayTimeType = new DayTimeType();
			int dayTimeTypeId = Integer.parseInt(request.getParameter("dayTimeTypeId"));
			dayTimeType.setDayTimeTypeId(dayTimeTypeId);
			String dayTimeTypeName = new String(request.getParameter("dayTimeTypeName").getBytes("iso-8859-1"), "UTF-8");
			dayTimeType.setDayTimeTypeName(dayTimeTypeName);

			/* 调用业务层执行添加操作 */
			String result = dayTimeTypeDAO.AddDayTimeType(dayTimeType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除时间段类型：获取时间段类型的记录编号*/
			int dayTimeTypeId = Integer.parseInt(request.getParameter("dayTimeTypeId"));
			/*调用业务逻辑层执行删除操作*/
			String result = dayTimeTypeDAO.DeleteDayTimeType(dayTimeTypeId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新时间段类型之前先根据dayTimeTypeId查询某个时间段类型*/
			int dayTimeTypeId = Integer.parseInt(request.getParameter("dayTimeTypeId"));
			DayTimeType dayTimeType = dayTimeTypeDAO.GetDayTimeType(dayTimeTypeId);

			// 客户端查询的时间段类型对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("dayTimeTypeId").value(dayTimeType.getDayTimeTypeId());
			  stringer.key("dayTimeTypeName").value(dayTimeType.getDayTimeTypeName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新时间段类型：获取时间段类型参数，参数保存到新建的时间段类型对象 */ 
			DayTimeType dayTimeType = new DayTimeType();
			int dayTimeTypeId = Integer.parseInt(request.getParameter("dayTimeTypeId"));
			dayTimeType.setDayTimeTypeId(dayTimeTypeId);
			String dayTimeTypeName = new String(request.getParameter("dayTimeTypeName").getBytes("iso-8859-1"), "UTF-8");
			dayTimeType.setDayTimeTypeName(dayTimeTypeName);

			/* 调用业务层执行更新操作 */
			String result = dayTimeTypeDAO.UpdateDayTimeType(dayTimeType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
