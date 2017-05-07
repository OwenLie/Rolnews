package com.rolnews.animation;

import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.Timer;

/**
 * 窗体组件动画类，通过使用计时器，实现组件的简单动画效果
 * 
 * @author Owen Lie
 */
public abstract class FlashUI implements Runnable, ActionListener{

	//被移动的组件对象
	protected JComponent component;
	//计时器对象
	protected Timer timer;
	
	//备用变量
	protected int arg1;
	protected int arg2;
	protected String arg3;
	
	public final void setArguments(JComponent component, Timer timer, int arg1, int arg2)
	{
		this.component = component;
		this.timer = timer;
		this.arg1 = arg1;
		this.arg2 = arg2;
	}
	
	public final void setArguments(JComponent component, Timer timer, String arg3)
	{
		this.component = component;
		this.timer = timer;
		this.arg3 = arg3;
	}
	
	public boolean timerOn()
	{
		return this.timer.isRunning();
	}
}
