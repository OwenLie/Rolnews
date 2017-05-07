package com.rolnews.animation;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.Timer;

/**
 * ���ֵ��������л�����
 * @author Owen Lie
 * @version 1.0.0
 */
public class FadeAndShow implements Runnable, ActionListener{

	//���ƶ����������
	private JTextField component;
	//��ʱ������
	private Timer timer;
	private String newText = "Rolnews";

	//���캯��
	public FadeAndShow(JTextField component, String newText)
	{
		this.component = component;
		this.newText = newText;
	}
	
	//���ü�ʱ��
	public void SetTimer(Timer timer)
	{
		this.timer = timer;
	}
	
	public void run()
	{
		timer.start();
	}

	private Color transparency = null;
	private boolean fading = true;  //��־�Ƿ��ڵ����׶�
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
