package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.HistoryTask;
import com.mobileclient.util.HttpUtil;

/*历史任务管理业务逻辑层*/
public class HistoryTaskService {
	/* 添加历史任务 */
	public String AddHistoryTask(HistoryTask historyTask) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("historyTaskId", historyTask.getHistoryTaskId() + "");
		params.put("leaveObj", historyTask.getLeaveObj() + "");
		params.put("noteObj", historyTask.getNoteObj() + "");
		params.put("checkSuggest", historyTask.getCheckSuggest());
		params.put("userObj", historyTask.getUserObj());
		params.put("taskTime", historyTask.getTaskTime());
		params.put("checkState", historyTask.getCheckState() + "");
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HistoryTaskServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询历史任务 */
	public List<HistoryTask> QueryHistoryTask(HistoryTask queryConditionHistoryTask) throws Exception {
		String urlString = HttpUtil.BASE_URL + "HistoryTaskServlet?action=query";
		if(queryConditionHistoryTask != null) {
			urlString += "&leaveObj=" + queryConditionHistoryTask.getLeaveObj();
			urlString += "&noteObj=" + queryConditionHistoryTask.getNoteObj();
			urlString += "&userObj=" + URLEncoder.encode(queryConditionHistoryTask.getUserObj(), "UTF-8") + "";
			urlString += "&taskTime=" + URLEncoder.encode(queryConditionHistoryTask.getTaskTime(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		HistoryTaskListHandler historyTaskListHander = new HistoryTaskListHandler();
		xr.setContentHandler(historyTaskListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<HistoryTask> historyTaskList = historyTaskListHander.getHistoryTaskList();
		return historyTaskList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<HistoryTask> historyTaskList = new ArrayList<HistoryTask>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				HistoryTask historyTask = new HistoryTask();
				historyTask.setHistoryTaskId(object.getInt("historyTaskId"));
				historyTask.setLeaveObj(object.getInt("leaveObj"));
				historyTask.setNoteObj(object.getInt("noteObj"));
				historyTask.setCheckSuggest(object.getString("checkSuggest"));
				historyTask.setUserObj(object.getString("userObj"));
				historyTask.setTaskTime(object.getString("taskTime"));
				historyTask.setCheckState(object.getInt("checkState"));
				historyTaskList.add(historyTask);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return historyTaskList;
	}

	/* 更新历史任务 */
	public String UpdateHistoryTask(HistoryTask historyTask) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("historyTaskId", historyTask.getHistoryTaskId() + "");
		params.put("leaveObj", historyTask.getLeaveObj() + "");
		params.put("noteObj", historyTask.getNoteObj() + "");
		params.put("checkSuggest", historyTask.getCheckSuggest());
		params.put("userObj", historyTask.getUserObj());
		params.put("taskTime", historyTask.getTaskTime());
		params.put("checkState", historyTask.getCheckState() + "");
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HistoryTaskServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除历史任务 */
	public String DeleteHistoryTask(int historyTaskId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("historyTaskId", historyTaskId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HistoryTaskServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "历史任务信息删除失败!";
		}
	}

	/* 根据任务历史记录Id获取历史任务对象 */
	public HistoryTask GetHistoryTask(int historyTaskId)  {
		List<HistoryTask> historyTaskList = new ArrayList<HistoryTask>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("historyTaskId", historyTaskId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HistoryTaskServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				HistoryTask historyTask = new HistoryTask();
				historyTask.setHistoryTaskId(object.getInt("historyTaskId"));
				historyTask.setLeaveObj(object.getInt("leaveObj"));
				historyTask.setNoteObj(object.getInt("noteObj"));
				historyTask.setCheckSuggest(object.getString("checkSuggest"));
				historyTask.setUserObj(object.getString("userObj"));
				historyTask.setTaskTime(object.getString("taskTime"));
				historyTask.setCheckState(object.getInt("checkState"));
				historyTaskList.add(historyTask);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = historyTaskList.size();
		if(size>0) return historyTaskList.get(0); 
		else return null; 
	}
}
