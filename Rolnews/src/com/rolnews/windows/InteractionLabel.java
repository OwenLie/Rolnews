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
	 * ���캯��������Ϊ˽�У���������Ϊ������
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
		
		//�����������
		interactionLabel.removeAll();
		
		//����
		Font font = Rolnews.parameters.getLabelFont().getLabelFont().getFont();
		
//[start] �½���������ǩ
		
		JLabel actionTitle = new JLabel(title);
		actionTitle.setFont(font);
		actionTitle.setForeground(Color.WHITE);

		//��ȡlabel�����س���
		int titleWidth = actionTitle.getFontMetrics
				(actionTitle.getFont()).stringWidth(title);

		//����label��λ��
		actionTitle.setBounds(new Rectangle(0, 0, titleWidth, 20));
		interactionLabel.add(actionTitle);
		
//[end]
		
//[start] �½�ѡ���ǩ
		
		int size = items.length;
		JLabel[] labels = new JLabel[size];
		int baseLoc = titleWidth;
		for(int i = 0; i < size; i++)
		{
			labels[i] = new JLabel(items[i]);
			labels[i].setFont(font);
			labels[i].setForeground(Color.white);
			//��ȡlabel�����س���
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
