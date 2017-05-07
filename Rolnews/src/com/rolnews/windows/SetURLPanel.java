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
 * ��ַ�������
 * 
 * @author Owen Lie
 */
public class SetURLPanel extends JFrame{

//	private SetURLPanel websitePanel;
	private JPanel siteChosen;  //��ʾ���е���ַ

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
	 * ����������ַ�����
	 */
	private void drawPanel()
	{
		int baseY = 30;
		int height = 25;
		int countLine = 0;  //�����������

//[start] ����Զ�����ַ���

		final SetURLs setURLs = new SetURLs();  //�������¼�
		
		JTextField addTitle = new JTextField();
		addTitle.setName("title");
		addTitle.setFont(new Font("Dialog", 1, 15));
		addTitle.setText("  ������վ����");
		addTitle.setForeground(Color.DARK_GRAY);
		addTitle.setBounds(30, baseY + 30 * countLine, 300, height);
		addFocusListener(addTitle);
		countLine++;

		JTextField addWebsite = new JTextField();
		addWebsite.setName("website");
		addWebsite.setFont(new Font("Dialog", 1, 15));
		addWebsite.setText("  ������ַ");
		addWebsite.setForeground(Color.DARK_GRAY);
		addWebsite.setBounds(30, baseY + 30 * countLine, 300, height);
		addFocusListener(addWebsite);
		countLine++;

		JButton confirmAdd = new JButton("���");
		confirmAdd.setActionCommand("selfDefine,");
		confirmAdd.setFont(new Font("Dialog", 1, 15));
		confirmAdd.setForeground(Color.DARK_GRAY);
		confirmAdd.setBounds(240, baseY + 30 * countLine, 90, height);
		confirmAdd.addActionListener(setURLs);
//		confirmAdd.setEnabled(false);						//��ʱ�ر��Զ�����ַ����
		countLine++;

		JPanel addSite = new JPanel();
		addSite.setLayout(null);
		addSite.setBounds(0, 10, 360, baseY + countLine * 30 + 10);
		addSite.setBorder(BorderFactory.createTitledBorder("�Զ�����ַ"));
		addSite.add(addTitle);
		addSite.add(addWebsite);
		addSite.add(confirmAdd);
		countLine = 0;
//[end]		

//[start] ���������ַ���

		JCheckBox baidu = new JCheckBox("�ٶ����� -- ȫ�������������ƽ̨");
		baidu.setActionCommand("knownSite,baidu");
		baidu.setFont(new Font("Dialog", 1, 15));
		baidu.setForeground(Color.DARK_GRAY);
		baidu.setBounds(30, baseY, 300, height);
		baidu.addActionListener(setURLs);
		countLine++;

		JCheckBox souhu = new JCheckBox("���ŵ�һ�Ż� -- �Ѻ�����");
		souhu.setActionCommand("knownSite,souhu");
		souhu.setFont(new Font("Dialog", 1, 15));
		souhu.setForeground(Color.DARK_GRAY);
		souhu.setBounds(30, baseY + 30 * countLine, 300, height);
		souhu.addActionListener(setURLs);
		countLine++;

		JCheckBox wangyi = new JCheckBox("�������� -- ��̬�ȵ������Ż�");
		wangyi.setActionCommand("knownSite,wangyi");
		wangyi.setFont(new Font("Dialog", 1, 15));
		wangyi.setForeground(Color.DARK_GRAY);
		wangyi.setBounds(30, baseY + 30 * countLine, 300, height);
		wangyi.addActionListener(setURLs);
		countLine++;

		JCheckBox sina = new JCheckBox("������������");
		sina.setActionCommand("knownSite,sina");
		sina.setFont(new Font("Dialog", 1, 15));
		sina.setForeground(Color.DARK_GRAY);
		sina.setBounds(30, baseY + 30 * countLine, 300, height);
		sina.addActionListener(setURLs);
		countLine++;

		JCheckBox ifeng = new JCheckBox("�����Ѷ -- �����");
		ifeng.setActionCommand("knownSite,ifeng");
		ifeng.setFont(new Font("Dialog", 1, 15));
		ifeng.setForeground(Color.DARK_GRAY);
		ifeng.setBounds(30, baseY + 30 * countLine, 300, height);
		ifeng.addActionListener(setURLs);
		countLine++;

		JCheckBox huanqiu = new JCheckBox("������ -- ȫ���������Ż�");
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
		knownWeb.setBorder(BorderFactory.createTitledBorder("֪�������Ż�"));
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

//[start] ��������������

		JRadioButton yule = new JRadioButton("����");
		yule.setActionCommand("category,yule");
		yule.setFont(new Font("Dialog", 1, 15));
		yule.setForeground(Color.DARK_GRAY);
		yule.setBounds(30, baseY + 30 * countLine, 100, height);
		yule.addActionListener(setURLs);

		JRadioButton junshi = new JRadioButton("����");
		junshi.setActionCommand("category,junshi");
		junshi.setFont(new Font("Dialog", 1, 15));
		junshi.setForeground(Color.DARK_GRAY);
		junshi.setBounds(130, baseY + 30 * countLine, 100, height);
		junshi.addActionListener(setURLs);

		JRadioButton caijing = new JRadioButton("�ƾ�");
		caijing.setActionCommand("category,caijing");
		caijing.setFont(new Font("Dialog", 1, 15));
		caijing.setForeground(Color.DARK_GRAY);
		caijing.setBounds(230, baseY + 30 * countLine, 100, height);
		caijing.addActionListener(setURLs);
		countLine++;

		JRadioButton tiyu = new JRadioButton("����");
		tiyu.setActionCommand("category,tiyu");
		tiyu.setFont(new Font("Dialog", 1, 15));
		tiyu.setForeground(Color.DARK_GRAY);
		tiyu.setBounds(30, baseY + 30 * countLine, 100, height);
		tiyu.addActionListener(setURLs);

		JRadioButton shehui = new JRadioButton("���");
		shehui.setActionCommand("category,shehui");
		shehui.setFont(new Font("Dialog", 1, 15));
		shehui.setForeground(Color.DARK_GRAY);
		shehui.setBounds(130, baseY + 30 * countLine, 100, height);
		shehui.addActionListener(setURLs);

		JRadioButton keji = new JRadioButton("�Ƽ�");
		keji.setActionCommand("category,keji");
		keji.setFont(new Font("Dialog", 1, 15));
		keji.setForeground(Color.DARK_GRAY);
		keji.setBounds(230, baseY + 30 * countLine, 100, height);
		keji.addActionListener(setURLs);
		countLine++;

		JRadioButton youxi = new JRadioButton("��Ϸ");
		youxi.setActionCommand("category,youxi");
		youxi.setFont(new Font("Dialog", 1, 15));
		youxi.setForeground(Color.DARK_GRAY);
		youxi.setBounds(30, baseY + 30 * countLine, 100, height);
		youxi.addActionListener(setURLs);

		JRadioButton lishi = new JRadioButton("��ʷ");
		lishi.setActionCommand("category,lishi");
		lishi.setFont(new Font("Dialog", 1, 15));
		lishi.setForeground(Color.DARK_GRAY);
		lishi.setBounds(130, baseY + 30 * countLine, 100, height);
		lishi.addActionListener(setURLs);
		
		JRadioButton unlimited = new JRadioButton("�����");
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
		categoryPanel.setBorder(BorderFactory.createTitledBorder("��������"));
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

//[start] ������ַ

		siteChosen = new JPanel();
		siteChosen.setLayout(null);
		siteChosen.setBorder(BorderFactory.createTitledBorder("��ѡ��ַ�����������"));

		List<String[]> urls = Rolnews.parameters.getURLs();
		int size = urls.size();
		int deleteY = 0;  //ɾ����ť��y����
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

//[start] ��ӹ�����

		JScrollPane sPane = new JScrollPane(panel);
		sPane.setBounds(0, 0, 380, 500);
		//����panel�ĸ�
		panel.setPreferredSize(new Dimension(380, 
				(int)siteChosen.getLocation().getY() + siteChosen.getHeight()));

//[end]

		this.getContentPane().add(sPane);
	}
	
	/**
	 * ��������ַ������������ַ��ʾ
	 * @param title Ҫ��ʾ������
	 * @param n ����ַ���������ʾ�ڵ�n��
	 * @param setURLs ִ�д��ļ���ɾ����ַ�ĺ�̨����
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
				//˫��ɾ��
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
	 * �������򽹵��¼�
	 */
	private void addFocusListener(final JTextField textField)
	{
		textField.addFocusListener(new FocusListener()
		{
			@Override
			public void focusGained(FocusEvent e) 
			{
				System.out.println("��ý���");
				if(((JTextField)e.getSource()).getName().equals("title"))
				{
					if(textField.getText().equals("  ������վ����"))
						textField.setText("");
				}
				else
					if(textField.getText().equals("  ������ַ"))
						textField.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) 
			{
				System.out.println("ʧȥ����");
				if(((JTextField)e.getSource()).getName().equals("title"))
				{
					if(textField.getText().trim().equals(""))
						textField.setText("  ������վ����");
				}
				else
					if(textField.getText().equals(""))
						textField.setText("  ������ַ");
			}
		});
	}

	public JPanel getSiteChosen() { return siteChosen; }
	public void setSiteChosen(JPanel siteChosen) { this.siteChosen = siteChosen; }
}

