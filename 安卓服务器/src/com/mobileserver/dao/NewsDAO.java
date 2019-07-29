package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.News;
import com.mobileserver.util.DB;

public class NewsDAO {

	public List<News> QueryNews(String title,Timestamp publishDate) {
		List<News> newsList = new ArrayList<News>();
		DB db = new DB();
		String sql = "select * from News where 1=1";
		if (!title.equals(""))
			sql += " and title like '%" + title + "%'";
		if(publishDate!=null)
			sql += " and publishDate='" + publishDate + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				News news = new News();
				news.setNewsId(rs.getInt("newsId"));
				news.setTitle(rs.getString("title"));
				news.setContent(rs.getString("content"));
				news.setPublishDate(rs.getTimestamp("publishDate"));
				newsList.add(news);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return newsList;
	}
	/* 传入新闻公告对象，进行新闻公告的添加业务 */
	public String AddNews(News news) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新新闻公告 */
			String sqlString = "insert into News(title,content,publishDate) values (";
			sqlString += "'" + news.getTitle() + "',";
			sqlString += "'" + news.getContent() + "',";
			sqlString += "'" + news.getPublishDate() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "新闻公告添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "新闻公告添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除新闻公告 */
	public String DeleteNews(int newsId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from News where newsId=" + newsId;
			db.executeUpdate(sqlString);
			result = "新闻公告删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "新闻公告删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据公告编号获取到新闻公告 */
	public News GetNews(int newsId) {
		News news = null;
		DB db = new DB();
		String sql = "select * from News where newsId=" + newsId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				news = new News();
				news.setNewsId(rs.getInt("newsId"));
				news.setTitle(rs.getString("title"));
				news.setContent(rs.getString("content"));
				news.setPublishDate(rs.getTimestamp("publishDate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return news;
	}
	/* 更新新闻公告 */
	public String UpdateNews(News news) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update News set ";
			sql += "title='" + news.getTitle() + "',";
			sql += "content='" + news.getContent() + "',";
			sql += "publishDate='" + news.getPublishDate() + "'";
			sql += " where newsId=" + news.getNewsId();
			db.executeUpdate(sql);
			result = "新闻公告更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "新闻公告更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
