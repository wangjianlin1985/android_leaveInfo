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

	/*����ְ����Ϣҵ������*/
	private PositionDAO positionDAO = new PositionDAO();

	/*Ĭ�Ϲ��캯��*/
	public PositionServlet() {
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
			/*��ȡ��ѯְ����Ϣ�Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ��ְ����Ϣ��ѯ*/
			List<Position> positionList = positionDAO.QueryPosition();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���ְ����Ϣ����ȡְ����Ϣ�������������浽�½���ְ����Ϣ���� */ 
			Position position = new Position();
			int positionId = Integer.parseInt(request.getParameter("positionId"));
			position.setPositionId(positionId);
			String positionName = new String(request.getParameter("positionName").getBytes("iso-8859-1"), "UTF-8");
			position.setPositionName(positionName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = positionDAO.AddPosition(position);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��ְ����Ϣ����ȡְ����Ϣ��ְ��ID*/
			int positionId = Integer.parseInt(request.getParameter("positionId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = positionDAO.DeletePosition(positionId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����ְ����Ϣ֮ǰ�ȸ���positionId��ѯĳ��ְ����Ϣ*/
			int positionId = Integer.parseInt(request.getParameter("positionId"));
			Position position = positionDAO.GetPosition(positionId);

			// �ͻ��˲�ѯ��ְ����Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����ְ����Ϣ����ȡְ����Ϣ�������������浽�½���ְ����Ϣ���� */ 
			Position position = new Position();
			int positionId = Integer.parseInt(request.getParameter("positionId"));
			position.setPositionId(positionId);
			String positionName = new String(request.getParameter("positionName").getBytes("iso-8859-1"), "UTF-8");
			position.setPositionName(positionName);

			/* ����ҵ���ִ�и��²��� */
			String result = positionDAO.UpdatePosition(position);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
