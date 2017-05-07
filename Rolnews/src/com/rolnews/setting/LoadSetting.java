package com.rolnews.setting;

import java.io.File;
import java.io.IOException;

import com.rolnews.main.Rolnews;
import com.rolnews.values.NewsFont;
import com.rolnews.values.ThemeColor;

/**
 * ���ò���������
 * <p>��ɴӱ����ļ��������ò�����������������ļ������ڣ����½������ļ���
 * ���������ļ���д��Ĭ�����ò���
 * 
 * @author Owen Lie
 */

public class LoadSetting {

	public LoadSetting() {}
	
	/**
	 * ����������������ļ��м��س���������������ò�������������ļ������ڣ����½������ļ���
	 * ��д��Ĭ�ϲ���
	 */
	public void loadSetting()
	{
		File file = new File("setting.txt");
		SettingOperation setting = new SettingOperation("setting.txt");
		
		String param = null;
		
		//�ж��Ƿ����setting�ļ�
		if(!file.exists())
		{
			try 
			{
				//�½������ļ�
				file.createNewFile();

				//д��Ĭ�ϲ���
				boolean writed = setting.writeDefaultSetting();
				if(writed)
					System.out.println("setting�ļ������ڣ����½�!");
				else
					System.out.println("setting�ļ������ڣ��½�ʧ��");
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		file = null;  //�ͷ����ö���
				
//[start] ��������ɫ
		
		System.out.println("Starting to load the news setting!");
		
		param = setting.getParaByKey("<themeColor>");
		ThemeColor bgColor = null;
		ThemeColor mouseEnterColor = null;
		switch(param)
		{
			case "BLUE":
				bgColor = ThemeColor.BLUE;
				mouseEnterColor = ThemeColor.BLUE_MOUENT;
				break;
			case "RED":
				bgColor = ThemeColor.RED;
				mouseEnterColor = ThemeColor.RED_MOUENT;
				break;
			case "GOLDEN":
				bgColor = ThemeColor.GOLDEN;
				mouseEnterColor = ThemeColor.GOLDEN_MOUENT;
				break;
			case "BROWN":
				bgColor = ThemeColor.BROWN;
				mouseEnterColor = ThemeColor.BROWN_MOUENT;
				break;
			case "PURPLE":
				bgColor = ThemeColor.PURPLE;
				mouseEnterColor = ThemeColor.PURPLE_MOUENT;
				break;
			default:
				bgColor = ThemeColor.GREEN;
				mouseEnterColor = ThemeColor.GREEN_MOUENT;
				break;
		}
		Rolnews.parameters.setBgColor(bgColor);
		Rolnews.parameters.setMouseEnterColor(mouseEnterColor);
		
		bgColor = null;
		mouseEnterColor = null;
		
//[end]
		
//[start] ��������
		
		param = setting.getParaByKey("<newsFont>");
		NewsFont labelFont = null;
		switch(param)
		{
			case "SONG":
				labelFont = NewsFont.SONG;
				break;
			case "HUAWENXIHEI":
				labelFont = NewsFont.HUAWENXIHEI;
				break;
			case "HUAWENXINWEI":
				labelFont = NewsFont.HUAWENXINWEI;
				break;
			case "FANGSONG":
				labelFont = NewsFont.FANGSONG;
				break;
			case "KAITI":
				labelFont = NewsFont.KAITI;
				break;
			case "LISHU":
				labelFont = NewsFont.LISHU;
				break;
			case "HUAWENXINGKAI":
				labelFont = NewsFont.HUAWENXINGKAI;
				break;
			default:
				labelFont = NewsFont.DEFAULTFONT;
				break;
		}
		Rolnews.parameters.setLabelFont(labelFont);
		
		labelFont = null;
		
//[end]
		
//[start] �Ƿ���ʾ�˵���ť�߿�
		
		param = setting.getParaByKey("<menuBorder>");
		if(param.equals("show"))
			Rolnews.parameters.setShowBorder(true);
		else
			Rolnews.parameters.setShowBorder(false);
		
//[end]
		
//[start] �������ų�ʱ����
		
		param = setting.getParaByKey("<timeout>");
		int loadingTime = 0;
		switch(param)
		{
			case "10000":
				loadingTime = 10000;
				break;
			case "15000":
				loadingTime = 15000;
			default:
				loadingTime = 5000;
				break;
		}
		Rolnews.parameters.setLoadingTime(loadingTime);
		
//[end]
		
//[start] �������Ź����������
		
		param = setting.getParaByKey("<headleft>");
		if(param.equals("true"))
			Rolnews.parameters.setHeadingLeft(true);
		else
			Rolnews.parameters.setHeadingLeft(false);
		
//[end]
		
//[start] �鿴�Ƿ�ʹ�����ŷָ���
		
		param = setting.getParaByKey("<useBar>");
		if(param.equals("true"))
			Rolnews.parameters.setUseBar(true);
		else
			Rolnews.parameters.setUseBar(false);
		
//[end]
		
//[start] �鿴�Ƿ�ʼ�մ�����Ļ��ǰ��
		
		param = setting.getParaByKey("<topMost>");
		if(param.equals("true"))
			Rolnews.parameters.setTopMost(true);
		else
			Rolnews.parameters.setTopMost(false);
		
//[end]
		
//[start]  �鿴�Ƿ�ɱ䶯��С
		
		param = setting.getParaByKey("<resizable>");
		if(param.equals("true"))
			Rolnews.parameters.setResizable(true);
		else
			Rolnews.parameters.setResizable(false);
		
//[end]
		
//[start] ���ع����ٶ�
		
		param = setting.getParaByKey("<speed>");
		int pxlSpeed = 0;
		boolean headingLeft = Rolnews.parameters.isHeadingLeft();
		switch(param)
		{
			case "slow":
				if(headingLeft)
					pxlSpeed = 1;
				else
					pxlSpeed = -1;
				break;
			case "fast":
				if(headingLeft)
					pxlSpeed = 3;
				else
					pxlSpeed = -3;
				break;
			default:
				if(headingLeft)
					pxlSpeed = 2;
				else
					pxlSpeed = -2;
				break;
		}
		Rolnews.parameters.setSpeed(pxlSpeed);
		
//[end]
		
//[start] ����ˢ��ʱ��
		
		param = setting.getParaByKey("<refresh>");
		int freshTime = 0;
		switch(param)
		{
			case "120":
				freshTime = 120;
				break;
			case "180":
				freshTime = 180;
				break;
			default:
				freshTime = 60;
				break;
		}
		Rolnews.parameters.setRefreshTime(freshTime); 
//[end]
		
//[start] ����˵�����
		
		param = setting.getParaByKey("<enableAni>");
		if(param.equals("true"))
			Rolnews.parameters.setEnableMenuAnimation(true);
		else
			Rolnews.parameters.setEnableMenuAnimation(false);
		
//[end]
		
//[start] �����ļ�����������
		
		param = setting.getParaByKey("<backupNewsCount>");
		Rolnews.parameters.setNewsCount(Integer.parseInt(param));
//[end]
	}
}