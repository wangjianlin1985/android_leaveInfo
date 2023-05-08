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
import com.chengxusheji.domain.Position;

@Service @Transactional
public class PositionDAO {

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
    public void AddPosition(Position position) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(position);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Position> QueryPositionInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Position position where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List positionList = q.list();
    	return (ArrayList<Position>) positionList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Position> QueryPositionInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Position position where 1=1";
    	Query q = s.createQuery(hql);
    	List positionList = q.list();
    	return (ArrayList<Position>) positionList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Position> QueryAllPositionInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Position";
        Query q = s.createQuery(hql);
        List positionList = q.list();
        return (ArrayList<Position>) positionList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From Position position where 1=1";
        Query q = s.createQuery(hql);
        List positionList = q.list();
        recordNumber = positionList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Position GetPositionByPositionId(int positionId) {
        Session s = factory.getCurrentSession();
        Position position = (Position)s.get(Position.class, positionId);
        return position;
    }

    /*����Position��Ϣ*/
    public void UpdatePosition(Position position) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(position);
    }

    /*ɾ��Position��Ϣ*/
    public void DeletePosition (int positionId) throws Exception {
        Session s = factory.getCurrentSession();
        Object position = s.load(Position.class, positionId);
        s.delete(position);
    }

}
