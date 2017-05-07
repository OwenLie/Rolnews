package com.rolnews.animation;

import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.Timer;

/**
 * ������������࣬ͨ��ʹ�ü�ʱ����ʵ������ļ򵥶���Ч��
 * 
 * @author Owen Lie
 */
public abstract class FlashUI implements Runnable, ActionListener{

	//���ƶ����������
	protected JComponent component;
	//��ʱ������
	protected Timer timer;
	
	//���ñ���
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
