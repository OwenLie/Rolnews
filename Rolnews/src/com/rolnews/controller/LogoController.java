package com.rolnews.controller;

import java.awt.Image;
import java.net.URL;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import com.rolnews.animation.AnimationFactory;
import com.rolnews.animation.FlashUI;
import com.rolnews.loadnews.LoadNews;
import com.rolnews.main.Rolnews;
import com.rolnews.values.NewsSource;
import com.rolnews.values.ThemeColor;
import com.rolnews.windows.AdPanel;
import com.rolnews.windows.RNPopupMenu;
import com.rolnews.windows.RollPanel;

/**
 * 额外信息控制类
 * @author Owen Lie
 */
public class LogoController implements Runnable{

	private Random rand;   //随机数对象，产生10到20之间的随机数，用来决定线程睡眠时间
	private static boolean controllerOn = false;  //控制器是否正在运行
	
	public LogoController()
	{
		controllerOn = true;
	}
	
	@Override
	public void run()
	{
		//初始化线程要使用的各种对象
		JLabel logoA = ((JLabel)AdPanel.getInstance().getComponent(0));  //logoA控件
		JLabel logoB = ((JLabel)AdPanel.getInstance().getComponent(1));  //logoB控件
		FlashUI logoADropDown = new AnimationFactory().getTopToBottom();  //动画控制对象
		FlashUI logoBDropDown = new AnimationFactory().getTopToBottom();  //动画控制对象
		Thread logoAThread = null;  //动画线程对象
		Thread logoBThread = null;
		Timer logoATimer = new Timer(40, logoADropDown);  //计时器对象
		Timer logoBTimer = new Timer(40, logoBDropDown);  //计时器对象
		
		while(Rolnews.parameters.isEnableMenuAnimation())
		{
			//线程睡眠一段时间
			try 
			{
				int ran = generateRandom(10);
				
				Thread.currentThread();
				Thread.sleep(ran * 1000);
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			//动画下推切换显示，如果当前logo在显示，则logo退出
			if(logoA.getLocation().y == 1)
			{
				logoADropDown.setArguments(logoA, logoATimer, 20, 0);
				logoAThread = new Thread(logoADropDown);
				logoAThread.start();
			}
			
			//另一个logo进入
			resetLogo(getSource(), 2);
			logoB.setLocation(0, -21);
			logoBDropDown.setArguments(logoB, logoBTimer, 0, 0);
			logoBThread = new Thread(logoBDropDown);
			logoBThread.start();
			
			//线程睡眠一段时间
			try 
			{
				int ran = generateRandom(10);
				
				Thread.currentThread();
				Thread.sleep(ran * 1000);
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			//动画下推切换显示，如果当前logo在显示，则logo退出
			if(logoB.getLocation().y == 1)
			{
				logoBDropDown.setArguments(logoB, logoBTimer, 20, 0);
				logoBThread = new Thread(logoBDropDown);
				logoBThread.start();
			}
			
			resetLogo(getSource(), 1);
			logoA.setLocation(0, -21);
			logoADropDown.setArguments(logoA, logoATimer, 0, 0);
			logoAThread = new Thread(logoADropDown);
			logoAThread.start();
		}
		
		controllerOn = false;
	}
	
	//生成10到20之间的随机数
	private int generateRandom(int range)
	{
		if(rand == null)
			rand = new Random();
		
		return rand.nextInt(range) + 10;
	}

	//获知当前显示的新闻的来源
	private NewsSource getSource()
	{
		int state = Rolnews.ROLLSTATE;  //获知当前哪个面板在滚动
		RollPanel showingPanel = null;  //当前显示的新闻
		int location = 0;  //滚动面板的位置
		if(state == 1)
			showingPanel = Rolnews.getMainFrame().getPanelA();
		else if(state == 2)
			showingPanel = Rolnews.getMainFrame().getPanelB();
		else
			return NewsSource.Rolnews;
		
		int n = 0;  //当前显示的是第几条新闻（估算）
		
		//估算当前显示的是第几条新闻，通过滚动面板的位置与边界点位置来计算
		if(Rolnews.parameters.isHeadingLeft())
		{
			location = showingPanel.getLocation().x;
			n = (int)((Rolnews.parameters.getLeftBorder() - location) * LoadNews.getReadUnit() / showingPanel.getWidth()) + 1;
		}
		else
		{
			location = showingPanel.getLocation().x + showingPanel.getWidth() - Rolnews.parameters.getLeftBorder();
			n = 4 - ((int)((location - Rolnews.parameters.getRightBorder()) * LoadNews.getReadUnit() / showingPanel.getWidth()) + 1);
		}
		
		//获取新闻来源
		if(n < 0 || n > 4)
			return NewsSource.Rolnews;
		NewsSource source = NewsSource.Rolnews;
		if(LoadNews.newsBuffer[n][1].contains("baidu"))
			source = NewsSource.Baidu;
		else if(LoadNews.newsBuffer[n][1].contains("cnr"))
			source = NewsSource.CNR;
		else if(LoadNews.newsBuffer[n][1].contains("sohu"))
			source = NewsSource.Souhu;
		else if(LoadNews.newsBuffer[n][1].contains("163.com"))
			source = NewsSource.EaseNet;
		else if(LoadNews.newsBuffer[n][1].contains("sina"))
			source = NewsSource.Sina;
		else if(LoadNews.newsBuffer[n][1].contains("ifeng"))
			source = NewsSource.iFeng;
		else if(LoadNews.newsBuffer[n][1].contains("huanqiu"))
			source = NewsSource.Huanqiu;
		
		return source;
	}
	
	//重置logo
	private void resetLogo(NewsSource source, int flag)
	{
		URL logo = RNPopupMenu.class.getResource("news.png");
		
		//根据不同的主题分配logo
		switch(source)
		{
			case Baidu:
				logo = RNPopupMenu.class.getResource("baidu.jpg");
				break;
			case CNR:
				if(Rolnews.parameters.getBgColor() == ThemeColor.RED)
					logo = RNPopupMenu.class.getResource("cnr1.png");
				else
					logo = RNPopupMenu.class.getResource("cnr.png");
				break;
			case Souhu:
				if(Rolnews.parameters.getBgColor() == ThemeColor.BROWN)
					logo = RNPopupMenu.class.getResource("souhu1.png");
				else
					logo = RNPopupMenu.class.getResource("souhu.png");
				break;
			case EaseNet:
				if(Rolnews.parameters.getBgColor() == ThemeColor.RED || Rolnews.parameters.getBgColor() == ThemeColor.BROWN)
					logo = RNPopupMenu.class.getResource("wangyi1.png");
				else
					logo = RNPopupMenu.class.getResource("wangyi.png");
				break;
			case Sina:
				if(Rolnews.parameters.getBgColor() == ThemeColor.PURPLE || Rolnews.parameters.getBgColor() == ThemeColor.BROWN ||
				   Rolnews.parameters.getBgColor() == ThemeColor.BLUE || Rolnews.parameters.getBgColor() == ThemeColor.RED)
					logo = RNPopupMenu.class.getResource("sina1.png");
				else
					logo = RNPopupMenu.class.getResource("sina.png");
				break;
			case iFeng:
				if(Rolnews.parameters.getBgColor() == ThemeColor.BROWN || Rolnews.parameters.getBgColor() == ThemeColor.RED ||
				   Rolnews.parameters.getBgColor() == ThemeColor.PURPLE || Rolnews.parameters.getBgColor() == ThemeColor.GREEN)
					logo = RNPopupMenu.class.getResource("ifeng1.png");
				else
					logo = RNPopupMenu.class.getResource("ifeng.png");
				break;
			case Huanqiu:
				if(Rolnews.parameters.getBgColor() == ThemeColor.RED || Rolnews.parameters.getBgColor() == ThemeColor.BROWN)
					logo = RNPopupMenu.class.getResource("huanqiu1.png");
				else
					logo = RNPopupMenu.class.getResource("huanqiu.png");
				break;
			default:
				if(Rolnews.parameters.getBgColor() == ThemeColor.BROWN || Rolnews.parameters.getBgColor() == ThemeColor.RED)
					logo = RNPopupMenu.class.getResource("news1.png");
				else
					logo = RNPopupMenu.class.getResource("news.png");
				break;
		}
		
		//重置logo
		ImageIcon icon = new ImageIcon(logo);
		icon.setImage(icon.getImage().getScaledInstance(80, 20, Image.SCALE_DEFAULT));
		if(flag == 1)
			AdPanel.getInstance().getLogoA().setIcon(icon);
		else
			AdPanel.getInstance().getLogoB().setIcon(icon);
	}

	public static boolean isControllerOn() { return controllerOn; }
	public static void setControllerOn(boolean controllerOn) { LogoController.controllerOn = controllerOn; }
}
