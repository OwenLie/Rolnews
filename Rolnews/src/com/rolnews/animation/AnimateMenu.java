package com.rolnews.animation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import com.rolnews.main.Rolnews;
import com.rolnews.windows.MainFrame;
import com.rolnews.windows.MenuButton;

public class AnimateMenu implements Runnable, ActionListener{

	//控制动画的计时器
	private Timer timer;
	private MenuButton bttn1;
	private MenuButton bttn2;
	private MenuButton bttn3;
	private MenuButton bttn4;
	
	private boolean up = false;  //标志菜单动画是否向上
	
	public AnimateMenu(){}
	
	public void setComponents(Timer timer, MenuButton bttn1, MenuButton bttn2, MenuButton bttn3, MenuButton bttn4)
	{
		this.bttn1 = bttn1;
		this.bttn2 = bttn2;
		this.bttn3 = bttn3;
		this.bttn4 = bttn4;
		
		this.timer = timer;
	}
	
	public void run()
	{
		//如果禁止了动画，直接返回
		if(!Rolnews.parameters.isEnableMenuAnimation())
		{
			MainFrame.lockAnimation = false;
			return;
		}
		
		//菜单处于显示状态则隐藏
		if(MainFrame.menuShowing)
		{
			//当前线程睡眠五秒
			try 
			{
				Thread.currentThread();
				Thread.sleep(15000);
				
				while(MenuButton.keepSleeping)
				{
					Thread.currentThread();
					Thread.sleep(10000);
				}
				
				up = false;
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		
		//菜单处于隐藏状态，显示
		else
			up = true;
		
		//开启计时器
		if(this.timer != null)
			this.timer.start();
		
		//如果是向上的动画，线程沉睡，等待隐藏菜单按钮
		if(up)
		{
			//当前线程睡眠15秒
			try 
			{
				Thread.currentThread();
				Thread.sleep(15000);
				
				while(MenuButton.keepSleeping)
				{
					Thread.currentThread();
					Thread.sleep(10000);
				}
				
				up = false;
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
			if(this.timer != null)
				this.timer.start();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		//向上
		if(up)
		{
			//移动第一个按钮
			int y1 = this.bttn1.getLocation().y - 1;
			if(y1 >= 0)
				this.bttn1.setLocation(this.bttn1.getLocation().x, y1);
			
			//移动第二个按钮
			int y2 = this.bttn2.getLocation().y - 1;
			if(y1 < 16 && y2 >= 0)
				this.bttn2.setLocation(this.bttn2.getLocation().x, y2);
			
			//移动第三个按钮
			int y3 = this.bttn3.getLocation().y - 1;
			if(y2 < 16 && y3 >= 0)
				this.bttn3.setLocation(this.bttn3.getLocation().x, y3);
			
			//移动第四个按钮
			int y4 = this.bttn4.getLocation().y - 1;
			if(y3 < 16 && y4 >= 0)
				this.bttn4.setLocation(this.bttn4.getLocation().x, y4);
			
			//移动图标
			int y5 = Rolnews.getMainFrame().getRightPanel().getComponent(0).getLocation().y - 1;
			if(y1 < 20 && y5 >= -20)
				Rolnews.getMainFrame().getRightPanel().getComponent(0).setLocation(5, y5);
			
			if(y4 == 0)
			{
				this.timer.stop();
				MainFrame.menuShowing = true;
			}
		}
		
		//菜单向下
		else
		{
			//移动第一个按钮
			int y1 = this.bttn1.getLocation().y + 1;
			if(y1 < 21)
				this.bttn1.setLocation(this.bttn1.getLocation().x, y1);
			
			//移动第二个按钮
			int y2 = this.bttn2.getLocation().y + 1;
			if(y1 > 4 && y2 < 21)
				this.bttn2.setLocation(this.bttn2.getLocation().x, y2);
			
			//移动第三个按钮
			int y3 = this.bttn3.getLocation().y + 1;
			if(y2 > 4 && y3 < 21)
				this.bttn3.setLocation(this.bttn3.getLocation().x, y3);
			
			//移动第四个按钮
			int y4 = this.bttn4.getLocation().y + 1;
			if(y3 > 4 && y4 < 21)
				this.bttn4.setLocation(this.bttn4.getLocation().x, y4);
			
			//移动图标
			int y5 = Rolnews.getMainFrame().getRightPanel().getComponent(0).getLocation().y + 1;
			if(y4 < 16 && y5 <= 0)
				Rolnews.getMainFrame().getRightPanel().getComponent(0).setLocation(5, y5);
			
			if(y4 == 21)
			{
				this.timer.stop();
				MainFrame.menuShowing = false;
				MainFrame.lockAnimation = false;
			}
		}
	}

	public void setUp(boolean up) { this.up = up; }
}
