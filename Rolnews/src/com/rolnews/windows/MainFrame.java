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
 * 程序主窗体，完成以下组件的绘制
 * <ol>
 * <li>功能区面板（functionPanel);
 * <li>左面板(leftPanel);
 * <li>滚动面板（rollPanel);
 * <li>右面板(rightPanel);
 * </ol>
 * 
 * @author Owen Lie
 */
public class MainFrame extends JFrame implements ActionListener{

//[start]私有成员变量区
	
	//静态单例对象
	private static MainFrame mainFrame;  //主窗体
	private JPanel leftPanel;  //左面板，放向左快进按钮
	private JPanel rightPanel;  //右面板，放向右快进，最小化，关闭等菜单按钮
	private JPanel rollPanel;  //滚动面板，提供新闻字幕滚动的容器
	private RollPanel panelA;
	private RollPanel panelB;
	private JLabel message;  //系统提示语
	private JPanel adPanel;  //额外提示信息（主要承载图片显示）

	private Color bgColor = Rolnews.parameters.getBgColor().getValue();  //窗体主题色
	private int padding = Rolnews.parameters.getPadding();  //面板相对窗体的间隔
	private int windowWidth = 600;  //主窗体宽
	private int windowHeight = 30;  //主窗体高

	private JTextField searchArea;  //搜索框
	private ShrinkFrame shrinkFrame;  //缩小主窗体的按钮
	
	public static boolean lockRoll = false;  //锁定面板滚动，为true时，面板不能通过鼠标离开滚动面板而开始滚动

//[end]
	
	/**构造函数，初始化窗体并添加各面板*/
	private MainFrame()
	{
		//绘制主窗体，并设置各项参数
		this.setUndecorated(true);  //去掉边框
		if(Rolnews.parameters.isTopMost())
			this.setAlwaysOnTop(true);
		this.setLayout(null);  //设置为绝对布局
		this.setResizable(false);  //不允许调整窗体大小
		this.getContentPane().setBackground(bgColor);  //设置窗体背景色
		this.setBounds(700,60, windowWidth,windowHeight);  //设置初始位置和窗体大小
		this.setVisible(true);  //显示窗体
		
		int height = windowHeight - 2 * padding;
		
		//向主窗体添加左面板
		leftPanel = new JPanel();
		leftPanel.setLayout(null);  //设置为绝对布局
		leftPanel.setBackground(bgColor);  //设置窗体背景色
		leftPanel.setBounds(padding,
							padding,
							windowHeight - padding + height,
							height);  //设置初始位置和窗体大小

		//向主窗体添加右面板
		rightPanel = new JPanel();
		rightPanel.setLayout(null);  //设置为绝对布局
		rightPanel.setBackground(bgColor);  //设置窗体背景色
		rightPanel.setBounds(windowWidth - 2 * padding - 4 * height,
							 padding,
							 4 * height + padding,
							 height);  //设置初始位置和窗体大小
		
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
		
		//向主窗体添加滚动面板
		rollPanel = new JPanel();
		rollPanel.setLayout(null);
		rollPanel.setBackground(bgColor);
		rollPanel.setBounds(2 * padding + 2 * height,
				padding,
				windowWidth - (5 * height + 2 * padding) - (2 * padding + height),
				height);
		
		//向滚动面板添加组件
		rollPanel.add(panelA);
		rollPanel.add(panelB);
		rollPanel.add(message);
		
		//搜索框
		searchArea = new JTextField();
		searchArea.setFont(new Font("华文新魏", 1, 18));
		searchArea.setBounds(50, 35, 525, 20);
		searchArea.setBackground(Rolnews.parameters.getMouseEnterColor().getValue());
		searchArea.setForeground(new Color(255, 255, 255, 255));
		searchArea.setBorder(null);
		searchArea.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent e)
			{
				//点击回车键
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
		
		//收缩主窗体按钮
		shrinkFrame = ShrinkFrame.getInstance();
		
		this.add(rightPanel);
		this.add(leftPanel);
		this.add(rollPanel);
		this.add(shrinkFrame);
		this.add(searchArea);
		
		this.setDragable();  //设置窗体可拖动
		this.setMouseEvent();  //设置鼠标进入和离开事件
		
		mainFrame = this;
	}
	
