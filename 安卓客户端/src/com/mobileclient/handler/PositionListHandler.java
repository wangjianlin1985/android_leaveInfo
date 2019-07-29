package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Position;
public class PositionListHandler extends DefaultHandler {
	private List<Position> positionList = null;
	private Position position;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (position != null) { 
            String valueString = new String(ch, start, length); 
            if ("positionId".equals(tempString)) 
            	position.setPositionId(new Integer(valueString).intValue());
            else if ("positionName".equals(tempString)) 
            	position.setPositionName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Position".equals(localName)&&position!=null){
			positionList.add(position);
			position = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		positionList = new ArrayList<Position>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Position".equals(localName)) {
            position = new Position(); 
        }
        tempString = localName; 
	}

	public List<Position> getPositionList() {
		return this.positionList;
	}
}
