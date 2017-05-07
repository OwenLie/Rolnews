package com.rolnews.recommendation;

import java.awt.Color;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JLabel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.rolnews.loadnews.LoadNews;
import com.rolnews.main.Rolnews;
import com.rolnews.values.LabelFont;

/**
 * 推荐新闻
 * @author Owen Lie
 * @version 1.0.0
 */
public class NewsRecommendation {
	
	public static List<String[]> recommendations = new ArrayList<String[]>();
	
	public static JLabel getRecommNews(int location)
	{
		int size = recommendations.size();
		LabelFont labelFont = Rolnews.parameters.getLabelFont().getLabelFont();  //jlabel上新闻字体
		JLabel label = null;
		if(size > 0)
		{
			Random random = new Random();
			String[] news = recommendations.get(random.nextInt(recommendations.size()));
			label = new JLabel(news[0]);
			label.setName(news[1]);
			label.setFont(labelFont.getFont());
			//获取label的像素长度
			int labelWidth = label.getFontMetrics
					(label.getFont()).stringWidth(news[0]);
			label.setBounds(location, labelFont.getLableY(), labelWidth, 20);
			label.setForeground(Color.red);
		}
		
		return label;
	}
	
	public List<String[]> getRecommNews(String url)
	{
		List<String[]> recoms = new ArrayList<String[]>();
		String standard = "";
		switch(url)
		{
			case "http://www.ifeng.com/":
				standard = "h1 > a";
				recoms = getRecommNewsFromURL(url, standard);
				standard = "a.strong";
				recoms.addAll(getRecommNewsFromURL(url, standard));
				break;
			case "http://www.163.com/":
				standard = "li.hx > a";
				recoms = getRecommNewsFromURL(url, standard);
				break;
			case "http://www.huanqiu.com/":
				standard = "h4 > a";
				recoms = getRecommNewsFromURL(url, standard);
				break;
			default:
				standard = "strong > a";
				recoms = getRecommNewsFromURL(url, standard);
				standard = "li.bold-item > a";
				recoms.addAll(getRecommNewsFromURL(url, standard));
				break;
		}
		return recoms;
	}
	
	/**
	 * 获取推荐新闻
	 */
	public List<String[]> getRecommNews()
	{
		List<String[]> recommendations = new ArrayList<String[]>();
		Map<String, List<String[]>> map = new HashMap<String, List<String[]>>();
		
		//将从百度网址上获取的推荐新闻添加到list列表中，再将列表添加到hash表中
		//从百度获取推荐新闻
		String url = "http://news.baidu.com";
		String selection = "strong > a";
		List<String[]> baidu = getRecommNewsFromURL(url, selection);
		selection = "li.bold-item > a";
		baidu.addAll(getRecommNewsFromURL(url, selection));
		map.put("0", baidu);
		
		//从凤凰网获取推荐新闻
		url = "http://www.ifeng.com/";
		selection = "h1 > a";
		List<String[]> ifeng = getRecommNewsFromURL(url, selection);
		selection = "a.strong";
		ifeng.addAll(getRecommNewsFromURL(url, selection));
		map.put("1", ifeng);
		
		//从网易获取推荐新闻
		url = "http://www.163.com/";
		selection = "li.hx > a";
		List<String[]> wangyi = getRecommNewsFromURL(url, selection);
		map.put("2", wangyi);
		
		//从环球网获取推荐新闻
		url = "http://www.huanqiu.com/";
		selection = "h4 > a";
		List<String[]> huanqiu = getRecommNewsFromURL(url, selection);
		map.put("3", huanqiu);
		
		for(int i = 0; i < map.size() - 1; i++)
		{
			List<String[]> newsA = map.get(i + "");
			List<String[]> newsB = map.get((i + 1) + "");
			for(String[] news_A : newsA)
				for(String[] news_B : newsB)
				{
					double similarity = getSimilarity(news_A[0], news_B[0]);
					if(similarity > 0.5)
					{
						if(!recommendations.contains(news_A))
						{
							recommendations.add(news_A);
							if(!recommendations.contains(news_B))
								recommendations.add(news_B);
						}
						break;
					}
				}
		}
		
		return recommendations;
	}
	
