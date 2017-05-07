package com.rolnews.windows;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.rolnews.main.Rolnews;
/**
 * �˵���ť�࣬���������λ�����ݣ���ť����
 * 
 * @author Owen Lie
 */
public class MenuButton extends JPanel{

	protected MenuButton button;  //��ť����
	protected int padding = Rolnews.parameters.getPadding();
	protected int width = Rolnews.getMainFrame().getWindowHeight() - 2 * padding;
	
	//ָʾ���ư�ť�Ķ����߳��Ƿ��������
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
				//�ı䴰����ɫ
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
