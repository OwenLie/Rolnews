package com.rolnews.windows;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.rolnews.loadnews.JLabelFactory;
import com.rolnews.loadnews.LoadNews;
import com.rolnews.main.Rolnews;

/**
 * 新闻滚动面板，通过计时器实现新闻滚动控制
 */
public class RollPanel extends JPanel implements ActionListener{

	//[start] 私有成员变量区

	//滚动面板对象
	private RollPanel panel;
	private Timer timer;  //计时器对象

	private final int leftBorder = Rolnews.parameters.getLeftBorder();
	//	private int rightBorder = Rolnews.parameters.getRightBorder();

	//滚动面板的初始大小
	private int width = 600;
	private int height = 20;

	//面板标记，不同的滚动面板标记不同，此变量用于调节滚动状态
	private int panelTag = 0;

	//[end]

	/**构造函数，初始化面板，并实例化timer*/
	public RollPanel(int tag)
	{
		this.panelTag = tag;

		this.setLayout(null);
		this.setBackground(Rolnews.parameters.getBgColor().getValue());
		this.setBounds(Rolnews.parameters.getRightBorder(), 0, width, height);

		//测试时使用，标记不同滚动面板
//		if(tag == 1)
//		{
//			this.setBackground(Color.black);
//			this.setBounds(10, 0, width, height);
//		}
//		else
//		{
//			this.setBackground(Color.blue);
//			this.setBounds(30, 0, width, height);
//		}

		panel = this;
		timer = new Timer(Rolnews.parameters.getInterval(), panel);
	}

	/**
	 * 计时器动作（滚动算法）
	 */
	private Point location;  //存放滚动面板滚动之前的位置
	private Point newLocation; //存放滚动面板滚动之后的位置
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		//获取当前面板位置
		location = panel.getLocation();
		//新建滚动之后的位置
		newLocation = new Point(location.x - Rolnews.parameters.getSpeed(), location.y);
		panel.setLocation(newLocation);

		//面板尾部位置，用于判断是否越界
		int tailLocation = 0;
		//获取当前状态变量
		int rollState = Rolnews.ROLLSTATE;

		//不同的滚动方向，面板尾部位置计算方法不同
		//如果向左滚动
		if(Rolnews.parameters.getSpeed() > 0)
		{
			//获取尾部位置
			tailLocation = newLocation.x + panel.getWidth();

			//面板尾部位置小于右边界时
			if(tailLocation < Rolnews.parameters.getRightBorder())
			{
				//更改滚动状态，通过set方法触发另一个面板的timer，使其开始滚动
				if(rollState == this.panelTag)
				{
					JLabelFactory.setDestBoard(3 - this.panelTag);
					Rolnews.setRollState(3);

					//新建线程加载本地文件中的新闻
					Thread loadNewsThread = new Thread(new LoadNews());
					loadNewsThread.setName("LoadNews");
					loadNewsThread.start();
				}

				//面板尾部位置小于左边界时
				if(tailLocation < leftBorder)
				{
					//更改滚动状态，通过set方法触发此面板的timer停止，并将面板移动到右边界等待下一次滚动
					Rolnews.setRollState(rollState - this.panelTag);
					panel.setLocation(new Point(Rolnews.parameters.getRightBorder(), 0));
					panel.removeAll();
				}
			}
		}

		//如果向右滚动
		else
		{
			//获取头部位置
			tailLocation = newLocation.x;

			//面板尾部大于左边界时
			if(tailLocation > leftBorder)
			{
				//更改滚动状态，通过set方法触发另一个面板的timer开始滚动
				if(rollState == this.panelTag)
				{
					JLabelFactory.setDestBoard(3 - this.panelTag);
					Rolnews.setRollState(3);

					//新建线程加载本地文件中的新闻
					Thread loadNewsThread = new Thread(new LoadNews());
					loadNewsThread.setName("LoadNews");
					loadNewsThread.start();
				}

				//面板尾部位置大于右边界时
				if(tailLocation > Rolnews.parameters.getRightBorder())
				{
					//更改滚动状态，通过set方法触发此面板timer停止，并将面板移动到左边界等待下一次滚动
					Rolnews.setRollState(rollState - this.panelTag);
					panel.setLocation(new Point(leftBorder - panel.getWidth(), 0));
					//清空所有新闻数据
					panel.removeAll();
				}
			}
		}
	}

	public RollPanel getPanel() { return panel; }
	public void setPanel(RollPanel panel) { this.panel = panel; }

	public Timer getTimer() { return timer; }
	public void setTimer(Timer timer) { this.timer = timer; }

	public int getPanelTag() { return panelTag; }
	public void setPanelTag(int panelTag) { this.panelTag = panelTag; }
}

