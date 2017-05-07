package com.rolnews.loadnews;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import com.rolnews.main.Rolnews;

/**
 * ���ż�����
 * <p>�������ļ��еĻ�����������ݼ��ص���������
 * 
 * @author Owen Lie
 */
public class LoadNews implements Runnable{

	//��¼��ǰ�Ѿ������˶���������
	public static int newsPointer = 0;
	private static int readUnit = 5;  //һ�ζ�ȡ���ŵ�����
	
	//�����������ݵĶ�ά����
	public static String[][] newsBuffer = new String[readUnit][2];
	
	//��ȡ�����ļ��Ķ���
	private File newsFile = new File(Rolnews.parameters.getNewsFile());
	private int lineNum = Rolnews.parameters.getNewsCount();  //������������
	
	@Override
	public void run() 
	{
		System.out.println("������������߳�");
		
		/**
		 * ���ļ���ȡ��������
		 * 
		 * HEADINGLEFT: ��¼��ǰ��������������
		 * FORWARD: ��¼��ǰ���������ٶ�
		 * 
		 * �������������������ٶȴ�����ʱ������������ҹ������ٶ�С����ʱ���ļ�Ӧ�ô�ͷ���ļ�β��ȡ����
		 * �������������������ٶ�С����ʱ������������ҹ������ٶȴ�����ʱ���ļ�Ӧ�ô�β��ͷ��ȡ����
		 */
		if((Rolnews.parameters.isHeadingLeft() && (Rolnews.parameters.getSpeed() > 0))
				|| (!Rolnews.parameters.isHeadingLeft() && (Rolnews.parameters.getSpeed() < 0)))
			loadNews(true);
		else
			loadNews(false);
		
		//����JLabel���
		JLabelFactory factory = new JLabelFactory();
		factory.generateJLabel();
	}
	
	/**
	 * �ӱ����ļ��������ŵ�������
	 * 
	 * @param headToTail
	 * 		��־�ӱ����ļ��ж�ȡ�������ݵ�˳��ֵΪtrue��ʾ���ļ�ͷ�����ļ�β�������β��ͷ��
	 */
	private void loadNews(boolean headToTail)
	{
		BufferedReader buf = null;
		
		try 
		{
			buf = new BufferedReader(new InputStreamReader(new FileInputStream(newsFile)));
			int read = 0;  //����ѭ���ı���
			boolean keepReading = true;
			
//[start] ���ļ�ͷ����β����ʼ��ȡ����
			
			if(headToTail)
			{
				int i = 0;  //�����������±�
				int scope = newsPointer + readUnit;
				//��ȡreadUnit������֮���Ƿ��Ѿ������ļ�ĩβ
				if(scope <= lineNum)
				{
					while(keepReading)
					{
						//��������ָ��֮��ʼ�򻺳���д������
						if(read >= newsPointer)
						{
							/**
							 * �˴�����ʹ��whileѭ���������ȡ���ݣ�����ȡ���ݲ�Ϊ "" ʱ������ѭ����ȡ��һ�У�
							 * �������Ϊ "" ����ᵼ���������ݶ�ȡ������ȡ����λ "" ��������ҳ�����Լ�ĩβ�� "" ���µ�
							 */
							while((newsBuffer[i][0] = buf.readLine()).equals(""));
							while((newsBuffer[i][1] = buf.readLine()).equals(""));
							//�����п���Ϊץȡ�����˹�д�룬������ "" ;
							buf.readLine();
									
							//��ӡ��ȡ�����ݣ��ڿ���̨�鿴
//							System.out.println(newsBuffer[i][0]);
//							System.out.println(newsBuffer[i][1]);
							
							//�����������±�����
							i++;
						}
						
						//û�ж�������ָ�봦
						else
						{
							while((newsBuffer[i][0] = buf.readLine()).equals(""));
							while((newsBuffer[i][1] = buf.readLine()).equals(""));
							buf.readLine();
						}

						//���Ӷ�ȡ���ŵ���Ŀ
						read++;
						
						if(read == scope)
						{
							keepReading = false;
							newsPointer = scope;
						}
					}
				}
				
				//��ȡ֮���Ѿ�����������������������ָ�뵽��ͷ�����¶�ȡ
				else
				{
					newsPointer = 0;
					loadNews(headToTail);
				}
			}
//[end]
			
//[start] ������ļ�ĩβ��ʼ���ļ�ͷ����ȡ����
			
			else
			{
				int i = readUnit - 1;  //�����������±�
				int scope = newsPointer - readUnit;
				//��ȡreadUnit����������֮��û�е����ļ�ͷ��
				if(scope >= 0)
				{
					while(keepReading)
					{
						if(read >= scope)
						{
							while((newsBuffer[i][0] = buf.readLine()).equals(""));
							while((newsBuffer[i][1] = buf.readLine()).equals(""));
							buf.readLine();
							
							//��ӡ��ȡ�����ݣ��ڿ���̨�鿴
//							System.out.println(newsBuffer[i][0]);
//							System.out.println(newsBuffer[i][1]);
							
							//�����������±�����
							i--;
						}
						
						//û�ж�������ָ�봦
						else
						{
							while((newsBuffer[i][0] = buf.readLine()).equals(""));
							while((newsBuffer[i][1] = buf.readLine()).equals(""));
							buf.readLine();
						}
						
						//���Ӷ�ȡ���ŵ���Ŀ
						read++;
						
						if(read == newsPointer)
						{
							keepReading = false;
							newsPointer = scope;
						}
					}
				}
				
				//��ȡ֮���Ѿ��ص����ſ�ͷ����������ָ�뵽�ļ�β�����¶�ȡ
				else
				{
					newsPointer = lineNum;
					loadNews(headToTail);
				}
			}
//[end]
			
//			System.out.println("����֮������ָ��λ��Ϊ: " + newsPointer);
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
			if(buf != null)
				try 
				{
					buf.close();
				} 
				catch (IOException e)
				{
					e.printStackTrace();
				}
		}
	}
	
	public static int getReadUnit() { return readUnit; }
	public static void setReadUnit(int readUnit) { LoadNews.readUnit = readUnit; }

	public File getNewsFile() { return newsFile; }
	public void setNewsFile(File newsFile) { this.newsFile = newsFile; }
	
	//���Զ�ȡ���ŵĹ���
//	public static void main(String args[])
//	{
//		Arguments.LINENUM = 756;
//		new LoadNews().loadNews(false);
//	}
}