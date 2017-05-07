package com.rolnews.animation;

import java.awt.Point;
import java.awt.event.ActionEvent;

/**
 * 实现从下向上移动组件的动画，使用的备用变量为arg1(int);
 * 
 * @author Owen Lie
 */
public class BottomToTop extends FlashUI{

	@Override
	public void run()
	{
		super.timer.start();
	}

	private int x;  //组件当前x坐标
	private int y;  //组件当前y坐标
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		//获取组件的当前y坐标
		y = component.getLocation().y;
		x = component.getLocation().x;

		//设置新的坐标
		Point newLoc = new Point(x, y - 1);
		component.setLocation(newLoc);

		if(newLoc.y < this.arg1)
			this.timer.stop();
	}
}
