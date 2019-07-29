package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.LeaveInfo;
public class LeaveInfoListHandler extends DefaultHandler {
	private List<LeaveInfo> leaveInfoList = null;
	private LeaveInfo leaveInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (leaveInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("leaveId".equals(tempString)) 
            	leaveInfo.setLeaveId(new Integer(valueString).intValue());
            else if ("writeTime".equals(tempString)) 
            	leaveInfo.setWriteTime(valueString); 
            else if ("departmentObj".equals(tempString)) 
            	leaveInfo.setDepartmentObj(new Integer(valueString).intValue());
            else if ("userObj".equals(tempString)) 
            	leaveInfo.setUserObj(valueString); 
            else if ("positionObj".equals(tempString)) 
            	leaveInfo.setPositionObj(new Integer(valueString).intValue());
            else if ("leaveTypeObj".equals(tempString)) 
            	leaveInfo.setLeaveTypeObj(new Integer(valueString).intValue());
            else if ("sfcj".equals(tempString)) 
            	leaveInfo.setSfcj(valueString); 
            else if ("startDate".equals(tempString)) 
            	leaveInfo.setStartDate(Timestamp.valueOf(valueString));
            else if ("startDayTimeType".equals(tempString)) 
            	leaveInfo.setStartDayTimeType(new Integer(valueString).intValue());
            else if ("endDate".equals(tempString)) 
            	leaveInfo.setEndDate(Timestamp.valueOf(valueString));
            else if ("endDayTimeType".equals(tempString)) 
            	leaveInfo.setEndDayTimeType(new Integer(valueString).intValue());
            else if ("leaveDays".equals(tempString)) 
            	leaveInfo.setLeaveDays(new Float(valueString).floatValue());
            else if ("writeUserObj".equals(tempString)) 
            	leaveInfo.setWriteUserObj(valueString); 
            else if ("place".equals(tempString)) 
            	leaveInfo.setPlace(valueString); 
            else if ("reason".equals(tempString)) 
            	leaveInfo.setReason(valueString); 
            else if ("memo".equals(tempString)) 
            	leaveInfo.setMemo(valueString); 
            else if ("state".equals(tempString)) 
            	leaveInfo.setState(new Integer(valueString).intValue());
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("LeaveInfo".equals(localName)&&leaveInfo!=null){
			leaveInfoList.add(leaveInfo);
			leaveInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		leaveInfoList = new ArrayList<LeaveInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("LeaveInfo".equals(localName)) {
            leaveInfo = new LeaveInfo(); 
        }
        tempString = localName; 
	}

	public List<LeaveInfo> getLeaveInfoList() {
		return this.leaveInfoList;
	}
}
