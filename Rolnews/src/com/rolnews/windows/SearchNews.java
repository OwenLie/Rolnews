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
		
		//添加搜索图标
		URL img = RNPopupMenu.class.getResource("search-white.png");
		ImageIcon icon = new ImageIcon(img);
		icon.setImage(icon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		JLabel label = new JLabel(icon);
		label.setBounds(0, 0, 20, 20);
		this.add(label);
		
		search = this;
		
		//添加鼠标事件
		search.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				//改变窗体颜色
				search.setBackground(Rolnews.parameters.getMouseEnterColor().getValue());
			}

			public void mouseExited(MouseEvent e)
			{
				search.setBackground(Rolnews.parameters.getBgColor().getValue());
			}
			
			public void mouseClicked(MouseEvent e)
			{
				//窗体未扩张是新建线程显示搜索框
				if(Rolnews.getMainFrame().getSize().height == 30)
				{
					animating = true;
					Stretch stretch = new AnimationFactory().getStretch();
					stretch.setArguments(Rolnews.getMainFrame(), new Timer(20, stretch), 60);
					Thread stretchThread = new Thread(stretch);
					stretchThread.setName("stretchMainFrame");
					stretchThread.start();
					
					//启动控制器，新建启动切换动画的对象
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
