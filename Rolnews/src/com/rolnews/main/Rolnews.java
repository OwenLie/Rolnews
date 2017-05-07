package com.rolnews.main;

import javax.swing.Timer;

import com.rolnews.grabnews.GrabNews;
import com.rolnews.message.Message;
import com.rolnews.setting.LoadSetting;
import com.rolnews.setting.LoadURL;
import com.rolnews.values.Parameters;
import com.rolnews.windows.CloseFrame;
import com.rolnews.windows.HeadingLeft;
import com.rolnews.windows.HeadingRight;
import com.rolnews.windows.MainFrame;
import com.rolnews.windows.MenuButton;
import com.rolnews.windows.MinimizeFrame;
import com.rolnews.windows.SearchNews;
import com.rolnews.windows.SetURLPanel;
import com.rolnews.windows.SettingButton;

/**
 * 程序入口类，完成以下过程
 * 
 * 1 从设置文件获取设置参数
 * 2 绘制程序窗体
 * 3 启动计时器，开启抓取新闻的线程
 * 
 * @author Owen Lie
 */
public class Rolnews {

//[start] 私有成员变量区

	private static MainFrame mainFrame;
	public static Parameters parameters;
	private static Timer timer;  //刷新新闻计时器
	private static SetURLPanel websitePanel; //网址设置面板

	/**
	 * 滚动状态变量，用于记录当前滚动的面板
	 * 
	 * 0: 没有面板滚动
	 * 1: 面板A在滚动
	 * 2: 面板B在滚动
	 * 3: 面板A和B都在滚动 
	 */
	public static int ROLLSTATE = 0;

//[end]
	
	/**
	 * 程序入口，main方法，完成以下过程
	 * <li>加载设置参数；
	 * <li>绘制滚动窗口；
	 * <li>新建抓取新闻的线程获取新闻；
	 */
	public static void main(String args[])
	{
		//加载设置参数
		parameters = new Parameters();
		new LoadSetting().loadSetting();
		
		//加载新闻网址
		new LoadURL().loadURL();
		
		//绘制滚动窗体
		Rolnews rolnews = new Rolnews();
		rolnews.drawWindow();
		
		//启动新闻刷新计时器
		timer = new Timer(parameters.getRefreshTime() * 1000 * 60, mainFrame);
		timer.start();
		Thread.currentThread().setName("mainThread");
		
		//新建抓取新闻线程
		Thread grabNews = new Thread(new GrabNews());
		grabNews.setName("GrabNews");
		grabNews.start();
		
		//显示提示消息
		Thread showMessage = new Message("正在从网络获取新闻，请稍后...", 4);
		showMessage.start();
		
//[start]没有网络连接时使用本地文件
		
//		Arguments.LINENUM = 700;
//		
//		//新建线程加载新闻，生成jlabel对象
//		Thread loadNews = new Thread(new LoadNews());
//		loadNews.setName("LoadNews");
//		loadNews.start();
//		
//		try {
//			loadNews.join();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		//重置面板的位置
//		if(Arguments.HEADINGLEFT)
//		{
//			Entrance.getMainWindow().getPanelA().setLocation(Arguments.RIGHTBORDER, 0);
//			Entrance.getMainWindow().getPanelB().setLocation(Arguments.RIGHTBORDER, 0);
//		}
//		else
//		{
//			Entrance.getMainWindow().getPanelA().setLocation
//			(new Point(Arguments.LEFTBORDER - Entrance.getMainWindow().getPanelA().getWidth(), 0));
//			Entrance.getMainWindow().getPanelB().setLocation
//			(new Point(Arguments.LEFTBORDER - Entrance.getMainWindow().getPanelB().getWidth(), 0));
//		}
//		//打开面板A的计时器
//		Entrance.getMainWindow().getPanelA().getTimer().start();
//		Entrance.ROLLSTATE = 1;
		
//[end]
	}
	
