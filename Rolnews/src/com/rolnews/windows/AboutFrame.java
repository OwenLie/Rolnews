package com.rolnews.windows;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.rolnews.main.Rolnews;

public class AboutFrame extends JFrame
{
	/**
	 * 显示关于面板的信息
	 */
	public void showAbout()
	{
		//面板窗体
		JFrame about = new JFrame("About Rolnews");
		about.setBounds(500, 100, 400, 600);
		about.setLayout(null);
		about.setResizable(false);
		about.setVisible(true);
		
		//logo信息
		ImageIcon icon = new ImageIcon(RNPopupMenu.class.getResource("news.png"));
		icon.setImage(icon.getImage().getScaledInstance(160, 60, Image.SCALE_DEFAULT));
		JLabel logo = new JLabel(icon);
		logo.setBounds(10, 20, 160, 60);
		
		//获取关于信息
		StringBuffer buffer = new StringBuffer("<html>");
		BufferedReader stdIn;
		String line;
		
		try 
		{
			stdIn = new BufferedReader(new InputStreamReader
					(new FileInputStream(new File("About.txt"))));
			line = stdIn.readLine();
			while(line != null && line.length() != 0)
		    {
		    	buffer.append(line);
		    	line = stdIn.readLine();
		    }
			buffer.append("</html>");
		} 
		catch (FileNotFoundException e1) 
		{
			e1.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	    
		//Rolnews信息
		JLabel aboutTxt = new JLabel();
		aboutTxt.setText(buffer.toString());
		aboutTxt.setFont(new java.awt.Font("Dialog",0,12));
		aboutTxt.setBounds(20, 70, 350, 440);
		
		int baseY = 500;
		int height = 20;
		//作者信息
		JLabel author = new JLabel();
		author.setText("作者： 李靖 (Owen Lie)");
		author.setBounds(20, baseY, 200, height);
		
		//版本信息
		JLabel version = new JLabel();
		version.setText("版本：1.1.0");
		version.setBounds(20, baseY + height, 200, height);
		
		JLabel attachInfo = new JLabel();
		attachInfo.setText("四川大学软件学院2012级毕业设计项目");
		attachInfo.setBounds(20, baseY + height + height, 300, height);
		
		//copyright
		JLabel copyright = new JLabel();
		copyright.setText("copyright@Owen Lie all rights reserved");
		copyright.setFont(new java.awt.Font("Dialog",0,10));
		copyright.setBounds(200, 3, 200, height);
		
		about.add(logo);
		about.add(aboutTxt);
		about.add(author);
		about.add(version);
		about.add(attachInfo);
		about.add(copyright);
	}
}
