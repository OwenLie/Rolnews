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
 * �Ƽ�����
 * @author Owen Lie
 * @version 1.0.0
 */
public class NewsRecommendation {
	
	public static List<String[]> recommendations = new ArrayList<String[]>();
	
	public static JLabel getRecommNews(int location)
	{
		int size = recommendations.size();
		LabelFont labelFont = Rolnews.parameters.getLabelFont().getLabelFont();  //jlabel����������
		JLabel label = null;
		if(size > 0)
		{
			Random random = new Random();
			String[] news = recommendations.get(random.nextInt(recommendations.size()));
			label = new JLabel(news[0]);
			label.setName(news[1]);
			label.setFont(labelFont.getFont());
			//��ȡlabel�����س���
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
	 * ��ȡ�Ƽ�����
	 */
	public List<String[]> getRecommNews()
	{
		List<String[]> recommendations = new ArrayList<String[]>();
		Map<String, List<String[]>> map = new HashMap<String, List<String[]>>();
		
		//���Ӱٶ���ַ�ϻ�ȡ���Ƽ�������ӵ�list�б��У��ٽ��б���ӵ�hash����
		//�ӰٶȻ�ȡ�Ƽ�����
		String url = "http://news.baidu.com";
		String selection = "strong > a";
		List<String[]> baidu = getRecommNewsFromURL(url, selection);
		selection = "li.bold-item > a";
		baidu.addAll(getRecommNewsFromURL(url, selection));
		map.put("0", baidu);
		
		//�ӷ������ȡ�Ƽ�����
		url = "http://www.ifeng.com/";
		selection = "h1 > a";
		List<String[]> ifeng = getRecommNewsFromURL(url, selection);
		selection = "a.strong";
		ifeng.addAll(getRecommNewsFromURL(url, selection));
		map.put("1", ifeng);
		
		//�����׻�ȡ�Ƽ�����
		url = "http://www.163.com/";
		selection = "li.hx > a";
		List<String[]> wangyi = getRecommNewsFromURL(url, selection);
		map.put("2", wangyi);
		
		//�ӻ�������ȡ�Ƽ�����
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
	 * ���ݸ�������ַ�����б�׼��ȡҪ�Ƽ�����������
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
	 * ����������������ŵ����ƶ�
	 */
	private double getSimilarity(String newsA, String newsB) 
	{
		if (newsA != null && newsA.trim().length() > 0 && newsB != null && newsB.trim().length() > 0)
		{
			//ʹ��Map�ṹ�洢�ַ����ֵĴ�����ͳ�ƽ����Integer���͵ļ�Ϊ�����ַ�Ψһ��ͨ��gb2312���ɵ�
			//��λ�����֣�������һ��Ψһ��int[]���͵�ֵ�Ƕ�ά���飬int[0]Ϊ���ַ���str1�г��ֵĴ�����int[1]Ϊ���ַ���str2�г��ֵĴ���
			Map<Integer, int[]> AlgorithmMap = new HashMap<Integer, int[]>();

			//ͳ�Ƶ�һ���ַ����и��ַ����ֵĸ���
			for (int i = 0; i < newsA.length(); i++)
			{
				//��ȡstr1�еĵ�i���ַ�
				char d1 = newsA.charAt(i);
				//�ж��Ƿ�Ϊ����
				if(isHanZi(d1))
				{
					//��ȡ�ú���ͨ��gb2312�������ɵ�Ψһ����
					int charIndex = getGB2312Id(d1);
					if(charIndex != -1)
					{
						int[] fq = AlgorithmMap.get(charIndex);
						//���ַ��Ѿ�������Map�У�ֻ�轫������ַ����ִ�����fq[0]�Զ���һ����
						if(fq != null && fq.length == 2)
							fq[0]++;
						//���ַ�û��ͳ�ƹ����½���������ִ����Ķ�ά����
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

			//��ȡ���еļ��ĵ�����
			Iterator<Integer> iterator = AlgorithmMap.keySet().iterator();
			double sqstr1 = 0;
			double sqstr2 = 0;
			double denominator = 0; 
			
			//�����������ƶ�
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

	// �ж��Ƿ���
	private boolean isHanZi(char ch) 
	{
		return (ch >= 0x4E00 && ch <= 0x9FA5);
	}

	/**
	 * ������������ַ�����GB2312����Ĺ�����ת��������һ������
	 * 
	 * @param �����ַ�
	 * @return ����GB2312�������ɵ�����
	 */
	private short getGB2312Id(char ch) 
	{
		try 
		{
			//���ú�����GB2312���룬����Ϊ�����ֽ�
			byte[] buffer = Character.toString(ch).getBytes("GB2312");
			//������������ֽڣ���˵��������ַ����������ַ�
			if (buffer.length != 2) 
				return -1;
			
			//��ȡ��һ���ֽڶ�Ӧ������
			int b0 = (int) (buffer[0] & 0x0FF) - 161;
			//��ȡ�ڶ����ֽڶ�Ӧ��λ��
			int b1 = (int) (buffer[1] & 0x0FF) - 161;
			
			//���������ź�λ�ŵõ������֣�����Ѱַģʽ��
			return (short) (b0 * 94 + b1);
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
		return -1;
	}
}
