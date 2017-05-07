package com.rolnews.windows;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.rolnews.main.Rolnews;

/**
 * ��ʾlogo�����
 * @author profe
 */
public class AdPanel extends JPanel{

	private static AdPanel adPanel;  //������
	private JLabel logoA;  //��ʾlogo�����
	private JLabel logoB;  //��ʾ��Ϣ�����
	
	private AdPanel()
	{
		ImageIcon icon1 = new ImageIcon(Rolnews.parameters.getFileLocation() + "//icon//news.png");
		icon1.setImage(icon1.getImage().getScaledInstance(80, 20, Image.SCALE_DEFAULT));
		logoA = new JLabel(icon1);
		logoA.setBounds(0, -21, 80, 20);
		
		ImageIcon icon2 = new ImageIcon(Rolnews.parameters.getFileLocation() + "//icon//news.png");
		icon2.setImage(icon2.getImage().getScaledInstance(80, 20, Image.SCALE_DEFAULT));
		logoB = new JLabel(icon2);
		logoB.setBounds(0, -21, 80, 20);
		
		this.setLayout(null);
		this.setBackground(Rolnews.parameters.getBgColor().getValue());  //���ô��屳��ɫ
		this.setBounds(5, -21, 80, 20);
		this.add(logoA);
		this.add(logoB);
		
		adPanel = this;
	}
	
	//��ȡ������
	public static AdPanel getInstance()
	{
		if(adPanel == null)
			adPanel = new AdPanel();
		
		return adPanel;
	}

	public JLabel getLogoA() { return logoA; }
	public void setLogoA(JLabel logo) { this.logoA = logo; }

	public JLabel getLogoB() { return logoB; }
	public void setLogoB(JLabel logo) { this.logoB = logo; }
}
