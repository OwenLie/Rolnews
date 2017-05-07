package com.rolnews.animation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;
/**
 * 收缩动画，收缩主窗体高度
 * @author Owen Lie
 */
public class Shrink implements Runnable, ActionListener{

	private Timer timer;
	private JFrame mainFrame;
	
	@Override
	public void run()
	{
		if(timer != null)
			this.timer.start();
	}

	public void setArguments(JFrame mainFrame, Timer timer)
	{
		this.mainFrame = mainFrame;
		this.timer = timer;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		//获取组件当前大小
		int width = mainFrame.getSize().width;
		int height = mainFrame.getSize().height;

		//设置新的高度
		mainFrame.setSize(width, height - 1);

		//结束扩展
		if(height - 1 <= 30)
			this.timer.stop();
	}
}
