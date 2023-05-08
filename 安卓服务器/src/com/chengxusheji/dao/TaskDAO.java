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
import com.chengxusheji.domain.Task;

@Service @Transactional
public class TaskDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddTask(Task task) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(task);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Task> QueryTaskInfo(LeaveInfo leaveInfoObj,Note noteObj,UserInfo userObj,String taskTime,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Task task where 1=1";
    	if(null != leaveInfoObj && leaveInfoObj.getLeaveId()!=0) hql += " and task.leaveInfoObj.leaveId=" + leaveInfoObj.getLeaveId();
    	if(null != noteObj && noteObj.getNoteId()!=0) hql += " and task.noteObj.noteId=" + noteObj.getNoteId();
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and task.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!taskTime.equals("")) hql = hql + " and task.taskTime like '%" + taskTime + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List taskList = q.list();
    	return (ArrayList<Task>) taskList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Task> QueryTaskInfo(LeaveInfo leaveInfoObj,Note noteObj,UserInfo userObj,String taskTime) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Task task where 1=1";
    	if(null != leaveInfoObj && leaveInfoObj.getLeaveId()!=0) hql += " and task.leaveInfoObj.leaveId=" + leaveInfoObj.getLeaveId();
    	if(null != noteObj && noteObj.getNoteId()!=0) hql += " and task.noteObj.noteId=" + noteObj.getNoteId();
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and task.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!taskTime.equals("")) hql = hql + " and task.taskTime like '%" + taskTime + "%'";
    	Query q = s.createQuery(hql);
    	List taskList = q.list();
    	return (ArrayList<Task>) taskList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Task> QueryAllTaskInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Task";
        Query q = s.createQuery(hql);
        List taskList = q.list();
        return (ArrayList<Task>) taskList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(LeaveInfo leaveInfoObj,Note noteObj,UserInfo userObj,String taskTime) {
        Session s = factory.getCurrentSession();
        String hql = "From Task task where 1=1";
        if(null != leaveInfoObj && leaveInfoObj.getLeaveId()!=0) hql += " and task.leaveInfoObj.leaveId=" + leaveInfoObj.getLeaveId();
        if(null != noteObj && noteObj.getNoteId()!=0) hql += " and task.noteObj.noteId=" + noteObj.getNoteId();
        if(null != userObj && !userObj.getUser_name().equals("")) hql += " and task.userObj.user_name='" + userObj.getUser_name() + "'";
        if(!taskTime.equals("")) hql = hql + " and task.taskTime like '%" + taskTime + "%'";
        Query q = s.createQuery(hql);
        List taskList = q.list();
        recordNumber = taskList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Task GetTaskByTaskId(int taskId) {
        Session s = factory.getCurrentSession();
        Task task = (Task)s.get(Task.class, taskId);
        return task;
    }

    /*更新Task信息*/
    public void UpdateTask(Task task) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(task);
    }

    /*删除Task信息*/
    public void DeleteTask (int taskId) throws Exception {
        Session s = factory.getCurrentSession();
        Object task = s.load(Task.class, taskId);
        s.delete(task);
    }

}
