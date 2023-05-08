package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Position;
import com.mobileserver.util.DB;

public class PositionDAO {

	public List<Position> QueryPosition() {
		List<Position> positionList = new ArrayList<Position>();
		DB db = new DB();
		String sql = "select * from Position where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Position position = new Position();
				position.setPositionId(rs.getInt("positionId"));
				position.setPositionName(rs.getString("positionName"));
				positionList.add(position);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return positionList;
	}
	/* 传入职级信息对象，进行职级信息的添加业务 */
	public String AddPosition(Position position) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新职级信息 */
			String sqlString = "insert into Position(positionName) values (";
			sqlString += "'" + position.getPositionName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "职级信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "职级信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除职级信息 */
	public String DeletePosition(int positionId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Position where positionId=" + positionId;
			db.executeUpdate(sqlString);
			result = "职级信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "职级信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据职级ID获取到职级信息 */
	public Position GetPosition(int positionId) {
		Position position = null;
		DB db = new DB();
		String sql = "select * from Position where positionId=" + positionId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				position = new Position();
				position.setPositionId(rs.getInt("positionId"));
				position.setPositionName(rs.getString("positionName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return position;
	}
	/* 更新职级信息 */
	public String UpdatePosition(Position position) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Position set ";
			sql += "positionName='" + position.getPositionName() + "'";
			sql += " where positionId=" + position.getPositionId();
			db.executeUpdate(sql);
			result = "职级信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "职级信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
