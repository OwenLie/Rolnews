package com.rolnews.setting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

/**
 * 设置类
 * <p>设置文件中的参数存储结构为<<em>key>value</em>的格式,此类实现对设置文件setting.txt
 * 的基本操作，包括：
 * <ol>
 * <li> 向设置文件中写入默认参数
 * <li> 向网址文件写入默认网址
 * <li> 从设置文件中读取指定key的设置参数value；
 * <li> 向设置文件中写入设置参数；
 * <li> 向新闻缓存文件中写入新闻数据；
 * 
 * @author Owen Lie
 */
public class SettingOperation {

	//设置文件的位置
	private final String FILEPATH;
	
	public SettingOperation(String filePath)
	{
		this.FILEPATH = filePath;
	}
	
	/**
	 * 向设置文件写入默认设置参数
	 * @return 写入设置参数成功则返回true，否则返回false
	 */
	public boolean writeDefaultSetting()
	{
		boolean writed = false;
		
		FileWriter pw = null;
		try 
		{
			pw = new FileWriter(FILEPATH);
			
			//写入默认设置数据
			pw.write("<themeColor>GREEN\r\n");  //默认皮肤色为green
			pw.write("<newsFont>DEFAULTFONT\r\n");  //默认字体为Dialog
			pw.write("<menuBorder>show\r\n");  //默认菜单按钮有边界线
			pw.write("<timeout>10000\r\n");  //默认超时时间为10秒
			pw.write("<headleft>true\r\n");  //默认滚动方向向左
			pw.write("<useBar>true\r\n");  //默认使用滚动新闻条
			pw.write("<topMost>true\r\n");  //默认新闻条始终处于屏幕最前方
			pw.write("<resizable>true\r\n");  //默认可拖动大小
			pw.write("<enableAni>true\r\n");  //默认可拖动大小
			pw.write("<speed>normal\r\n");  //默认滚动速度正常
			pw.write("<refresh>60\r\n");  //默认刷新新闻时间为60分钟
			pw.write("<backupNewsCount>0\r\n");
			
			writed = true;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(pw != null)
				try 
				{
					pw.close();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
		}
		
		return writed;
	}
	
	/**
	 * 向存储网址的文件webs.txt写入默认网址“http://news.baidu.com"
	 * <ol>
	 * <li><<em>websCount></em>: 此标签标识有几个可用的网址
	 * <li><<em>webn></em>: 第n个可用网址
	 */
	public boolean writeDefaultURL()
	{
		boolean writed = false;
		
		FileWriter pw = null;
		try 
		{
			pw = new FileWriter(FILEPATH);
			
			//写入网址数量
			pw.write("<web1>http://news.baidu.com/\r\n");
			pw.write("<web1title>百度新闻 -- 全球最大中文新闻平台\r\n");
			pw.write("<web2>\r\n");
			pw.write("<web2title>\r\n");
			pw.write("<web3>\r\n");
			pw.write("<web3title>\r\n");
			pw.write("<web4>\r\n");
			pw.write("<web4title>\r\n");
			pw.write("<web5>\r\n");
			pw.write("<web5title>\r\n");
			pw.write("<web6>\r\n");
			pw.write("<web6title>\r\n");
			pw.write("<category>no");
			
			writed = true;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(pw != null)
				try 
				{
					pw.close();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
		}
		
		return writed;
	}
	
	/**
	 * 获取指定key的设置参数
	 * 
	 * @param key 设置参数的键，可以为空；
	 * @return
	 * 		<ol>
	 * 		<li>FILE_NOT_FOUND: 要打开的文件没有找到；
	 * 		<li>EXCEPTION_WHILE_READING：  读取文件数据时出错；
	 * 		<li>null: 没有读到指定行号的数据
	 */
	public String getParaByKey(String key)
	{
		if(key == null)
			return null;
		
		//声明缓冲区，从文件test.txt读取数据
		BufferedReader buff = null;
		String readStr = "";
		try 
		{
			buff = new BufferedReader
					(new InputStreamReader
							(new FileInputStream(FILEPATH)));
			
			while(!readStr.startsWith(key))
				readStr = buff.readLine();
			
			readStr = readStr.substring(readStr.indexOf(">") + 1, readStr.length());
			return readStr;
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			return "FILE_NOT_FOUND";
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return "EXCEPTION_WHILE_READING";
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
			return null;
		}
		
		//关闭BufferedReader
		finally
		{
			if(buff != null)
				try 
				{
					buff.close();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * 向设置文件写入设置数据.
	 * <ol>
	 * <li> 如果设置项已存在，值不同则改，否则不管
	 * <li> 如果设置项不存在，添加该设置项；
	 * </ol>
	 * @param key 设置参数的键
	 * @param value 设置参数的值
	 * @return 
	 * 		<li> ADDED: 设置文件不存在该参数，添加成功
	 * 		<li> FILE_NOT_FOUND: 没有找到设置文件
	 * 		<li> EXCEPTION_WHILE_WRITTING：  写入设置参数出错
	 */
	public String setParam(String key, String value)
	{
		//检查指定的设置项是否存在
		String param = this.getParaByKey(key);
		
		//如果设置文件中不存在该设置项
		if(param == null || param.equals("FILE_NOT_FOUND") 
				|| param.equals("EXCEPTION_WHILE_READING"))
		{
			return this.addParam(key, value);
		}
		
		//存在该设置项，更改该设置参数
		else
		{
			return this.updateParam(key, value);
		}
	}
	
	/**
	 * 向设置文件中添加设置项
	 * 
	 * @param key 设置项的键
	 * @param value 设置项的值
	 * @return
	 * 		<li>ADDED: 设置项添加成功
	 * 		<li>FILE_NOT_FOUND： 没有找到设置文件
	 * 		<li>EXCEPTION_WHILE_WRITTING： 写入设置数据出错
	 */
	public String addParam(String key, String value)
	{
		if(key == null)
			return null;
		
		//声明RandomAccessFile对象
		RandomAccessFile raf = null;
		
		try 
		{
			raf = new RandomAccessFile(FILEPATH, "rw");
			String str = "\r\n" + key + value;
			
			//在设置文件末尾添加设置参数
			raf.seek(raf.length());
			raf.write(str.getBytes());
			
			return "ADDED";
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			return "FILE_NOT_FOUND";
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return "EXCEPTION_WHILE_WRITTING";
		}
		finally
		{
			if(raf != null)
				try 
				{
					raf.close();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * 向设置文件修改设置数据
	 * @param key 设置项的键
	 * @param value 修改之后的值
	 * @return
	 * 		<li>UPDATED: 设置参数修改成功
	 * 		<li>FILE_NOT_FOUND： 没找到设置文件
	 * 		<li>EXCEPTION_WHILE_WRITTING： 写入设置出错
	 */
	public String updateParam(String key, String value)
	{
		if(key == null)
			return null;
		
		//声明缓冲区，从文件读取数据
		BufferedReader buff = null;
		String readStr = "";
		
		//缓存从文件中读出的内容
		StringBuffer sbuf = new StringBuffer();
		
		//将缓存在StringBuffer中的内容写回文件
		FileOutputStream fos = null;
		PrintWriter pw = null;

		try 
		{
			buff = new BufferedReader
					(new InputStreamReader
							(new FileInputStream(FILEPATH)));
			
			//将要修改的行之前的设置数据存入StringBuffer
			for(;(readStr = buff.readLine()) != null
					&& !readStr.startsWith(key);)
			{
				sbuf = sbuf.append(readStr);
				sbuf = sbuf.append("\r\n");
			}
			
			//更改设置参数
			sbuf = sbuf.append(key + value);
			
			//将要修改的行之后的设置数据存入StringBuffer
			while((readStr = buff.readLine()) != null)
			{
				sbuf = sbuf.append("\r\n");
				sbuf = sbuf.append(readStr);
			}
			
			//将StringBuffer中的数据写回txt文件
			fos = new FileOutputStream(new File(FILEPATH));
			pw = new PrintWriter(fos);
			pw.write(sbuf.toString().toCharArray());
			
			return "UPDATED";
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			return "FILE_NOT_FOUND";
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return "EXCEPTION_WHILE_WRITTING";
		}
		//关闭BufferedReader
		finally
		{
			if(buff != null)
				try 
				{
					buff.close();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			
			if(pw != null)
				pw.close();
		}
	}
}
