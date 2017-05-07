package com.rolnews.windows;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * �����ť
 * 
 * @author Owen Lie
 */
public class SettingButton extends MenuButton{

	//�����˵�����
	private RNPopupMenu rnPopupMenu = null;
		
	/**
	 * �������Ʋ˵�ͼ��
	 */
	@Override
	public void paint(Graphics g) 
	{
		super.paint(g);
		g.setColor(Color.white);
		g.drawOval(padding, 
				padding, 
				width - 2 * padding, 
				width - 2 * padding);
		g.fillOval(padding + 2, 
				padding + 2,
				width - 2 * padding - 4,
				width - 2 * padding - 4);
	}
	
	/**
	 * ���캯��������ť�����������¼�
	 */
	public SettingButton(double bttnNum) 
	{
		super(bttnNum);
		
		if(button == null)
			button = new MenuButton(0);
		
		rnPopupMenu = new RNPopupMenu();
		button.addMouseListener(new MouseAdapter()
		{
			//��갴���¼�
			public void mouseClicked(MouseEvent e)
			{
				rnPopupMenu.getNewsMenu().show(e.getComponent() , 0, 25);
			}
		});
	}

}
