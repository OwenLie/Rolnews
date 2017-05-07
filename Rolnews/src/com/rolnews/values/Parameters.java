package com.rolnews.values;

import java.util.ArrayList;
import java.util.List;

/**
 * 程序参数，存放整个程序的各种参数
 * 
 * @author Owen Lie
 */
public class Parameters {

	private boolean TopMost; //窗体处于屏幕最前方
	
	private ThemeColor BgColor;  //窗体背景色
	private ThemeColor MouseEnterColor;  //鼠标进入的颜色
	
	private int Padding = 5;  //到窗体边缘的距离
	
	private String ShowSearchPanel = "show";
	
	private boolean ShowBorder;
	
	private boolean headingLeft;  //是否向左滚动
	private int Speed;  //滚动的速度
	
	private int LeftBorder = 0;  //滚动面板状态改变左边界
	private int RightBorder = 500;  //滚动面板状态改变左边界
	
	private int Interval = 35;  //滚动面板计时器时间间隔
	
	private NewsFont labelFont;  //滚动新闻字体样式
	
	private int LoadingTime;  //加载时限
	private int RefreshTime;  //新闻刷新时间
	
	private boolean UseBar;  //使用分隔条
	
	private List<String[]> URLs = new ArrayList<String[]>();  //存放网址数据
	private String newsCategory;  //新闻种类
	
	private int newsCount;  //新闻总数目
	private String fileLocation = "src//com//rolnews";
	
	private boolean resizable;  //是否可以改变大小
	
	private boolean enableMenuAnimation = true;  //是否允许菜单按钮动画
	private String newsFile;  //从此文件获取新闻
	
	public boolean isTopMost() { return TopMost; }
	public void setTopMost(boolean topMost) { TopMost = topMost; }
	
	public ThemeColor getBgColor() { return BgColor; }
	public void setBgColor(ThemeColor bgColor) { BgColor = bgColor; }
	
	public ThemeColor getMouseEnterColor() { return MouseEnterColor; }
	public void setMouseEnterColor(ThemeColor mouseEnterColor) { MouseEnterColor = mouseEnterColor; }
	
	public int getPadding() { return Padding; }
	public void setPadding(int padding) { Padding = padding; }
	
	public String getShowSearchPanel() { return ShowSearchPanel; }
	public void setShowSearchPanel(String showSearchPanel) { ShowSearchPanel = showSearchPanel; }
	
	public boolean isShowBorder() { return ShowBorder; }
	public void setShowBorder(boolean showBorder) { ShowBorder = showBorder; }
	
	public int getSpeed() { return Speed; }
	public void setSpeed(int speed) { Speed = speed; }
	
	public int getLeftBorder() { return LeftBorder; }
	public void setLeftBorder(int leftBorder) { LeftBorder = leftBorder; }
	
	public int getRightBorder() { return RightBorder; }
	public void setRightBorder(int rightBorder) { RightBorder = rightBorder; }
	
	public int getInterval() { return Interval; }
	public void setInterval(int interval) { Interval = interval; }
	
	public NewsFont getLabelFont() { return labelFont; }
	public void setLabelFont(NewsFont labelFont) { this.labelFont = labelFont; }
	
	public int getLoadingTime() { return LoadingTime; }
	public void setLoadingTime(int loadingTime) { LoadingTime = loadingTime; }
	
	public boolean isHeadingLeft() { return headingLeft; }
	public void setHeadingLeft(boolean headingLeft) { this.headingLeft = headingLeft; }
	
	public boolean isUseBar() { return UseBar; }
	public void setUseBar(boolean useBar) { UseBar = useBar; }
	
	public int getRefreshTime() { return RefreshTime; }
	public void setRefreshTime(int refreshTime) { RefreshTime = refreshTime; }
	
	public List<String[]> getURLs() { return URLs; }
	public void setURLs(List<String[]> uRLs) { URLs = uRLs; }
	
	public String getNewsCategory() { return newsCategory; }
	public void setNewsCategory(String newsCategory) { this.newsCategory = newsCategory; }
	
	public int getNewsCount() { return newsCount; }
	public void setNewsCount(int newsCount) { this.newsCount = newsCount; }
	
	public String getFileLocation() { return fileLocation; }
	public void setFileLocation(String fileLocation) { this.fileLocation = fileLocation; }
	
	public boolean isResizable() { return resizable; }
	public void setResizable(boolean resizable) { this.resizable = resizable; }
	
	public boolean isEnableMenuAnimation() { return enableMenuAnimation; }
	public void setEnableMenuAnimation(boolean enableMenuAnimation) { this.enableMenuAnimation = enableMenuAnimation; }
	
	public String getNewsFile() { return newsFile; }
	public void setNewsFile(String newsFile) { this.newsFile = newsFile; }
}
