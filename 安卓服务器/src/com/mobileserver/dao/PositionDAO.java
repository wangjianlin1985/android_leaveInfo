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
	/* ����ְ����Ϣ���󣬽���ְ����Ϣ�����ҵ�� */
	public String AddPosition(Position position) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����ְ����Ϣ */
			String sqlString = "insert into Position(positionName) values (";
			sqlString += "'" + position.getPositionName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "ְ����Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ְ����Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��ְ����Ϣ */
	public String DeletePosition(int positionId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Position where positionId=" + positionId;
			db.executeUpdate(sqlString);
			result = "ְ����Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ְ����Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ����ְ��ID��ȡ��ְ����Ϣ */
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
	/* ����ְ����Ϣ */
	public String UpdatePosition(Position position) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Position set ";
			sql += "positionName='" + position.getPositionName() + "'";
			sql += " where positionId=" + position.getPositionId();
			db.executeUpdate(sql);
			result = "ְ����Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ְ����Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
