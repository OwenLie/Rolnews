package com.rolnews.windows;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import com.rolnews.animation.AnimateMenu;
import com.rolnews.controller.LogoController;
import com.rolnews.grabnews.GrabNews;
import com.rolnews.loadnews.JLabelFactory;
import com.rolnews.main.Rolnews;
import com.rolnews.message.Message;
import com.rolnews.setting.LoadURL;

/**
 * ���������壬�����������Ļ���
 * <ol>
 * <li>��������壨functionPanel);
 * <li>�����(leftPanel);
 * <li>������壨rollPanel);
 * <li>�����(rightPanel);
 * </ol>
 * 
 * @author Owen Lie
 */
public class MainFrame extends JFrame implements ActionListener{

//[start]˽�г�Ա������
	
	//��̬��������
	private static MainFrame mainFrame;  //������
	private JPanel leftPanel;  //����壬����������ť
	private JPanel rightPanel;  //����壬�����ҿ������С�����رյȲ˵���ť
	private JPanel rollPanel;  //������壬�ṩ������Ļ����������
	private RollPanel panelA;
	private RollPanel panelB;
	private JLabel message;  //ϵͳ��ʾ��
	private JPanel adPanel;  //������ʾ��Ϣ����Ҫ����ͼƬ��ʾ��

	private Color bgColor = Rolnews.parameters.getBgColor().getValue();  //��������ɫ
	private int padding = Rolnews.parameters.getPadding();  //�����Դ���ļ��
	private int windowWidth = 600;  //�������
	private int windowHeight = 30;  //�������

	private JTextField searchArea;  //������
	private ShrinkFrame shrinkFrame;  //��С������İ�ť
	
	public static boolean lockRoll = false;  //������������Ϊtrueʱ����岻��ͨ������뿪����������ʼ����

//[end]
	
	/**���캯������ʼ�����岢��Ӹ����*/
	private MainFrame()
	{
		//���������壬�����ø������
		this.setUndecorated(true);  //ȥ���߿�
		if(Rolnews.parameters.isTopMost())
			this.setAlwaysOnTop(true);
		this.setLayout(null);  //����Ϊ���Բ���
		this.setResizable(false);  //��������������С
		this.getContentPane().setBackground(bgColor);  //���ô��屳��ɫ
		this.setBounds(700,60, windowWidth,windowHeight);  //���ó�ʼλ�úʹ����С
		this.setVisible(true);  //��ʾ����
		
		int height = windowHeight - 2 * padding;
		
		//����������������
		leftPanel = new JPanel();
		leftPanel.setLayout(null);  //����Ϊ���Բ���
		leftPanel.setBackground(bgColor);  //���ô��屳��ɫ
		leftPanel.setBounds(padding,
							padding,
							windowHeight - padding + height,
							height);  //���ó�ʼλ�úʹ����С

		//����������������
		rightPanel = new JPanel();
		rightPanel.setLayout(null);  //����Ϊ���Բ���
		rightPanel.setBackground(bgColor);  //���ô��屳��ɫ
		rightPanel.setBounds(windowWidth - 2 * padding - 4 * height,
							 padding,
							 4 * height + padding,
							 height);  //���ó�ʼλ�úʹ����С
		
		adPanel = AdPanel.getInstance();
		rightPanel.add(adPanel);

		panelA = new RollPanel(1);
		panelB = new RollPanel(2);
		
		message = new JLabel("Hello Owen");
		message.setHorizontalAlignment(SwingConstants.CENTER);
		message.setFont(Rolnews.parameters.getLabelFont().getLabelFont().getFont());
		message.setBackground(Color.DARK_GRAY);
		message.setForeground(Rolnews.parameters.getLabelFont().getLabelFont().getFontColor());
		message.setBounds(0, 21, 480, 20);
		
		//����������ӹ������
		rollPanel = new JPanel();
		rollPanel.setLayout(null);
		rollPanel.setBackground(bgColor);
		rollPanel.setBounds(2 * padding + 2 * height,
				padding,
				windowWidth - (5 * height + 2 * padding) - (2 * padding + height),
				height);
		
		//��������������
		rollPanel.add(panelA);
		rollPanel.add(panelB);
		rollPanel.add(message);
		
		//������
		searchArea = new JTextField();
		searchArea.setFont(new Font("������κ", 1, 18));
		searchArea.setBounds(50, 35, 525, 20);
		searchArea.setBackground(Rolnews.parameters.getMouseEnterColor().getValue());
		searchArea.setForeground(new Color(255, 255, 255, 255));
		searchArea.setBorder(null);
		searchArea.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent e)
			{
				//����س���
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					try 
					{
						Runtime.getRuntime().exec("cmd /c start www.baidu.com/s?wd=" + searchArea.getText());
					} 
					catch (IOException e1)
					{
						e1.printStackTrace();
					}
					
					System.out.println(searchArea.getText());
				}
			}
			
			@Override
			public void keyTyped(KeyEvent e) { }
			@Override
			public void keyReleased(KeyEvent e) { }
		});
		
		//���������尴ť
		shrinkFrame = ShrinkFrame.getInstance();
		
		this.add(rightPanel);
		this.add(leftPanel);
		this.add(rollPanel);
		this.add(shrinkFrame);
		this.add(searchArea);
		
		this.setDragable();  //���ô�����϶�
		this.setMouseEvent();  //������������뿪�¼�
		
		mainFrame = this;
	}
	
	/**
	 * ��ȡʵ�����󣬴���Ϊ������
	 */
	public static MainFrame getInstance()
	{
		if(mainFrame == null)
			mainFrame = new MainFrame();
		
		return mainFrame;
	}
	
