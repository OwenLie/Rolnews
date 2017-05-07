package com.rolnews.loadnews;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import com.rolnews.main.Rolnews;

/**
 * 新闻加载类
 * <p>将本地文件中的缓存的新闻数据加载到缓冲区中
 * 
 * @author Owen Lie
 */
public class LoadNews implements Runnable{

	//记录当前已经加载了多少条新闻
	public static int newsPointer = 0;
	private static int readUnit = 5;  //一次读取新闻的数量
	
	//缓存新闻数据的二维数组
	public static String[][] newsBuffer = new String[readUnit][2];
	
	//获取新闻文件的对象
	private File newsFile = new File(Rolnews.parameters.getNewsFile());
	private int lineNum = Rolnews.parameters.getNewsCount();  //新闻数据行数
	
	@Override
	public void run() 
	{
		System.out.println("进入加载新闻线程");
		
		/**
		 * 从文件读取新闻数据
		 * 
		 * HEADINGLEFT: 记录当前滚动面板向左滚动
		 * FORWARD: 记录当前滚动面板的速度
		 * 
		 * 当面板向左滚动，并且速度大于零时，或者面板向右滚动，速度小于零时，文件应该从头向文件尾读取数据
		 * 当面板向左滚动，并且速度小于零时，或者面板向右滚动，速度大于零时，文件应该从尾向头读取数据
		 */
		if((Rolnews.parameters.isHeadingLeft() && (Rolnews.parameters.getSpeed() > 0))
				|| (!Rolnews.parameters.isHeadingLeft() && (Rolnews.parameters.getSpeed() < 0)))
			loadNews(true);
		else
			loadNews(false);
		
		//生成JLabel组件
		JLabelFactory factory = new JLabelFactory();
		factory.generateJLabel();
	}
	
	/**
	 * 从本地文件加载新闻到缓冲区
	 * 
	 * @param headToTail
	 * 		标志从本地文件中读取新闻数据的顺序，值为true表示从文件头读向文件尾，否则从尾向头读
	 */
	private void loadNews(boolean headToTail)
	{
		BufferedReader buf = null;
		
		try 
		{
			buf = new BufferedReader(new InputStreamReader(new FileInputStream(newsFile)));
			int read = 0;  //控制循环的变量
			boolean keepReading = true;
			
//[start] 从文件头部到尾部开始读取数据
			
			if(headToTail)
			{
				int i = 0;  //缓冲区数组下标
				int scope = newsPointer + readUnit;
				//读取readUnit条数据之后是否已经到达文件末尾
				if(scope <= lineNum)
				{
					while(keepReading)
					{
						//读到新闻指针之后开始向缓冲区写入数据
						if(read >= newsPointer)
						{
							/**
							 * 此处必须使用while循环语句来读取数据，当读取数据不为 "" 时，跳过循环读取下一行，
							 * 但是如果为 "" ，则会导致其后的数据读取出错，读取数据位 "" 是由于网页数据自己末尾带 "" 导致的
							 */
							while((newsBuffer[i][0] = buf.readLine()).equals(""));
							while((newsBuffer[i][1] = buf.readLine()).equals(""));
							//第三行空行为抓取新闻人工写入，不存在 "" ;
							buf.readLine();
									
							//打印读取的数据，在控制台查看
//							System.out.println(newsBuffer[i][0]);
//							System.out.println(newsBuffer[i][1]);
							
							//缓冲区数组下标增长
							i++;
						}
						
						//没有读到新闻指针处
						else
						{
							while((newsBuffer[i][0] = buf.readLine()).equals(""));
							while((newsBuffer[i][1] = buf.readLine()).equals(""));
							buf.readLine();
						}

						//增加读取新闻的数目
						read++;
						
						if(read == scope)
						{
							keepReading = false;
							newsPointer = scope;
						}
					}
				}
				
				//读取之后已经超过新闻总数，重置新闻指针到开头，重新读取
				else
				{
					newsPointer = 0;
					loadNews(headToTail);
				}
			}
//[end]
			
//[start] 如果从文件末尾开始向文件头部读取数据
			
			else
			{
				int i = readUnit - 1;  //缓冲区数组下标
				int scope = newsPointer - readUnit;
				//读取readUnit条新闻数据之后没有到达文件头部
				if(scope >= 0)
				{
					while(keepReading)
					{
						if(read >= scope)
						{
							while((newsBuffer[i][0] = buf.readLine()).equals(""));
							while((newsBuffer[i][1] = buf.readLine()).equals(""));
							buf.readLine();
							
							//打印读取的数据，在控制台查看
//							System.out.println(newsBuffer[i][0]);
//							System.out.println(newsBuffer[i][1]);
							
							//缓冲区数组下标增长
							i--;
						}
						
						//没有读到新闻指针处
						else
						{
							while((newsBuffer[i][0] = buf.readLine()).equals(""));
							while((newsBuffer[i][1] = buf.readLine()).equals(""));
							buf.readLine();
						}
						
						//增加读取新闻的数目
						read++;
						
						if(read == newsPointer)
						{
							keepReading = false;
							newsPointer = scope;
						}
					}
				}
				
				//读取之后已经回到新闻开头，重置新闻指针到文件尾，重新读取
				else
				{
					newsPointer = lineNum;
					loadNews(headToTail);
				}
			}
//[end]
			
//			System.out.println("加载之后新闻指针位置为: " + newsPointer);
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
	
	//测试读取新闻的功能
//	public static void main(String args[])
//	{
//		Arguments.LINENUM = 756;
//		new LoadNews().loadNews(false);
//	}
}