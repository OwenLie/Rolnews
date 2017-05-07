package com.rolnews.setting;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.rolnews.main.Rolnews;

/**
 * 网址信息加载类
 * <p>完成从本地网址文件中加载网址信息的过程
 * 
 * @author Owen Lie
 */
public class LoadURL {

	public LoadURL(){}
	
	/**
	 * 加载URL
	 */
	public void loadURL()
	{
		String path = "webs.txt";
		File file = new File(path);
		SettingOperation setting = new SettingOperation(path);
		
		//判断是否存在web网址文件
		if(!file.exists())
		{
			try 
			{
				//新建web网址信息文件
				file.createNewFile();
				
				//写入默认参数
				boolean writed = setting.writeDefaultURL();
				if(writed)
					System.out.println("web网址文件不存在，已新建!");
				else
					System.out.println("web网址文件不存在，新建失败");
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
//[start] 获取网址信息
		
		int websCount = 0;
		Rolnews.parameters.getURLs().clear();  //清除存储网址信息的列表
		//获取是否限制新闻种类的设置信息
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