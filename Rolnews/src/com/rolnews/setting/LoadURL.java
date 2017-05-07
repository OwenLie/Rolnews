package com.rolnews.setting;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.rolnews.main.Rolnews;

/**
 * ��ַ��Ϣ������
 * <p>��ɴӱ�����ַ�ļ��м�����ַ��Ϣ�Ĺ���
 * 
 * @author Owen Lie
 */
public class LoadURL {

	public LoadURL(){}
	
	/**
	 * ����URL
	 */
	public void loadURL()
	{
		String path = "webs.txt";
		File file = new File(path);
		SettingOperation setting = new SettingOperation(path);
		
		//�ж��Ƿ����web��ַ�ļ�
		if(!file.exists())
		{
			try 
			{
				//�½�web��ַ��Ϣ�ļ�
				file.createNewFile();
				
				//д��Ĭ�ϲ���
				boolean writed = setting.writeDefaultURL();
				if(writed)
					System.out.println("web��ַ�ļ������ڣ����½�!");
				else
					System.out.println("web��ַ�ļ������ڣ��½�ʧ��");
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
//[start] ��ȡ��ַ��Ϣ
		
		int websCount = 0;
		Rolnews.parameters.getURLs().clear();  //����洢��ַ��Ϣ���б�
		//��ȡ�Ƿ��������������������Ϣ
		Rolnews.parameters.setNewsCategory(setting.getParaByKey("<category>"));
		
		for(int i = 0; i < 6; i++)
		{
			String url = setting.getParaByKey("<web" + (i + 1) + ">");
			String title = setting.getParaByKey("<web" + (i + 1) + "title>");

			if(!url.equals("") && !title.equals(""))
			{
				String[] urls = new String[]{url, title};
				Rolnews.parameters.getURLs().add(urls);
				websCount++;
			}
		}
		
		List<String[]> str = Rolnews.parameters.getURLs();
		for(String[] a : str)
		{
			System.out.println(a[0] + "   " + a[1]);
		}
		System.out.println("");
	}
}