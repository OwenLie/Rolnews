package com.rolnews.setting;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.rolnews.loadnews.LoadNews;
import com.rolnews.main.Rolnews;
import com.rolnews.message.Message;
import com.rolnews.values.NewsFont;
import com.rolnews.values.ThemeColor;
import com.rolnews.windows.AboutFrame;
import com.rolnews.windows.MainFrame;
import com.rolnews.windows.SetURLPanel;

public class ExtractParams implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String command = e.getActionCommand();
		String[] levels = command.split(",");
		
		String key = null;  //存入设置文件中的数据的键
		String value = null;  //存入设置文件中的数据值
		
		switch(levels[0])
		{
		
//[start] 更换皮肤
			case "skin":
				
				ThemeColor bg = null;  //主题色
				ThemeColor enter = null;  //适配的鼠标进入色
				MainFrame mainFrame = Rolnews.getMainFrame();
				
				switch(levels[1])
				{
					case "blue":
						bg = ThemeColor.BLUE;
						enter = ThemeColor.BLUE_MOUENT;
						break;
					case "red":
						bg = ThemeColor.RED;
						enter = ThemeColor.RED_MOUENT;
						break;
					case "golden":
						bg = ThemeColor.GOLDEN;
						enter = ThemeColor.GOLDEN_MOUENT;
						break;
					case "brown":
						bg = ThemeColor.BROWN;
						enter = ThemeColor.BROWN_MOUENT;
						break;
					case "purple":
						bg = ThemeColor.PURPLE;;
						enter = ThemeColor.PURPLE_MOUENT;
						break;
					default:
						bg = ThemeColor.GREEN;
						enter = ThemeColor.GREEN_MOUENT;
						break;
				}
				
				//改变面板的颜色
				Color bgColor = bg.getValue();
				Color enterColor = enter.getValue();
				mainFrame.getContentPane().setBackground(bgColor);
				mainFrame.getLeftPanel().setBackground(bgColor);
				mainFrame.getLeftPanel().getComponent(0).setBackground(bgColor);
				mainFrame.getLeftPanel().getComponent(1).setBackground(bgColor);
				mainFrame.getRightPanel().setBackground(bgColor);
				mainFrame.getRightPanel().getComponent(0).setBackground(bgColor);
				mainFrame.getRightPanel().getComponent(1).setBackground(bgColor);
				mainFrame.getRightPanel().getComponent(2).setBackground(bgColor);
				mainFrame.getRightPanel().getComponent(3).setBackground(bgColor);
				mainFrame.getRightPanel().getComponent(4).setBackground(bgColor);
				mainFrame.getRollPanel().setBackground(bgColor);
				mainFrame.getPanelA().setBackground(bgColor);
				mainFrame.getPanelB().setBackground(bgColor);
				mainFrame.getSearchArea().setBackground(enterColor);
				mainFrame.getShrinkFrame().setBackground(bgColor);
				
				//改变Arguments中该变量的值
				Rolnews.parameters.setBgColor(bg);
				Rolnews.parameters.setMouseEnterColor(enter);
				
				//设置写入设置文件的设置参数
				key = "<themeColor>";
				value = bg.name();
				break;
//[end]
		
//[start] 更换字体
				
			case "font":
				
				NewsFont newsFont = null; //滚动新闻字体
				
				switch(levels[1])
				{
					case "song":
						newsFont = NewsFont.SONG;
						break;
					case "xihei":
						newsFont = NewsFont.HUAWENXIHEI;
						break;
					case "xinwei":
						newsFont = NewsFont.HUAWENXINWEI;
						break;
					case "fangsong":
						newsFont = NewsFont.FANGSONG;
						break;
					case "kaiti":
						newsFont = NewsFont.KAITI;
						break;
					case "lishu":
						newsFont = NewsFont.LISHU;
						break;
					case "xingkai":
						newsFont = NewsFont.HUAWENXINGKAI;
						break;
					default:
						newsFont = NewsFont.DEFAULTFONT;
						break;
				}
				
				//改变Arguments中该变量的值
				Rolnews.parameters.setLabelFont(newsFont);
				//提示正
				Rolnews.getMainFrame().actionPerformed(null);
				
				//提示正在更换字体
				Thread showMessage = new Message("正在更换字体，请稍后...", 3);
				showMessage.start();
				
				//重新加载新闻
				LoadNews.newsPointer -= LoadNews.getReadUnit();
				Thread loadNews = new Thread(new LoadNews());
				loadNews.setName("LoadNews");
				loadNews.start();
				
				//打开面板A的计时器
				Rolnews.getMainFrame().getPanelA().getTimer().start();
				Rolnews.ROLLSTATE = 1;
				
				//设置写入设置文件的设置参数
				key = "<newsFont>";
				value = newsFont.name();
				break;
//[end]
		
//[start] 强制刷新
		
			case "refresh":
				
				ActionEvent ae = new ActionEvent(new String("Hello"), 0, null);
				Rolnews.getMainFrame().actionPerformed(ae);
				
				//重置timer
				Rolnews.getTimer().restart();
				break;
//[end]
				
//[start] 关于
			
			case "about":
				
				AboutFrame about = new AboutFrame();
				about.showAbout();
				
				break;
//[end]
				
//[start] 帮助
		
			case "help":
				
				String href = "http://blog.sina.com.cn/s/blog_12cf9678e0102wrpj.html";
				try 
				{
					Runtime.getRuntime().exec("cmd /c start " + href);
				} 
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
				
				break;
//[end]
	
//[start] 是否显示菜单边框
		
			case "menuborder":
				
				key = "<menuBorder>";
				//如果当前边框为显示状态
				MainFrame mainWindow = Rolnews.getMainFrame();
				if(Rolnews.parameters.isShowBorder())
				{
					Rolnews.parameters.setShowBorder(false);
					value = "hide";
					
					//关闭所有的菜单边框
					((JPanel) mainWindow.getLeftPanel().getComponent(0)).setBorder(null);
					((JPanel) mainWindow.getLeftPanel().getComponent(1)).setBorder(null);
					((JPanel) mainWindow.getRightPanel().getComponent(1)).setBorder(null);
					((JPanel) mainWindow.getRightPanel().getComponent(2)).setBorder(null);
					((JPanel) mainWindow.getRightPanel().getComponent(3)).setBorder(null);
					((JPanel) mainWindow.getRightPanel().getComponent(4)).setBorder(null);
				}
				
				//如果当前边框为隐藏状态
				else
				{
					Rolnews.parameters.setShowBorder(true);
					value = "show";
					
					//打开菜单边框
					TitledBorder border = BorderFactory.createTitledBorder("");
					((JPanel) mainWindow.getLeftPanel().getComponent(0)).setBorder(border);
					((JPanel) mainWindow.getLeftPanel().getComponent(1)).setBorder(border);
					((JPanel) mainWindow.getRightPanel().getComponent(1)).setBorder(border);
					((JPanel) mainWindow.getRightPanel().getComponent(2)).setBorder(border);
					((JPanel) mainWindow.getRightPanel().getComponent(3)).setBorder(border);
					((JPanel) mainWindow.getRightPanel().getComponent(4)).setBorder(border);
				}
				break;
//[end]
		
//[start] 设置获取新闻超时
		
			case "loadtime":
				
				key = "<timeout>";
				switch(levels[1])
				{
					case "5":
						Rolnews.parameters.setLoadingTime(5000);
						value = "5000";
						break;
					case "10":
						Rolnews.parameters.setLoadingTime(10000);
						value = "10000";
						break;
					case "15":
						Rolnews.parameters.setLoadingTime(15000);
						value = "15000";
						break;
				}
				break;
//[end]
		
//[start] 设置向左滚动
		
			case "headleft":
				
				key = "<headleft>";
				
				Rolnews.parameters.setSpeed(0 - Rolnews.parameters.getSpeed());
				if(Rolnews.parameters.isHeadingLeft())
				{
					Rolnews.parameters.setHeadingLeft(false);
					value = "false";
				}
				else
				{
					Rolnews.parameters.setHeadingLeft(true);
					value = "true";
				}
				break;
//[end]
		
//[start] 是否使用分隔条
		
			case "usebar":
				
				key = "<useBar>";
				
				//提示正在去除分隔条
				Rolnews.getMainFrame().actionPerformed(null);
				
				if(Rolnews.parameters.isUseBar())
				{
					Rolnews.parameters.setUseBar(false);
					value = "false";
					//显示提示消息
					Thread hideUseBar = new Message("正在剔除分隔条，请稍后...", 2);
					hideUseBar.start();
				}
				else
				{
					Rolnews.parameters.setUseBar(true);
					value = "true";
					//显示提示消息
					Thread showUseBar = new Message("正在插入分隔条，请稍后...", 2);
					showUseBar.start();
				}
				
				//重新加载新闻
				LoadNews.newsPointer -= LoadNews.getReadUnit();
				Thread loadNewsThread = new Thread(new LoadNews());
				loadNewsThread.setName("LoadNews");
				loadNewsThread.start();
				
				//打开面板A的计时器
				Rolnews.getMainFrame().getPanelA().getTimer().start();
				Rolnews.ROLLSTATE = 1;
				
				break;
//[end]
		
//[start] 始终处于屏幕最前方
		
			case "topmost":
				
				key = "<topMost>";
				if(Rolnews.parameters.isTopMost())
				{
					Rolnews.parameters.setTopMost(false);
					value = "false";
				}
				else
				{
					Rolnews.parameters.setTopMost(true);
					value = "true";
				}
				
				//设置窗体当前处于屏幕最上方
				Rolnews.getMainFrame().setAlwaysOnTop(Rolnews.parameters.isTopMost());
				break;
//[end]
				
//[start]
		
			case "resizable":
				
				key = "<resizable>";
				if(Rolnews.parameters.isResizable())
				{
					Rolnews.parameters.setResizable(false);
					value = "false";
				}
				else
				{
					Rolnews.parameters.setResizable(true);
					value = "true";
				}
				break;
//[end]
		
//[start] 设置滚动速度
		
			case "speed":
				
				key = "<speed>";
				switch(levels[1])
				{
					case "fast":
						if(Rolnews.parameters.isHeadingLeft())
							Rolnews.parameters.setSpeed(3);
						else
							Rolnews.parameters.setSpeed(-3);
						value = "fast";
						break;
					case "normal":
						if(Rolnews.parameters.isHeadingLeft())
							Rolnews.parameters.setSpeed(2);
						else
							Rolnews.parameters.setSpeed(-2);
						value = "normal";
						break;
					case "slow":
						if(Rolnews.parameters.isHeadingLeft())
							Rolnews.parameters.setSpeed(1);
						else
							Rolnews.parameters.setSpeed(-1);
						value = "slow";
						break;
				}
				break;
//[end]
		
//[start] 设置自动刷新时间
		
			case "refreshtime":
				
				key = "<refresh>";
				switch(levels[1])
				{
					case "60":
						Rolnews.parameters.setRefreshTime(60);
						value = "60";
						break;
					case "120":
						Rolnews.parameters.setRefreshTime(120);
						value = "120";
						break;
					case "180":
						Rolnews.parameters.setRefreshTime(180);
						value = "180";
						break;
				}
				break;
//[end]
		
//[start]
		
			case "enableAni":
				
				//更改系统参数
				key = "<enableAni>";
				if(Rolnews.parameters.isEnableMenuAnimation())
					value = "false";
				else
					value = "true";
				break;
//[end]
				
//[start] 设置网址
		
			case "site":
				
				//停止面板滚动
				Rolnews.stopRolling();
				
				if(Rolnews.getWebsitePanel() == null && Rolnews.ROLLSTATE != 0)
				{
					MainFrame.lockRoll = true;
					
					SetURLPanel urlSettingPanel = new SetURLPanel();
					urlSettingPanel.addWindowListener(new WindowAdapter() 
					{
						public void windowClosing (WindowEvent we) 
						{
							System.out.println("hello Closing");
							MainFrame.lockRoll = false;
							
							//释放资源
							Rolnews.setWebsitePanel(null);
						}
					});
					
					Rolnews.setWebsitePanel(urlSettingPanel);
				}
				else
				{
					Rolnews.getWebsitePanel().setVisible(true);
				}
				
				break;
//[end]
		}
		
//[start] 将设置参数存入设置文件
		
		//向设置文件写入参数
//		URL url = RNPopupMenu.class.getResource("setting.txt");
		File file = new File("setting.txt");
		SettingOperation setting = new SettingOperation("setting.txt");
		if(file.exists())
		{
			setting.setParam(key, value);
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
					setting.setParam(key, value);
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
//[end]
	}
}
