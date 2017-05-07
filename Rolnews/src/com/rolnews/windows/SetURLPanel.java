package com.rolnews.windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.rolnews.main.Rolnews;
import com.rolnews.setting.SetURLs;

/**
 * 网址设置面板
 * 
 * @author Owen Lie
 */
public class SetURLPanel extends JFrame{

//	private SetURLPanel websitePanel;
	private JPanel siteChosen;  //显示已有的网址

	public SetURLPanel()
	{
		this.setLayout(null);
		this.setBounds(600, 100, 380, 500);
		this.setResizable(false);
		this.setVisible(true);

//		websitePanel = this;

		drawPanel();
	}

	/**
	 * 绘制设置网址的面板
	 */
	private void drawPanel()
	{
		int baseY = 30;
		int height = 25;
		int countLine = 0;  //计算组件行数

//[start] 添加自定义网址面板

		final SetURLs setURLs = new SetURLs();  //监控鼠标事件
		
		JTextField addTitle = new JTextField();
		addTitle.setName("title");
		addTitle.setFont(new Font("Dialog", 1, 15));
		addTitle.setText("  输入网站名称");
		addTitle.setForeground(Color.DARK_GRAY);
		addTitle.setBounds(30, baseY + 30 * countLine, 300, height);
		addFocusListener(addTitle);
		countLine++;

		JTextField addWebsite = new JTextField();
		addWebsite.setName("website");
		addWebsite.setFont(new Font("Dialog", 1, 15));
		addWebsite.setText("  输入网址");
		addWebsite.setForeground(Color.DARK_GRAY);
		addWebsite.setBounds(30, baseY + 30 * countLine, 300, height);
		addFocusListener(addWebsite);
		countLine++;

		JButton confirmAdd = new JButton("添加");
		confirmAdd.setActionCommand("selfDefine,");
		confirmAdd.setFont(new Font("Dialog", 1, 15));
		confirmAdd.setForeground(Color.DARK_GRAY);
		confirmAdd.setBounds(240, baseY + 30 * countLine, 90, height);
		confirmAdd.addActionListener(setURLs);
//		confirmAdd.setEnabled(false);						//暂时关闭自定义网址功能
		countLine++;

		JPanel addSite = new JPanel();
		addSite.setLayout(null);
		addSite.setBounds(0, 10, 360, baseY + countLine * 30 + 10);
		addSite.setBorder(BorderFactory.createTitledBorder("自定义网址"));
		addSite.add(addTitle);
		addSite.add(addWebsite);
		addSite.add(confirmAdd);
		countLine = 0;
//[end]		

//[start] 添加已有网址面板

		JCheckBox baidu = new JCheckBox("百度新闻 -- 全球最大中文新闻平台");
		baidu.setActionCommand("knownSite,baidu");
		baidu.setFont(new Font("Dialog", 1, 15));
		baidu.setForeground(Color.DARK_GRAY);
		baidu.setBounds(30, baseY, 300, height);
		baidu.addActionListener(setURLs);
		countLine++;

		JCheckBox souhu = new JCheckBox("新闻第一门户 -- 搜狐新闻");
		souhu.setActionCommand("knownSite,souhu");
		souhu.setFont(new Font("Dialog", 1, 15));
		souhu.setForeground(Color.DARK_GRAY);
		souhu.setBounds(30, baseY + 30 * countLine, 300, height);
		souhu.addActionListener(setURLs);
		countLine++;

		JCheckBox wangyi = new JCheckBox("网易新闻 -- 有态度的新闻门户");
		wangyi.setActionCommand("knownSite,wangyi");
		wangyi.setFont(new Font("Dialog", 1, 15));
		wangyi.setForeground(Color.DARK_GRAY);
		wangyi.setBounds(30, baseY + 30 * countLine, 300, height);
		wangyi.addActionListener(setURLs);
		countLine++;

		JCheckBox sina = new JCheckBox("新浪新闻中心");
		sina.setActionCommand("knownSite,sina");
		sina.setFont(new Font("Dialog", 1, 15));
		sina.setForeground(Color.DARK_GRAY);
		sina.setBounds(30, baseY + 30 * countLine, 300, height);
		sina.addActionListener(setURLs);
		countLine++;

		JCheckBox ifeng = new JCheckBox("凤凰资讯 -- 凤凰网");
		ifeng.setActionCommand("knownSite,ifeng");
		ifeng.setFont(new Font("Dialog", 1, 15));
		ifeng.setForeground(Color.DARK_GRAY);
		ifeng.setBounds(30, baseY + 30 * countLine, 300, height);
		ifeng.addActionListener(setURLs);
		countLine++;

		JCheckBox huanqiu = new JCheckBox("环球网 -- 全球生活新门户");
		huanqiu.setActionCommand("knownSite,huanqiu");
		huanqiu.setFont(new Font("Dialog", 1, 15));
		huanqiu.setForeground(Color.DARK_GRAY);
		huanqiu.setBounds(30, baseY + 30 * countLine, 300, height);
		huanqiu.addActionListener(setURLs);
		countLine++;

		JPanel knownWeb = new JPanel();
		knownWeb.setLayout(null);
		knownWeb.setBounds(0, 
				(int)addSite.getLocation().getY() + addSite.getHeight(), 
				360,
				baseY + countLine * 30 + 10);
		knownWeb.setBorder(BorderFactory.createTitledBorder("知名新闻门户"));
		knownWeb.add(baidu);
		knownWeb.add(souhu);
		knownWeb.add(wangyi);
		knownWeb.add(sina);
		knownWeb.add(ifeng);
		knownWeb.add(huanqiu);
		countLine = 0;
		
		List<String[]> weburls = Rolnews.parameters.getURLs();
		int len = weburls.size();
		for(int i = 0; i < len; i++)
		{
			switch(weburls.get(i)[0])
			{
				case "http://news.baidu.com/":
					baidu.setSelected(true);
					break;
				case "http://news.sohu.com/":
					souhu.setSelected(true);
					break;
				case "http://www.163.com/":
					wangyi.setSelected(true);
					break;
				case "http://www.sina.com.cn/":
					sina.setSelected(true);
					break;
				case "http://www.ifeng.com/":
					ifeng.setSelected(true);
					break;
				case "http://www.huanqiu.com/":
					huanqiu.setSelected(true);
					break;
				default:
					break;
			}
		}
//[end]

//[start] 添加种类新闻面板

		JRadioButton yule = new JRadioButton("娱乐");
		yule.setActionCommand("category,yule");
		yule.setFont(new Font("Dialog", 1, 15));
		yule.setForeground(Color.DARK_GRAY);
		yule.setBounds(30, baseY + 30 * countLine, 100, height);
		yule.addActionListener(setURLs);

		JRadioButton junshi = new JRadioButton("军事");
		junshi.setActionCommand("category,junshi");
		junshi.setFont(new Font("Dialog", 1, 15));
		junshi.setForeground(Color.DARK_GRAY);
		junshi.setBounds(130, baseY + 30 * countLine, 100, height);
		junshi.addActionListener(setURLs);

		JRadioButton caijing = new JRadioButton("财经");
		caijing.setActionCommand("category,caijing");
		caijing.setFont(new Font("Dialog", 1, 15));
		caijing.setForeground(Color.DARK_GRAY);
		caijing.setBounds(230, baseY + 30 * countLine, 100, height);
		caijing.addActionListener(setURLs);
		countLine++;

		JRadioButton tiyu = new JRadioButton("体育");
		tiyu.setActionCommand("category,tiyu");
		tiyu.setFont(new Font("Dialog", 1, 15));
		tiyu.setForeground(Color.DARK_GRAY);
		tiyu.setBounds(30, baseY + 30 * countLine, 100, height);
		tiyu.addActionListener(setURLs);

		JRadioButton shehui = new JRadioButton("社会");
		shehui.setActionCommand("category,shehui");
		shehui.setFont(new Font("Dialog", 1, 15));
		shehui.setForeground(Color.DARK_GRAY);
		shehui.setBounds(130, baseY + 30 * countLine, 100, height);
		shehui.addActionListener(setURLs);

		JRadioButton keji = new JRadioButton("科技");
		keji.setActionCommand("category,keji");
		keji.setFont(new Font("Dialog", 1, 15));
		keji.setForeground(Color.DARK_GRAY);
		keji.setBounds(230, baseY + 30 * countLine, 100, height);
		keji.addActionListener(setURLs);
		countLine++;

		JRadioButton youxi = new JRadioButton("游戏");
		youxi.setActionCommand("category,youxi");
		youxi.setFont(new Font("Dialog", 1, 15));
		youxi.setForeground(Color.DARK_GRAY);
		youxi.setBounds(30, baseY + 30 * countLine, 100, height);
		youxi.addActionListener(setURLs);

		JRadioButton lishi = new JRadioButton("历史");
		lishi.setActionCommand("category,lishi");
		lishi.setFont(new Font("Dialog", 1, 15));
		lishi.setForeground(Color.DARK_GRAY);
		lishi.setBounds(130, baseY + 30 * countLine, 100, height);
		lishi.addActionListener(setURLs);
		
		JRadioButton unlimited = new JRadioButton("无类别");
		unlimited.setActionCommand("category,unlimited");
		unlimited.setFont(new Font("Dialog", 1, 15));
		unlimited.setForeground(Color.DARK_GRAY);
		unlimited.setBounds(230, baseY + 30 * countLine, 100, height);
		unlimited.addActionListener(setURLs);
		countLine++;
		
		ButtonGroup group = new ButtonGroup();
		group.add(yule);
		group.add(junshi);
		group.add(caijing);
		group.add(tiyu);
		group.add(shehui);
		group.add(keji);
		group.add(youxi);
		group.add(lishi);
		group.add(unlimited);

		String limitCate = Rolnews.parameters.getNewsCategory();
		switch(limitCate)
		{
			case "yule":
				yule.setSelected(true);
				break;
			case "junshi":
				junshi.setSelected(true);
				break;
			case "caijing":
				caijing.setSelected(true);
				break;
			case "tiyu":
				tiyu.setSelected(true);
				break;
			case "shehui":
				shehui.setSelected(true);
				break;
			case "keji":
				keji.setSelected(true);
				break;
			case "youxi":
				youxi.setSelected(true);
				break;
			case "lishi":
				lishi.setSelected(true);
				break;
			default:
				unlimited.setSelected(true);
				break;
		}
		
		JPanel categoryPanel = new JPanel();
		categoryPanel.setLayout(null);
		categoryPanel.setBounds(0, 
				(int)knownWeb.getLocation().getY() + knownWeb.getHeight(), 
				360, 
				baseY + countLine * 30 + 10);
		categoryPanel.setBorder(BorderFactory.createTitledBorder("新闻种类"));
		categoryPanel.add(yule);
		categoryPanel.add(junshi);
		categoryPanel.add(caijing);
		categoryPanel.add(tiyu);
		categoryPanel.add(shehui);
		categoryPanel.add(keji);
		categoryPanel.add(youxi);
		categoryPanel.add(lishi);
		categoryPanel.add(unlimited);
		countLine = 0;
//[end]

//[start] 已有网址

		siteChosen = new JPanel();
		siteChosen.setLayout(null);
		siteChosen.setBorder(BorderFactory.createTitledBorder("已选网址（最多六个）"));

		List<String[]> urls = Rolnews.parameters.getURLs();
		int size = urls.size();
		int deleteY = 0;  //删除按钮的y坐标
		for(int i = 0; i < size; i++)
		{
			addSiteToPanel(urls.get(i)[1], i, setURLs);
			deleteY = baseY + 30 * (i + 1);
		}

		siteChosen.setBounds(0, 
				(int)categoryPanel.getLocation().getY() + categoryPanel.getHeight(), 360, deleteY + 50);
//[end]

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.add(addSite);
		panel.add(knownWeb);
		panel.add(categoryPanel);
		panel.add(siteChosen);

//[start] 添加滚动条

		JScrollPane sPane = new JScrollPane(panel);
		sPane.setBounds(0, 0, 380, 500);
		//重置panel的高
		panel.setPreferredSize(new Dimension(380, 
				(int)siteChosen.getLocation().getY() + siteChosen.getHeight()));

//[end]

		this.getContentPane().add(sPane);
	}
	
