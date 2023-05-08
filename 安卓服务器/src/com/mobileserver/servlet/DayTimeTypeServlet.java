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

	/*����ʱ�������ҵ������*/
	private DayTimeTypeDAO dayTimeTypeDAO = new DayTimeTypeDAO();

	/*Ĭ�Ϲ��캯��*/
	public DayTimeTypeServlet() {
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
			/*��ȡ��ѯʱ������͵Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ��ʱ������Ͳ�ѯ*/
			List<DayTimeType> dayTimeTypeList = dayTimeTypeDAO.QueryDayTimeType();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���ʱ������ͣ���ȡʱ������Ͳ������������浽�½���ʱ������Ͷ��� */ 
			DayTimeType dayTimeType = new DayTimeType();
			int dayTimeTypeId = Integer.parseInt(request.getParameter("dayTimeTypeId"));
			dayTimeType.setDayTimeTypeId(dayTimeTypeId);
			String dayTimeTypeName = new String(request.getParameter("dayTimeTypeName").getBytes("iso-8859-1"), "UTF-8");
			dayTimeType.setDayTimeTypeName(dayTimeTypeName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = dayTimeTypeDAO.AddDayTimeType(dayTimeType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��ʱ������ͣ���ȡʱ������͵ļ�¼���*/
			int dayTimeTypeId = Integer.parseInt(request.getParameter("dayTimeTypeId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = dayTimeTypeDAO.DeleteDayTimeType(dayTimeTypeId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����ʱ�������֮ǰ�ȸ���dayTimeTypeId��ѯĳ��ʱ�������*/
			int dayTimeTypeId = Integer.parseInt(request.getParameter("dayTimeTypeId"));
			DayTimeType dayTimeType = dayTimeTypeDAO.GetDayTimeType(dayTimeTypeId);

			// �ͻ��˲�ѯ��ʱ������Ͷ��󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����ʱ������ͣ���ȡʱ������Ͳ������������浽�½���ʱ������Ͷ��� */ 
			DayTimeType dayTimeType = new DayTimeType();
			int dayTimeTypeId = Integer.parseInt(request.getParameter("dayTimeTypeId"));
			dayTimeType.setDayTimeTypeId(dayTimeTypeId);
			String dayTimeTypeName = new String(request.getParameter("dayTimeTypeName").getBytes("iso-8859-1"), "UTF-8");
			dayTimeType.setDayTimeTypeName(dayTimeTypeName);

			/* ����ҵ���ִ�и��²��� */
			String result = dayTimeTypeDAO.UpdateDayTimeType(dayTimeType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
