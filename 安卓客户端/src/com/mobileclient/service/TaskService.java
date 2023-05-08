package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Task;
import com.mobileclient.util.HttpUtil;

/*任务信息管理业务逻辑层*/
public class TaskService {
	/* 添加任务信息 */
	public String AddTask(Task task) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("taskId", task.getTaskId() + "");
		params.put("leaveInfoObj", task.getLeaveInfoObj() + "");
		params.put("noteObj", task.getNoteObj() + "");
		params.put("state", task.getState() + "");
		params.put("userObj", task.getUserObj());
		params.put("taskTime", task.getTaskTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TaskServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询任务信息 */
	public List<Task> QueryTask(Task queryConditionTask) throws Exception {
		String urlString = HttpUtil.BASE_URL + "TaskServlet?action=query";
		if(queryConditionTask != null) {
			urlString += "&leaveInfoObj=" + queryConditionTask.getLeaveInfoObj();
			urlString += "&noteObj=" + queryConditionTask.getNoteObj();
			urlString += "&userObj=" + URLEncoder.encode(queryConditionTask.getUserObj(), "UTF-8") + "";
			urlString += "&taskTime=" + URLEncoder.encode(queryConditionTask.getTaskTime(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		TaskListHandler taskListHander = new TaskListHandler();
		xr.setContentHandler(taskListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Task> taskList = taskListHander.getTaskList();
		return taskList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Task> taskList = new ArrayList<Task>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Task task = new Task();
				task.setTaskId(object.getInt("taskId"));
				task.setLeaveInfoObj(object.getInt("leaveInfoObj"));
				task.setNoteObj(object.getInt("noteObj"));
				task.setState(object.getInt("state"));
				task.setUserObj(object.getString("userObj"));
				task.setTaskTime(object.getString("taskTime"));
				taskList.add(task);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return taskList;
	}

	/* 更新任务信息 */
	public String UpdateTask(Task task) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("taskId", task.getTaskId() + "");
		params.put("leaveInfoObj", task.getLeaveInfoObj() + "");
		params.put("noteObj", task.getNoteObj() + "");
		params.put("state", task.getState() + "");
		params.put("userObj", task.getUserObj());
		params.put("taskTime", task.getTaskTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TaskServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除任务信息 */
	public String DeleteTask(int taskId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("taskId", taskId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TaskServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "任务信息信息删除失败!";
		}
	}

	/* 根据任务ID获取任务信息对象 */
	public Task GetTask(int taskId)  {
		List<Task> taskList = new ArrayList<Task>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("taskId", taskId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TaskServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Task task = new Task();
				task.setTaskId(object.getInt("taskId"));
				task.setLeaveInfoObj(object.getInt("leaveInfoObj"));
				task.setNoteObj(object.getInt("noteObj"));
				task.setState(object.getInt("state"));
				task.setUserObj(object.getString("userObj"));
				task.setTaskTime(object.getString("taskTime"));
				taskList.add(task);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = taskList.size();
		if(size>0) return taskList.get(0); 
		else return null; 
	}
}
