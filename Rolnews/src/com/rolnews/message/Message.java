package com.rolnews.message;

import javax.swing.JLabel;
import javax.swing.Timer;

import com.rolnews.animation.AnimationFactory;
import com.rolnews.animation.FlashUI;
import com.rolnews.main.Rolnews;

/**
 * 消息提示，负责提示消息的显示
 * 
 * @author Owen Lie
 */
public class Message extends Thread{

	/**
	 * 一个显示过程包括，message组件动画进入，消息静态显示和组件动画退出三个阶段
	 */
	public static boolean showing;  //标志当前message是否处于显示过程
	private String message;  //要显示的消息
	private int last = 0;  //显示持续时间（秒）
	public volatile static boolean keepWaiting = true;  //保证消息显示线程处于显示阶段，为false时开始进入消息动画退出阶段
	
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
		//进入消息显示过程
		if(!showing)
			showing = true;
		else
			return;

		JLabel messageLabel = Rolnews.getMainFrame().getMessage();
		messageLabel.setText(message);
		messageLabel.setFont(Rolnews.parameters.getLabelFont().getLabelFont().getFont());

		//消息显示第一个阶段，动画进入
		FlashUI messageIn = new AnimationFactory().getBottomToTop();
		messageIn.setArguments(messageLabel, new Timer(40, messageIn), 2, 0);
		Thread inThread = new Thread(messageIn);
		inThread.setName("Message In");
		inThread.start();

		//消息进入第二阶段，静态显示
		keepWaiting = true;
		try 
		{
			Thread.currentThread();
			Thread.sleep(this.last * 1000);
			
			//如果消息进入的动画还没有完成，继续睡眠
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
		
		//进入第三阶段，动画退出
		FlashUI messageOut = new AnimationFactory().getTopToBottom();
		messageOut.setArguments(messageLabel, new Timer(40, messageOut), 21, 0);
		Thread outThread = new Thread(messageOut);
		outThread.setName("Message Out");
		outThread.start();
		
		//等待动画退出完成之后退出显示过程
		try 
		{
			Thread.currentThread();
			Thread.sleep(1500);
			
			showing = false;
			System.out.println("已经退出消息显示过程");
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 退出消息显示
	 */
	public static void messageOut()
	{
		if(showing)
			keepWaiting = false;
	}
	
	/**
	 * 重置提示消息
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