	/**
	 * 向已有网址的面板上添加网址显示
	 * @param title 要显示的文字
	 * @param n 此网址在面板上显示在第n行
	 * @param setURLs 执行从文件中删除网址的后台对象
	 */
	public void addSiteToPanel(String title, int n, final SetURLs setURLs)
	{
		final JLabel weburl = new JLabel(title);
		weburl.setFont(new Font("Dialog", 1, 15));
		weburl.setForeground(Color.DARK_GRAY);
		weburl.setBounds(30, 30 + 30 * n, 300, 25);
		weburl.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				//双击删除
				if(e.getClickCount() == 2)
				{
					setURLs.deleteSite(weburl.getText());
					siteChosen.remove((JLabel)e.getSource());
				}
			}
		});
		
		siteChosen.add(weburl);
	}
	
	/**
	 * 添加输入框焦点事件
	 */
	private void addFocusListener(final JTextField textField)
	{
		textField.addFocusListener(new FocusListener()
		{
			@Override
			public void focusGained(FocusEvent e) 
			{
				System.out.println("获得焦点");
				if(((JTextField)e.getSource()).getName().equals("title"))
				{
					if(textField.getText().equals("  输入网站名称"))
						textField.setText("");
				}
				else
					if(textField.getText().equals("  输入网址"))
						textField.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) 
			{
				System.out.println("失去焦点");
				if(((JTextField)e.getSource()).getName().equals("title"))
				{
					if(textField.getText().trim().equals(""))
						textField.setText("  输入网站名称");
				}
				else
					if(textField.getText().equals(""))
						textField.setText("  输入网址");
			}
		});
	}

	public JPanel getSiteChosen() { return siteChosen; }
	public void setSiteChosen(JPanel siteChosen) { this.siteChosen = siteChosen; }
}

