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
 * ������
 * <p>�����ļ��еĲ����洢�ṹΪ<<em>key>value</em>�ĸ�ʽ,����ʵ�ֶ������ļ�setting.txt
 * �Ļ���������������
 * <ol>
 * <li> �������ļ���д��Ĭ�ϲ���
 * <li> ����ַ�ļ�д��Ĭ����ַ
 * <li> �������ļ��ж�ȡָ��key�����ò���value��
 * <li> �������ļ���д�����ò�����
 * <li> �����Ż����ļ���д���������ݣ�
 * 
 * @author Owen Lie
 */
public class SettingOperation {

	//�����ļ���λ��
	private final String FILEPATH;
	
	public SettingOperation(String filePath)
	{
		this.FILEPATH = filePath;
	}
	
	/**
	 * �������ļ�д��Ĭ�����ò���
	 * @return д�����ò����ɹ��򷵻�true�����򷵻�false
	 */
	public boolean writeDefaultSetting()
	{
		boolean writed = false;
		
		FileWriter pw = null;
		try 
		{
			pw = new FileWriter(FILEPATH);
			
			//д��Ĭ����������
			pw.write("<themeColor>GREEN\r\n");  //Ĭ��Ƥ��ɫΪgreen
			pw.write("<newsFont>DEFAULTFONT\r\n");  //Ĭ������ΪDialog
			pw.write("<menuBorder>show\r\n");  //Ĭ�ϲ˵���ť�б߽���
			pw.write("<timeout>10000\r\n");  //Ĭ�ϳ�ʱʱ��Ϊ10��
			pw.write("<headleft>true\r\n");  //Ĭ�Ϲ�����������
			pw.write("<useBar>true\r\n");  //Ĭ��ʹ�ù���������
			pw.write("<topMost>true\r\n");  //Ĭ��������ʼ�մ�����Ļ��ǰ��
			pw.write("<resizable>true\r\n");  //Ĭ�Ͽ��϶���С
			pw.write("<enableAni>true\r\n");  //Ĭ�Ͽ��϶���С
			pw.write("<speed>normal\r\n");  //Ĭ�Ϲ����ٶ�����
			pw.write("<refresh>60\r\n");  //Ĭ��ˢ������ʱ��Ϊ60����
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
	 * ��洢��ַ���ļ�webs.txtд��Ĭ����ַ��http://news.baidu.com"
	 * <ol>
	 * <li><<em>websCount></em>: �˱�ǩ��ʶ�м������õ���ַ
	 * <li><<em>webn></em>: ��n��������ַ
	 */
	public boolean writeDefaultURL()
	{
		boolean writed = false;
		
		FileWriter pw = null;
		try 
		{
			pw = new FileWriter(FILEPATH);
			
			//д����ַ����
			pw.write("<web1>http://news.baidu.com/\r\n");
			pw.write("<web1title>�ٶ����� -- ȫ�������������ƽ̨\r\n");
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
	 * ��ȡָ��key�����ò���
	 * 
	 * @param key ���ò����ļ�������Ϊ�գ�
	 * @return
	 * 		<ol>
	 * 		<li>FILE_NOT_FOUND: Ҫ�򿪵��ļ�û���ҵ���
	 * 		<li>EXCEPTION_WHILE_READING��  ��ȡ�ļ�����ʱ����
	 * 		<li>null: û�ж���ָ���кŵ�����
	 */
	public String getParaByKey(String key)
	{
		if(key == null)
			return null;
		
		//���������������ļ�test.txt��ȡ����
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
		
		//�ر�BufferedReader
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
	 * �������ļ�д����������.
	 * <ol>
	 * <li> ����������Ѵ��ڣ�ֵ��ͬ��ģ����򲻹�
	 * <li> �����������ڣ���Ӹ������
	 * </ol>
	 * @param key ���ò����ļ�
	 * @param value ���ò�����ֵ
	 * @return 
	 * 		<li> ADDED: �����ļ������ڸò�������ӳɹ�
	 * 		<li> FILE_NOT_FOUND: û���ҵ������ļ�
	 * 		<li> EXCEPTION_WHILE_WRITTING��  д�����ò�������
	 */
	public String setParam(String key, String value)
	{
		//���ָ�����������Ƿ����
		String param = this.getParaByKey(key);
		
		//��������ļ��в����ڸ�������
		if(param == null || param.equals("FILE_NOT_FOUND") 
				|| param.equals("EXCEPTION_WHILE_READING"))
		{
			return this.addParam(key, value);
		}
		
		//���ڸ���������ĸ����ò���
		else
		{
			return this.updateParam(key, value);
		}
	}
	
	/**
	 * �������ļ������������
	 * 
	 * @param key ������ļ�
	 * @param value �������ֵ
	 * @return
	 * 		<li>ADDED: ��������ӳɹ�
	 * 		<li>FILE_NOT_FOUND�� û���ҵ������ļ�
	 * 		<li>EXCEPTION_WHILE_WRITTING�� д���������ݳ���
	 */
	public String addParam(String key, String value)
	{
		if(key == null)
			return null;
		
		//����RandomAccessFile����
		RandomAccessFile raf = null;
		
		try 
		{
			raf = new RandomAccessFile(FILEPATH, "rw");
			String str = "\r\n" + key + value;
			
			//�������ļ�ĩβ������ò���
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
	 * �������ļ��޸���������
	 * @param key ������ļ�
	 * @param value �޸�֮���ֵ
	 * @return
	 * 		<li>UPDATED: ���ò����޸ĳɹ�
	 * 		<li>FILE_NOT_FOUND�� û�ҵ������ļ�
	 * 		<li>EXCEPTION_WHILE_WRITTING�� д�����ó���
	 */
	public String updateParam(String key, String value)
	{
		if(key == null)
			return null;
		
		//���������������ļ���ȡ����
		BufferedReader buff = null;
		String readStr = "";
		
		//������ļ��ж���������
		StringBuffer sbuf = new StringBuffer();
		
		//��������StringBuffer�е�����д���ļ�
		FileOutputStream fos = null;
		PrintWriter pw = null;

		try 
		{
			buff = new BufferedReader
					(new InputStreamReader
							(new FileInputStream(FILEPATH)));
			
			//��Ҫ�޸ĵ���֮ǰ���������ݴ���StringBuffer
			for(;(readStr = buff.readLine()) != null
					&& !readStr.startsWith(key);)
			{
				sbuf = sbuf.append(readStr);
				sbuf = sbuf.append("\r\n");
			}
			
			//�������ò���
			sbuf = sbuf.append(key + value);
			
			//��Ҫ�޸ĵ���֮����������ݴ���StringBuffer
			while((readStr = buff.readLine()) != null)
			{
				sbuf = sbuf.append("\r\n");
				sbuf = sbuf.append(readStr);
			}
			
			//��StringBuffer�е�����д��txt�ļ�
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
		//�ر�BufferedReader
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
