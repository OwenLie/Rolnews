package com.rolnews.setting;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import com.rolnews.main.Rolnews;
/**
 * 处理添加网址面板的设置请求
 * 
 * @author Owen Lie
 */
public class SetURLs implements ActionListener{

	private String txtPath = "webs.txt";
			
	@Override
	public void actionPerformed(ActionEvent e)
	{
		//获取请求命令，通过","分割后获知其带入文件操作的数据
		String[] commands = e.getActionCommand().split(",");
		switch(commands[0])
		{
//[start] 来自已知网址的请求命令
			case "knownSite":
				
				//最多添加6个网址
				int size = Rolnews.parameters.getURLs().size();
				if(size < 7)
				{
					String siteValue = "";  //网址值
					String title = "";  //网址名内容
					
					switch(commands[1])
					{
						//处理百度网址的请求
						case "souhu":
							siteValue = "http://news.sohu.com/";
							title = "新闻第一门户 -- 搜狐新闻";
							break;
						case "wangyi":
							siteValue = "http://www.163.com/";
							title = "网易新闻 -- 有态度的新闻门户";
							break;
						case "sina":
							siteValue = "http://www.sina.com.cn/";
							title = "新浪新闻中心";
							break;
						case "ifeng":
							siteValue = "http://www.ifeng.com/";
							title = "凤凰资讯 -- 凤凰网";
							break;
						case "huanqiu":
							siteValue = "http://www.huanqiu.com/";
							title = "环球网 -- 全球生活新门户";
							break;
						default:
							siteValue = "http://news.baidu.com/";
							title = "百度新闻 -- 全球最大中文新闻平台";
							break;
					}
					
					//执行数据写入或清除的文件操作
					if(((JCheckBox)e.getSource()).isSelected())
					{
						addSite(title, siteValue);
						
						//同步更新显示已有网址的面板，设计缺陷，暂时不同步
//						Rolnews.getWebsitePanel().addSiteToPanel(title, Rolnews.parameters.getURLs().size(), this);
					}
					else
						if(size > 1)
						{
							deleteSite(title);
						}
						//最后一个网址，不能设置为不选中
						else
							((JCheckBox)e.getSource()).setSelected(true);
				}
				break;
//[end]
				
//[start] 种类设置
				
			case "category":
				
				String category = "";
				switch(commands[1])
				{
					//处理百度网址的请求
					case "yule":
						category = "yule";
						break;
					case "junshi":
						category = "junshi";
						break;
					case "caijing":
						category = "caijing";
						break;
					case "tiyu":
						category = "tiyu";
						break;
					case "shehui":
						category = "shehui";
						break;
					case "keji":
						category = "keji";
						break;
					case "youxi":
						category = "youxi";
						break;
					case "lishi":
						category = "lishi";
						break;
					default:
						category = "no";
						break;
				}

				//向设置文件写入参数
				File file = new File(txtPath);
				SettingOperation setting = new SettingOperation(txtPath);
				if(file.exists())
					setting.updateParam("<category>", category);
				else
				{
					try 
					{
						//新建web网址信息文件
						file.createNewFile();
						System.out.println("setting文件不存在，已新建!");
						
						//写入默认参数
						boolean writed = setting.writeDefaultSetting();
						if(writed)
						{
							setting.updateParam("<category>", category);
							System.out.println("向setting文件写入设置参数成功!");
						}
						else
							System.out.println("向setting文件写入默认设置参数失败!");
					} 
					catch (IOException ex) 
					{
						ex.printStackTrace();
					}
				}
				break;
//[end]
				
//[start] 自定义网址

			case "selfDefine":
				
				String title = ((JTextField)(((JButton)e.getSource()).getParent().getComponent(0))).getText();
				String url = ((JTextField)(((JButton)e.getSource()).getParent().getComponent(1))).getText();
				System.out.println("获取页面传入的网址title为： " + title + "\n传入的url: " + url);
				
				//过滤输入的数据
				if(title.trim().equals("") || title.equals("  输入网站名称"))
					title = "自定义网址";
				if(!url.startsWith("http"))
					return;
				
				//验证给定的网址能否获得指定新闻
				addSite(title, url);
				
				//同步更新显示已有网址的面板，设计缺陷，暂时不同步
//				Rolnews.getWebsitePanel().addSiteToPanel(title, Rolnews.parameters.getURLs().size(), this);
				
				break;
//[end]
				
//[start] 删除网址
				
			default:
				break;
		}
		
		//重新加载网址
		new LoadURL().loadURL();
	}
	
	/**
	 * 向网址文件写入网址
	 */
	private void addSite(String title, String site)
	{
		//向设置文件写入参数
		File file = new File(txtPath);
		SettingOperation setting = new SettingOperation(txtPath);
		if(file.exists())
		{
			//将网址数据添加到设置文件中第一个能添加的位置
			for(int i = 0; i < 6; i++)
			{
				String url = setting.getParaByKey("<web" + (i + 1) + ">");
				if(url.equals(""))
				{
					//写入title
					setting.updateParam("<web" + (i + 1) + "title>", title);
					//写入网址
					setting.updateParam("<web" + (i + 1) + ">", site);
					break;
				}
			}
		}
		else
		{
			try 
			{
				//新建web网址信息文件
				file.createNewFile();
				System.out.println("setting文件不存在，已新建!");
				
				//写入默认参数
				boolean writed = setting.writeDefaultSetting();
				if(writed)
				{
					addSite(title, site);
					System.out.println("向setting文件写入设置参数成功!");
				}
				else
					System.out.println("向setting文件写入默认设置参数失败!");
			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * 删除指定网址数据，通过title辨别
	 */
	public void deleteSite(String title)
	{
		//向设置文件写入参数
		File file = new File(txtPath);
		SettingOperation setting = new SettingOperation(txtPath);
		if(file.exists())
		{
			//将网址数据添加到设置文件中第一个能添加的位置
			for(int i = 0; i < 6; i++)
			{
				String titleValue = setting.getParaByKey("<web" + (i + 1) + "title>");
				if(titleValue.equals(title))
				{
					//写入title
					setting.updateParam("<web" + (i + 1) + "title>", "");
					//写入网址
					setting.updateParam("<web" + (i + 1) + ">", "");
					break;
				}
			}
		}
		else
		{
			try 
			{
				//新建web网址信息文件
				file.createNewFile();
				System.out.println("setting文件不存在，已新建!");

				//写入默认参数
				boolean writed = setting.writeDefaultSetting();
				if(writed)
				{
					deleteSite(title);
					System.out.println("向setting文件删除设置参数成功!");
				}
				else
					System.out.println("向setting文件删除默认设置参数失败!");
			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
	}
}
