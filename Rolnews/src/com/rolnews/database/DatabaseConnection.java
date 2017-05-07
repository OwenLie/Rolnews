package com.rolnews.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * ���ݿ������࣬��ȡ���ݿ�����
 * @author Owen Lie
 * @version 1.0.0
 */
public class DatabaseConnection {

	private Connection connection = null;
	
	/**
	 * ���캯������ʼ�����Ӷ���
	 */
	public DatabaseConnection()
	{
		this.connection = getConnection();
	}
	
	/**
	 * ������ݿ����Ӷ���
	 * @return �������Ӷ���
	 */
	private Connection getConnection()
	{
		String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Rolnews";  //�������ݿ��URL
		String userName = "sa";  //�û���
		String password = "4612";  //����
		
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
	 * �����ݿ�HotWords���в�������
	 */
	public boolean InsertHotWords(String word, String href)
	{
		//��ȡ����
		Statement stmt = null;
		boolean result = false;
		
		//ƴ��SQL���
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
	 * �ر����ݿ�����
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
