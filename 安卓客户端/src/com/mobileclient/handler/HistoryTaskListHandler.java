package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.HistoryTask;
public class HistoryTaskListHandler extends DefaultHandler {
	private List<HistoryTask> historyTaskList = null;
	private HistoryTask historyTask;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (historyTask != null) { 
            String valueString = new String(ch, start, length); 
            if ("historyTaskId".equals(tempString)) 
            	historyTask.setHistoryTaskId(new Integer(valueString).intValue());
            else if ("leaveObj".equals(tempString)) 
            	historyTask.setLeaveObj(new Integer(valueString).intValue());
            else if ("noteObj".equals(tempString)) 
            	historyTask.setNoteObj(new Integer(valueString).intValue());
            else if ("checkSuggest".equals(tempString)) 
            	historyTask.setCheckSuggest(valueString); 
            else if ("userObj".equals(tempString)) 
            	historyTask.setUserObj(valueString); 
            else if ("taskTime".equals(tempString)) 
            	historyTask.setTaskTime(valueString); 
            else if ("checkState".equals(tempString)) 
            	historyTask.setCheckState(new Integer(valueString).intValue());
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("HistoryTask".equals(localName)&&historyTask!=null){
			historyTaskList.add(historyTask);
			historyTask = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		historyTaskList = new ArrayList<HistoryTask>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("HistoryTask".equals(localName)) {
            historyTask = new HistoryTask(); 
        }
        tempString = localName; 
	}

	public List<HistoryTask> getHistoryTaskList() {
		return this.historyTaskList;
	}
}
