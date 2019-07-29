package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Task;
public class TaskListHandler extends DefaultHandler {
	private List<Task> taskList = null;
	private Task task;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (task != null) { 
            String valueString = new String(ch, start, length); 
            if ("taskId".equals(tempString)) 
            	task.setTaskId(new Integer(valueString).intValue());
            else if ("leaveInfoObj".equals(tempString)) 
            	task.setLeaveInfoObj(new Integer(valueString).intValue());
            else if ("noteObj".equals(tempString)) 
            	task.setNoteObj(new Integer(valueString).intValue());
            else if ("state".equals(tempString)) 
            	task.setState(new Integer(valueString).intValue());
            else if ("userObj".equals(tempString)) 
            	task.setUserObj(valueString); 
            else if ("taskTime".equals(tempString)) 
            	task.setTaskTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Task".equals(localName)&&task!=null){
			taskList.add(task);
			task = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		taskList = new ArrayList<Task>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Task".equals(localName)) {
            task = new Task(); 
        }
        tempString = localName; 
	}

	public List<Task> getTaskList() {
		return this.taskList;
	}
}
