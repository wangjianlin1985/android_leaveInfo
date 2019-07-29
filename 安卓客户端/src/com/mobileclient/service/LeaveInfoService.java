package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.LeaveInfo;
import com.mobileclient.util.HttpUtil;

/*请假信息管理业务逻辑层*/
public class LeaveInfoService {
	/* 添加请假信息 */
	public String AddLeaveInfo(LeaveInfo leaveInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("leaveId", leaveInfo.getLeaveId() + "");
		params.put("writeTime", leaveInfo.getWriteTime());
		params.put("departmentObj", leaveInfo.getDepartmentObj() + "");
		params.put("userObj", leaveInfo.getUserObj());
		params.put("positionObj", leaveInfo.getPositionObj() + "");
		params.put("leaveTypeObj", leaveInfo.getLeaveTypeObj() + "");
		params.put("sfcj", leaveInfo.getSfcj());
		params.put("startDate", leaveInfo.getStartDate().toString());
		params.put("startDayTimeType", leaveInfo.getStartDayTimeType() + "");
		params.put("endDate", leaveInfo.getEndDate().toString());
		params.put("endDayTimeType", leaveInfo.getEndDayTimeType() + "");
		params.put("leaveDays", leaveInfo.getLeaveDays() + "");
		params.put("writeUserObj", leaveInfo.getWriteUserObj());
		params.put("place", leaveInfo.getPlace());
		params.put("reason", leaveInfo.getReason());
		params.put("memo", leaveInfo.getMemo());
		params.put("state", leaveInfo.getState() + "");
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LeaveInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询请假信息 */
	public List<LeaveInfo> QueryLeaveInfo(LeaveInfo queryConditionLeaveInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "LeaveInfoServlet?action=query";
		if(queryConditionLeaveInfo != null) {
			urlString += "&writeTime=" + URLEncoder.encode(queryConditionLeaveInfo.getWriteTime(), "UTF-8") + "";
			urlString += "&departmentObj=" + queryConditionLeaveInfo.getDepartmentObj();
			urlString += "&userObj=" + URLEncoder.encode(queryConditionLeaveInfo.getUserObj(), "UTF-8") + "";
			urlString += "&positionObj=" + queryConditionLeaveInfo.getPositionObj();
			urlString += "&leaveTypeObj=" + queryConditionLeaveInfo.getLeaveTypeObj();
			if(queryConditionLeaveInfo.getStartDate() != null) {
				urlString += "&startDate=" + URLEncoder.encode(queryConditionLeaveInfo.getStartDate().toString(), "UTF-8");
			}
			urlString += "&startDayTimeType=" + queryConditionLeaveInfo.getStartDayTimeType();
			if(queryConditionLeaveInfo.getEndDate() != null) {
				urlString += "&endDate=" + URLEncoder.encode(queryConditionLeaveInfo.getEndDate().toString(), "UTF-8");
			}
			urlString += "&endDayTimeType=" + queryConditionLeaveInfo.getEndDayTimeType();
			urlString += "&place=" + URLEncoder.encode(queryConditionLeaveInfo.getPlace(), "UTF-8") + "";
			urlString += "&reason=" + URLEncoder.encode(queryConditionLeaveInfo.getReason(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		LeaveInfoListHandler leaveInfoListHander = new LeaveInfoListHandler();
		xr.setContentHandler(leaveInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<LeaveInfo> leaveInfoList = leaveInfoListHander.getLeaveInfoList();
		return leaveInfoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<LeaveInfo> leaveInfoList = new ArrayList<LeaveInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				LeaveInfo leaveInfo = new LeaveInfo();
				leaveInfo.setLeaveId(object.getInt("leaveId"));
				leaveInfo.setWriteTime(object.getString("writeTime"));
				leaveInfo.setDepartmentObj(object.getInt("departmentObj"));
				leaveInfo.setUserObj(object.getString("userObj"));
				leaveInfo.setPositionObj(object.getInt("positionObj"));
				leaveInfo.setLeaveTypeObj(object.getInt("leaveTypeObj"));
				leaveInfo.setSfcj(object.getString("sfcj"));
				leaveInfo.setStartDate(Timestamp.valueOf(object.getString("startDate")));
				leaveInfo.setStartDayTimeType(object.getInt("startDayTimeType"));
				leaveInfo.setEndDate(Timestamp.valueOf(object.getString("endDate")));
				leaveInfo.setEndDayTimeType(object.getInt("endDayTimeType"));
				leaveInfo.setLeaveDays((float) object.getDouble("leaveDays"));
				leaveInfo.setWriteUserObj(object.getString("writeUserObj"));
				leaveInfo.setPlace(object.getString("place"));
				leaveInfo.setReason(object.getString("reason"));
				leaveInfo.setMemo(object.getString("memo"));
				leaveInfo.setState(object.getInt("state"));
				leaveInfoList.add(leaveInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return leaveInfoList;
	}

	/* 更新请假信息 */
	public String UpdateLeaveInfo(LeaveInfo leaveInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("leaveId", leaveInfo.getLeaveId() + "");
		params.put("writeTime", leaveInfo.getWriteTime());
		params.put("departmentObj", leaveInfo.getDepartmentObj() + "");
		params.put("userObj", leaveInfo.getUserObj());
		params.put("positionObj", leaveInfo.getPositionObj() + "");
		params.put("leaveTypeObj", leaveInfo.getLeaveTypeObj() + "");
		params.put("sfcj", leaveInfo.getSfcj());
		params.put("startDate", leaveInfo.getStartDate().toString());
		params.put("startDayTimeType", leaveInfo.getStartDayTimeType() + "");
		params.put("endDate", leaveInfo.getEndDate().toString());
		params.put("endDayTimeType", leaveInfo.getEndDayTimeType() + "");
		params.put("leaveDays", leaveInfo.getLeaveDays() + "");
		params.put("writeUserObj", leaveInfo.getWriteUserObj());
		params.put("place", leaveInfo.getPlace());
		params.put("reason", leaveInfo.getReason());
		params.put("memo", leaveInfo.getMemo());
		params.put("state", leaveInfo.getState() + "");
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LeaveInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除请假信息 */
	public String DeleteLeaveInfo(int leaveId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("leaveId", leaveId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LeaveInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "请假信息信息删除失败!";
		}
	}

	/* 根据请假记录ID获取请假信息对象 */
	public LeaveInfo GetLeaveInfo(int leaveId)  {
		List<LeaveInfo> leaveInfoList = new ArrayList<LeaveInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("leaveId", leaveId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LeaveInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				LeaveInfo leaveInfo = new LeaveInfo();
				leaveInfo.setLeaveId(object.getInt("leaveId"));
				leaveInfo.setWriteTime(object.getString("writeTime"));
				leaveInfo.setDepartmentObj(object.getInt("departmentObj"));
				leaveInfo.setUserObj(object.getString("userObj"));
				leaveInfo.setPositionObj(object.getInt("positionObj"));
				leaveInfo.setLeaveTypeObj(object.getInt("leaveTypeObj"));
				leaveInfo.setSfcj(object.getString("sfcj"));
				leaveInfo.setStartDate(Timestamp.valueOf(object.getString("startDate")));
				leaveInfo.setStartDayTimeType(object.getInt("startDayTimeType"));
				leaveInfo.setEndDate(Timestamp.valueOf(object.getString("endDate")));
				leaveInfo.setEndDayTimeType(object.getInt("endDayTimeType"));
				leaveInfo.setLeaveDays((float) object.getDouble("leaveDays"));
				leaveInfo.setWriteUserObj(object.getString("writeUserObj"));
				leaveInfo.setPlace(object.getString("place"));
				leaveInfo.setReason(object.getString("reason"));
				leaveInfo.setMemo(object.getString("memo"));
				leaveInfo.setState(object.getInt("state"));
				leaveInfoList.add(leaveInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = leaveInfoList.size();
		if(size>0) return leaveInfoList.get(0); 
		else return null; 
	}
}
