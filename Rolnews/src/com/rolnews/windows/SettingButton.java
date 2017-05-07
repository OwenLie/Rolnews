package com.rolnews.windows;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 设置项按钮
 * 
 * @author Owen Lie
 */
public class SettingButton extends MenuButton{

	//弹出菜单对象
	private RNPopupMenu rnPopupMenu = null;
		
	/**
	 * 向面板绘制菜单图标
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
	 * 构造函数，给按钮对象添加鼠标事件
	 */
	public SettingButton(double bttnNum) 
	{
		super(bttnNum);
		
		if(button == null)
			button = new MenuButton(0);
		
		rnPopupMenu = new RNPopupMenu();
		button.addMouseListener(new MouseAdapter()
		{
			//鼠标按下事件
			public void mouseClicked(MouseEvent e)
			{
				rnPopupMenu.getNewsMenu().show(e.getComponent() , 0, 25);
			}
		});
	}

}
