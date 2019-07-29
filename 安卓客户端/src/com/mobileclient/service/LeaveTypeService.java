package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.LeaveType;
import com.mobileclient.util.HttpUtil;

/*请假类型管理业务逻辑层*/
public class LeaveTypeService {
	/* 添加请假类型 */
	public String AddLeaveType(LeaveType leaveType) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("leaveTypeId", leaveType.getLeaveTypeId() + "");
		params.put("leaveTypeName", leaveType.getLeaveTypeName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LeaveTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询请假类型 */
	public List<LeaveType> QueryLeaveType(LeaveType queryConditionLeaveType) throws Exception {
		String urlString = HttpUtil.BASE_URL + "LeaveTypeServlet?action=query";
		if(queryConditionLeaveType != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		LeaveTypeListHandler leaveTypeListHander = new LeaveTypeListHandler();
		xr.setContentHandler(leaveTypeListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<LeaveType> leaveTypeList = leaveTypeListHander.getLeaveTypeList();
		return leaveTypeList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<LeaveType> leaveTypeList = new ArrayList<LeaveType>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				LeaveType leaveType = new LeaveType();
				leaveType.setLeaveTypeId(object.getInt("leaveTypeId"));
				leaveType.setLeaveTypeName(object.getString("leaveTypeName"));
				leaveTypeList.add(leaveType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return leaveTypeList;
	}

	/* 更新请假类型 */
	public String UpdateLeaveType(LeaveType leaveType) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("leaveTypeId", leaveType.getLeaveTypeId() + "");
		params.put("leaveTypeName", leaveType.getLeaveTypeName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LeaveTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除请假类型 */
	public String DeleteLeaveType(int leaveTypeId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("leaveTypeId", leaveTypeId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LeaveTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "请假类型信息删除失败!";
		}
	}

	/* 根据请假类型ID获取请假类型对象 */
	public LeaveType GetLeaveType(int leaveTypeId)  {
		List<LeaveType> leaveTypeList = new ArrayList<LeaveType>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("leaveTypeId", leaveTypeId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LeaveTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				LeaveType leaveType = new LeaveType();
				leaveType.setLeaveTypeId(object.getInt("leaveTypeId"));
				leaveType.setLeaveTypeName(object.getString("leaveTypeName"));
				leaveTypeList.add(leaveType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = leaveTypeList.size();
		if(size>0) return leaveTypeList.get(0); 
		else return null; 
	}
}
