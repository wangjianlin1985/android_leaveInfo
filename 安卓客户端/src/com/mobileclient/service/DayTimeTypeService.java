package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.DayTimeType;
import com.mobileclient.util.HttpUtil;

/*时间段类型管理业务逻辑层*/
public class DayTimeTypeService {
	/* 添加时间段类型 */
	public String AddDayTimeType(DayTimeType dayTimeType) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("dayTimeTypeId", dayTimeType.getDayTimeTypeId() + "");
		params.put("dayTimeTypeName", dayTimeType.getDayTimeTypeName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DayTimeTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询时间段类型 */
	public List<DayTimeType> QueryDayTimeType(DayTimeType queryConditionDayTimeType) throws Exception {
		String urlString = HttpUtil.BASE_URL + "DayTimeTypeServlet?action=query";
		if(queryConditionDayTimeType != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		DayTimeTypeListHandler dayTimeTypeListHander = new DayTimeTypeListHandler();
		xr.setContentHandler(dayTimeTypeListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<DayTimeType> dayTimeTypeList = dayTimeTypeListHander.getDayTimeTypeList();
		return dayTimeTypeList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<DayTimeType> dayTimeTypeList = new ArrayList<DayTimeType>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				DayTimeType dayTimeType = new DayTimeType();
				dayTimeType.setDayTimeTypeId(object.getInt("dayTimeTypeId"));
				dayTimeType.setDayTimeTypeName(object.getString("dayTimeTypeName"));
				dayTimeTypeList.add(dayTimeType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dayTimeTypeList;
	}

	/* 更新时间段类型 */
	public String UpdateDayTimeType(DayTimeType dayTimeType) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("dayTimeTypeId", dayTimeType.getDayTimeTypeId() + "");
		params.put("dayTimeTypeName", dayTimeType.getDayTimeTypeName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DayTimeTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除时间段类型 */
	public String DeleteDayTimeType(int dayTimeTypeId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("dayTimeTypeId", dayTimeTypeId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DayTimeTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "时间段类型信息删除失败!";
		}
	}

	/* 根据记录编号获取时间段类型对象 */
	public DayTimeType GetDayTimeType(int dayTimeTypeId)  {
		List<DayTimeType> dayTimeTypeList = new ArrayList<DayTimeType>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("dayTimeTypeId", dayTimeTypeId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DayTimeTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				DayTimeType dayTimeType = new DayTimeType();
				dayTimeType.setDayTimeTypeId(object.getInt("dayTimeTypeId"));
				dayTimeType.setDayTimeTypeName(object.getString("dayTimeTypeName"));
				dayTimeTypeList.add(dayTimeType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = dayTimeTypeList.size();
		if(size>0) return dayTimeTypeList.get(0); 
		else return null; 
	}
}
