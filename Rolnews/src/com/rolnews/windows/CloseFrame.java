package com.rolnews.windows;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import javax.swing.Timer;

import com.rolnews.main.Rolnews;
import com.rolnews.message.Message;
import com.rolnews.setting.SettingOperation;

/**
 * 关闭程序按钮
 * 
 * @author Owen Lie
 */
public class CloseFrame extends MenuButton implements ActionListener{

	private Timer exitTimer = null;

	/**
	 * 向面板绘制菜单图标
	 */
	@Override
	public void paint(Graphics g) 
	{
		super.paint(g);
		g.setColor(Color.white);
		g.drawLine(padding,
				padding,
				width - padding,
				width - padding);
		g.drawLine(width - padding,
				padding,
				padding,
				width - padding);
	}

	/**
	 * 构造函数，向按钮对象添加点击事件
	 */
	public CloseFrame(double bttnNum)
	{
		super(bttnNum);

		if(button == null)
			button = new MenuButton(0);

		button.addMouseListener(new MouseAdapter()
		{
			//鼠标按下事件
			public void mouseClicked(MouseEvent e)
			{
				Rolnews.getMainFrame().actionPerformed(null);
				Message exitProgram = new Message("感谢您的使用, 再见!", 3);
				exitProgram.start();

				//将新闻数据存入缓存文件，以备下次使用
				newsDataBackup();

				exitTimer = new Timer(1500, (CloseFrame)Rolnews.getMainFrame().getRightPanel().getComponent(3));
				exitTimer.start();
			}
		});
	}

	/**
	 * 窗体淡出，退出系统
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		//重置延时
		exitTimer.setDelay(100);

		//获取当前窗体的透明度
		MainFrame mainFrame = Rolnews.getMainFrame();
		float opacity = mainFrame.getOpacity();

		//新建透明度
		opacity = (float)(opacity - 0.2);
		System.out.println("新建的透明度" + opacity);
		mainFrame.setOpacity(opacity);

		if(opacity <= 0.1)
		{
			exitTimer.stop();
			System.exit(0);
		}
	}

	/**
	 * 将新闻数据复制到缓存文件，以备下次无网络时使用
	 */
	private void newsDataBackup()
	{
		//获取备份文件对象
		File backupFile = new File("backup.txt");
		if(!backupFile.exists())
			try 
			{
				backupFile.createNewFile();
			} 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}

		//获取待备份文件
		File newsFile = new File("news.txt");
		
		//如果新文件为空，则不做备份
		if(newsFile.length() == 0)
			return;

		FileInputStream fis = null;  //文件读入流对象，获取news.txt文件中的内容
		FileOutputStream fos = null;  //文件写入流对象，向backup.txt文件中写入数据
		FileChannel inChannel = null;  //通道对象
		FileChannel outChannel = null;  //通道对象

		try 
		{
			//将流对象绑定到各自的文件
			fis = new FileInputStream(newsFile);
			fos = new FileOutputStream(backupFile);

			//获取通道
			inChannel = fis.getChannel();
			outChannel = fos.getChannel();

			//复制数据
			inChannel.transferTo(0, inChannel.size(), outChannel);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{

			try 
			{
				if(fis != null)
					fis.close();
				if(fos != null)
					fos.close();
				if(inChannel != null)
					inChannel.close();
				if(outChannel != null)
					outChannel.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
		File file = new File("setting.txt");
		SettingOperation setting = new SettingOperation("setting.txt");
		if(file.exists())
		{
			setting.setParam("<backupNewsCount>", Rolnews.parameters.getNewsCount() + "");
		}
		else
		{
			try 
			{
				//新建web网址信息文件
				file.createNewFile();
				System.out.println("setting文件不存在，已新建!");
				
				//写入默认参数
				boolean writed = setting.writeDefaultSetting();
				if(writed)
				{
					setting.setParam("<backupNewsCount>", Rolnews.parameters.getNewsCount() + "");
					System.out.println("向setting文件写入设置参数成功!");
				}
				else
					System.out.println("向setting文件写入默认设置参数失败!");
			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
	}
}
