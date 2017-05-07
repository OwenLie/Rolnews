package com.rolnews.windows;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.rolnews.animation.AnimationFactory;
import com.rolnews.animation.Shrink;
import com.rolnews.main.Rolnews;

/**
 * 收缩主窗体的按钮
 * @author Owen
 */
public class ShrinkFrame extends JPanel{

	private static ShrinkFrame button;
	private int padding = Rolnews.parameters.getPadding();
	private int width = 20;
	
	/**
	 * 向面板绘制菜单图标
	 */
	@Override
	public void paint(Graphics g) 
	{
		super.paint(g);
		g.setColor(Color.white);
		g.drawLine(padding,
				   width - padding,
				   width / 2,
				   padding);
		g.drawLine(width / 2,
				   padding,
				   width - padding,
				   width - padding);
	}
	
	private ShrinkFrame()
	{
		this.setLayout(null);
		this.setBackground(Rolnews.parameters.getBgColor().getValue());
		this.setBounds(25,
					   35,
					   width,
					   width);
		button = this;
		
		button.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				//改变窗体颜色
				button.setBackground(Rolnews.parameters.getMouseEnterColor().getValue());
			}

			public void mouseExited(MouseEvent e)
			{
				button.setBackground(Rolnews.parameters.getBgColor().getValue());
			}
			
			public void mouseClicked(MouseEvent e)
			{
				if(!SearchNews.animating)
				{
					Shrink shrink = new AnimationFactory().getShrink();
					shrink.setArguments(Rolnews.getMainFrame(), new Timer(20, shrink));
					Thread shrinkThread = new Thread(shrink);
					shrinkThread.setName("shrinkMainFrame");
					shrinkThread.start();
					
					Rolnews.getMainFrame().getSearchArea().setText("");
					
					//停止控制器的动画控制
					((SearchNews)(Rolnews.getMainFrame().getLeftPanel().getComponent(0))).getController().SetKeepChanging(false);
				}
			}
		});
		
		button.setBorder(BorderFactory.createTitledBorder(""));
	}
	
	public static ShrinkFrame getInstance()
	{
		if(button == null)
			button = new ShrinkFrame();
		
		return button;
	}
}
