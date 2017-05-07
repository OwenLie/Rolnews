package com.rolnews.animation;

import java.awt.Point;
import java.awt.event.ActionEvent;

/**
 * ʵ�ִ��������ƶ�����Ķ�����ʹ�õı��ñ���Ϊarg1(int);
 * 
 * @author Owen Lie
 */
public class BottomToTop extends FlashUI{

	@Override
	public void run()
	{
		super.timer.start();
	}

	private int x;  //�����ǰx����
	private int y;  //�����ǰy����
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		//��ȡ����ĵ�ǰy����
		y = component.getLocation().y;
		x = component.getLocation().x;

		//�����µ�����
		Point newLoc = new Point(x, y - 1);
		component.setLocation(newLoc);

		if(newLoc.y < this.arg1)
			this.timer.stop();
	}
}