	/**
	 * 根据给定的网址和评判标准获取要推荐的新闻数据
	 */
	private List<String[]> getRecommNewsFromURL(String url, String standard)
	{
		List<String[]> recommendations = new ArrayList<String[]>();
		try 
		{
			Document docs = Jsoup.parse(new URL(url), 10000);
			
			Elements elements = docs.select(standard);
			for(Element element : elements)
			{
				String title = element.text();
				if(title.length() < 10)
					continue;
				
				String href = element.attr("href");
				if(!href.startsWith("http"))
					continue;
				
				recommendations.add(new String[]{title, href});
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return recommendations;
	}
	
	/**
	 * 计算给定的两条新闻的相似度
	 */
	private double getSimilarity(String newsA, String newsB) 
	{
		if (newsA != null && newsA.trim().length() > 0 && newsB != null && newsB.trim().length() > 0)
		{
			//使用Map结构存储字符出现的次数的统计结果，Integer类型的键为中文字符唯一的通过gb2312生成的
			//区位号数字，该数字一定唯一，int[]类型的值是二维数组，int[0]为该字符在str1中出现的次数，int[1]为该字符在str2中出现的次数
			Map<Integer, int[]> AlgorithmMap = new HashMap<Integer, int[]>();

			//统计第一个字符串中各字符出现的个数
			for (int i = 0; i < newsA.length(); i++)
			{
				//获取str1中的第i个字符
				char d1 = newsA.charAt(i);
				//判断是否为汉字
				if(isHanZi(d1))
				{
					//获取该汉字通过gb2312编码生成的唯一数字
					int charIndex = getGB2312Id(d1);
					if(charIndex != -1)
					{
						int[] fq = AlgorithmMap.get(charIndex);
						//该字符已经保存在Map中，只需将保存该字符出现次数的fq[0]自动加一即可
						if(fq != null && fq.length == 2)
							fq[0]++;
						//该字符没有统计过，新建保存其出现次数的二维数组
						else 
						{
							fq = new int[2];
							fq[0] = 1;
							fq[1] = 0;
							AlgorithmMap.put(charIndex, fq);
						}
					}
				}
			}

			for (int i = 0; i < newsB.length(); i++)
			{
				char d2 = newsB.charAt(i);
				if(isHanZi(d2))
				{
					int charIndex = getGB2312Id(d2);
					if(charIndex != -1)
					{
						int[] fq = AlgorithmMap.get(charIndex);
						if(fq != null && fq.length == 2)
							fq[1]++;
						else
						{
							fq = new int[2];
							fq[0] = 0;
							fq[1] = 1;
							AlgorithmMap.put(charIndex, fq);
						}
					}
				}
			}

			//获取所有的键的迭代器
			Iterator<Integer> iterator = AlgorithmMap.keySet().iterator();
			double sqstr1 = 0;
			double sqstr2 = 0;
			double denominator = 0; 
			
			//迭代计算相似度
			while(iterator.hasNext())
			{
				int[] c = AlgorithmMap.get(iterator.next());
				denominator += c[0]*c[1];
				sqstr1 += c[0]*c[0];
				sqstr2 += c[1]*c[1];
			}

			return denominator / Math.sqrt(sqstr1*sqstr2);
		} 
		else 
			return 0.0;
	}

	// 判断是否汉字
	private boolean isHanZi(char ch) 
	{
		return (ch >= 0x4E00 && ch <= 0x9FA5);
	}

	/**
	 * 将输入的中文字符，以GB2312编码的规则反向转换，生成一个数字
	 * 
	 * @param 中文字符
	 * @return 根据GB2312规则生成的数字
	 */
	private short getGB2312Id(char ch) 
	{
		try 
		{
			//将该汉字以GB2312编码，保存为两个字节
			byte[] buffer = Character.toString(ch).getBytes("GB2312");
			//如果不是两个字节，则说明传入的字符不是中文字符
			if (buffer.length != 2) 
				return -1;
			
			//获取第一个字节对应的区号
			int b0 = (int) (buffer[0] & 0x0FF) - 161;
			//获取第二个字节对应的位号
			int b1 = (int) (buffer[1] & 0x0FF) - 161;
			
			//生成由区号和位号得到的数字（类似寻址模式）
			return (short) (b0 * 94 + b1);
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
		return -1;
	}
}
