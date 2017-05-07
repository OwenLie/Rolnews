package com.rolnews.animation;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.Timer;

/**
 * 文字淡出淡入切换动画
 * @author Owen Lie
 * @version 1.0.0
 */
public class FadeAndShow implements Runnable, ActionListener{

	//被移动的组件对象
	private JTextField component;
	//计时器对象
	private Timer timer;
	private String newText = "Rolnews";

	//构造函数
	public FadeAndShow(JTextField component, String newText)
	{
		this.component = component;
		this.newText = newText;
	}
	
	//设置计时器
	public void SetTimer(Timer timer)
	{
		this.timer = timer;
	}
	
	public void run()
	{
		timer.start();
	}

	private Color transparency = null;
	private boolean fading = true;  //标志是否处于淡出阶段
	public void actionPerformed(ActionEvent e)
	{
		transparency = component.getForeground();
		if(transparency.getAlpha() >= 50 && fading)
		{
			transparency = new Color(transparency.getRed(), transparency.getGreen(), 
					transparency.getBlue(), transparency.getAlpha() - 50);
			component.setForeground(transparency);
		}
		else
		{
			fading = false;
			component.setText(this.newText);
			transparency = new Color(transparency.getRed(), transparency.getGreen(),
					transparency.getBlue(), transparency.getAlpha() + 50);
			component.setForeground(transparency);
		}
			
		if(transparency.getAlpha() == 255)
			timer.stop();
	}
}
