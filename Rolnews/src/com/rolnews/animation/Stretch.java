package com.rolnews.animation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import com.rolnews.windows.SearchNews;

/**
 * ��������չ��������ʾ������ʱʹ��
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
		//��ȡ�����ǰ��С
		int width = mainFrame.getSize().width;
		int height = mainFrame.getSize().height;
		
		//�����µĸ߶�
		mainFrame.setSize(width, height + 1);
		
		//������չ
		if(height + 1 >= this.arg1)
		{
			this.timer.stop();
			SearchNews.animating = false;
		}
	}
}
