package com.rolnews.setting;

import java.io.File;
import java.io.IOException;

import com.rolnews.main.Rolnews;
import com.rolnews.values.NewsFont;
import com.rolnews.values.ThemeColor;

/**
 * 设置参数加载类
 * <p>完成从本地文件加载设置参数的任务，如果设置文件不存在，则新建设置文件，
 * 并向设置文件中写入默认设置参数
 * 
 * @author Owen Lie
 */

public class LoadSetting {

	public LoadSetting() {}
	
	/**
	 * 加载设置项，从设置文件中加载程序运行所需的设置参数，如果设置文件不存在，则新建设置文件，
	 * 并写入默认参数
	 */
	public void loadSetting()
	{
		File file = new File("setting.txt");
		SettingOperation setting = new SettingOperation("setting.txt");
		
		String param = null;
		
		//判断是否存在setting文件
		if(!file.exists())
		{
			try 
			{
				//新建设置文件
				file.createNewFile();

				//写入默认参数
				boolean writed = setting.writeDefaultSetting();
				if(writed)
					System.out.println("setting文件不存在，已新建!");
				else
					System.out.println("setting文件不存在，新建失败");
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		file = null;  //释放无用对象
				
//[start] 加载主题色
		
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
		
//[start] 加载字体
		
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
		
//[start] 是否显示菜单按钮边框
		
		param = setting.getParaByKey("<menuBorder>");
		if(param.equals("show"))
			Rolnews.parameters.setShowBorder(true);
		else
			Rolnews.parameters.setShowBorder(false);
		
//[end]
		
//[start] 加载新闻超时参数
		
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
		
//[start] 加载新闻滚动方向参数
		
		param = setting.getParaByKey("<headleft>");
		if(param.equals("true"))
			Rolnews.parameters.setHeadingLeft(true);
		else
			Rolnews.parameters.setHeadingLeft(false);
		
//[end]
		
//[start] 查看是否使用新闻分隔条
		
		param = setting.getParaByKey("<useBar>");
		if(param.equals("true"))
			Rolnews.parameters.setUseBar(true);
		else
			Rolnews.parameters.setUseBar(false);
		
//[end]
		
//[start] 查看是否始终处于屏幕最前方
		
		param = setting.getParaByKey("<topMost>");
		if(param.equals("true"))
			Rolnews.parameters.setTopMost(true);
		else
			Rolnews.parameters.setTopMost(false);
		
//[end]
		
//[start]  查看是否可变动大小
		
		param = setting.getParaByKey("<resizable>");
		if(param.equals("true"))
			Rolnews.parameters.setResizable(true);
		else
			Rolnews.parameters.setResizable(false);
		
//[end]
		
//[start] 加载滚动速度
		
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
		
//[start] 加载刷新时间
		
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
		
//[start] 允许菜单动画
		
		param = setting.getParaByKey("<enableAni>");
		if(param.equals("true"))
			Rolnews.parameters.setEnableMenuAnimation(true);
		else
			Rolnews.parameters.setEnableMenuAnimation(false);
		
//[end]
		
//[start] 备份文件中新闻数量
		
		param = setting.getParaByKey("<backupNewsCount>");
		Rolnews.parameters.setNewsCount(Integer.parseInt(param));
//[end]
	}
}