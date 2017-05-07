package com.rolnews.setting;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import com.rolnews.main.Rolnews;
/**
 * ���������ַ������������
 * 
 * @author Owen Lie
 */
public class SetURLs implements ActionListener{

	private String txtPath = "webs.txt";
			
	@Override
	public void actionPerformed(ActionEvent e)
	{
		//��ȡ�������ͨ��","�ָ���֪������ļ�����������
		String[] commands = e.getActionCommand().split(",");
		switch(commands[0])
		{
//[start] ������֪��ַ����������
			case "knownSite":
				
				//������6����ַ
				int size = Rolnews.parameters.getURLs().size();
				if(size < 7)
				{
					String siteValue = "";  //��ֵַ
					String title = "";  //��ַ������
					
					switch(commands[1])
					{
						//����ٶ���ַ������
						case "souhu":
							siteValue = "http://news.sohu.com/";
							title = "���ŵ�һ�Ż� -- �Ѻ�����";
							break;
						case "wangyi":
							siteValue = "http://www.163.com/";
							title = "�������� -- ��̬�ȵ������Ż�";
							break;
						case "sina":
							siteValue = "http://www.sina.com.cn/";
							title = "������������";
							break;
						case "ifeng":
							siteValue = "http://www.ifeng.com/";
							title = "�����Ѷ -- �����";
							break;
						case "huanqiu":
							siteValue = "http://www.huanqiu.com/";
							title = "������ -- ȫ���������Ż�";
							break;
						default:
							siteValue = "http://news.baidu.com/";
							title = "�ٶ����� -- ȫ�������������ƽ̨";
							break;
					}
					
					//ִ������д���������ļ�����
					if(((JCheckBox)e.getSource()).isSelected())
					{
						addSite(title, siteValue);
						
						//ͬ��������ʾ������ַ����壬���ȱ�ݣ���ʱ��ͬ��
//						Rolnews.getWebsitePanel().addSiteToPanel(title, Rolnews.parameters.getURLs().size(), this);
					}
					else
						if(size > 1)
						{
							deleteSite(title);
						}
						//���һ����ַ����������Ϊ��ѡ��
						else
							((JCheckBox)e.getSource()).setSelected(true);
				}
				break;
//[end]
				
//[start] ��������
				
			case "category":
				
				String category = "";
				switch(commands[1])
				{
					//����ٶ���ַ������
					case "yule":
						category = "yule";
						break;
					case "junshi":
						category = "junshi";
						break;
					case "caijing":
						category = "caijing";
						break;
					case "tiyu":
						category = "tiyu";
						break;
					case "shehui":
						category = "shehui";
						break;
					case "keji":
						category = "keji";
						break;
					case "youxi":
						category = "youxi";
						break;
					case "lishi":
						category = "lishi";
						break;
					default:
						category = "no";
						break;
				}

				//�������ļ�д�����
				File file = new File(txtPath);
				SettingOperation setting = new SettingOperation(txtPath);
				if(file.exists())
					setting.updateParam("<category>", category);
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
							setting.updateParam("<category>", category);
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
				break;
//[end]
				
//[start] �Զ�����ַ

			case "selfDefine":
				
				String title = ((JTextField)(((JButton)e.getSource()).getParent().getComponent(0))).getText();
				String url = ((JTextField)(((JButton)e.getSource()).getParent().getComponent(1))).getText();
				System.out.println("��ȡҳ�洫�����ַtitleΪ�� " + title + "\n�����url: " + url);
				
				//�������������
				if(title.trim().equals("") || title.equals("  ������վ����"))
					title = "�Զ�����ַ";
				if(!url.startsWith("http"))
					return;
				
				//��֤��������ַ�ܷ���ָ������
				addSite(title, url);
				
				//ͬ��������ʾ������ַ����壬���ȱ�ݣ���ʱ��ͬ��
//				Rolnews.getWebsitePanel().addSiteToPanel(title, Rolnews.parameters.getURLs().size(), this);
				
				break;
//[end]
				
//[start] ɾ����ַ
				
			default:
				break;
		}
		
		//���¼�����ַ
		new LoadURL().loadURL();
	}
	
	/**
	 * ����ַ�ļ�д����ַ
	 */
	private void addSite(String title, String site)
	{
		//�������ļ�д�����
		File file = new File(txtPath);
		SettingOperation setting = new SettingOperation(txtPath);
		if(file.exists())
		{
			//����ַ������ӵ������ļ��е�һ������ӵ�λ��
			for(int i = 0; i < 6; i++)
			{
				String url = setting.getParaByKey("<web" + (i + 1) + ">");
				if(url.equals(""))
				{
					//д��title
					setting.updateParam("<web" + (i + 1) + "title>", title);
					//д����ַ
					setting.updateParam("<web" + (i + 1) + ">", site);
					break;
				}
			}
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
					addSite(title, site);
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
	
	/**
	 * ɾ��ָ����ַ���ݣ�ͨ��title���
	 */
	public void deleteSite(String title)
	{
		//�������ļ�д�����
		File file = new File(txtPath);
		SettingOperation setting = new SettingOperation(txtPath);
		if(file.exists())
		{
			//����ַ������ӵ������ļ��е�һ������ӵ�λ��
			for(int i = 0; i < 6; i++)
			{
				String titleValue = setting.getParaByKey("<web" + (i + 1) + "title>");
				if(titleValue.equals(title))
				{
					//д��title
					setting.updateParam("<web" + (i + 1) + "title>", "");
					//д����ַ
					setting.updateParam("<web" + (i + 1) + ">", "");
					break;
				}
			}
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
					deleteSite(title);
					System.out.println("��setting�ļ�ɾ�����ò����ɹ�!");
				}
				else
					System.out.println("��setting�ļ�ɾ��Ĭ�����ò���ʧ��!");
			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
	}
}
