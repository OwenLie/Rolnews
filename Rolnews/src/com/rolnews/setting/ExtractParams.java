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
		
		String key = null;  //���������ļ��е����ݵļ�
		String value = null;  //���������ļ��е�����ֵ
		
		switch(levels[0])
		{
		
//[start] ����Ƥ��
			case "skin":
				
				ThemeColor bg = null;  //����ɫ
				ThemeColor enter = null;  //�����������ɫ
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
				
				//�ı�������ɫ
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
				
				//�ı�Arguments�иñ�����ֵ
				Rolnews.parameters.setBgColor(bg);
				Rolnews.parameters.setMouseEnterColor(enter);
				
				//����д�������ļ������ò���
				key = "<themeColor>";
				value = bg.name();
				break;
//[end]
		
//[start] ��������
				
			case "font":
				
				NewsFont newsFont = null; //������������
				
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
				
				//�ı�Arguments�иñ�����ֵ
				Rolnews.parameters.setLabelFont(newsFont);
				//��ʾ��
				Rolnews.getMainFrame().actionPerformed(null);
				
				//��ʾ���ڸ�������
				Thread showMessage = new Message("���ڸ������壬���Ժ�...", 3);
				showMessage.start();
				
				//���¼�������
				LoadNews.newsPointer -= LoadNews.getReadUnit();
				Thread loadNews = new Thread(new LoadNews());
				loadNews.setName("LoadNews");
				loadNews.start();
				
				//�����A�ļ�ʱ��
				Rolnews.getMainFrame().getPanelA().getTimer().start();
				Rolnews.ROLLSTATE = 1;
				
				//����д�������ļ������ò���
				key = "<newsFont>";
				value = newsFont.name();
				break;
//[end]
		
//[start] ǿ��ˢ��
		
			case "refresh":
				
				ActionEvent ae = new ActionEvent(new String("Hello"), 0, null);
				Rolnews.getMainFrame().actionPerformed(ae);
				
				//����timer
				Rolnews.getTimer().restart();
				break;
//[end]
				
//[start] ����
			
			case "about":
				
				AboutFrame about = new AboutFrame();
				about.showAbout();
				
				break;
//[end]
				
//[start] ����
		
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
	
//[start] �Ƿ���ʾ�˵��߿�
		
			case "menuborder":
				
				key = "<menuBorder>";
				//�����ǰ�߿�Ϊ��ʾ״̬
				MainFrame mainWindow = Rolnews.getMainFrame();
				if(Rolnews.parameters.isShowBorder())
				{
					Rolnews.parameters.setShowBorder(false);
					value = "hide";
					
					//�ر����еĲ˵��߿�
					((JPanel) mainWindow.getLeftPanel().getComponent(0)).setBorder(null);
					((JPanel) mainWindow.getLeftPanel().getComponent(1)).setBorder(null);
					((JPanel) mainWindow.getRightPanel().getComponent(1)).setBorder(null);
					((JPanel) mainWindow.getRightPanel().getComponent(2)).setBorder(null);
					((JPanel) mainWindow.getRightPanel().getComponent(3)).setBorder(null);
					((JPanel) mainWindow.getRightPanel().getComponent(4)).setBorder(null);
				}
				
				//�����ǰ�߿�Ϊ����״̬
				else
				{
					Rolnews.parameters.setShowBorder(true);
					value = "show";
					
					//�򿪲˵��߿�
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
		
//[start] ���û�ȡ���ų�ʱ
		
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
		
//[start] �����������
		
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
		
//[start] �Ƿ�ʹ�÷ָ���
		
			case "usebar":
				
				key = "<useBar>";
				
				//��ʾ����ȥ���ָ���
				Rolnews.getMainFrame().actionPerformed(null);
				
				if(Rolnews.parameters.isUseBar())
				{
					Rolnews.parameters.setUseBar(false);
					value = "false";
					//��ʾ��ʾ��Ϣ
					Thread hideUseBar = new Message("�����޳��ָ��������Ժ�...", 2);
					hideUseBar.start();
				}
				else
				{
					Rolnews.parameters.setUseBar(true);
					value = "true";
					//��ʾ��ʾ��Ϣ
					Thread showUseBar = new Message("���ڲ���ָ��������Ժ�...", 2);
					showUseBar.start();
				}
				
				//���¼�������
				LoadNews.newsPointer -= LoadNews.getReadUnit();
				Thread loadNewsThread = new Thread(new LoadNews());
				loadNewsThread.setName("LoadNews");
				loadNewsThread.start();
				
				//�����A�ļ�ʱ��
				Rolnews.getMainFrame().getPanelA().getTimer().start();
				Rolnews.ROLLSTATE = 1;
				
				break;
//[end]
		
//[start] ʼ�մ�����Ļ��ǰ��
		
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
				
				//���ô��嵱ǰ������Ļ���Ϸ�
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
		
//[start] ���ù����ٶ�
		
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
		
//[start] �����Զ�ˢ��ʱ��
		
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
				
				//����ϵͳ����
				key = "<enableAni>";
				if(Rolnews.parameters.isEnableMenuAnimation())
					value = "false";
				else
					value = "true";
				break;
//[end]
				
//[start] ������ַ
		
			case "site":
				
				//ֹͣ������
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
							
							//�ͷ���Դ
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
		
//[start] �����ò������������ļ�
		
		//�������ļ�д�����
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
				//�½�web��ַ��Ϣ�ļ�
				file.createNewFile();
				System.out.println("setting�ļ������ڣ����½�!");
				
				//д��Ĭ�ϲ���
				boolean writed = setting.writeDefaultSetting();
				if(writed)
				{
					setting.setParam(key, value);
					System.out.println("��setting�ļ�д�����ò����ɹ�!");
				}
				else
					System.out.println("��setting�ļ�д��Ĭ�����ò���ʧ��!");
			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
//[end]
	}
}
