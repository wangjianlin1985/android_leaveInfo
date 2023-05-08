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
import com.chengxusheji.domain.DayTimeType;

@Service @Transactional
public class DayTimeTypeDAO {

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
    public void AddDayTimeType(DayTimeType dayTimeType) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(dayTimeType);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<DayTimeType> QueryDayTimeTypeInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From DayTimeType dayTimeType where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List dayTimeTypeList = q.list();
    	return (ArrayList<DayTimeType>) dayTimeTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<DayTimeType> QueryDayTimeTypeInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From DayTimeType dayTimeType where 1=1";
    	Query q = s.createQuery(hql);
    	List dayTimeTypeList = q.list();
    	return (ArrayList<DayTimeType>) dayTimeTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<DayTimeType> QueryAllDayTimeTypeInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From DayTimeType";
        Query q = s.createQuery(hql);
        List dayTimeTypeList = q.list();
        return (ArrayList<DayTimeType>) dayTimeTypeList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From DayTimeType dayTimeType where 1=1";
        Query q = s.createQuery(hql);
        List dayTimeTypeList = q.list();
        recordNumber = dayTimeTypeList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public DayTimeType GetDayTimeTypeByDayTimeTypeId(int dayTimeTypeId) {
        Session s = factory.getCurrentSession();
        DayTimeType dayTimeType = (DayTimeType)s.get(DayTimeType.class, dayTimeTypeId);
        return dayTimeType;
    }

    /*����DayTimeType��Ϣ*/
    public void UpdateDayTimeType(DayTimeType dayTimeType) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(dayTimeType);
    }

    /*ɾ��DayTimeType��Ϣ*/
    public void DeleteDayTimeType (int dayTimeTypeId) throws Exception {
        Session s = factory.getCurrentSession();
        Object dayTimeType = s.load(DayTimeType.class, dayTimeTypeId);
        s.delete(dayTimeType);
    }

}
