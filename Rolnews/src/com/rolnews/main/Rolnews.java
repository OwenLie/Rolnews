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
 * ��������࣬������¹���
 * 
 * 1 �������ļ���ȡ���ò���
 * 2 ���Ƴ�����
 * 3 ������ʱ��������ץȡ���ŵ��߳�
 * 
 * @author Owen Lie
 */
public class Rolnews {

//[start] ˽�г�Ա������

	private static MainFrame mainFrame;
	public static Parameters parameters;
	private static Timer timer;  //ˢ�����ż�ʱ��
	private static SetURLPanel websitePanel; //��ַ�������

	/**
	 * ����״̬���������ڼ�¼��ǰ���������
	 * 
	 * 0: û��������
	 * 1: ���A�ڹ���
	 * 2: ���B�ڹ���
	 * 3: ���A��B���ڹ��� 
	 */
	public static int ROLLSTATE = 0;

//[end]
	
	/**
	 * ������ڣ�main������������¹���
	 * <li>�������ò�����
	 * <li>���ƹ������ڣ�
	 * <li>�½�ץȡ���ŵ��̻߳�ȡ���ţ�
	 */
	public static void main(String args[])
	{
		//�������ò���
		parameters = new Parameters();
		new LoadSetting().loadSetting();
		
		//����������ַ
		new LoadURL().loadURL();
		
		//���ƹ�������
		Rolnews rolnews = new Rolnews();
		rolnews.drawWindow();
		
		//��������ˢ�¼�ʱ��
		timer = new Timer(parameters.getRefreshTime() * 1000 * 60, mainFrame);
		timer.start();
		Thread.currentThread().setName("mainThread");
		
		//�½�ץȡ�����߳�
		Thread grabNews = new Thread(new GrabNews());
		grabNews.setName("GrabNews");
		grabNews.start();
		
		//��ʾ��ʾ��Ϣ
		Thread showMessage = new Message("���ڴ������ȡ���ţ����Ժ�...", 4);
		showMessage.start();
		
//[start]û����������ʱʹ�ñ����ļ�
		
//		Arguments.LINENUM = 700;
//		
//		//�½��̼߳������ţ�����jlabel����
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
//		//��������λ��
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
//		//�����A�ļ�ʱ��
//		Entrance.getMainWindow().getPanelA().getTimer().start();
//		Entrance.ROLLSTATE = 1;
		
//[end]
	}
	
	/**
	 * ���ƹ���������
	 */
	public void drawWindow()
	{
		mainFrame = MainFrame.getInstance();
		
		//��Ӷ�����尴ť
		SearchNews searchPanel = new SearchNews();
		mainFrame.getLeftPanel().add(searchPanel);
		
		//�����������ť, -0.25
		MenuButton headingLeft = new HeadingLeft(0.75);
		mainFrame.getLeftPanel().add(headingLeft);
		
		//������ҿ����ť
		MenuButton headingRight = new HeadingRight(0.00);
		mainFrame.getRightPanel().add(headingRight);
		
		//�����С����ť
		MenuButton minimizeFrame = new MinimizeFrame(1.00);
		mainFrame.getRightPanel().add(minimizeFrame);
		
		//��ӹرճ���ť
		MenuButton closeFrame = new CloseFrame(2.00);
		mainFrame.getRightPanel().add(closeFrame);
		
		//��ӹرճ���ť
		MenuButton settingButton = new SettingButton(3.00);
		mainFrame.getRightPanel().add(settingButton);
		
		mainFrame.repaint();
		
		//��ӽ���ʽ��ǩ
//		String[] items = {"��", "������"};
//		InteractionLabel interactionLabel = InteractionLabel.getInstance("�Ƿ�ʹ�ñ�������?", items);
//		mainFrame.getRollPanel().add(interactionLabel);
	}

	/**
	 * ����״̬������set�������������õĹ���״ֵ̬���ļ�ʱ����������ֹͣ״̬
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
	 * ��ʼ����
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
	 * ֹͣ����
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
		
		//����״̬Ϊ0�����ٲ��õ�����¹�����һ�εı����ļ�
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
