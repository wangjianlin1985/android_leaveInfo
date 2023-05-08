package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.LeaveInfoDAO;
import com.mobileserver.domain.LeaveInfo;

import org.json.JSONStringer;

public class LeaveInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*���������Ϣҵ������*/
	private LeaveInfoDAO leaveInfoDAO = new LeaveInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public LeaveInfoServlet() {
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
			/*��ȡ��ѯ�����Ϣ�Ĳ�����Ϣ*/
			String writeTime = request.getParameter("writeTime");
			writeTime = writeTime == null ? "" : new String(request.getParameter(
					"writeTime").getBytes("iso-8859-1"), "UTF-8");
			int departmentObj = 0;
			if (request.getParameter("departmentObj") != null)
				departmentObj = Integer.parseInt(request.getParameter("departmentObj"));
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");
			int positionObj = 0;
			if (request.getParameter("positionObj") != null)
				positionObj = Integer.parseInt(request.getParameter("positionObj"));
			int leaveTypeObj = 0;
			if (request.getParameter("leaveTypeObj") != null)
				leaveTypeObj = Integer.parseInt(request.getParameter("leaveTypeObj"));
			Timestamp startDate = null;
			if (request.getParameter("startDate") != null)
				startDate = Timestamp.valueOf(request.getParameter("startDate"));
			int startDayTimeType = 0;
			if (request.getParameter("startDayTimeType") != null)
				startDayTimeType = Integer.parseInt(request.getParameter("startDayTimeType"));
			Timestamp endDate = null;
			if (request.getParameter("endDate") != null)
				endDate = Timestamp.valueOf(request.getParameter("endDate"));
			int endDayTimeType = 0;
			if (request.getParameter("endDayTimeType") != null)
				endDayTimeType = Integer.parseInt(request.getParameter("endDayTimeType"));
			String place = request.getParameter("place");
			place = place == null ? "" : new String(request.getParameter(
					"place").getBytes("iso-8859-1"), "UTF-8");
			String reason = request.getParameter("reason");
			reason = reason == null ? "" : new String(request.getParameter(
					"reason").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ�������Ϣ��ѯ*/
			List<LeaveInfo> leaveInfoList = leaveInfoDAO.QueryLeaveInfo(writeTime,departmentObj,userObj,positionObj,leaveTypeObj,startDate,startDayTimeType,endDate,endDayTimeType,place,reason);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<LeaveInfos>").append("\r\n");
			for (int i = 0; i < leaveInfoList.size(); i++) {
				sb.append("	<LeaveInfo>").append("\r\n")
				.append("		<leaveId>")
				.append(leaveInfoList.get(i).getLeaveId())
				.append("</leaveId>").append("\r\n")
				.append("		<writeTime>")
				.append(leaveInfoList.get(i).getWriteTime())
				.append("</writeTime>").append("\r\n")
				.append("		<departmentObj>")
				.append(leaveInfoList.get(i).getDepartmentObj())
				.append("</departmentObj>").append("\r\n")
				.append("		<userObj>")
				.append(leaveInfoList.get(i).getUserObj())
				.append("</userObj>").append("\r\n")
				.append("		<positionObj>")
				.append(leaveInfoList.get(i).getPositionObj())
				.append("</positionObj>").append("\r\n")
				.append("		<leaveTypeObj>")
				.append(leaveInfoList.get(i).getLeaveTypeObj())
				.append("</leaveTypeObj>").append("\r\n")
				.append("		<sfcj>")
				.append(leaveInfoList.get(i).getSfcj())
				.append("</sfcj>").append("\r\n")
				.append("		<startDate>")
				.append(leaveInfoList.get(i).getStartDate())
				.append("</startDate>").append("\r\n")
				.append("		<startDayTimeType>")
				.append(leaveInfoList.get(i).getStartDayTimeType())
				.append("</startDayTimeType>").append("\r\n")
				.append("		<endDate>")
				.append(leaveInfoList.get(i).getEndDate())
				.append("</endDate>").append("\r\n")
				.append("		<endDayTimeType>")
				.append(leaveInfoList.get(i).getEndDayTimeType())
				.append("</endDayTimeType>").append("\r\n")
				.append("		<leaveDays>")
				.append(leaveInfoList.get(i).getLeaveDays())
				.append("</leaveDays>").append("\r\n")
				.append("		<writeUserObj>")
				.append(leaveInfoList.get(i).getWriteUserObj())
				.append("</writeUserObj>").append("\r\n")
				.append("		<place>")
				.append(leaveInfoList.get(i).getPlace())
				.append("</place>").append("\r\n")
				.append("		<reason>")
				.append(leaveInfoList.get(i).getReason())
				.append("</reason>").append("\r\n")
				.append("		<memo>")
				.append(leaveInfoList.get(i).getMemo())
				.append("</memo>").append("\r\n")
				.append("		<state>")
				.append(leaveInfoList.get(i).getState())
				.append("</state>").append("\r\n")
				.append("	</LeaveInfo>").append("\r\n");
			}
			sb.append("</LeaveInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(LeaveInfo leaveInfo: leaveInfoList) {
				  stringer.object();
			  stringer.key("leaveId").value(leaveInfo.getLeaveId());
			  stringer.key("writeTime").value(leaveInfo.getWriteTime());
			  stringer.key("departmentObj").value(leaveInfo.getDepartmentObj());
			  stringer.key("userObj").value(leaveInfo.getUserObj());
			  stringer.key("positionObj").value(leaveInfo.getPositionObj());
			  stringer.key("leaveTypeObj").value(leaveInfo.getLeaveTypeObj());
			  stringer.key("sfcj").value(leaveInfo.getSfcj());
			  stringer.key("startDate").value(leaveInfo.getStartDate());
			  stringer.key("startDayTimeType").value(leaveInfo.getStartDayTimeType());
			  stringer.key("endDate").value(leaveInfo.getEndDate());
			  stringer.key("endDayTimeType").value(leaveInfo.getEndDayTimeType());
			  stringer.key("leaveDays").value(leaveInfo.getLeaveDays());
			  stringer.key("writeUserObj").value(leaveInfo.getWriteUserObj());
			  stringer.key("place").value(leaveInfo.getPlace());
			  stringer.key("reason").value(leaveInfo.getReason());
			  stringer.key("memo").value(leaveInfo.getMemo());
			  stringer.key("state").value(leaveInfo.getState());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��������Ϣ����ȡ�����Ϣ�������������浽�½��������Ϣ���� */ 
			LeaveInfo leaveInfo = new LeaveInfo();
			int leaveId = Integer.parseInt(request.getParameter("leaveId"));
			leaveInfo.setLeaveId(leaveId);
			String writeTime = new String(request.getParameter("writeTime").getBytes("iso-8859-1"), "UTF-8");
			leaveInfo.setWriteTime(writeTime);
			int departmentObj = Integer.parseInt(request.getParameter("departmentObj"));
			leaveInfo.setDepartmentObj(departmentObj);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			leaveInfo.setUserObj(userObj);
			int positionObj = Integer.parseInt(request.getParameter("positionObj"));
			leaveInfo.setPositionObj(positionObj);
			int leaveTypeObj = Integer.parseInt(request.getParameter("leaveTypeObj"));
			leaveInfo.setLeaveTypeObj(leaveTypeObj);
			String sfcj = new String(request.getParameter("sfcj").getBytes("iso-8859-1"), "UTF-8");
			leaveInfo.setSfcj(sfcj);
			Timestamp startDate = Timestamp.valueOf(request.getParameter("startDate"));
			leaveInfo.setStartDate(startDate);
			int startDayTimeType = Integer.parseInt(request.getParameter("startDayTimeType"));
			leaveInfo.setStartDayTimeType(startDayTimeType);
			Timestamp endDate = Timestamp.valueOf(request.getParameter("endDate"));
			leaveInfo.setEndDate(endDate);
			int endDayTimeType = Integer.parseInt(request.getParameter("endDayTimeType"));
			leaveInfo.setEndDayTimeType(endDayTimeType);
			float leaveDays = Float.parseFloat(request.getParameter("leaveDays"));
			leaveInfo.setLeaveDays(leaveDays);
			String writeUserObj = new String(request.getParameter("writeUserObj").getBytes("iso-8859-1"), "UTF-8");
			leaveInfo.setWriteUserObj(writeUserObj);
			String place = new String(request.getParameter("place").getBytes("iso-8859-1"), "UTF-8");
			leaveInfo.setPlace(place);
			String reason = new String(request.getParameter("reason").getBytes("iso-8859-1"), "UTF-8");
			leaveInfo.setReason(reason);
			String memo = new String(request.getParameter("memo").getBytes("iso-8859-1"), "UTF-8");
			leaveInfo.setMemo(memo);
			int state = Integer.parseInt(request.getParameter("state"));
			leaveInfo.setState(state);

			/* ����ҵ���ִ����Ӳ��� */
			String result = leaveInfoDAO.AddLeaveInfo(leaveInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ�������Ϣ����ȡ�����Ϣ����ټ�¼ID*/
			int leaveId = Integer.parseInt(request.getParameter("leaveId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = leaveInfoDAO.DeleteLeaveInfo(leaveId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���������Ϣ֮ǰ�ȸ���leaveId��ѯĳ�������Ϣ*/
			int leaveId = Integer.parseInt(request.getParameter("leaveId"));
			LeaveInfo leaveInfo = leaveInfoDAO.GetLeaveInfo(leaveId);

			// �ͻ��˲�ѯ�������Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("leaveId").value(leaveInfo.getLeaveId());
			  stringer.key("writeTime").value(leaveInfo.getWriteTime());
			  stringer.key("departmentObj").value(leaveInfo.getDepartmentObj());
			  stringer.key("userObj").value(leaveInfo.getUserObj());
			  stringer.key("positionObj").value(leaveInfo.getPositionObj());
			  stringer.key("leaveTypeObj").value(leaveInfo.getLeaveTypeObj());
			  stringer.key("sfcj").value(leaveInfo.getSfcj());
			  stringer.key("startDate").value(leaveInfo.getStartDate());
			  stringer.key("startDayTimeType").value(leaveInfo.getStartDayTimeType());
			  stringer.key("endDate").value(leaveInfo.getEndDate());
			  stringer.key("endDayTimeType").value(leaveInfo.getEndDayTimeType());
			  stringer.key("leaveDays").value(leaveInfo.getLeaveDays());
			  stringer.key("writeUserObj").value(leaveInfo.getWriteUserObj());
			  stringer.key("place").value(leaveInfo.getPlace());
			  stringer.key("reason").value(leaveInfo.getReason());
			  stringer.key("memo").value(leaveInfo.getMemo());
			  stringer.key("state").value(leaveInfo.getState());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���������Ϣ����ȡ�����Ϣ�������������浽�½��������Ϣ���� */ 
			LeaveInfo leaveInfo = new LeaveInfo();
			int leaveId = Integer.parseInt(request.getParameter("leaveId"));
			leaveInfo.setLeaveId(leaveId);
			String writeTime = new String(request.getParameter("writeTime").getBytes("iso-8859-1"), "UTF-8");
			leaveInfo.setWriteTime(writeTime);
			int departmentObj = Integer.parseInt(request.getParameter("departmentObj"));
			leaveInfo.setDepartmentObj(departmentObj);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			leaveInfo.setUserObj(userObj);
			int positionObj = Integer.parseInt(request.getParameter("positionObj"));
			leaveInfo.setPositionObj(positionObj);
			int leaveTypeObj = Integer.parseInt(request.getParameter("leaveTypeObj"));
			leaveInfo.setLeaveTypeObj(leaveTypeObj);
			String sfcj = new String(request.getParameter("sfcj").getBytes("iso-8859-1"), "UTF-8");
			leaveInfo.setSfcj(sfcj);
			Timestamp startDate = Timestamp.valueOf(request.getParameter("startDate"));
			leaveInfo.setStartDate(startDate);
			int startDayTimeType = Integer.parseInt(request.getParameter("startDayTimeType"));
			leaveInfo.setStartDayTimeType(startDayTimeType);
			Timestamp endDate = Timestamp.valueOf(request.getParameter("endDate"));
			leaveInfo.setEndDate(endDate);
			int endDayTimeType = Integer.parseInt(request.getParameter("endDayTimeType"));
			leaveInfo.setEndDayTimeType(endDayTimeType);
			float leaveDays = Float.parseFloat(request.getParameter("leaveDays"));
			leaveInfo.setLeaveDays(leaveDays);
			String writeUserObj = new String(request.getParameter("writeUserObj").getBytes("iso-8859-1"), "UTF-8");
			leaveInfo.setWriteUserObj(writeUserObj);
			String place = new String(request.getParameter("place").getBytes("iso-8859-1"), "UTF-8");
			leaveInfo.setPlace(place);
			String reason = new String(request.getParameter("reason").getBytes("iso-8859-1"), "UTF-8");
			leaveInfo.setReason(reason);
			String memo = new String(request.getParameter("memo").getBytes("iso-8859-1"), "UTF-8");
			leaveInfo.setMemo(memo);
			int state = Integer.parseInt(request.getParameter("state"));
			leaveInfo.setState(state);

			/* ����ҵ���ִ�и��²��� */
			String result = leaveInfoDAO.UpdateLeaveInfo(leaveInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
