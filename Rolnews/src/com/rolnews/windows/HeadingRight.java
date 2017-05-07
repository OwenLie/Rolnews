package com.rolnews.windows;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.rolnews.main.Rolnews;

/**
 * ���ҿ����ť
 * 
 * @author Owen Lie
 */
public class HeadingRight extends MenuButton{

	/**
	 * �������Ʋ˵�ͼ��
	 */
	@Override
	public void paint(Graphics g) 
	{
		super.paint(g);
		g.setColor(Color.white);
		g.drawLine(padding,
				   padding,
				   width - padding,
				   width / 2);
		g.drawLine(width - padding,
				   width / 2,
				   padding,
				   width - padding);
	}
	
	/**
	 * ���캯�������̳еİ�ť�����������¼�
	 */
	private int speed = 0;  //���ڱ��������֮ǰ���ٶ�
	public HeadingRight(double bttnNum) 
	{
		super(bttnNum);
		
		if(button == null)
			button = new MenuButton(0);
		
		button.addMouseListener(new MouseAdapter()
		{
			//��갴���¼�
			public void mousePressed(MouseEvent e)
			{
				speed = Rolnews.parameters.getSpeed();
				Rolnews.parameters.setSpeed(-6);
				System.out.println("pressed");
			}
			
			public void mouseReleased(MouseEvent e)
			{
				Rolnews.parameters.setSpeed(speed);
				System.out.println("released");
			}
		});
	}
}
