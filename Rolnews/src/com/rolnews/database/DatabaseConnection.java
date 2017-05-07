package com.rolnews.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * 数据库连接类，获取数据库连接
 * @author Owen Lie
 * @version 1.0.0
 */
public class DatabaseConnection {

	private Connection connection = null;
	
	/**
	 * 构造函数，初始化连接对象
	 */
	public DatabaseConnection()
	{
		this.connection = getConnection();
	}
	
	/**
	 * 获得数据库连接对象
	 * @return 返回连接对象
	 */
	private Connection getConnection()
	{
		String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Rolnews";  //连接数据库的URL
		String userName = "sa";  //用户名
		String password = "4612";  //密码
		
		Connection con = null;
		try 
		{
			con = DriverManager.getConnection(url, userName, password);
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return con;
	}
	
	/**
	 * 向数据库HotWords表中插入数据
	 */
	public boolean InsertHotWords(String word, String href)
	{
		//获取连接
		Statement stmt = null;
		boolean result = false;
		
		//拼凑SQL语句
		StringBuffer sql = new StringBuffer("if not exists (select * from HotWords where recDate = convert(varchar(10), getDate(), 102) and word = '");
		sql.append(word);
		sql.append("' and href = '");
		sql.append(href);
		sql.append("') begin insert into HotWords(id, word, href, recDate) values(next value for HotWord_s, '");
		sql.append(word);
		sql.append("', '");
		sql.append(href);
		sql.append("', convert(varchar(10), getDate(), 102)) end");
		
		try 
		{
			if(this.connection != null)
			{
				stmt = this.connection.createStatement();
				result = stmt.execute(sql.toString());
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 关闭数据库连接
	 */
	public void CloseConnection()
	{
		if(this.connection != null)
			try
			{
				connection.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	}
}
