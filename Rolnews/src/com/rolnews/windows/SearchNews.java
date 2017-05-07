package com.rolnews.windows;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.rolnews.animation.AnimationFactory;
import com.rolnews.animation.Stretch;
import com.rolnews.controller.HotWordController;
import com.rolnews.main.Rolnews;

public class SearchNews extends JPanel{

	protected SearchNews search;
	protected int padding = Rolnews.parameters.getPadding();
	protected int width = Rolnews.getMainFrame().getWindowHeight() - 2 * padding;
	public static boolean animating = false;
	private HotWordController controller = null;
	
	public SearchNews()
	{
		this.setLayout(null);
		this.setBackground(Rolnews.parameters.getBgColor().getValue());
		this.setBounds(0,
					   0,
					   width,
					   width);
		
		//�������ͼ��
		URL img = RNPopupMenu.class.getResource("search-white.png");
		ImageIcon icon = new ImageIcon(img);
		icon.setImage(icon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		JLabel label = new JLabel(icon);
		label.setBounds(0, 0, 20, 20);
		this.add(label);
		
		search = this;
		
		//�������¼�
		search.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				//�ı䴰����ɫ
				search.setBackground(Rolnews.parameters.getMouseEnterColor().getValue());
			}

			public void mouseExited(MouseEvent e)
			{
				search.setBackground(Rolnews.parameters.getBgColor().getValue());
			}
			
			public void mouseClicked(MouseEvent e)
			{
				//����δ�������½��߳���ʾ������
				if(Rolnews.getMainFrame().getSize().height == 30)
				{
					animating = true;
					Stretch stretch = new AnimationFactory().getStretch();
					stretch.setArguments(Rolnews.getMainFrame(), new Timer(20, stretch), 60);
					Thread stretchThread = new Thread(stretch);
					stretchThread.setName("stretchMainFrame");
					stretchThread.start();
					
					//�������������½������л������Ķ���
					controller = new HotWordController();
					Thread thread = new Thread(controller);
					thread.start();
				}
			}
		});
	}

	public HotWordController getController() {
		return controller;
	}

	public void setController(HotWordController controller) {
		this.controller = controller;
	}
}
