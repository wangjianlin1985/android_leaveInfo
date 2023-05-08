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
    public void AddLeaveType(LeaveType leaveType) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(leaveType);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<LeaveType> QueryLeaveTypeInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From LeaveType leaveType where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
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

    /*�����ܵ�ҳ���ͼ�¼��*/
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

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public LeaveType GetLeaveTypeByLeaveTypeId(int leaveTypeId) {
        Session s = factory.getCurrentSession();
        LeaveType leaveType = (LeaveType)s.get(LeaveType.class, leaveTypeId);
        return leaveType;
    }

    /*����LeaveType��Ϣ*/
    public void UpdateLeaveType(LeaveType leaveType) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(leaveType);
    }

    /*ɾ��LeaveType��Ϣ*/
    public void DeleteLeaveType (int leaveTypeId) throws Exception {
        Session s = factory.getCurrentSession();
        Object leaveType = s.load(LeaveType.class, leaveTypeId);
        s.delete(leaveType);
    }

}
