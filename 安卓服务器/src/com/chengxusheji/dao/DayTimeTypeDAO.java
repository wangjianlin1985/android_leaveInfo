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
    public void AddDayTimeType(DayTimeType dayTimeType) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(dayTimeType);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<DayTimeType> QueryDayTimeTypeInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From DayTimeType dayTimeType where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
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

    /*计算总的页数和记录数*/
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

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public DayTimeType GetDayTimeTypeByDayTimeTypeId(int dayTimeTypeId) {
        Session s = factory.getCurrentSession();
        DayTimeType dayTimeType = (DayTimeType)s.get(DayTimeType.class, dayTimeTypeId);
        return dayTimeType;
    }

    /*更新DayTimeType信息*/
    public void UpdateDayTimeType(DayTimeType dayTimeType) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(dayTimeType);
    }

    /*删除DayTimeType信息*/
    public void DeleteDayTimeType (int dayTimeTypeId) throws Exception {
        Session s = factory.getCurrentSession();
        Object dayTimeType = s.load(DayTimeType.class, dayTimeTypeId);
        s.delete(dayTimeType);
    }

}
