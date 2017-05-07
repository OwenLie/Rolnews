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
 * �رճ���ť
 * 
 * @author Owen Lie
 */
public class CloseFrame extends MenuButton implements ActionListener{

	private Timer exitTimer = null;

	/**
	 * �������Ʋ˵�ͼ��
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
	 * ���캯������ť������ӵ���¼�
	 */
	public CloseFrame(double bttnNum)
	{
		super(bttnNum);

		if(button == null)
			button = new MenuButton(0);

		button.addMouseListener(new MouseAdapter()
		{
			//��갴���¼�
			public void mouseClicked(MouseEvent e)
			{
				Rolnews.getMainFrame().actionPerformed(null);
				Message exitProgram = new Message("��л����ʹ��, �ټ�!", 3);
				exitProgram.start();

				//���������ݴ��뻺���ļ����Ա��´�ʹ��
				newsDataBackup();

				exitTimer = new Timer(1500, (CloseFrame)Rolnews.getMainFrame().getRightPanel().getComponent(3));
				exitTimer.start();
			}
		});
	}

	/**
	 * ���嵭�����˳�ϵͳ
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		//������ʱ
		exitTimer.setDelay(100);

		//��ȡ��ǰ�����͸����
		MainFrame mainFrame = Rolnews.getMainFrame();
		float opacity = mainFrame.getOpacity();

		//�½�͸����
		opacity = (float)(opacity - 0.2);
		System.out.println("�½���͸����" + opacity);
		mainFrame.setOpacity(opacity);

		if(opacity <= 0.1)
		{
			exitTimer.stop();
			System.exit(0);
		}
	}

	/**
	 * ���������ݸ��Ƶ������ļ����Ա��´�������ʱʹ��
	 */
	private void newsDataBackup()
	{
		//��ȡ�����ļ�����
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

		//��ȡ�������ļ�
		File newsFile = new File("news.txt");
		
		//������ļ�Ϊ�գ���������
		if(newsFile.length() == 0)
			return;

		FileInputStream fis = null;  //�ļ����������󣬻�ȡnews.txt�ļ��е�����
		FileOutputStream fos = null;  //�ļ�д����������backup.txt�ļ���д������
		FileChannel inChannel = null;  //ͨ������
		FileChannel outChannel = null;  //ͨ������

		try 
		{
			//��������󶨵����Ե��ļ�
			fis = new FileInputStream(newsFile);
			fos = new FileOutputStream(backupFile);

			//��ȡͨ��
			inChannel = fis.getChannel();
			outChannel = fos.getChannel();

			//��������
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
				//�½�web��ַ��Ϣ�ļ�
				file.createNewFile();
				System.out.println("setting�ļ������ڣ����½�!");
				
				//д��Ĭ�ϲ���
				boolean writed = setting.writeDefaultSetting();
				if(writed)
				{
					setting.setParam("<backupNewsCount>", Rolnews.parameters.getNewsCount() + "");
					System.out.println("��setting�ļ�д�����ò����ɹ�!");
				}
				else
					System.out.println("��setting�ļ�д��Ĭ�����ò���ʧ��!");
			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
	}
}
