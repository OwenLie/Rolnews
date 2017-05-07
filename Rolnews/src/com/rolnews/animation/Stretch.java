package com.rolnews.animation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import com.rolnews.windows.SearchNews;

/**
 * 主窗体伸展动画，显示搜索框时使用
 * @author Owen Lie
 */
public class Stretch implements Runnable, ActionListener{

	private Timer timer;
	private int arg1;
	private JFrame mainFrame;
	
	@Override
	public void run()
	{
		if(timer != null)
			timer.start();
	}

	public void setArguments(JFrame mainFrame, Timer timer, int arg1)
	{
		this.mainFrame = mainFrame;
		this.timer = timer;
		this.arg1 = arg1;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		//获取组件当前大小
		int width = mainFrame.getSize().width;
		int height = mainFrame.getSize().height;
		
		//设置新的高度
		mainFrame.setSize(width, height + 1);
		
		//结束扩展
		if(height + 1 >= this.arg1)
		{
			this.timer.stop();
			SearchNews.animating = false;
		}
	}
}