//[start] ���������¼�
	
	private Point location = null;
	private Point tempLoc = null;
	public static boolean isDragged = false;
	public static boolean isExtending = false;  

	/**
	 * ��������϶��¼�
	 */
	private void setDragable()
	{
		this.addMouseListener(new MouseAdapter()
		{
			public void mouseReleased(MouseEvent e)
			{
				isDragged = false;
				isExtending = false;
				mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				
				if(Rolnews.parameters.isResizable())
				{
					//���ô��峤�ȣ�������峤�ȣ�������֪ͨ��Ϣλ�ú��ұ߽��λ��
					int len = windowHeight - 2 * padding;
					windowWidth = mainFrame.getSize().width;
					rollPanel.setSize((int)(windowWidth - (4 * len + 2 * padding) - (2 * padding + 2 * len)), windowHeight - 2 * padding);
					message.setLocation((int)(rollPanel.getWidth()/2 - message.getWidth()/2), message.getLocation().y);
					Rolnews.parameters.setRightBorder(rightPanel.getLocation().x + 10);
					
					//�����ұ߽�ĵȴ��������ŵĹ�������λ��
					if(Rolnews.ROLLSTATE == 1)
						panelB.setLocation(Rolnews.parameters.getRightBorder(), panelB.getLocation().y);
					else if(Rolnews.ROLLSTATE == 2)
						panelA.setLocation(Rolnews.parameters.getRightBorder(), panelA.getLocation().y);
				}
			}

			public void mousePressed(MouseEvent e)
			{
				tempLoc = new Point(e.getX(), e.getY());
				
				//��겻�Ƿŵ�������ĩβʱ��ʾ�϶�
				if(e.getPoint().x < (mainFrame.getSize().width - padding))
				{
					isDragged = true;
					mainFrame.setCursor(new Cursor(Cursor.MOVE_CURSOR));
				}
				else
				{
					isExtending = true;
					if(!Rolnews.parameters.isResizable())
					{
						isDragged = true;
						mainFrame.setCursor(new Cursor(Cursor.MOVE_CURSOR));
					}
				}
			}
		});

		this.addMouseMotionListener(new MouseMotionAdapter()
		{
			public void mouseDragged(MouseEvent e)
			{
				location = new Point
						((int)(mainFrame.getLocation().x + e.getX() - tempLoc.getX()),
								(int)(mainFrame.getLocation().y + e.getY() - tempLoc.getY()));
				if(isDragged)
					mainFrame.setLocation(location);
				else
				{
					if(Rolnews.parameters.isResizable())
					{
						int len = windowHeight - 2 * padding;
						int width = (int)(windowWidth + e.getX() - tempLoc.getX());
						if(width > 600 && width < 1920)
						{
							mainFrame.setSize(width, mainFrame.getSize().height);
							rightPanel.setLocation(new Point((int)(mainFrame.getSize().width - 4 * len - 2 * padding), padding));
						}
					}
				}
			}
		});
	}
	
	/**
	 * ����������¼�
	 */
	public static boolean menuShowing = true;  //��־�˵���ť�Ƿ�������ʾ
	public static boolean lockAnimation = false;  //�����˵�����
	private void setMouseEvent()
	{
		this.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				mainFrame.getContentPane().setBackground
				(Rolnews.parameters.getMouseEnterColor().getValue());
				
				if(e.getPoint().x > (mainFrame.getSize().width - padding))
				{
					if(Rolnews.parameters.isResizable())
						mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
				}
				
				//����˵�����û�������������˵�����
				if(!lockAnimation)
					if(!menuShowing)
					{
						lockAnimation = true;
						
						AnimateMenu aniMenu = new AnimateMenu();
						Timer timer = new Timer(20, aniMenu);
						aniMenu.setComponents(timer, (MenuButton)Rolnews.getMainFrame().getRightPanel().getComponent(1),
													 (MenuButton)Rolnews.getMainFrame().getRightPanel().getComponent(2),
													 (MenuButton)Rolnews.getMainFrame().getRightPanel().getComponent(3),
													 (MenuButton)Rolnews.getMainFrame().getRightPanel().getComponent(4));
						Thread menuAniThread = new Thread(aniMenu);
						menuAniThread.start();
					}
				//���ƶ����ı��������ƶ�������
				MenuButton.keepSleeping = false;
			}
			
			public void mouseExited(MouseEvent e)
			{
				mainFrame.getContentPane().setBackground
				(Rolnews.parameters.getBgColor().getValue());
				
				mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				
				//����˵�����û�������������˵�����
				if(!lockAnimation)
					if(menuShowing)
					{
						lockAnimation = true;
						
						AnimateMenu aniMenu = new AnimateMenu();
						Timer timer = new Timer(20, aniMenu);
						aniMenu.setComponents(timer, (MenuButton)Rolnews.getMainFrame().getRightPanel().getComponent(1),
													 (MenuButton)Rolnews.getMainFrame().getRightPanel().getComponent(2),
													 (MenuButton)Rolnews.getMainFrame().getRightPanel().getComponent(3),
													 (MenuButton)Rolnews.getMainFrame().getRightPanel().getComponent(4));
						Thread menuAniThread = new Thread(aniMenu);
						menuAniThread.start();
						
						//����AdPanel���л������߳�
						if(!LogoController.isControllerOn() && Rolnews.parameters.isEnableMenuAnimation())
						{
							LogoController info = new LogoController();
							
							Thread controller = new Thread(info);
							controller.setName("Logo Thread");
							controller.start();
						}
					}
				//���ƶ����ı��������ƶ�������
				MenuButton.keepSleeping = false;
			}
			
