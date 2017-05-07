package com.rolnews.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.rolnews.model.History;
import com.rolnews.model.HotWord;

/**
 * 数据处理对象，提供增删查改的数据处理方法
 * @author Owen Lie
 * @version 1.0.0
 */
public class DataProcessor {

	//文件路径/文件名称
	private String filePath = null;

	/**
	 * 构造函数，初始化文件路径
	 */
	public DataProcessor(String filePath, String root)
	{
		//判断文件是否存在
		File file = new File(filePath);
		if(!file.exists())
		{
			try 
			{
				file.createNewFile();
				//写入默认数据
				FileWriter fw = new FileWriter(file);
				fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?><" + root + "></" + root + ">");
				fw.close();

			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		this.filePath = filePath;
	}

	/**
	 * 批量向数据库添加热搜词汇
	 * @param hotWords
	 */
	public void AddHotWord(List<HotWord> hotWords)
	{
		SAXReader saxReader = new SAXReader();
		Document doc = null;
		File file = new File(this.filePath);

		try
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			String today = df.format(new Date());

			//读取文件
			doc = saxReader.read(new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")));
			//得到根节点
			Element root = doc.getRootElement();
			//得到根节点下所有的HotWord节点的集合
			List<Element> words = doc.selectNodes("//HotWords/HotWord");
			//获得迭代器
			Iterator<Element> iterator = words.iterator();
			Iterator<HotWord> wordIterator = hotWords.iterator();

			while(wordIterator.hasNext())
			{
				HotWord hotWord = wordIterator.next();
				boolean added = false;
				while(iterator.hasNext())
				{
					//获取word元素
					Element word = iterator.next();
					//获取热词记录的日期并判断，如果不是今天的数据，则不用判断是否已经存入
					Attribute attr = word.attribute("RecDate");
					if(!today.equals(attr.getText()))
						continue;

					//判断是否已经存入过数据
					Attribute attr1 = word.attribute("Word");
					Attribute attr2 = word.attribute("Href");
					if(attr1.getText().equals(hotWord.getWord()) && attr2.getText().equals(hotWord.getHref()))
					{
						added = true;
						break;
					}
				}

				//没有存入过数据库，则添加到数据库中
				if(!added)
				{
					Element newWord = root.addElement("HotWord");
					newWord.addAttribute("Word", hotWord.getWord());
					newWord.addAttribute("Href", hotWord.getHref());
					newWord.addAttribute("RecDate", today);
				}
			}

			//将文档数据写会
			OutputFormat format = new OutputFormat();
			format.setEncoding("UTF-8");
			XMLWriter output = new XMLWriter(new FileOutputStream(file), format);
			output.write(doc);
			output.close();
		}
		catch (UnsupportedEncodingException | FileNotFoundException
				| DocumentException e)
		{
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 从数据库查询热词，模糊查询
	 * @return
	 */
	public List<HotWord> QueryHotWords(String attribute, String value)
	{
		SAXReader saxReader = new SAXReader();
		Document doc = null;
		File file = new File(this.filePath);
		List<HotWord> hotWords = new ArrayList<HotWord>();

		try
		{
			//读取文件
			doc = saxReader.read(new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")));
			//得到根节点下所有的HotWord节点的集合
			List<Element> words = doc.selectNodes("//HotWords/HotWord");
			//获得迭代器
			Iterator<Element> iterator = words.iterator();
			while(iterator.hasNext())
			{
				//获取HotWord元素
				Element word = iterator.next();
				//获取指定的属性并判断
				Attribute attr = word.attribute(attribute);
				//判断是否为想要的，如果是，直接添加到List
				if(attr.getText().contains(value))
				{
					//新建hotword对象的所有属性
					HotWord hotWord = new HotWord(word.attribute("Word").getText(), 
							word.attribute("Href").getText(),
							word.attribute("RecDate").getText());
					hotWords.add(hotWord);
				}
			}

			//将文档数据写会
			OutputFormat format = new OutputFormat();
			format.setEncoding("UTF-8");
			XMLWriter output = new XMLWriter(new FileOutputStream(file), format);
			output.write(doc);
			output.close();
		}
		catch (UnsupportedEncodingException | FileNotFoundException
				| DocumentException e)
		{
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return hotWords;
	}

	/**
	 * 向数据库添加历史记录信息
	 */
	public void AddHistoryRecord(History history)
	{
		SAXReader saxReader = new SAXReader();
		Document doc = null;
		File file = new File(this.filePath);

		try
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String today = df.format(new Date());

			//读取文件
			doc = saxReader.read(new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")));
			//得到根节点
			Element root = doc.getRootElement();
			//添加历史记录
			Element newHistory = root.addElement("History");
			//设置新添加元素的属性
			newHistory.addAttribute("Title", history.getTitle());
			newHistory.addAttribute("Href", history.getHref());
			newHistory.addAttribute("Description", history.getDescription());
			newHistory.addAttribute("RecDate", today);

			//将文档数据写会
			OutputFormat format = new OutputFormat();
			format.setEncoding("UTF-8");
			XMLWriter output = new XMLWriter(new FileOutputStream(file), format);
			output.write(doc);
			output.close();
		}
		catch (UnsupportedEncodingException | FileNotFoundException
				| DocumentException e)
		{
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 清理两天前的数据
	 */
	public void DeleteData(String[] databases, String[] roots)
	{
		SAXReader saxReader = new SAXReader();
		Document doc = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		long dayBeforeYestoday = new Date().getTime() - 24 * 60 * 60 * 1000 * 2;

		try
		{
			int size = databases.length;
			for(int i = 0; i < size; i++)
			{
				File file = new File(databases[i] + ".xml");
				if(!file.exists())
					continue;

				//读取文件
				doc = saxReader.read(new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")));
				//得到根节点
				Element root = doc.getRootElement();
				//得到根节点下所有的user节点的集合
				List<Element> list = doc.selectNodes("//" + databases[i] + "/" + roots[i]);
				//获得迭代器
				Iterator<Element> iterator = list.iterator();
				while(iterator.hasNext())
				{
					//获取user元素
					Element e = iterator.next();
					//获取ID并判断
					Attribute attr = e.attribute("RecDate");
					//判断是否为想要的，如果是，直接删除
					if(dayBeforeYestoday > df.parse(attr.getText()).getTime())
					{
						System.out.println("删除的数据：" + e.attribute("Href").getText());
						root.remove(e);
					}
				}

				//将文档数据写会
				OutputFormat format = new OutputFormat();
				format.setEncoding("UTF-8");
				XMLWriter output = new XMLWriter(new FileOutputStream(file), format);
				output.write(doc);
				output.close();
			}
		}
		catch (UnsupportedEncodingException | FileNotFoundException
				| DocumentException e)
		{
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 
		catch (ParseException e1)
		{
			e1.printStackTrace();
		}
	}
}
