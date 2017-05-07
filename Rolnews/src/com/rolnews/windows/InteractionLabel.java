package com.rolnews.windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.rolnews.main.Rolnews;

public class InteractionLabel extends JPanel{

	private static InteractionLabel interactionLabel;
	protected int padding = Rolnews.parameters.getPadding();
	protected int width = Rolnews.getMainFrame().getWindowHeight() - 2 * padding;
	
	/**
	 * 构造函数，设置为私有，此类设置为单例类
	 */
	private InteractionLabel()
	{
		this.setLayout(null);
		this.setBackground(Rolnews.parameters.getMouseEnterColor().getValue());
		this.setBounds(3 * padding,
					   0,
					   width,
					   width);
		
		interactionLabel = this;
	}
	
	public static InteractionLabel getInstance(String title, String[] items)
	{
		if(interactionLabel == null)
			interactionLabel = new InteractionLabel();
		
		//清空已有数据
		interactionLabel.removeAll();
		
		//字体
		Font font = Rolnews.parameters.getLabelFont().getLabelFont().getFont();
		
//[start] 新建交互语句标签
		
		JLabel actionTitle = new JLabel(title);
		actionTitle.setFont(font);
		actionTitle.setForeground(Color.WHITE);

		//获取label的像素长度
		int titleWidth = actionTitle.getFontMetrics
				(actionTitle.getFont()).stringWidth(title);

		//设置label的位置
		actionTitle.setBounds(new Rectangle(0, 0, titleWidth, 20));
		interactionLabel.add(actionTitle);
		
//[end]
		
//[start] 新建选项标签
		
		int size = items.length;
		JLabel[] labels = new JLabel[size];
		int baseLoc = titleWidth;
		for(int i = 0; i < size; i++)
		{
			labels[i] = new JLabel(items[i]);
			labels[i].setFont(font);
			labels[i].setForeground(Color.white);
			//获取label的像素长度
			int itemlength = actionTitle.getFontMetrics
					(actionTitle.getFont()).stringWidth(items[i]);
			
			labels[i].setBounds(new Rectangle(baseLoc + 30, 0, itemlength, 20));
			baseLoc = baseLoc + 30 + itemlength;
			
			interactionLabel.add(labels[i]);
		}
//[end]
		
		interactionLabel.setBounds(0, 0, baseLoc, 20);
		
		return interactionLabel;
	}
}
