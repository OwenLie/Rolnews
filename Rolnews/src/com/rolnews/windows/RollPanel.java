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
 * ���Ź�����壬ͨ����ʱ��ʵ�����Ź�������
 */
public class RollPanel extends JPanel implements ActionListener{

	//[start] ˽�г�Ա������

	//����������
	private RollPanel panel;
	private Timer timer;  //��ʱ������

	private final int leftBorder = Rolnews.parameters.getLeftBorder();
	//	private int rightBorder = Rolnews.parameters.getRightBorder();

	//�������ĳ�ʼ��С
	private int width = 600;
	private int height = 20;

	//����ǣ���ͬ�Ĺ�������ǲ�ͬ���˱������ڵ��ڹ���״̬
	private int panelTag = 0;

	//[end]

	/**���캯������ʼ����壬��ʵ����timer*/
	public RollPanel(int tag)
	{
		this.panelTag = tag;

		this.setLayout(null);
		this.setBackground(Rolnews.parameters.getBgColor().getValue());
		this.setBounds(Rolnews.parameters.getRightBorder(), 0, width, height);

		//����ʱʹ�ã���ǲ�ͬ�������
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
	 * ��ʱ�������������㷨��
	 */
	private Point location;  //��Ź���������֮ǰ��λ��
	private Point newLocation; //��Ź���������֮���λ��
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		//��ȡ��ǰ���λ��
		location = panel.getLocation();
		//�½�����֮���λ��
		newLocation = new Point(location.x - Rolnews.parameters.getSpeed(), location.y);
		panel.setLocation(newLocation);

		//���β��λ�ã������ж��Ƿ�Խ��
		int tailLocation = 0;
		//��ȡ��ǰ״̬����
		int rollState = Rolnews.ROLLSTATE;

		//��ͬ�Ĺ����������β��λ�ü��㷽����ͬ
		//����������
		if(Rolnews.parameters.getSpeed() > 0)
		{
			//��ȡβ��λ��
			tailLocation = newLocation.x + panel.getWidth();

			//���β��λ��С���ұ߽�ʱ
			if(tailLocation < Rolnews.parameters.getRightBorder())
			{
				//���Ĺ���״̬��ͨ��set����������һ������timer��ʹ�俪ʼ����
				if(rollState == this.panelTag)
				{
					JLabelFactory.setDestBoard(3 - this.panelTag);
					Rolnews.setRollState(3);

					//�½��̼߳��ر����ļ��е�����
					Thread loadNewsThread = new Thread(new LoadNews());
					loadNewsThread.setName("LoadNews");
					loadNewsThread.start();
				}

				//���β��λ��С����߽�ʱ
				if(tailLocation < leftBorder)
				{
					//���Ĺ���״̬��ͨ��set��������������timerֹͣ����������ƶ����ұ߽�ȴ���һ�ι���
					Rolnews.setRollState(rollState - this.panelTag);
					panel.setLocation(new Point(Rolnews.parameters.getRightBorder(), 0));
					panel.removeAll();
				}
			}
		}

		//������ҹ���
		else
		{
			//��ȡͷ��λ��
			tailLocation = newLocation.x;

			//���β��������߽�ʱ
			if(tailLocation > leftBorder)
			{
				//���Ĺ���״̬��ͨ��set����������һ������timer��ʼ����
				if(rollState == this.panelTag)
				{
					JLabelFactory.setDestBoard(3 - this.panelTag);
					Rolnews.setRollState(3);

					//�½��̼߳��ر����ļ��е�����
					Thread loadNewsThread = new Thread(new LoadNews());
					loadNewsThread.setName("LoadNews");
					loadNewsThread.start();
				}

				//���β��λ�ô����ұ߽�ʱ
				if(tailLocation > Rolnews.parameters.getRightBorder())
				{
					//���Ĺ���״̬��ͨ��set�������������timerֹͣ����������ƶ�����߽�ȴ���һ�ι���
					Rolnews.setRollState(rollState - this.panelTag);
					panel.setLocation(new Point(leftBorder - panel.getWidth(), 0));
					//���������������
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

