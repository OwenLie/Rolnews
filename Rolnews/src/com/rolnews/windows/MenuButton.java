package com.rolnews.windows;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.rolnews.main.Rolnews;
/**
 * 菜单按钮类，定义基础的位置数据，按钮对象
 * 
 * @author Owen Lie
 */
public class MenuButton extends JPanel{

	protected MenuButton button;  //按钮对象
	protected int padding = Rolnews.parameters.getPadding();
	protected int width = Rolnews.getMainFrame().getWindowHeight() - 2 * padding;
	
	//指示控制按钮的动画线程是否继续休眠
	public static boolean keepSleeping = false;
	
	public MenuButton(double bttnNum)
	{
		this.setLayout(null);
		this.setBackground(Rolnews.parameters.getBgColor().getValue());
		this.setBounds(padding + (int)(bttnNum * width),
					   0,
					   width,
					   width);
		button = this;
		
		button.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				//改变窗体颜色
				button.setBackground(Rolnews.parameters.getMouseEnterColor().getValue());
				keepSleeping = true;
			}

			public void mouseExited(MouseEvent e)
			{
				button.setBackground(Rolnews.parameters.getBgColor().getValue());
				keepSleeping = false;
			}
		});
		
		if(Rolnews.parameters.isShowBorder())
			this.setBorder(BorderFactory.createTitledBorder(""));
	}
}
