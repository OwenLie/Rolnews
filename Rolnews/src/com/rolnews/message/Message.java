package com.rolnews.message;

import javax.swing.JLabel;
import javax.swing.Timer;

import com.rolnews.animation.AnimationFactory;
import com.rolnews.animation.FlashUI;
import com.rolnews.main.Rolnews;

/**
 * ��Ϣ��ʾ��������ʾ��Ϣ����ʾ
 * 
 * @author Owen Lie
 */
public class Message extends Thread{

	/**
	 * һ����ʾ���̰�����message����������룬��Ϣ��̬��ʾ����������˳������׶�
	 */
	public static boolean showing;  //��־��ǰmessage�Ƿ�����ʾ����
	private String message;  //Ҫ��ʾ����Ϣ
	private int last = 0;  //��ʾ����ʱ�䣨�룩
	public volatile static boolean keepWaiting = true;  //��֤��Ϣ��ʾ�̴߳�����ʾ�׶Σ�Ϊfalseʱ��ʼ������Ϣ�����˳��׶�
	
	public Message(String message)
	{
		this.message = message;
	}
	
	public Message(String message, int last)
	{
		this.message = message;
		this.last = last;
	}
	
	@Override
	public void run()
	{
		//������Ϣ��ʾ����
		if(!showing)
			showing = true;
		else
			return;

		JLabel messageLabel = Rolnews.getMainFrame().getMessage();
		messageLabel.setText(message);
		messageLabel.setFont(Rolnews.parameters.getLabelFont().getLabelFont().getFont());

		//��Ϣ��ʾ��һ���׶Σ���������
		FlashUI messageIn = new AnimationFactory().getBottomToTop();
		messageIn.setArguments(messageLabel, new Timer(40, messageIn), 2, 0);
		Thread inThread = new Thread(messageIn);
		inThread.setName("Message In");
		inThread.start();

		//��Ϣ����ڶ��׶Σ���̬��ʾ
		keepWaiting = true;
		try 
		{
			Thread.currentThread();
			Thread.sleep(this.last * 1000);
			
			//�����Ϣ����Ķ�����û����ɣ�����˯��
			while(messageIn.timerOn())
			{
				Thread.currentThread();
				Thread.sleep(1000);
			}
			
			while(keepWaiting)
			{
				Thread.currentThread();
				Thread.sleep(1000);
			}
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		//��������׶Σ������˳�
		FlashUI messageOut = new AnimationFactory().getTopToBottom();
		messageOut.setArguments(messageLabel, new Timer(40, messageOut), 21, 0);
		Thread outThread = new Thread(messageOut);
		outThread.setName("Message Out");
		outThread.start();
		
		//�ȴ������˳����֮���˳���ʾ����
		try 
		{
			Thread.currentThread();
			Thread.sleep(1500);
			
			showing = false;
			System.out.println("�Ѿ��˳���Ϣ��ʾ����");
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * �˳���Ϣ��ʾ
	 */
	public static void messageOut()
	{
		if(showing)
			keepWaiting = false;
	}
	
	/**
	 * ������ʾ��Ϣ
	 * @param newMessage
	 */
	public static void messageReset(String newMessage)
	{
		if(showing)
			Rolnews.getMainFrame().getMessage().setText(newMessage);
	}

	public static boolean isShowing() { return showing; }
	public static void setShowing(boolean showing) { Message.showing = showing; }
}
