package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Position;
import com.mobileclient.util.HttpUtil;

/*职级信息管理业务逻辑层*/
public class PositionService {
	/* 添加职级信息 */
	public String AddPosition(Position position) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("positionId", position.getPositionId() + "");
		params.put("positionName", position.getPositionName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PositionServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询职级信息 */
	public List<Position> QueryPosition(Position queryConditionPosition) throws Exception {
		String urlString = HttpUtil.BASE_URL + "PositionServlet?action=query";
		if(queryConditionPosition != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		PositionListHandler positionListHander = new PositionListHandler();
		xr.setContentHandler(positionListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Position> positionList = positionListHander.getPositionList();
		return positionList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Position> positionList = new ArrayList<Position>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Position position = new Position();
				position.setPositionId(object.getInt("positionId"));
				position.setPositionName(object.getString("positionName"));
				positionList.add(position);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return positionList;
	}

	/* 更新职级信息 */
	public String UpdatePosition(Position position) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("positionId", position.getPositionId() + "");
		params.put("positionName", position.getPositionName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PositionServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除职级信息 */
	public String DeletePosition(int positionId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("positionId", positionId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PositionServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "职级信息信息删除失败!";
		}
	}

	/* 根据职级ID获取职级信息对象 */
	public Position GetPosition(int positionId)  {
		List<Position> positionList = new ArrayList<Position>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("positionId", positionId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PositionServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Position position = new Position();
				position.setPositionId(object.getInt("positionId"));
				position.setPositionName(object.getString("positionName"));
				positionList.add(position);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = positionList.size();
		if(size>0) return positionList.get(0); 
		else return null; 
	}
}
