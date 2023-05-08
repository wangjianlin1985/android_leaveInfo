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

	/*�����������ҵ������*/
	private LeaveTypeDAO leaveTypeDAO = new LeaveTypeDAO();

	/*Ĭ�Ϲ��캯��*/
	public LeaveTypeServlet() {
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
			/*��ȡ��ѯ������͵Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ��������Ͳ�ѯ*/
			List<LeaveType> leaveTypeList = leaveTypeDAO.QueryLeaveType();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���������ͣ���ȡ������Ͳ������������浽�½���������Ͷ��� */ 
			LeaveType leaveType = new LeaveType();
			int leaveTypeId = Integer.parseInt(request.getParameter("leaveTypeId"));
			leaveType.setLeaveTypeId(leaveTypeId);
			String leaveTypeName = new String(request.getParameter("leaveTypeName").getBytes("iso-8859-1"), "UTF-8");
			leaveType.setLeaveTypeName(leaveTypeName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = leaveTypeDAO.AddLeaveType(leaveType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��������ͣ���ȡ������͵��������ID*/
			int leaveTypeId = Integer.parseInt(request.getParameter("leaveTypeId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = leaveTypeDAO.DeleteLeaveType(leaveTypeId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*�����������֮ǰ�ȸ���leaveTypeId��ѯĳ���������*/
			int leaveTypeId = Integer.parseInt(request.getParameter("leaveTypeId"));
			LeaveType leaveType = leaveTypeDAO.GetLeaveType(leaveTypeId);

			// �ͻ��˲�ѯ��������Ͷ��󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����������ͣ���ȡ������Ͳ������������浽�½���������Ͷ��� */ 
			LeaveType leaveType = new LeaveType();
			int leaveTypeId = Integer.parseInt(request.getParameter("leaveTypeId"));
			leaveType.setLeaveTypeId(leaveTypeId);
			String leaveTypeName = new String(request.getParameter("leaveTypeName").getBytes("iso-8859-1"), "UTF-8");
			leaveType.setLeaveTypeName(leaveTypeName);

			/* ����ҵ���ִ�и��²��� */
			String result = leaveTypeDAO.UpdateLeaveType(leaveType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
