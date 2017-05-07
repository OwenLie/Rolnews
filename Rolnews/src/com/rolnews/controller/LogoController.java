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
 * ������Ϣ������
 * @author Owen Lie
 */
public class LogoController implements Runnable{

	private Random rand;   //��������󣬲���10��20֮�������������������߳�˯��ʱ��
	private static boolean controllerOn = false;  //�������Ƿ���������
	
	public LogoController()
	{
		controllerOn = true;
	}
	
	@Override
	public void run()
	{
		//��ʼ���߳�Ҫʹ�õĸ��ֶ���
		JLabel logoA = ((JLabel)AdPanel.getInstance().getComponent(0));  //logoA�ؼ�
		JLabel logoB = ((JLabel)AdPanel.getInstance().getComponent(1));  //logoB�ؼ�
		FlashUI logoADropDown = new AnimationFactory().getTopToBottom();  //�������ƶ���
		FlashUI logoBDropDown = new AnimationFactory().getTopToBottom();  //�������ƶ���
		Thread logoAThread = null;  //�����̶߳���
		Thread logoBThread = null;
		Timer logoATimer = new Timer(40, logoADropDown);  //��ʱ������
		Timer logoBTimer = new Timer(40, logoBDropDown);  //��ʱ������
		
		while(Rolnews.parameters.isEnableMenuAnimation())
		{
			//�߳�˯��һ��ʱ��
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
			
			//���������л���ʾ�������ǰlogo����ʾ����logo�˳�
			if(logoA.getLocation().y == 1)
			{
				logoADropDown.setArguments(logoA, logoATimer, 20, 0);
				logoAThread = new Thread(logoADropDown);
				logoAThread.start();
			}
			
			//��һ��logo����
			resetLogo(getSource(), 2);
			logoB.setLocation(0, -21);
			logoBDropDown.setArguments(logoB, logoBTimer, 0, 0);
			logoBThread = new Thread(logoBDropDown);
			logoBThread.start();
			
			//�߳�˯��һ��ʱ��
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
			
			//���������л���ʾ�������ǰlogo����ʾ����logo�˳�
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
	
	//����10��20֮��������
	private int generateRandom(int range)
	{
		if(rand == null)
			rand = new Random();
		
		return rand.nextInt(range) + 10;
	}

	//��֪��ǰ��ʾ�����ŵ���Դ
	private NewsSource getSource()
	{
		int state = Rolnews.ROLLSTATE;  //��֪��ǰ�ĸ�����ڹ���
		RollPanel showingPanel = null;  //��ǰ��ʾ������
		int location = 0;  //��������λ��
		if(state == 1)
			showingPanel = Rolnews.getMainFrame().getPanelA();
		else if(state == 2)
			showingPanel = Rolnews.getMainFrame().getPanelB();
		else
			return NewsSource.Rolnews;
		
		int n = 0;  //��ǰ��ʾ���ǵڼ������ţ����㣩
		
		//���㵱ǰ��ʾ���ǵڼ������ţ�ͨ����������λ����߽��λ��������
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
		
		//��ȡ������Դ
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
	
	//����logo
	private void resetLogo(NewsSource source, int flag)
	{
		URL logo = RNPopupMenu.class.getResource("news.png");
		
		//���ݲ�ͬ���������logo
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
		
		//����logo
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
