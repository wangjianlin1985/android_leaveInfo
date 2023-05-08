package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.LeaveType;
public class LeaveTypeListHandler extends DefaultHandler {
	private List<LeaveType> leaveTypeList = null;
	private LeaveType leaveType;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (leaveType != null) { 
            String valueString = new String(ch, start, length); 
            if ("leaveTypeId".equals(tempString)) 
            	leaveType.setLeaveTypeId(new Integer(valueString).intValue());
            else if ("leaveTypeName".equals(tempString)) 
            	leaveType.setLeaveTypeName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("LeaveType".equals(localName)&&leaveType!=null){
			leaveTypeList.add(leaveType);
			leaveType = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		leaveTypeList = new ArrayList<LeaveType>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("LeaveType".equals(localName)) {
            leaveType = new LeaveType(); 
        }
        tempString = localName; 
	}

	public List<LeaveType> getLeaveTypeList() {
		return this.leaveTypeList;
	}
}