	/**
	 * 获取实例对象，此类为单例类
	 */
	public static MainFrame getInstance()
	{
		if(mainFrame == null)
			mainFrame = new MainFrame();
		
		return mainFrame;
	}
	
//[start] 窗体的鼠标事件
	
	private Point location = null;
	private Point tempLoc = null;
	public static boolean isDragged = false;
	public static boolean isExtending = false;  

	/**
	 * 设置鼠标拖动事件
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
					//重置窗体长度，滚动面板长度，并重置通知消息位置和右边界的位置
					int len = windowHeight - 2 * padding;
					windowWidth = mainFrame.getSize().width;
					rollPanel.setSize((int)(windowWidth - (4 * len + 2 * padding) - (2 * padding + 2 * len)), windowHeight - 2 * padding);
					message.setLocation((int)(rollPanel.getWidth()/2 - message.getWidth()/2), message.getLocation().y);
					Rolnews.parameters.setRightBorder(rightPanel.getLocation().x + 10);
					
					//重置右边界的等待加载新闻的滚动面板的位置
					if(Rolnews.ROLLSTATE == 1)
						panelB.setLocation(Rolnews.parameters.getRightBorder(), panelB.getLocation().y);
					else if(Rolnews.ROLLSTATE == 2)
						panelA.setLocation(Rolnews.parameters.getRightBorder(), panelA.getLocation().y);
				}
			}

			public void mousePressed(MouseEvent e)
			{
				tempLoc = new Point(e.getX(), e.getY());
				
				//鼠标不是放到滚动条末尾时表示拖动
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
	 * 设置鼠标点击事件
	 */
	public static boolean menuShowing = true;  //标志菜单按钮是否正在显示
	public static boolean lockAnimation = false;  //锁定菜单动画
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
				
				//如果菜单动画没有启动，启动菜单动画
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
				//控制动画的变量，抑制动画进行
				MenuButton.keepSleeping = false;
			}
			
			public void mouseExited(MouseEvent e)
			{
				mainFrame.getContentPane().setBackground
				(Rolnews.parameters.getBgColor().getValue());
				
				mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				
				//如果菜单动画没有启动，启动菜单动画
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
						
						//开启AdPanel的切换控制线程
						if(!LogoController.isControllerOn() && Rolnews.parameters.isEnableMenuAnimation())
						{
							LogoController info = new LogoController();
							
							Thread controller = new Thread(info);
							controller.setName("Logo Thread");
							controller.start();
						}
					}
				//控制动画的变量，抑制动画进行
				MenuButton.keepSleeping = false;
			}
			
//[start] 鼠标点击事件
			
			public void mouseClicked(MouseEvent e)
			{
				//双次点击鼠标
				if(e.getClickCount() == 2)
				{
					//左键双击停止面板滚动
					if(e.getButton() == MouseEvent.BUTTON1)
						Rolnews.stopRolling();
					
					//中键双击滚动条置顶
					else if(e.getButton() == MouseEvent.BUTTON2)
					{
						
					}
					
					//右键双击使用备份文件
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
	 * 实现ActionListener接口，主要用于计时器到时，重新刷新新闻
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		//重置面板
		resetPanel();
		
		//重新加载新闻网址
		new LoadURL().loadURL();
				
		if(e != null)
		{
			//新建线程获取网页数据
			Thread grabNews = new Thread(new GrabNews());
			grabNews.start();
			
			//显示提示消息
			Thread refreshMessage = new Message("正在更新新闻，请稍候...", 3);
			refreshMessage.start();
			
			System.out.println("新建了抓取新闻的线程");
		}
		else
			System.out.println("没有新建抓取新闻的线程");
	}
	
	/**
	 * 重置滚动面板
	 * @return
	 */
	private void resetPanel()
	{
		//清空滚动面板A和B的所有内容，并调整位置
		panelA.getTimer().stop();
		panelB.getTimer().stop();
		
		//重置滚动状态变量
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