	/**
	 * 绘制滚动条窗体
	 */
	public void drawWindow()
	{
		mainFrame = MainFrame.getInstance();
		
		//添加额外面板按钮
		SearchNews searchPanel = new SearchNews();
		mainFrame.getLeftPanel().add(searchPanel);
		
		//添加向左快进按钮, -0.25
		MenuButton headingLeft = new HeadingLeft(0.75);
		mainFrame.getLeftPanel().add(headingLeft);
		
		//添加向右快进按钮
		MenuButton headingRight = new HeadingRight(0.00);
		mainFrame.getRightPanel().add(headingRight);
		
		//添加最小化按钮
		MenuButton minimizeFrame = new MinimizeFrame(1.00);
		mainFrame.getRightPanel().add(minimizeFrame);
		
		//添加关闭程序按钮
		MenuButton closeFrame = new CloseFrame(2.00);
		mainFrame.getRightPanel().add(closeFrame);
		
		//添加关闭程序按钮
		MenuButton settingButton = new SettingButton(3.00);
		mainFrame.getRightPanel().add(settingButton);
		
		mainFrame.repaint();
		
		//添加交互式标签
//		String[] items = {"是", "不用了"};
//		InteractionLabel interactionLabel = InteractionLabel.getInstance("是否使用备用数据?", items);
//		mainFrame.getRollPanel().add(interactionLabel);
	}

	/**
	 * 滚动状态变量的set方法，根据设置的滚动状态值更改计时器的启动和停止状态
	 */
	public static void setRollState(int RollState)
	{
		ROLLSTATE = RollState;

		if(ROLLSTATE == 0)
		{
			mainFrame.getPanelA().getTimer().stop();
			mainFrame.getPanelB().getTimer().stop();
		}
		else if(ROLLSTATE == 1)
		{
			mainFrame.getPanelB().getTimer().stop();
		}
		else if(ROLLSTATE == 2)
		{
			mainFrame.getPanelA().getTimer().stop();
		}
		else
		{
			mainFrame.getPanelA().getTimer().start();
			mainFrame.getPanelB().getTimer().start();
		}
	}
	
	/**
	 * 开始滚动
	 */
	public static void startRolling()
	{
		if(ROLLSTATE == 1)
			Rolnews.getMainFrame().getPanelA().getTimer().start();
		else if(ROLLSTATE == 2)
			Rolnews.getMainFrame().getPanelB().getTimer().start();
		else if(ROLLSTATE == 3)
		{
			Rolnews.getMainFrame().getPanelA().getTimer().start();
			Rolnews.getMainFrame().getPanelB().getTimer().start();
		}
			
		if(ROLLSTATE == 0)
		{
			mainFrame.getPanelA().getTimer().start();
			ROLLSTATE = 1;
		}
	}
	
	/**
	 * 停止滚动
	 */
	public static void stopRolling()
	{
		if(ROLLSTATE == 1)
			Rolnews.getMainFrame().getPanelA().getTimer().stop();
		else if(ROLLSTATE == 2)
			Rolnews.getMainFrame().getPanelB().getTimer().stop();
		else if(ROLLSTATE == 3)
		{
			Rolnews.getMainFrame().getPanelA().getTimer().stop();
			Rolnews.getMainFrame().getPanelB().getTimer().stop();
		}
		
		//滚动状态为0，网速不好的情况下滚动上一次的备份文件
		else
		{
			
		}
	}
	
	public static int getRollState(){ return ROLLSTATE;}
	
	public static MainFrame getMainFrame() { return mainFrame; }
	public static void setMainFrame(MainFrame mainFrame) { Rolnews.mainFrame = mainFrame; }
	
	public static Timer getTimer() { return timer; }
	public static void setTimer(Timer timer) { Rolnews.timer = timer; }

	public static SetURLPanel getWebsitePanel() { return websitePanel; }
	public static void setWebsitePanel(SetURLPanel websitePanel) { Rolnews.websitePanel = websitePanel; }
}
