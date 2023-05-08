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
import com.chengxusheji.domain.LeaveInfo;
import com.chengxusheji.domain.Note;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.domain.HistoryTask;

@Service @Transactional
public class HistoryTaskDAO {

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
    public void AddHistoryTask(HistoryTask historyTask) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(historyTask);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<HistoryTask> QueryHistoryTaskInfo(LeaveInfo leaveObj,Note noteObj,UserInfo userObj,String taskTime,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From HistoryTask historyTask where 1=1";
    	if(null != leaveObj && leaveObj.getLeaveId()!=0) hql += " and historyTask.leaveObj.leaveId=" + leaveObj.getLeaveId();
    	if(null != noteObj && noteObj.getNoteId()!=0) hql += " and historyTask.noteObj.noteId=" + noteObj.getNoteId();
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and historyTask.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!taskTime.equals("")) hql = hql + " and historyTask.taskTime like '%" + taskTime + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List historyTaskList = q.list();
    	return (ArrayList<HistoryTask>) historyTaskList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<HistoryTask> QueryHistoryTaskInfo(LeaveInfo leaveObj,Note noteObj,UserInfo userObj,String taskTime) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From HistoryTask historyTask where 1=1";
    	if(null != leaveObj && leaveObj.getLeaveId()!=0) hql += " and historyTask.leaveObj.leaveId=" + leaveObj.getLeaveId();
    	if(null != noteObj && noteObj.getNoteId()!=0) hql += " and historyTask.noteObj.noteId=" + noteObj.getNoteId();
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and historyTask.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!taskTime.equals("")) hql = hql + " and historyTask.taskTime like '%" + taskTime + "%'";
    	Query q = s.createQuery(hql);
    	List historyTaskList = q.list();
    	return (ArrayList<HistoryTask>) historyTaskList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<HistoryTask> QueryAllHistoryTaskInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From HistoryTask";
        Query q = s.createQuery(hql);
        List historyTaskList = q.list();
        return (ArrayList<HistoryTask>) historyTaskList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(LeaveInfo leaveObj,Note noteObj,UserInfo userObj,String taskTime) {
        Session s = factory.getCurrentSession();
        String hql = "From HistoryTask historyTask where 1=1";
        if(null != leaveObj && leaveObj.getLeaveId()!=0) hql += " and historyTask.leaveObj.leaveId=" + leaveObj.getLeaveId();
        if(null != noteObj && noteObj.getNoteId()!=0) hql += " and historyTask.noteObj.noteId=" + noteObj.getNoteId();
        if(null != userObj && !userObj.getUser_name().equals("")) hql += " and historyTask.userObj.user_name='" + userObj.getUser_name() + "'";
        if(!taskTime.equals("")) hql = hql + " and historyTask.taskTime like '%" + taskTime + "%'";
        Query q = s.createQuery(hql);
        List historyTaskList = q.list();
        recordNumber = historyTaskList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public HistoryTask GetHistoryTaskByHistoryTaskId(int historyTaskId) {
        Session s = factory.getCurrentSession();
        HistoryTask historyTask = (HistoryTask)s.get(HistoryTask.class, historyTaskId);
        return historyTask;
    }

    /*����HistoryTask��Ϣ*/
    public void UpdateHistoryTask(HistoryTask historyTask) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(historyTask);
    }

    /*ɾ��HistoryTask��Ϣ*/
    public void DeleteHistoryTask (int historyTaskId) throws Exception {
        Session s = factory.getCurrentSession();
        Object historyTask = s.load(HistoryTask.class, historyTaskId);
        s.delete(historyTask);
    }

}
