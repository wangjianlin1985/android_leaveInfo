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
	/* 传入时间段类型对象，进行时间段类型的添加业务 */
	public String AddDayTimeType(DayTimeType dayTimeType) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新时间段类型 */
			String sqlString = "insert into DayTimeType(dayTimeTypeName) values (";
			sqlString += "'" + dayTimeType.getDayTimeTypeName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "时间段类型添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "时间段类型添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除时间段类型 */
	public String DeleteDayTimeType(int dayTimeTypeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from DayTimeType where dayTimeTypeId=" + dayTimeTypeId;
			db.executeUpdate(sqlString);
			result = "时间段类型删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "时间段类型删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到时间段类型 */
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
	/* 更新时间段类型 */
	public String UpdateDayTimeType(DayTimeType dayTimeType) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update DayTimeType set ";
			sql += "dayTimeTypeName='" + dayTimeType.getDayTimeTypeName() + "'";
			sql += " where dayTimeTypeId=" + dayTimeType.getDayTimeTypeId();
			db.executeUpdate(sql);
			result = "时间段类型更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "时间段类型更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
