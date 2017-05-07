package com.rolnews.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.Timer;

import com.rolnews.animation.FadeAndShow;
import com.rolnews.database.DataProcessor;
import com.rolnews.main.Rolnews;
import com.rolnews.model.HotWord;
import com.rolnews.windows.SearchNews;

/**
 * �����ȴ���ʾ
 * @author Owen Lie
 * @version 1.0.0
 */
public class HotWordController implements Runnable {

	private boolean keepChanging = true;
	private JTextField text = null;
	public void SetKeepChanging(boolean stopChanging)
	{
		this.keepChanging = stopChanging;
	}

	@Override
	public void run()
	{
		//�����ݿ��ȡ���յ������ȴ�
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
		String today = df.format(new Date());
		final List<HotWord> hotWords = new DataProcessor("HotWords.xml", "HotWords").QueryHotWords("RecDate", today);
		int index = 0;
		//��ʾ�ȴ�
		text = Rolnews.getMainFrame().getSearchArea();
		text.setText(" ��������: " + hotWords.get(index).getWord());
		text.setName(hotWords.get(index).getHref());
		text.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if(e.getButton() == MouseEvent.BUTTON3)
				{
					//��ȡ��ǰlabel�����ƣ���ַ���ӣ�
					String href = text.getName();
					System.out.println("���ʵ�ַ��" + href);
					try 
					{
						java.net.URI uri = java.net.URI.create(href); 
						//��ȡ��ǰϵͳ������չ
						java.awt.Desktop dp = java.awt.Desktop.getDesktop();
						//�ж�ϵͳ�����Ƿ�֧��Ҫִ�еĹ���
						if(dp.isSupported(java.awt.Desktop.Action.BROWSE))
							dp.browse(uri);    //��ȡϵͳĬ�������������
					} 
					catch (IOException e1)
					{
						e1.printStackTrace();
					}
				}
				else if(e.getButton() == MouseEvent.BUTTON1)
				{
					if(e.getClickCount() == 2)
					{
						//ֹͣ�������Ķ����л�
						((SearchNews)(Rolnews.getMainFrame().getLeftPanel().getComponent(0))).getController().SetKeepChanging(false);
						//�������
						text.setText("");
					}
				}
			}
		});
		index++;

		Timer timer = null;
		Thread thread = null;

		while(keepChanging)
		{
			//˯��
			try
			{
				Thread.currentThread();
				Thread.sleep(7000);
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			//�½��߳̿��������л���ʾ����
			if(index == hotWords.size())
				index = 0;

			if(!keepChanging)
				break;

			//��ʾ�ȴ�
			text = Rolnews.getMainFrame().getSearchArea();
			text.setName(hotWords.get(index).getHref());

			FadeAndShow fade = new FadeAndShow(text, " ��������: " + hotWords.get(index).getWord());
			timer = new Timer(40, fade);
			fade.SetTimer(timer);

			thread = new Thread(fade);
			thread.start();

			index++;
			System.out.println("�����������ѣ�" + index);
		}
	}
}
