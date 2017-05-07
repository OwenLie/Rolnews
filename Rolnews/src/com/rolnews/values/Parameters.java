package com.rolnews.values;

import java.util.ArrayList;
import java.util.List;

/**
 * ��������������������ĸ��ֲ���
 * 
 * @author Owen Lie
 */
public class Parameters {

	private boolean TopMost; //���崦����Ļ��ǰ��
	
	private ThemeColor BgColor;  //���屳��ɫ
	private ThemeColor MouseEnterColor;  //���������ɫ
	
	private int Padding = 5;  //�������Ե�ľ���
	
	private String ShowSearchPanel = "show";
	
	private boolean ShowBorder;
	
	private boolean headingLeft;  //�Ƿ��������
	private int Speed;  //�������ٶ�
	
	private int LeftBorder = 0;  //�������״̬�ı���߽�
	private int RightBorder = 500;  //�������״̬�ı���߽�
	
	private int Interval = 35;  //��������ʱ��ʱ����
	
	private NewsFont labelFont;  //��������������ʽ
	
	private int LoadingTime;  //����ʱ��
	private int RefreshTime;  //����ˢ��ʱ��
	
	private boolean UseBar;  //ʹ�÷ָ���
	
	private List<String[]> URLs = new ArrayList<String[]>();  //�����ַ����
	private String newsCategory;  //��������
	
	private int newsCount;  //��������Ŀ
	private String fileLocation = "src//com//rolnews";
	
	private boolean resizable;  //�Ƿ���Ըı��С
	
	private boolean enableMenuAnimation = true;  //�Ƿ�����˵���ť����
	private String newsFile;  //�Ӵ��ļ���ȡ����
	
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
