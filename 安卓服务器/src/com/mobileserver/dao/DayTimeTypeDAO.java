package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.DayTimeType;
import com.mobileserver.util.DB;

public class DayTimeTypeDAO {

	public List<DayTimeType> QueryDayTimeType() {
		List<DayTimeType> dayTimeTypeList = new ArrayList<DayTimeType>();
		DB db = new DB();
		String sql = "select * from DayTimeType where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				DayTimeType dayTimeType = new DayTimeType();
				dayTimeType.setDayTimeTypeId(rs.getInt("dayTimeTypeId"));
				dayTimeType.setDayTimeTypeName(rs.getString("dayTimeTypeName"));
				dayTimeTypeList.add(dayTimeType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return dayTimeTypeList;
	}
	/* ����ʱ������Ͷ��󣬽���ʱ������͵����ҵ�� */
	public String AddDayTimeType(DayTimeType dayTimeType) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����ʱ������� */
			String sqlString = "insert into DayTimeType(dayTimeTypeName) values (";
			sqlString += "'" + dayTimeType.getDayTimeTypeName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "ʱ���������ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ʱ����������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��ʱ������� */
	public String DeleteDayTimeType(int dayTimeTypeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from DayTimeType where dayTimeTypeId=" + dayTimeTypeId;
			db.executeUpdate(sqlString);
			result = "ʱ�������ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ʱ�������ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ��ʱ������� */
	public DayTimeType GetDayTimeType(int dayTimeTypeId) {
		DayTimeType dayTimeType = null;
		DB db = new DB();
		String sql = "select * from DayTimeType where dayTimeTypeId=" + dayTimeTypeId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				dayTimeType = new DayTimeType();
				dayTimeType.setDayTimeTypeId(rs.getInt("dayTimeTypeId"));
				dayTimeType.setDayTimeTypeName(rs.getString("dayTimeTypeName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return dayTimeType;
	}
	/* ����ʱ������� */
	public String UpdateDayTimeType(DayTimeType dayTimeType) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update DayTimeType set ";
			sql += "dayTimeTypeName='" + dayTimeType.getDayTimeTypeName() + "'";
			sql += " where dayTimeTypeId=" + dayTimeType.getDayTimeTypeId();
			db.executeUpdate(sql);
			result = "ʱ������͸��³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ʱ������͸���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
