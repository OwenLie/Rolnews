package com.rolnews.windows;

import java.net.URL;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;

import com.rolnews.main.Rolnews;
import com.rolnews.setting.ExtractParams;
import com.rolnews.values.NewsFont;
import com.rolnews.values.ThemeColor;

/**
 * 滚动新闻弹出菜单
 * 
 * @author Owen Lie
 */
public class RNPopupMenu {

	//弹出菜单对象
	private JPopupMenu newsMenu = null;

	public RNPopupMenu()
	{
		this.newsMenu = new JPopupMenu();

		initializePopupMenu();
	}

	/**
	 * 初始化popupMenu，绘制菜单
	 */
	public void initializePopupMenu()
	{
		//菜单动作对象
		ExtractParams performSetting = new ExtractParams();
		URL img = null;
		
//[start] 一级菜单选项  

		JMenuItem websURL = new JMenuItem("自定义新闻网址"); 
		img = RNPopupMenu.class.getResource("weburl.png");
		ImageIcon webLogo = new ImageIcon(img);
		websURL.setIcon(webLogo);
		websURL.addActionListener(performSetting);
		websURL.setActionCommand("site,");

		JMenu userSetting = new JMenu("设置"); 
		img = RNPopupMenu.class.getResource("setting1.png");
		ImageIcon websURLLogo = new ImageIcon(img);
		userSetting.setIcon(websURLLogo);

		JMenu theme = new JMenu("主题美化"); 
		img = RNPopupMenu.class.getResource("theme.png");
		ImageIcon themeLogo = new ImageIcon(img);
		theme.setIcon(themeLogo);

		JMenuItem refresh = new JMenuItem("刷新"); 
		img = RNPopupMenu.class.getResource("refresh.png");
		ImageIcon refreshLogo = new ImageIcon(img);
		refresh.setIcon(refreshLogo);
		refresh.addActionListener(performSetting);
		refresh.setActionCommand("refresh,");

		JMenuItem about = new JMenuItem("关于"); 
		img = RNPopupMenu.class.getResource("about.png");
		ImageIcon aboutLogo = new ImageIcon(img);
		about.setIcon(aboutLogo);
		about.addActionListener(performSetting);
		about.setActionCommand("about,");

		JMenuItem help = new JMenuItem("帮助"); 
		img = RNPopupMenu.class.getResource("help.png");
		ImageIcon helpLogo = new ImageIcon(img);
		help.setIcon(helpLogo);
		help.addActionListener(performSetting);
		help.setActionCommand("help,");

//[end]

//[start] 二级菜单选项

		JMenu skin = new JMenu("滚动条皮肤");  
		JMenu newsFont = new JMenu("新闻字体样式");

		JRadioButtonMenuItem menuBorder = new JRadioButtonMenuItem("显示菜单边框");
		menuBorder.addActionListener(performSetting);
		menuBorder.setActionCommand("menuborder,");
		if(Rolnews.parameters.isShowBorder())
			menuBorder.setSelected(true);

		JMenu opacity = new JMenu("透明度");
		opacity.setEnabled(false);
		
		JMenu timeout = new JMenu("新闻超时限制");
		
		JCheckBoxMenuItem headLeft = new JCheckBoxMenuItem("向左滚动");
		headLeft.addActionListener(performSetting);
		headLeft.setActionCommand("headleft,");
		if(Rolnews.parameters.isHeadingLeft())
			headLeft.setSelected(true);
		
		JCheckBoxMenuItem useBar = new JCheckBoxMenuItem("使用新闻分隔条");
		useBar.addActionListener(performSetting);
		useBar.setActionCommand("usebar,");
		if(Rolnews.parameters.isUseBar())
			useBar.setSelected(true);
		
		JCheckBoxMenuItem topMost = new JCheckBoxMenuItem("始终处于屏幕最上方");
		topMost.addActionListener(performSetting);
		topMost.setActionCommand("topmost,");
		if(Rolnews.parameters.isTopMost())
			topMost.setSelected(true);
		
		JCheckBoxMenuItem resizable = new JCheckBoxMenuItem("大小可变");
		resizable.addActionListener(performSetting);
		resizable.setActionCommand("resizable,");
		if(Rolnews.parameters.isResizable())
			resizable.setSelected(true);
		
		JMenu speed = new JMenu("滚动速度");
		JMenu refreshTime = new JMenu("新闻刷新时间");
		
		JCheckBoxMenuItem menuBtnAni = new JCheckBoxMenuItem("Logo显示（关闭时重启生效）");
		menuBtnAni.addActionListener(performSetting);
		menuBtnAni.setActionCommand("enableAni,");
		menuBtnAni.setSize(100, 30);
		if(Rolnews.parameters.isEnableMenuAnimation())
			menuBtnAni.setSelected(true);
		
		JMenuItem searchFunc = new JMenuItem("打开搜索功能");
		searchFunc.setEnabled(false);

//[end]

//[start] 滚动条皮肤三级菜单选项

		JRadioButtonMenuItem blue = new JRadioButtonMenuItem("商业湛蓝");
		img = RNPopupMenu.class.getResource("blue.png");
		ImageIcon blueLogo = new ImageIcon(img);
		blue.setIcon(blueLogo);
		blue.addActionListener(performSetting);
		blue.setActionCommand("skin,blue");

		JRadioButtonMenuItem red = new JRadioButtonMenuItem("夕阳血红");
		img = RNPopupMenu.class.getResource("red.png");
		ImageIcon redLogo = new ImageIcon(img);
		red.setIcon(redLogo);
		red.addActionListener(performSetting);
		red.setActionCommand("skin,red");

		JRadioButtonMenuItem golden = new JRadioButtonMenuItem("高亮金");
		img = RNPopupMenu.class.getResource("golden.png");
		ImageIcon goldenLogo = new ImageIcon(img);
		golden.setIcon(goldenLogo);
		golden.addActionListener(performSetting);
		golden.setActionCommand("skin,golden");

		JRadioButtonMenuItem green = new JRadioButtonMenuItem("翡翠绿");
		img = RNPopupMenu.class.getResource("green.png");
		ImageIcon greenLogo = new ImageIcon(img);
		green.setIcon(greenLogo);
		green.addActionListener(performSetting);
		green.setActionCommand("skin,green");

		JRadioButtonMenuItem brown = new JRadioButtonMenuItem("优雅棕黑");
		img = RNPopupMenu.class.getResource("black.png");
		ImageIcon blackLogo = new ImageIcon(img);
		brown.setIcon(blackLogo);
		brown.addActionListener(performSetting);
		brown.setActionCommand("skin,brown");

		JRadioButtonMenuItem purple = new JRadioButtonMenuItem("紫罗兰");
		img = RNPopupMenu.class.getResource("purple.png");
		ImageIcon purpleLogo = new ImageIcon(img);
		purple.setIcon(purpleLogo);
		purple.addActionListener(performSetting);
		purple.setActionCommand("skin,purple");

		ButtonGroup groupSkin = new ButtonGroup();
		groupSkin.add(blue);
		groupSkin.add(red);
		groupSkin.add(golden);
		groupSkin.add(green);
		groupSkin.add(brown);
		groupSkin.add(purple);

		//设置当前主题色选项为选中状态
		ThemeColor bgColor = Rolnews.parameters.getBgColor();
		switch(bgColor)
		{
			case BLUE:
				blue.setSelected(true);
				break;
			case RED:
				red.setSelected(true);
				break;
			case GOLDEN:
				golden.setSelected(true);
				break;
			case BROWN:
				brown.setSelected(true);
				break;
			case PURPLE:
				purple.setSelected(true);
				break;
			default:
				green.setSelected(true);
				break;
		}
//[end]

//[start] 字体样式三级菜单

		JRadioButtonMenuItem defaultFont = new JRadioButtonMenuItem("默认");
		defaultFont.setFont(NewsFont.DEFAULTFONT.getLabelFont().getFont());
		defaultFont.addActionListener(performSetting);
		defaultFont.setActionCommand("font,default");

		JRadioButtonMenuItem song = new JRadioButtonMenuItem("宋体");
		song.setFont(NewsFont.SONG.getLabelFont().getFont());
		song.addActionListener(performSetting);
		song.setActionCommand("font,song");

		JRadioButtonMenuItem xihei = new JRadioButtonMenuItem("华文细黑");
		xihei.setFont(NewsFont.HUAWENXIHEI.getLabelFont().getFont());
		xihei.addActionListener(performSetting);
		xihei.setActionCommand("font,xihei");

		JRadioButtonMenuItem xinwei = new JRadioButtonMenuItem("华文新魏");
		xinwei.setFont(NewsFont.HUAWENXINWEI.getLabelFont().getFont());
		xinwei.addActionListener(performSetting);
		xinwei.setActionCommand("font,xinwei");

		JRadioButtonMenuItem fangsong = new JRadioButtonMenuItem("仿宋");
		fangsong.setFont(NewsFont.FANGSONG.getLabelFont().getFont());
		fangsong.addActionListener(performSetting);
		fangsong.setActionCommand("font,fangsong");

		JRadioButtonMenuItem kaiti = new JRadioButtonMenuItem("楷体");
		kaiti.setFont(NewsFont.KAITI.getLabelFont().getFont());
		kaiti.addActionListener(performSetting);
		kaiti.setActionCommand("font,kaiti");

		JRadioButtonMenuItem lishu = new JRadioButtonMenuItem("隶书");
		lishu.setFont(NewsFont.LISHU.getLabelFont().getFont());
		lishu.addActionListener(performSetting);
		lishu.setActionCommand("font,lishu");

		JRadioButtonMenuItem xingkai = new JRadioButtonMenuItem("华文行楷");
		xingkai.setFont(NewsFont.HUAWENXINGKAI.getLabelFont().getFont());
		xingkai.addActionListener(performSetting);
		xingkai.setActionCommand("font,xingkai");

		ButtonGroup groupFont = new ButtonGroup();
		groupFont.add(defaultFont);
		groupFont.add(song);
		groupFont.add(xingkai);
		groupFont.add(lishu);
		groupFont.add(kaiti);
		groupFont.add(fangsong);
		groupFont.add(xinwei);
		groupFont.add(xihei);

		//设置当前字体选项为选中状态
		NewsFont newsLabelFont = Rolnews.parameters.getLabelFont();
		switch(newsLabelFont)
		{
			case SONG:
				song.setSelected(true);
				break;
			case HUAWENXINGKAI:
				xingkai.setSelected(true);
				break;
			case LISHU:
				lishu.setSelected(true);
				break;
			case KAITI:
				kaiti.setSelected(true);
				break;
			case FANGSONG:
				fangsong.setSelected(true);
				break;
			case HUAWENXINWEI:
				xinwei.setSelected(true);
				break;
			case HUAWENXIHEI:
				xihei.setSelected(true);
				break;
			default:
				defaultFont.setSelected(true);
				break;
			
		}
//[end]

//[start] 透明度三级菜单

		JRadioButtonMenuItem opacity1 = new JRadioButtonMenuItem("100%");
		JRadioButtonMenuItem opacity2 = new JRadioButtonMenuItem("80%");
		JRadioButtonMenuItem opacity3 = new JRadioButtonMenuItem("50%");

//[end]
		
//[start] 新闻超时限制三级菜单
		
		JRadioButtonMenuItem timeout5 = new JRadioButtonMenuItem("5秒（适用较好网速）");
		timeout5.addActionListener(performSetting);
		timeout5.setActionCommand("loadtime,5");
		
		JRadioButtonMenuItem timeout10 = new JRadioButtonMenuItem("10秒（适用一般网速）");
		timeout10.addActionListener(performSetting);
		timeout10.setActionCommand("loadtime,10");
		
		JRadioButtonMenuItem timeout15 = new JRadioButtonMenuItem("15秒（适用较慢网速）");
		timeout15.addActionListener(performSetting);
		timeout15.setActionCommand("loadtime,15");
		
		ButtonGroup timeoutGroup = new ButtonGroup();
		timeoutGroup.add(timeout5);
		timeoutGroup.add(timeout10);
		timeoutGroup.add(timeout15);
		
		if(Rolnews.parameters.getLoadingTime() == 15000)
			timeout15.setSelected(true);
		else if(Rolnews.parameters.getLoadingTime() == 10000)
			timeout10.setSelected(true);
		else
			timeout5.setSelected(true);
		
//[end]
		
//[start] 新闻滚速三级菜单
		
		JRadioButtonMenuItem speedFast = new JRadioButtonMenuItem("稍快");
		speedFast.addActionListener(performSetting);
		speedFast.setActionCommand("speed,fast");
		
		JRadioButtonMenuItem speedNormal = new JRadioButtonMenuItem("正常");
		speedNormal.addActionListener(performSetting);
		speedNormal.setActionCommand("speed,normal");
		
		JRadioButtonMenuItem speedSlow = new JRadioButtonMenuItem("慢速");
		speedSlow.addActionListener(performSetting);
		speedSlow.setActionCommand("speed,slow");
		
		ButtonGroup speedGroup = new ButtonGroup();
		speedGroup.add(speedFast);
		speedGroup.add(speedNormal);
		speedGroup.add(speedSlow);
		
		int rollSpeed = Rolnews.parameters.getSpeed();
		if(rollSpeed == 1 || rollSpeed == -1)
			speedSlow.setSelected(true);
		else if(rollSpeed == 2 || rollSpeed == -2)
			speedNormal.setSelected(true);
		else
			speedFast.setSelected(true);
		
//[end]
		
//[start] 新闻自动刷新时间三级菜单
		
		JRadioButtonMenuItem refresh60 = new JRadioButtonMenuItem("60分钟");
		refresh60.addActionListener(performSetting);
		refresh60.setActionCommand("refreshtime,60");
		
		JRadioButtonMenuItem refresh120 = new JRadioButtonMenuItem("120分钟");
		refresh120.addActionListener(performSetting);
		refresh120.setActionCommand("refreshtime,120");
		
		JRadioButtonMenuItem refresh180 = new JRadioButtonMenuItem("180分钟");
		refresh180.addActionListener(performSetting);
		refresh180.setActionCommand("refreshtime,180");
		
		ButtonGroup refreshGroup = new ButtonGroup();
		refreshGroup.add(refresh60);
		refreshGroup.add(refresh120);
		refreshGroup.add(refresh180);
		
		if(Rolnews.parameters.getRefreshTime() == 60)
			refresh60.setSelected(true);
		else if(Rolnews.parameters.getRefreshTime() == 120)
			refresh120.setSelected(true);
		else
			refresh180.setSelected(true);
		
//[end]

//[start] 添加一级菜单

		newsMenu.add(websURL);  
		newsMenu.add(userSetting); 
		newsMenu.addSeparator(); 
		newsMenu.add(theme); 
		newsMenu.addSeparator(); 
		newsMenu.add(refresh);
		newsMenu.add(about);  
		newsMenu.add(help);

		userSetting.add(timeout);
		userSetting.addSeparator();
		userSetting.add(headLeft);
		userSetting.add(useBar);
		userSetting.add(topMost);
		userSetting.add(resizable);
		userSetting.addSeparator();
		userSetting.add(speed);
		userSetting.add(refreshTime);
		userSetting.addSeparator();
		userSetting.add(menuBtnAni);
		userSetting.add(searchFunc);
		
		theme.add(skin);  
		theme.add(newsFont); 
		theme.addSeparator();
		theme.add(menuBorder);
		theme.addSeparator();
		theme.add(opacity);

		//添加三级菜单
		timeout.add(timeout5);
		timeout.add(timeout10);
		timeout.add(timeout15);
		
		speed.add(speedFast);
		speed.add(speedNormal);
		speed.add(speedSlow);
		
		refreshTime.add(refresh60);
		refreshTime.add(refresh120);
		refreshTime.add(refresh180);
		
		skin.add(blue);
		skin.add(red);
		skin.add(golden);
		skin.add(green);
		skin.add(brown);
		skin.add(purple);

		newsFont.add(defaultFont);
		newsFont.add(song);
		newsFont.add(xihei);
		newsFont.add(xinwei);
		newsFont.add(fangsong);
		newsFont.add(kaiti);
		newsFont.add(lishu);
		newsFont.add(xingkai);

		opacity.add(opacity1);
		opacity.add(opacity2);
		opacity.add(opacity3);

//[end]
	}

	public JPopupMenu getNewsMenu() { return newsMenu; }
	public void setNewsMenu(JPopupMenu newsMenu) { this.newsMenu = newsMenu; }
}