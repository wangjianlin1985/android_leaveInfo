package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.Department;
import com.chengxusheji.domain.Position;
import com.chengxusheji.domain.Note;

@Service @Transactional
public class NoteDAO {

	@Resource SessionFactory factory;
    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
    public void AddNote(Note note) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(note);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Note> QueryNoteInfo(String noteName,Department departmentObj,Position positionObj,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Note note where 1=1";
    	if(!noteName.equals("")) hql = hql + " and note.noteName like '%" + noteName + "%'";
    	if(null != departmentObj && departmentObj.getDepartmentId()!=0) hql += " and note.departmentObj.departmentId=" + departmentObj.getDepartmentId();
    	if(null != positionObj && positionObj.getPositionId()!=0) hql += " and note.positionObj.positionId=" + positionObj.getPositionId();
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List noteList = q.list();
    	return (ArrayList<Note>) noteList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Note> QueryNoteInfo(String noteName,Department departmentObj,Position positionObj) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Note note where 1=1";
    	if(!noteName.equals("")) hql = hql + " and note.noteName like '%" + noteName + "%'";
    	if(null != departmentObj && departmentObj.getDepartmentId()!=0) hql += " and note.departmentObj.departmentId=" + departmentObj.getDepartmentId();
    	if(null != positionObj && positionObj.getPositionId()!=0) hql += " and note.positionObj.positionId=" + positionObj.getPositionId();
    	Query q = s.createQuery(hql);
    	List noteList = q.list();
    	return (ArrayList<Note>) noteList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Note> QueryAllNoteInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Note";
        Query q = s.createQuery(hql);
        List noteList = q.list();
        return (ArrayList<Note>) noteList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String noteName,Department departmentObj,Position positionObj) {
        Session s = factory.getCurrentSession();
        String hql = "From Note note where 1=1";
        if(!noteName.equals("")) hql = hql + " and note.noteName like '%" + noteName + "%'";
        if(null != departmentObj && departmentObj.getDepartmentId()!=0) hql += " and note.departmentObj.departmentId=" + departmentObj.getDepartmentId();
        if(null != positionObj && positionObj.getPositionId()!=0) hql += " and note.positionObj.positionId=" + positionObj.getPositionId();
        Query q = s.createQuery(hql);
        List noteList = q.list();
        recordNumber = noteList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Note GetNoteByNoteId(int noteId) {
        Session s = factory.getCurrentSession();
        Note note = (Note)s.get(Note.class, noteId);
        return note;
    }

    /*����Note��Ϣ*/
    public void UpdateNote(Note note) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(note);
    }

    /*ɾ��Note��Ϣ*/
    public void DeleteNote (int noteId) throws Exception {
        Session s = factory.getCurrentSession();
        Object note = s.load(Note.class, noteId);
        s.delete(note);
    }

}
