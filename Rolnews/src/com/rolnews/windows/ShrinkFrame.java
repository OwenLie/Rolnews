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
 * ����������İ�ť
 * @author Owen
 */
public class ShrinkFrame extends JPanel{

	private static ShrinkFrame button;
	private int padding = Rolnews.parameters.getPadding();
	private int width = 20;
	
	/**
	 * �������Ʋ˵�ͼ��
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
				//�ı䴰����ɫ
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
					
					//ֹͣ�������Ķ�������
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
