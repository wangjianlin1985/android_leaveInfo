package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.UserInfoDAO;
import com.mobileserver.domain.UserInfo;

import org.json.JSONStringer;

public class UserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*�����û���Ϣҵ������*/
	private UserInfoDAO userInfoDAO = new UserInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public UserInfoServlet() {
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
			/*��ȡ��ѯ�û���Ϣ�Ĳ�����Ϣ*/
			String user_name = request.getParameter("user_name");
			user_name = user_name == null ? "" : new String(request.getParameter(
					"user_name").getBytes("iso-8859-1"), "UTF-8");
			String name = request.getParameter("name");
			name = name == null ? "" : new String(request.getParameter(
					"name").getBytes("iso-8859-1"), "UTF-8");
			Timestamp birthday = null;
			if (request.getParameter("birthday") != null)
				birthday = Timestamp.valueOf(request.getParameter("birthday"));
			int departmentObj = 0;
			if (request.getParameter("departmentObj") != null)
				departmentObj = Integer.parseInt(request.getParameter("departmentObj"));
			String mobileNumber = request.getParameter("mobileNumber");
			mobileNumber = mobileNumber == null ? "" : new String(request.getParameter(
					"mobileNumber").getBytes("iso-8859-1"), "UTF-8");
			int positionName = 0;
			if (request.getParameter("positionName") != null)
				positionName = Integer.parseInt(request.getParameter("positionName"));

			/*����ҵ���߼���ִ���û���Ϣ��ѯ*/
			List<UserInfo> userInfoList = userInfoDAO.QueryUserInfo(user_name,name,birthday,departmentObj,mobileNumber,positionName);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<UserInfos>").append("\r\n");
			for (int i = 0; i < userInfoList.size(); i++) {
				sb.append("	<UserInfo>").append("\r\n")
				.append("		<user_name>")
				.append(userInfoList.get(i).getUser_name())
				.append("</user_name>").append("\r\n")
				.append("		<password>")
				.append(userInfoList.get(i).getPassword())
				.append("</password>").append("\r\n")
				.append("		<name>")
				.append(userInfoList.get(i).getName())
				.append("</name>").append("\r\n")
				.append("		<sex>")
				.append(userInfoList.get(i).getSex())
				.append("</sex>").append("\r\n")
				.append("		<birthday>")
				.append(userInfoList.get(i).getBirthday())
				.append("</birthday>").append("\r\n")
				.append("		<departmentObj>")
				.append(userInfoList.get(i).getDepartmentObj())
				.append("</departmentObj>").append("\r\n")
				.append("		<mobileNumber>")
				.append(userInfoList.get(i).getMobileNumber())
				.append("</mobileNumber>").append("\r\n")
				.append("		<positionName>")
				.append(userInfoList.get(i).getPositionName())
				.append("</positionName>").append("\r\n")
				.append("	</UserInfo>").append("\r\n");
			}
			sb.append("</UserInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(UserInfo userInfo: userInfoList) {
				  stringer.object();
			  stringer.key("user_name").value(userInfo.getUser_name());
			  stringer.key("password").value(userInfo.getPassword());
			  stringer.key("name").value(userInfo.getName());
			  stringer.key("sex").value(userInfo.getSex());
			  stringer.key("birthday").value(userInfo.getBirthday());
			  stringer.key("departmentObj").value(userInfo.getDepartmentObj());
			  stringer.key("mobileNumber").value(userInfo.getMobileNumber());
			  stringer.key("positionName").value(userInfo.getPositionName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ����û���Ϣ����ȡ�û���Ϣ�������������浽�½����û���Ϣ���� */ 
			UserInfo userInfo = new UserInfo();
			String user_name = new String(request.getParameter("user_name").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setUser_name(user_name);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setPassword(password);
			String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setName(name);
			String sex = new String(request.getParameter("sex").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setSex(sex);
			Timestamp birthday = Timestamp.valueOf(request.getParameter("birthday"));
			userInfo.setBirthday(birthday);
			int departmentObj = Integer.parseInt(request.getParameter("departmentObj"));
			userInfo.setDepartmentObj(departmentObj);
			String mobileNumber = new String(request.getParameter("mobileNumber").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setMobileNumber(mobileNumber);
			int positionName = Integer.parseInt(request.getParameter("positionName"));
			userInfo.setPositionName(positionName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = userInfoDAO.AddUserInfo(userInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ���û���Ϣ����ȡ�û���Ϣ���û���*/
			String user_name = new String(request.getParameter("user_name").getBytes("iso-8859-1"), "UTF-8");
			/*����ҵ���߼���ִ��ɾ������*/
			String result = userInfoDAO.DeleteUserInfo(user_name);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*�����û���Ϣ֮ǰ�ȸ���user_name��ѯĳ���û���Ϣ*/
			String user_name = new String(request.getParameter("user_name").getBytes("iso-8859-1"), "UTF-8");
			UserInfo userInfo = userInfoDAO.GetUserInfo(user_name);

			// �ͻ��˲�ѯ���û���Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("user_name").value(userInfo.getUser_name());
			  stringer.key("password").value(userInfo.getPassword());
			  stringer.key("name").value(userInfo.getName());
			  stringer.key("sex").value(userInfo.getSex());
			  stringer.key("birthday").value(userInfo.getBirthday());
			  stringer.key("departmentObj").value(userInfo.getDepartmentObj());
			  stringer.key("mobileNumber").value(userInfo.getMobileNumber());
			  stringer.key("positionName").value(userInfo.getPositionName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* �����û���Ϣ����ȡ�û���Ϣ�������������浽�½����û���Ϣ���� */ 
			UserInfo userInfo = new UserInfo();
			String user_name = new String(request.getParameter("user_name").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setUser_name(user_name);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setPassword(password);
			String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setName(name);
			String sex = new String(request.getParameter("sex").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setSex(sex);
			Timestamp birthday = Timestamp.valueOf(request.getParameter("birthday"));
			userInfo.setBirthday(birthday);
			int departmentObj = Integer.parseInt(request.getParameter("departmentObj"));
			userInfo.setDepartmentObj(departmentObj);
			String mobileNumber = new String(request.getParameter("mobileNumber").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setMobileNumber(mobileNumber);
			int positionName = Integer.parseInt(request.getParameter("positionName"));
			userInfo.setPositionName(positionName);

			/* ����ҵ���ִ�и��²��� */
			String result = userInfoDAO.UpdateUserInfo(userInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
