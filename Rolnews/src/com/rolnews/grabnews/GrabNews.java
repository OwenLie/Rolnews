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
 * ץȡ������
 * <p>����ַ�����л�ȡҪ���ص����ŵ���ַ��ʹ��Jsoup������ع��ܻ�ȡ����ַ������
 * 
 * @author Owen Lie
 */
public class GrabNews implements Runnable{

	private Document[] docs = null;  //��Ÿ�����ַ��ȡ����ҳ�ĵ�
	private FileWriter pw = null;  //�򱾵��ļ�д�����ݵ�writer����
	private boolean[] loaded = null;  //��־��ҳ�ĵ�i�Ƿ��Ѿ��������
	private int newsCount = 0;  //��¼�ܹ��ж�������������

	@Override
	public void run()
	{
		boolean loaded = false;

		long startTime = System.currentTimeMillis();//��ȡ��ǰʱ��

		//������ַ��Ϣ��ȡ��ҳ�ĵ�
		getDocuments();

		//������ҳ�ĵ�����ȡ������Ϣ
		loaded = newsFilter();

		long endTime = System.currentTimeMillis();
		System.out.println("��ȡ����ʱ�䣺"+(endTime-startTime)+"ms");

		//�����ȡ������ɣ������̼߳������ݵ�������
		if(loaded)
		{
			Rolnews.parameters.setNewsFile("news.txt");
			loadNews();

			//����ץȡ������֪ͨ��Ϣ��ʾ�����˳�
			Message.messageOut();
		}
		else
		{
			Message.messageReset("�������ٲ��ѣ����Ժ����ԣ�");
			//������һ�ε����Ž��й���
			Rolnews.parameters.setNewsFile("backup.txt");
			loadNews();

			//����ץȡ������֪ͨ��Ϣ��ʾ�����˳�
			Message.messageOut();
		}
	}

	/**
	 * ��ȡ��ҳ��Դ�ĵ�������ȡ���ݴ����˽�б���docs��
	 */
	private void getDocuments()
	{
		//��ȡurl
		List<String[]> urls = Rolnews.parameters.getURLs();
		int size = urls.size();

		docs = new Document[size];
		loaded = new boolean[size];

		//��ȡ�Ƿ��������������������Ϣ
		boolean limited = true;
		String category = Rolnews.parameters.getNewsCategory();
		if(category.equals("no"))
			limited = false;

		URLFilter selectURL = new URLFilter();
		String url = "";  //Ҫ��ȡ����ַ

		//����url��ȡ��ҳ�ĵ�
		for(int i = 0; i < size; i++)
		{
			//����ͬһ���ĵ����ѭ��ִ�����Σ���ֹżȻ��ʱ���ĵ�δ�������
			for(int j = 0; j < 3; j++)
			{
				try 
				{
					//��ַ���������ƣ��������ַ
					url = urls.get(i)[0];
					if(limited)
						url = selectURL.getCategoryURL(url, category);

					if(url == null)
						url = urls.get(i)[0];

					docs[i] = Jsoup.parse(new URL(url), Rolnews.parameters.getLoadingTime());
					//��־���ĵ����������
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
	 * ��˳�򽫴Ӹ�URL��ȡ������д�뱾���ļ�
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

		//��ʼд������
		for(int i = 0; i < size; i++)
		{
			//��ȡ�����ӱ�ǩ���򱾵��ļ�д������
			if(loaded[i])
			{
				Elements elements = docs[i].select("a");
				for(Element element : elements)
				{
					writeData(element);
				}

				//ֻҪ��һ����ַ�����ݱ�д���˱����ļ������־��д��
				writed = true;
			}
		}

		//��¼��������Ŀ
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
	 * ���ļ�д������
	 */
	private void writeData(Element element)
	{
		//���˵����ű��ⳤ��С��10�ı�ǩ
		String title = element.text();
		if(title.length() <= 10)
			return;

		//��ȡ��������
		String href = element.attr("href");
		if(!href.startsWith("http"))
			return;
		
		//�ж��Ƿ�Ϊ�Ƽ�����
		if(element.parent().nodeName().equals("strong") ||
		   element.parent().attr("class").equals("bold-item") ||
		   element.parent().attr("class").equals("hx") ||
		   element.parent().nodeName().equals("h1") ||
		   element.attr("class").equals("strong") ||
		   element.parent().nodeName().equals("h4"))
			title = "�����Ƽ���" + title;

		//д�����ű�������ӵ�ַ
		try 
		{
			pw.write(title + "\r\n");
			pw.write(href + "\r\n");

			//д�����ŷָ���
			pw.write("\r\n");

			newsCount++;
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * �������ץȡ֮����ɸ��ֺ�������
	 */
	private void loadNews()
	{
		//��ȡ�Ƽ�����
//		NewsRecommendation.recommendations = new NewsRecommendation().getRecommNews("http://news.baidu.com");
		
		//�½��̼߳������ţ�����jlabel����
		LoadNews.newsPointer = 0;
		Thread loadNewsThread = new Thread(new LoadNews());
		loadNewsThread.setName("LoadNews");
		loadNewsThread.start();

		//�õ�ǰ�̵߳ȴ��߳�loadNews���֮�󣬴����A�Ĺ�����ʱ��
		try 
		{
			loadNewsThread.join();
			System.out.println("�������ŵ��߳̽���!");

			//��ȡ���Ѵʻ�
			getHotSearching();
			
			//���ù�������λ��
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

			//��ʼ����
			Rolnews.startRolling();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ���Ѵʺ��Ƽ������ţ����԰ٶ�������ҳ������Ŀ�ͻ�����Ұ��Ŀ
	 */
	private void getHotSearching()
	{
		Document docu = null;
		try 
		{
			//��ȡ�ٶ�������ҳ�ĵ�
			docu = Jsoup.parse(new URL("http://news.baidu.com/"), Rolnews.parameters.getLoadingTime());

			//�½����ݿ����
			DataProcessor processor = new DataProcessor("HotWords.xml", "HotWords");
			List<HotWord> hotWords = new ArrayList<HotWord>();

			//			DatabaseConnection connection = new DatabaseConnection();

			//��ȡ�����ӱ�ǩ
			Elements elements = docu.select("a");
			for(Element element : elements)
			{
				String title = element.text();
				int len = title.length();
				if(len > 10 || len == 0)
					continue;

				//��ȡ������
				String href = element.attr("href");
				if(!href.contains("hotquery"))
					continue;

				//����ȡ���ȵ�����б�
				HotWord hotWord = new HotWord(title, href);
				hotWords.add(hotWord);

				//				connection.InsertHotWords(title, href);
			}

			//�����б��е����ݴ������ݿ�
			processor.AddHotWord(hotWords);
			//��������ǰ������
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
