package com.rolnews.loadnews;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.rolnews.database.DataProcessor;
import com.rolnews.main.Rolnews;
import com.rolnews.message.Message;
import com.rolnews.model.History;
import com.rolnews.values.LabelFont;
import com.rolnews.windows.MainFrame;
import com.rolnews.windows.RollPanel;

/**
 * ���Ź�����
 * <p>���ݻ��������ص���������jlabel����������ص���������Ϲ���
 * 
 * @author Owen Lie
 */
public class JLabelFactory {

	//��̬JLabel���飬������ɵ����ű�ǩ���Ա����ص���������Ϲ���
	private JLabel[] newsLabel = new JLabel[LoadNews.getReadUnit()];
	private static int location = 0;  //JLabel�ڹ�������ϵ�λ��(x����)
	private static int destBoard = 0;  //��־jlabelҪ���ص�A��B�ĸ����������
	
	/**
	 * ����JLable���������newsLabel��
	 */
	public void generateJLabel()
	{
		//һ�μ��ص�������Ŀ
		int size = LoadNews.getReadUnit();
		boolean useBar = Rolnews.parameters.isUseBar();  //�Ƿ�ʹ�����ŷָ���
		LabelFont labelFont = Rolnews.parameters.getLabelFont().getLabelFont();  //jlabel����������
		
		for(int i = 0; i < size; i++)
		{
			//ʵ����JLabel����
			newsLabel[i] = new JLabel(LoadNews.newsBuffer[i][0]);
			//�������ŵ���ַ�������õ�����Name��
			newsLabel[i].setName(LoadNews.newsBuffer[i][1]);
			//������������
			newsLabel[i].setFont(labelFont.getFont());
			
			//�ж��Ƿ�Ϊ�Ƽ�����
			if(LoadNews.newsBuffer[i][0].startsWith("�����Ƽ�"))
				newsLabel[i].setForeground(Color.red);
			else
				newsLabel[i].setForeground(labelFont.getFontColor());

			//��ȡlabel�����س���
			int labelWidth = newsLabel[i].getFontMetrics
					(newsLabel[i].getFont()).stringWidth(LoadNews.newsBuffer[i][0]);

			//����label��λ��
			newsLabel[i].setBounds(new Rectangle(location, labelFont.getLableY(), labelWidth, 20));
			//��������ƶ�����label��ʱ��Ϊ����
			newsLabel[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			
			//�������¼�
			addMouseEvent(newsLabel[i]);
			
			if(useBar)
				location = location + labelWidth + 70;
			else
				location = location + labelWidth + 30;
		}
		
		//�����ż��ص����������
		if(destBoard == 2)
			loadJLaebl(Rolnews.getMainFrame().getPanelB());
		else
			loadJLaebl(Rolnews.getMainFrame().getPanelA());
		
		//����Ƿ�����Ϣ��ʾ
		Message.messageOut();
	}
	
	/**
	 * ��JLabel������ص����������
	 */
	private void loadJLaebl(RollPanel rollPanel)
	{
//		System.out.println("��ǰ�������ŵĹ������Ϊ : " + rollPanel.getPanelTag());
		rollPanel.removeAll();
		
		int height = 30 - 2 * Rolnews.parameters.getPadding();
		
		int size = newsLabel.length;
		int loc = 0;
		boolean useBar = Rolnews.parameters.isUseBar(); 
		
		for(int i = 0; i < size; i++)
		{
//			System.out.println("label�ϵ�����: " + newsLabel[i].getText());
			
			rollPanel.add(newsLabel[i]);
			
			//���Ҫʹ��������ӷָ����������
			if(useBar)
			{
				JLabel bar = new JLabel("|");
				bar.setHorizontalAlignment(SwingConstants.CENTER);
				bar.setFont(new java.awt.Font("Dialog",0,15));
				bar.setForeground(Rolnews.parameters.getLabelFont().getLabelFont().getFontColor());
				bar.setBounds(new Rectangle(loc + newsLabel[i].getWidth() + 30, 0, 10, 20));
				
				//�ָ���λ��
				loc = loc + newsLabel[i].getWidth() + 70;
				rollPanel.add(bar);
			}
		}
		
		//����Ƽ�����
//		JLabel label = NewsRecommendation.getRecommNews(loc);
//		if(label != null)
//		{
//			rollPanel.add(label);
//			int width = label.getWidth();
//			
//			//���Ҫʹ��������ӷָ����������
//			if(useBar)
//			{
//				JLabel bar = new JLabel("|");
//				bar.setHorizontalAlignment(SwingConstants.CENTER);
//				bar.setFont(new java.awt.Font("Dialog",0,15));
//				bar.setForeground(Rolnews.parameters.getLabelFont().getLabelFont().getFontColor());
//				bar.setBounds(new Rectangle(loc + width + 30, 0, 10, 20));
//				
//				//�ָ���λ��
//				location = loc + width + 70;
//				rollPanel.add(bar);
//			}
//		}
		
		//��������С��λ��
		if(Rolnews.parameters.getSpeed() > 0)
		{
			rollPanel.setSize(location, height);
			rollPanel.setLocation(Rolnews.parameters.getRightBorder(), 0);
		}
		else
		{
			rollPanel.setSize(location, height);
			rollPanel.setLocation(Rolnews.parameters.getLeftBorder() - rollPanel.getWidth(), 0);
		}
		
		//����λ�ñ���
		location = 0;
	}

	/**
	 * ��JLabel�������¼�
	 */
	private void addMouseEvent(final JLabel label)
	{
		label.addMouseListener(new MouseAdapter()
		{
			//�������¼�
			public void mouseEntered(MouseEvent e)
			{
				label.setForeground(Color.RED);
				
				//�����ǰ���������ק�����û�ȡ������������ֹ����
				if(MainFrame.isExtending || MainFrame.isDragged)
					return;
				
				//ֹͣ������
				Rolnews.stopRolling();
				
				//���ժҪ��Ϣ
				String desc = getDescription(label.getName());
				String tip = getTip(label.getName());
				if(desc != null)
				{
//					label.grabFocus();  //ǿ�ƻ�ý��㣬�Ա���ʾ����ժҪ
					label.requestFocus();
					label.setToolTipText("<html><p style='margin:3px'>" + tip + "</p><p style='width:300px; margin:3px; margin-bottom:5px'>"
					+ desc + "</p></html>");
				}
				System.out.println(label.getToolTipText());
			}
			
			//����뿪�¼�
			public void mouseExited(MouseEvent e)
			{
				label.setForeground(Rolnews.parameters.getLabelFont().getLabelFont().getFontColor());
				
				if(!MainFrame.lockRoll)
				{
					//����������
					Rolnews.startRolling();
				}
			}
			
			//������¼�
			public void mouseClicked(MouseEvent e)
			{
				//��ȡ��ǰlabel�����ƣ���ַ���ӣ�
				String href = label.getName();
				try 
				{
					Runtime.getRuntime().exec("cmd /c start " + href);
					
					//�������¼��ӵ����ݿ�
					DataProcessor processor = new DataProcessor("Histories.xml", "Histories");
					//�½���ʷ��¼����
					String title = label.getText();
					String desc = label.getToolTipText();
					History history = new History(title, href, desc);
					processor.AddHistoryRecord(history);
				} 
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		});
	}

	/**
	 * ��ȡ���ŵ�������Ϣ����Ե������ţ��Ӹ�����ַ��ȡ����
	 * 
	 * @param url
	 * @return
	 */
	private String getDescription(String url)
	{
		String result = null;
		try 
		{
			Document doc = Jsoup.parse(new URL(url), 300);
			String description = doc.select("meta[name=description]").get(0).attr("content");
			result = description;
		} 
		catch (Exception e) 
		{
		}
		
		return result;
	}
	
	/**
	 * ��ȡ������Ϣ
	 * @return
	 */
	private String getTip(String url)
	{
		String logoLoc = "�������� -- �����ȵ������Ϯ";
    	
    	if(url.contains("baidu"))
    		logoLoc = "�ٶ�����  -- ȫ��������������ƽ̨";
    	else if(url.contains("cnr"))
    		logoLoc = "��������㲥��̨";
    	else if(url.contains("sohu"))
    		logoLoc = "�Ѻ�����  -- �Ѻ�";
    	else if(url.contains("163.com"))
    		logoLoc = "��������  -- ��̬�ȵ������Ż�";
    	else if(url.contains("sina"))
    		logoLoc = "������������";
    	else if(url.contains("ifeng"))
    		logoLoc = "�����  -- �й����ȵ��ۺ��Ż���վ";
    	else if(url.contains("huanqiu"))
    		logoLoc = "��������  -- ȫ���������Ż�";
    	
    	return logoLoc;
	}
	
	public JLabel[] getNewsLabel() { return newsLabel; }
	public void setNewsLabel(JLabel[] newsLabel) { this.newsLabel = newsLabel; }

	public static int getDestBoard() { return destBoard; }
	public static void setDestBoard(int destBoard) { JLabelFactory.destBoard = destBoard; }
}
