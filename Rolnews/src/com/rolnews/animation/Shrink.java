package com.rolnews.animation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;
/**
 * ��������������������߶�
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
		//��ȡ�����ǰ��С
		int width = mainFrame.getSize().width;
		int height = mainFrame.getSize().height;

		//�����µĸ߶�
		mainFrame.setSize(width, height - 1);

		//������չ
		if(height - 1 <= 30)
			this.timer.stop();
	}
}
