package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.PositionDAO;
import com.mobileserver.domain.Position;

import org.json.JSONStringer;

public class PositionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造职级信息业务层对象*/
	private PositionDAO positionDAO = new PositionDAO();

	/*默认构造函数*/
	public PositionServlet() {
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
			/*获取查询职级信息的参数信息*/

			/*调用业务逻辑层执行职级信息查询*/
			List<Position> positionList = positionDAO.QueryPosition();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Positions>").append("\r\n");
			for (int i = 0; i < positionList.size(); i++) {
				sb.append("	<Position>").append("\r\n")
				.append("		<positionId>")
				.append(positionList.get(i).getPositionId())
				.append("</positionId>").append("\r\n")
				.append("		<positionName>")
				.append(positionList.get(i).getPositionName())
				.append("</positionName>").append("\r\n")
				.append("	</Position>").append("\r\n");
			}
			sb.append("</Positions>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Position position: positionList) {
				  stringer.object();
			  stringer.key("positionId").value(position.getPositionId());
			  stringer.key("positionName").value(position.getPositionName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加职级信息：获取职级信息参数，参数保存到新建的职级信息对象 */ 
			Position position = new Position();
			int positionId = Integer.parseInt(request.getParameter("positionId"));
			position.setPositionId(positionId);
			String positionName = new String(request.getParameter("positionName").getBytes("iso-8859-1"), "UTF-8");
			position.setPositionName(positionName);

			/* 调用业务层执行添加操作 */
			String result = positionDAO.AddPosition(position);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除职级信息：获取职级信息的职级ID*/
			int positionId = Integer.parseInt(request.getParameter("positionId"));
			/*调用业务逻辑层执行删除操作*/
			String result = positionDAO.DeletePosition(positionId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新职级信息之前先根据positionId查询某个职级信息*/
			int positionId = Integer.parseInt(request.getParameter("positionId"));
			Position position = positionDAO.GetPosition(positionId);

			// 客户端查询的职级信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("positionId").value(position.getPositionId());
			  stringer.key("positionName").value(position.getPositionName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新职级信息：获取职级信息参数，参数保存到新建的职级信息对象 */ 
			Position position = new Position();
			int positionId = Integer.parseInt(request.getParameter("positionId"));
			position.setPositionId(positionId);
			String positionName = new String(request.getParameter("positionName").getBytes("iso-8859-1"), "UTF-8");
			position.setPositionName(positionName);

			/* 调用业务层执行更新操作 */
			String result = positionDAO.UpdatePosition(position);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
