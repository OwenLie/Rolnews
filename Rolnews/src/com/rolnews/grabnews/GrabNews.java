package com.rolnews.grabnews;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.rolnews.database.DataProcessor;
import com.rolnews.loadnews.LoadNews;
import com.rolnews.main.Rolnews;
import com.rolnews.message.Message;
import com.rolnews.model.HotWord;
import com.rolnews.setting.URLFilter;

/**
 * 抓取新闻类
 * <p>从网址参数中获取要加载的新闻的网址，使用Jsoup包的相关功能获取该网址的新闻
 * 
 * @author Owen Lie
 */
public class GrabNews implements Runnable{

	private Document[] docs = null;  //存放根据网址获取的网页文档
	private FileWriter pw = null;  //向本地文件写入数据的writer对象
	private boolean[] loaded = null;  //标志网页文档i是否已经加载完成
	private int newsCount = 0;  //记录总共有多少条新闻数据

	@Override
	public void run()
	{
		boolean loaded = false;

		long startTime = System.currentTimeMillis();//获取当前时间

		//根据网址信息获取网页文档
		getDocuments();

		//过滤网页文档，获取新闻信息
		loaded = newsFilter();

		long endTime = System.currentTimeMillis();
		System.out.println("获取新闻时间："+(endTime-startTime)+"ms");

		//如果获取新闻完成，启动线程加载数据到缓冲区
		if(loaded)
		{
			Rolnews.parameters.setNewsFile("news.txt");
			loadNews();

			//新闻抓取结束，通知消息显示动画退出
			Message.messageOut();
		}
		else
		{
			Message.messageReset("您的网速不佳，请稍后再试！");
			//设置上一次的新闻进行滚动
			Rolnews.parameters.setNewsFile("backup.txt");
			loadNews();

			//新闻抓取结束，通知消息显示动画退出
			Message.messageOut();
		}
	}

