package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Note;
public class NoteListHandler extends DefaultHandler {
	private List<Note> noteList = null;
	private Note note;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (note != null) { 
            String valueString = new String(ch, start, length); 
            if ("noteId".equals(tempString)) 
            	note.setNoteId(new Integer(valueString).intValue());
            else if ("noteName".equals(tempString)) 
            	note.setNoteName(valueString); 
            else if ("departmentObj".equals(tempString)) 
            	note.setDepartmentObj(new Integer(valueString).intValue());
            else if ("positionObj".equals(tempString)) 
            	note.setPositionObj(new Integer(valueString).intValue());
            else if ("noteIndex".equals(tempString)) 
            	note.setNoteIndex(new Integer(valueString).intValue());
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Note".equals(localName)&&note!=null){
			noteList.add(note);
			note = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		noteList = new ArrayList<Note>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Note".equals(localName)) {
            note = new Note(); 
        }
        tempString = localName; 
	}

	public List<Note> getNoteList() {
		return this.noteList;
	}
}
