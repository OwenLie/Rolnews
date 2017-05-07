package com.rolnews.windows;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.rolnews.main.Rolnews;

/**
 * 向左快进按钮
 * 
 * @author Owen Lie
 */
public class HeadingLeft extends MenuButton{

	/**
	 * 向面板绘制菜单图标
	 */
	@Override
	public void paint(Graphics g) 
	{
		super.paint(g);
		g.setColor(Color.white);
		g.drawLine(width - padding,
				   padding,
				   padding,
				   width / 2);
		g.drawLine(padding,
				   width / 2,
				   width - padding,
				   width - padding);
	}
	
	/**
	 * 构造函数，将继承的按钮对象添加鼠标事件
	 */
	private int speed = 0;  //用于保存点击快进之前的速度
	public HeadingLeft(double bttnNum) 
	{ 
		super(bttnNum); 
		
		if(button == null)
			button = new MenuButton(0);
		
		button.addMouseListener(new MouseAdapter()
		{
			//鼠标按下事件
			public void mousePressed(MouseEvent e)
			{
				speed = Rolnews.parameters.getSpeed();
				Rolnews.parameters.setSpeed(6);
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