	/**
	 * 获取网页资源文档，将获取数据存放在私有变量docs中
	 */
	private void getDocuments()
	{
		//获取url
		List<String[]> urls = Rolnews.parameters.getURLs();
		int size = urls.size();

		docs = new Document[size];
		loaded = new boolean[size];

		//获取是否限制新闻种类的设置信息
		boolean limited = true;
		String category = Rolnews.parameters.getNewsCategory();
		if(category.equals("no"))
			limited = false;

		URLFilter selectURL = new URLFilter();
		String url = "";  //要获取的网址

		//根据url获取网页文档
		for(int i = 0; i < size; i++)
		{
			//加载同一个文档最多循环执行三次，防止偶然超时，文档未加载完成
			for(int j = 0; j < 3; j++)
			{
				try 
				{
					//网址受种类限制，需更换网址
					url = urls.get(i)[0];
					if(limited)
						url = selectURL.getCategoryURL(url, category);

					if(url == null)
						url = urls.get(i)[0];

					docs[i] = Jsoup.parse(new URL(url), Rolnews.parameters.getLoadingTime());
					//标志该文档加载已完成
					loaded[i] = true;

					break;
				} 
				catch (IOException e) 
				{
					loaded[i] = false;
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 按顺序将从各URL获取的数据写入本地文件
	 */
	private boolean newsFilter()
	{
		boolean writed = false;
		int size = docs.length;

		try 
		{
			pw = new FileWriter(new File("news.txt"));
		} 
		catch (IOException e1)
		{
			e1.printStackTrace();
			return writed;
		}

		//开始写入数据
		for(int i = 0; i < size; i++)
		{
			//提取超链接标签并向本地文件写入数据
			if(loaded[i])
			{
				Elements elements = docs[i].select("a");
				for(Element element : elements)
				{
					writeData(element);
				}

				//只要有一个网址的数据被写入了本地文件，则标志已写入
				writed = true;
			}
		}

		//记录新闻总数目
		if(newsCount != 0)
			Rolnews.parameters.setNewsCount(newsCount);

		if(pw != null)
			try 
			{
				pw.close();
			} 
			catch (IOException e)
			{
			e.printStackTrace();
			}

		return writed;
	}

	/**
	 * 向文件写入数据
	 */
	private void writeData(Element element)
	{
		//过滤掉新闻标题长度小于10的标签
		String title = element.text();
		if(title.length() <= 10)
			return;

		//获取新闻链接
		String href = element.attr("href");
		if(!href.startsWith("http"))
			return;
		
		//判断是否为推荐新闻
		if(element.parent().nodeName().equals("strong") ||
		   element.parent().attr("class").equals("bold-item") ||
		   element.parent().attr("class").equals("hx") ||
		   element.parent().nodeName().equals("h1") ||
		   element.attr("class").equals("strong") ||
		   element.parent().nodeName().equals("h4"))
			title = "今日推荐：" + title;

		//写入新闻标题和链接地址
		try 
		{
			pw.write(title + "\r\n");
			pw.write(href + "\r\n");

			//写入新闻分隔行
			pw.write("\r\n");

			newsCount++;
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 完成新闻抓取之后完成各种后续工作
	 */
	private void loadNews()
	{
		//获取推荐新闻
//		NewsRecommendation.recommendations = new NewsRecommendation().getRecommNews("http://news.baidu.com");
		
		//新建线程加载新闻，生成jlabel对象
		LoadNews.newsPointer = 0;
		Thread loadNewsThread = new Thread(new LoadNews());
		loadNewsThread.setName("LoadNews");
		loadNewsThread.start();

		//让当前线程等待线程loadNews完成之后，打开面板A的滚动计时器
		try 
		{
			loadNewsThread.join();
			System.out.println("加载新闻的线程结束!");

			//获取热搜词汇
			getHotSearching();
			
			//重置滚动面板的位置
			int newLocation = 0;
			if(Rolnews.parameters.isHeadingLeft())
			{
				newLocation = Rolnews.parameters.getRightBorder();
				Rolnews.getMainFrame().getPanelA().setLocation(newLocation, 0);
				Rolnews.getMainFrame().getPanelB().setLocation(newLocation, 0);
			}
			else
			{
				Rolnews.getMainFrame().getPanelA().setLocation
				(Rolnews.parameters.getLeftBorder() - Rolnews.getMainFrame().getPanelA().getWidth(), 0);
				Rolnews.getMainFrame().getPanelB().setLocation
				(Rolnews.parameters.getLeftBorder() - Rolnews.getMainFrame().getPanelB().getWidth(), 0);
			}

			//开始滚动
			Rolnews.startRolling();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * 获取热搜词和推荐信新闻，来自百度新闻首页热搜栏目和环球视野栏目
	 */
	private void getHotSearching()
	{
		Document docu = null;
		try 
		{
			//获取百度新闻首页文档
			docu = Jsoup.parse(new URL("http://news.baidu.com/"), Rolnews.parameters.getLoadingTime());

			//新建数据库对象
			DataProcessor processor = new DataProcessor("HotWords.xml", "HotWords");
			List<HotWord> hotWords = new ArrayList<HotWord>();

			//			DatabaseConnection connection = new DatabaseConnection();

			//提取超链接标签
			Elements elements = docu.select("a");
			for(Element element : elements)
			{
				String title = element.text();
				int len = title.length();
				if(len > 10 || len == 0)
					continue;

				//获取超链接
				String href = element.attr("href");
				if(!href.contains("hotquery"))
					continue;

				//将获取的热点存入列表
				HotWord hotWord = new HotWord(title, href);
				hotWords.add(hotWord);

				//				connection.InsertHotWords(title, href);
			}

			//所有列表中的数据存入数据库
			processor.AddHotWord(hotWords);
			//清理两天前的数据
			String[] databases = new String[]{"HotWords", "Histories"};
			String[] roots = new String[]{"HotWord", "History"};
			processor.DeleteData(databases, roots);

			//			connection.CloseConnection();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