//[start] ������¼�
			
			public void mouseClicked(MouseEvent e)
			{
				//˫�ε�����
				if(e.getClickCount() == 2)
				{
					//���˫��ֹͣ������
					if(e.getButton() == MouseEvent.BUTTON1)
						Rolnews.stopRolling();
					
					//�м�˫���������ö�
					else if(e.getButton() == MouseEvent.BUTTON2)
					{
						
					}
					
					//�Ҽ�˫��ʹ�ñ����ļ�
					else if(e.getButton() == MouseEvent.BUTTON3)
					{
					}
				}
			}
//[end]
		});
	}
//[end]

	/**
	 * ʵ��ActionListener�ӿڣ���Ҫ���ڼ�ʱ����ʱ������ˢ������
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		//�������
		resetPanel();
		
		//���¼���������ַ
		new LoadURL().loadURL();
				
		if(e != null)
		{
			//�½��̻߳�ȡ��ҳ����
			Thread grabNews = new Thread(new GrabNews());
			grabNews.start();
			
			//��ʾ��ʾ��Ϣ
			Thread refreshMessage = new Message("���ڸ������ţ����Ժ�...", 3);
			refreshMessage.start();
			
			System.out.println("�½���ץȡ���ŵ��߳�");
		}
		else
			System.out.println("û���½�ץȡ���ŵ��߳�");
	}
	
	/**
	 * ���ù������
	 * @return
	 */
	private void resetPanel()
	{
		//��չ������A��B���������ݣ�������λ��
		panelA.getTimer().stop();
		panelB.getTimer().stop();
		
		//���ù���״̬����
		Rolnews.ROLLSTATE = 0;
		JLabelFactory.setDestBoard(0);

		panelA.removeAll();
		panelB.removeAll();
		
		if(Rolnews.parameters.isHeadingLeft())
		{
			panelA.setLocation(new Point(Rolnews.parameters.getRightBorder(), 0));
			panelB.setLocation(new Point(Rolnews.parameters.getRightBorder(), 0));
		}
		else
		{
			panelA.setLocation(new Point(Rolnews.parameters.getLeftBorder() - panelA.getWidth(), 0));
			panelB.setLocation(new Point(Rolnews.parameters.getLeftBorder() - panelB.getWidth(), 0));
		}
	}
	
	public int getWindowWidth() { return windowWidth; }
	public void setWindowWidth(int windowWidth) { this.windowWidth = windowWidth; }

	public int getWindowHeight() { return windowHeight; }
	public void setWindowHeight(int windowHeight) { this.windowHeight = windowHeight; }

	public JPanel getLeftPanel() { return leftPanel; }
	public void setLeftPanel(JPanel leftPanel) { this.leftPanel = leftPanel; }

	public JPanel getRightPanel() { return rightPanel; }
	public void setRightPanel(JPanel rightPanel) { this.rightPanel = rightPanel; }

	public JPanel getRollPanel() { return rollPanel; }
	public void setRollPanel(JPanel rollPanel) { this.rollPanel = rollPanel; }

	public RollPanel getPanelA() { return panelA; }
	public void setPanelA(RollPanel panelA) { this.panelA = panelA; }

	public RollPanel getPanelB() { return panelB; }
	public void setPanelB(RollPanel panelB) { this.panelB = panelB; }

	public JLabel getMessage() { return message; }
	public void setMessage(JLabel message) { this.message = message; }

	public JPanel getAdPanel() { return adPanel; }
	public void setAdPanel(JPanel adIcon) { this.adPanel = adIcon; }

	public JTextField getSearchArea() { return searchArea; }
	public void setSearchArea(JTextField searchArea) { this.searchArea = searchArea; }

	public ShrinkFrame getShrinkFrame() { return shrinkFrame; }
	public void setShrinkFrame(ShrinkFrame shrinkFrame) { this.shrinkFrame = shrinkFrame; }
}
