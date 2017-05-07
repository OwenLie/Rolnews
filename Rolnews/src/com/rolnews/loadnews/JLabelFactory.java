package com.rolnews.loadnews;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.rolnews.database.DataProcessor;
import com.rolnews.main.Rolnews;
import com.rolnews.message.Message;
import com.rolnews.model.History;
import com.rolnews.values.LabelFont;
import com.rolnews.windows.MainFrame;
import com.rolnews.windows.RollPanel;

/**
 * 新闻工厂类
 * <p>根据缓冲区加载的新闻生成jlabel组件，并加载到滚动面板上滚动
 * 
 * @author Owen Lie
 */
public class JLabelFactory {

	//静态JLabel数组，存放生成的新闻标签，以备加载到滚动面板上滚动
	private JLabel[] newsLabel = new JLabel[LoadNews.getReadUnit()];
	private static int location = 0;  //JLabel在滚动面板上的位置(x方向)
	private static int destBoard = 0;  //标志jlabel要加载到A和B哪个滚动面板上
	
	/**
	 * 生成JLable组件，存入newsLabel中
	 */
	public void generateJLabel()
	{
		//一次加载的新闻数目
		int size = LoadNews.getReadUnit();
		boolean useBar = Rolnews.parameters.isUseBar();  //是否使用新闻分隔条
		LabelFont labelFont = Rolnews.parameters.getLabelFont().getLabelFont();  //jlabel上新闻字体
		
		for(int i = 0; i < size; i++)
		{
			//实例化JLabel对象
			newsLabel[i] = new JLabel(LoadNews.newsBuffer[i][0]);
			//将该新闻的网址链接设置到属性Name上
			newsLabel[i].setName(LoadNews.newsBuffer[i][1]);
			//设置新闻字体
			newsLabel[i].setFont(labelFont.getFont());
			
			//判断是否为推荐新闻
			if(LoadNews.newsBuffer[i][0].startsWith("今日推荐"))
				newsLabel[i].setForeground(Color.red);
			else
				newsLabel[i].setForeground(labelFont.getFontColor());

			//获取label的像素长度
			int labelWidth = newsLabel[i].getFontMetrics
					(newsLabel[i].getFont()).stringWidth(LoadNews.newsBuffer[i][0]);

			//设置label的位置
			newsLabel[i].setBounds(new Rectangle(location, labelFont.getLableY(), labelWidth, 20));
			//设置鼠标移动到此label上时变为手型
			newsLabel[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			
			//添加鼠标事件
			addMouseEvent(newsLabel[i]);
			
			if(useBar)
				location = location + labelWidth + 70;
			else
				location = location + labelWidth + 30;
		}
		
		//将新闻加载到滚动面板上
		if(destBoard == 2)
			loadJLaebl(Rolnews.getMainFrame().getPanelB());
		else
			loadJLaebl(Rolnews.getMainFrame().getPanelA());
		
		//检查是否有消息显示
		Message.messageOut();
	}
	
	/**
	 * 将JLabel组件加载到滚动面板上
	 */
	private void loadJLaebl(RollPanel rollPanel)
	{
//		System.out.println("当前加载新闻的滚动面板为 : " + rollPanel.getPanelTag());
		rollPanel.removeAll();
		
		int height = 30 - 2 * Rolnews.parameters.getPadding();
		
		int size = newsLabel.length;
		int loc = 0;
		boolean useBar = Rolnews.parameters.isUseBar(); 
		
		for(int i = 0; i < size; i++)
		{
//			System.out.println("label上的新闻: " + newsLabel[i].getText());
			
			rollPanel.add(newsLabel[i]);
			
			//如果要使用条，添加分隔条到面板上
			if(useBar)
			{
				JLabel bar = new JLabel("|");
				bar.setHorizontalAlignment(SwingConstants.CENTER);
				bar.setFont(new java.awt.Font("Dialog",0,15));
				bar.setForeground(Rolnews.parameters.getLabelFont().getLabelFont().getFontColor());
				bar.setBounds(new Rectangle(loc + newsLabel[i].getWidth() + 30, 0, 10, 20));
				
				//分隔条位置
				loc = loc + newsLabel[i].getWidth() + 70;
				rollPanel.add(bar);
			}
		}
		
		//添加推荐新闻
//		JLabel label = NewsRecommendation.getRecommNews(loc);
//		if(label != null)
//		{
//			rollPanel.add(label);
//			int width = label.getWidth();
//			
//			//如果要使用条，添加分隔条到面板上
//			if(useBar)
//			{
//				JLabel bar = new JLabel("|");
//				bar.setHorizontalAlignment(SwingConstants.CENTER);
//				bar.setFont(new java.awt.Font("Dialog",0,15));
//				bar.setForeground(Rolnews.parameters.getLabelFont().getLabelFont().getFontColor());
//				bar.setBounds(new Rectangle(loc + width + 30, 0, 10, 20));
//				
//				//分隔条位置
//				location = loc + width + 70;
//				rollPanel.add(bar);
//			}
//		}
		
		//重置面板大小和位置
		if(Rolnews.parameters.getSpeed() > 0)
		{
			rollPanel.setSize(location, height);
			rollPanel.setLocation(Rolnews.parameters.getRightBorder(), 0);
		}
		else
		{
			rollPanel.setSize(location, height);
			rollPanel.setLocation(Rolnews.parameters.getLeftBorder() - rollPanel.getWidth(), 0);
		}
		
		//重置位置变量
		location = 0;
	}

	/**
	 * 给JLabel添加鼠标事件
	 */
	private void addMouseEvent(final JLabel label)
	{
		label.addMouseListener(new MouseAdapter()
		{
			//鼠标进入事件
			public void mouseEntered(MouseEvent e)
			{
				label.setForeground(Color.RED);
				
				//如果当前处于鼠标拖拽，不用获取新闻描述，防止卡顿
				if(MainFrame.isExtending || MainFrame.isDragged)
					return;
				
				//停止面板滚动
				Rolnews.stopRolling();
				
				//添加摘要信息
				String desc = getDescription(label.getName());
				String tip = getTip(label.getName());
				if(desc != null)
				{
//					label.grabFocus();  //强制获得焦点，以便显示新闻摘要
					label.requestFocus();
					label.setToolTipText("<html><p style='margin:3px'>" + tip + "</p><p style='width:300px; margin:3px; margin-bottom:5px'>"
					+ desc + "</p></html>");
				}
				System.out.println(label.getToolTipText());
			}
			
			//鼠标离开事件
			public void mouseExited(MouseEvent e)
			{
				label.setForeground(Rolnews.parameters.getLabelFont().getLabelFont().getFontColor());
				
				if(!MainFrame.lockRoll)
				{
					//开启面板滚动
					Rolnews.startRolling();
				}
			}
			
			//鼠标点击事件
			public void mouseClicked(MouseEvent e)
			{
				//获取当前label的名称（网址链接）
				String href = label.getName();
				try 
				{
					Runtime.getRuntime().exec("cmd /c start " + href);
					
					//将浏览记录添加到数据库
					DataProcessor processor = new DataProcessor("Histories.xml", "Histories");
					//新建历史记录对象
					String title = label.getText();
					String desc = label.getToolTipText();
					History history = new History(title, href, desc);
					processor.AddHistoryRecord(history);
				} 
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		});
	}

	/**
	 * 获取新闻的描述信息，针对单条新闻，从给定网址获取描述
	 * 
	 * @param url
	 * @return
	 */
	private String getDescription(String url)
	{
		String result = null;
		try 
		{
			Document doc = Jsoup.parse(new URL(url), 300);
			String description = doc.select("meta[name=description]").get(0).attr("content");
			result = description;
		} 
		catch (Exception e) 
		{
		}
		
		return result;
	}
	
	/**
	 * 获取标语信息
	 * @return
	 */
	private String getTip(String url)
	{
		String logoLoc = "滚动新闻 -- 新闻热点滚动来袭";
    	
    	if(url.contains("baidu"))
    		logoLoc = "百度新闻  -- 全球最大的中文新闻平台";
    	else if(url.contains("cnr"))
    		logoLoc = "中央人民广播电台";
    	else if(url.contains("sohu"))
    		logoLoc = "搜狐新闻  -- 搜狐";
    	else if(url.contains("163.com"))
    		logoLoc = "网易新闻  -- 有态度的新闻门户";
    	else if(url.contains("sina"))
    		logoLoc = "新浪新闻中心";
    	else if(url.contains("ifeng"))
    		logoLoc = "凤凰网  -- 中国领先的综合门户网站";
    	else if(url.contains("huanqiu"))
    		logoLoc = "环球新闻  -- 全球生活新门户";
    	
    	return logoLoc;
	}
	
	public JLabel[] getNewsLabel() { return newsLabel; }
	public void setNewsLabel(JLabel[] newsLabel) { this.newsLabel = newsLabel; }

	public static int getDestBoard() { return destBoard; }
	public static void setDestBoard(int destBoard) { JLabelFactory.destBoard = destBoard; }
}
