package com.rolnews.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.Timer;

import com.rolnews.animation.FadeAndShow;
import com.rolnews.database.DataProcessor;
import com.rolnews.main.Rolnews;
import com.rolnews.model.HotWord;
import com.rolnews.windows.SearchNews;

/**
 * 控制热词显示
 * @author Owen Lie
 * @version 1.0.0
 */
public class HotWordController implements Runnable {

	private boolean keepChanging = true;
	private JTextField text = null;
	public void SetKeepChanging(boolean stopChanging)
	{
		this.keepChanging = stopChanging;
	}

	@Override
	public void run()
	{
		//从数据库获取今日的搜索热词
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		String today = df.format(new Date());
		final List<HotWord> hotWords = new DataProcessor("HotWords.xml", "HotWords").QueryHotWords("RecDate", today);
		int index = 0;
		//显示热词
		text = Rolnews.getMainFrame().getSearchArea();
		text.setText(" 今日热搜: " + hotWords.get(index).getWord());
		text.setName(hotWords.get(index).getHref());
		text.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if(e.getButton() == MouseEvent.BUTTON3)
				{
					//获取当前label的名称（网址链接）
					String href = text.getName();
					System.out.println("访问地址：" + href);
					try 
					{
						java.net.URI uri = java.net.URI.create(href); 
						//获取当前系统桌面扩展
						java.awt.Desktop dp = java.awt.Desktop.getDesktop();
						//判断系统桌面是否支持要执行的功能
						if(dp.isSupported(java.awt.Desktop.Action.BROWSE))
							dp.browse(uri);    //获取系统默认浏览器打开链接
					} 
					catch (IOException e1)
					{
						e1.printStackTrace();
					}
				}
				else if(e.getButton() == MouseEvent.BUTTON1)
				{
					if(e.getClickCount() == 2)
					{
						//停止控制器的动画切换
						((SearchNews)(Rolnews.getMainFrame().getLeftPanel().getComponent(0))).getController().SetKeepChanging(false);
						//清空数据
						text.setText("");
					}
				}
			}
		});
		index++;

		Timer timer = null;
		Thread thread = null;

		while(keepChanging)
		{
			//睡眠
			try
			{
				Thread.currentThread();
				Thread.sleep(7000);
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			//新建线程开启动画切换显示文字
			if(index == hotWords.size())
				index = 0;

			if(!keepChanging)
				break;

			//显示热词
			text = Rolnews.getMainFrame().getSearchArea();
			text.setName(hotWords.get(index).getHref());

			FadeAndShow fade = new FadeAndShow(text, " 今日热搜: " + hotWords.get(index).getWord());
			timer = new Timer(40, fade);
			fade.SetTimer(timer);

			thread = new Thread(fade);
			thread.start();

			index++;
			System.out.println("淡出淡入热搜：" + index);
		}
	}
}
