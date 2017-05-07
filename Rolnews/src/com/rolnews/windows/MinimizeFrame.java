package com.rolnews.windows;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import com.rolnews.main.Rolnews;

/**
 * 将窗体最小化
 * 
 * @author Owen Lie
 */
public class MinimizeFrame extends MenuButton{

	/**
	 * 向面板绘制菜单图标
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
	 * 构造函数，将继承的按钮对象添加鼠标事件
	 */
	public MinimizeFrame(double bttnNum)
	{
		super(bttnNum);
		
		if(button == null)
			button = new MenuButton(0);
		
		button.addMouseListener(new MouseAdapter()
		{
			//鼠标按下事件
			public void mouseClicked(MouseEvent e)
			{
				Rolnews.getMainFrame().setExtendedState(JFrame.ICONIFIED);
			}
		});
	}
}
