package com.rolnews.windows;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import com.rolnews.main.Rolnews;

/**
 * ��������С��
 * 
 * @author Owen Lie
 */
public class MinimizeFrame extends MenuButton{

	/**
	 * �������Ʋ˵�ͼ��
	 */
	@Override
	public void paint(Graphics g) 
	{
		super.paint(g);
		g.setColor(Color.white);
		g.drawLine(padding,
				   width / 2,
				   width - padding,
				   width / 2);
	}
	
	/**
	 * ���캯�������̳еİ�ť�����������¼�
	 */
	public MinimizeFrame(double bttnNum)
	{
		super(bttnNum);
		
		if(button == null)
			button = new MenuButton(0);
		
		button.addMouseListener(new MouseAdapter()
		{
			//��갴���¼�
			public void mouseClicked(MouseEvent e)
			{
				Rolnews.getMainFrame().setExtendedState(JFrame.ICONIFIED);
			}
		});
	}
}
