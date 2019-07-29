package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.DayTimeType;
public class DayTimeTypeListHandler extends DefaultHandler {
	private List<DayTimeType> dayTimeTypeList = null;
	private DayTimeType dayTimeType;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (dayTimeType != null) { 
            String valueString = new String(ch, start, length); 
            if ("dayTimeTypeId".equals(tempString)) 
            	dayTimeType.setDayTimeTypeId(new Integer(valueString).intValue());
            else if ("dayTimeTypeName".equals(tempString)) 
            	dayTimeType.setDayTimeTypeName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("DayTimeType".equals(localName)&&dayTimeType!=null){
			dayTimeTypeList.add(dayTimeType);
			dayTimeType = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		dayTimeTypeList = new ArrayList<DayTimeType>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("DayTimeType".equals(localName)) {
            dayTimeType = new DayTimeType(); 
        }
        tempString = localName; 
	}

	public List<DayTimeType> getDayTimeTypeList() {
		return this.dayTimeTypeList;
	}
}
