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
import com.chengxusheji.domain.LeaveType;

@Service @Transactional
public class LeaveTypeDAO {

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
    public void AddLeaveType(LeaveType leaveType) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(leaveType);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<LeaveType> QueryLeaveTypeInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From LeaveType leaveType where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List leaveTypeList = q.list();
    	return (ArrayList<LeaveType>) leaveTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<LeaveType> QueryLeaveTypeInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From LeaveType leaveType where 1=1";
    	Query q = s.createQuery(hql);
    	List leaveTypeList = q.list();
    	return (ArrayList<LeaveType>) leaveTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<LeaveType> QueryAllLeaveTypeInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From LeaveType";
        Query q = s.createQuery(hql);
        List leaveTypeList = q.list();
        return (ArrayList<LeaveType>) leaveTypeList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From LeaveType leaveType where 1=1";
        Query q = s.createQuery(hql);
        List leaveTypeList = q.list();
        recordNumber = leaveTypeList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public LeaveType GetLeaveTypeByLeaveTypeId(int leaveTypeId) {
        Session s = factory.getCurrentSession();
        LeaveType leaveType = (LeaveType)s.get(LeaveType.class, leaveTypeId);
        return leaveType;
    }

    /*更新LeaveType信息*/
    public void UpdateLeaveType(LeaveType leaveType) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(leaveType);
    }

    /*删除LeaveType信息*/
    public void DeleteLeaveType (int leaveTypeId) throws Exception {
        Session s = factory.getCurrentSession();
        Object leaveType = s.load(LeaveType.class, leaveTypeId);
        s.delete(leaveType);
    }

}
