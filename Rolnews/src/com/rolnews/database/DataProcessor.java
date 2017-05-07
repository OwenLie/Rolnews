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
 * ���ݴ�������ṩ��ɾ��ĵ����ݴ�����
 * @author Owen Lie
 * @version 1.0.0
 */
public class DataProcessor {

	//�ļ�·��/�ļ�����
	private String filePath = null;

	/**
	 * ���캯������ʼ���ļ�·��
	 */
	public DataProcessor(String filePath, String root)
	{
		//�ж��ļ��Ƿ����
		File file = new File(filePath);
		if(!file.exists())
		{
			try 
			{
				file.createNewFile();
				//д��Ĭ������
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
	 * ���������ݿ�������Ѵʻ�
	 * @param hotWords
	 */
	public void AddHotWord(List<HotWord> hotWords)
	{
		SAXReader saxReader = new SAXReader();
		Document doc = null;
		File file = new File(this.filePath);

		try
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
			String today = df.format(new Date());

			//��ȡ�ļ�
			doc = saxReader.read(new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")));
			//�õ����ڵ�
			Element root = doc.getRootElement();
			//�õ����ڵ������е�HotWord�ڵ�ļ���
			List<Element> words = doc.selectNodes("//HotWords/HotWord");
			//��õ�����
			Iterator<Element> iterator = words.iterator();
			Iterator<HotWord> wordIterator = hotWords.iterator();

			while(wordIterator.hasNext())
			{
				HotWord hotWord = wordIterator.next();
				boolean added = false;
				while(iterator.hasNext())
				{
					//��ȡwordԪ��
					Element word = iterator.next();
					//��ȡ�ȴʼ�¼�����ڲ��жϣ�������ǽ�������ݣ������ж��Ƿ��Ѿ�����
					Attribute attr = word.attribute("RecDate");
					if(!today.equals(attr.getText()))
						continue;

					//�ж��Ƿ��Ѿ����������
					Attribute attr1 = word.attribute("Word");
					Attribute attr2 = word.attribute("Href");
					if(attr1.getText().equals(hotWord.getWord()) && attr2.getText().equals(hotWord.getHref()))
					{
						added = true;
						break;
					}
				}

				//û�д�������ݿ⣬����ӵ����ݿ���
				if(!added)
				{
					Element newWord = root.addElement("HotWord");
					newWord.addAttribute("Word", hotWord.getWord());
					newWord.addAttribute("Href", hotWord.getHref());
					newWord.addAttribute("RecDate", today);
				}
			}

			//���ĵ�����д��
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
	 * �����ݿ��ѯ�ȴʣ�ģ����ѯ
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
			//��ȡ�ļ�
			doc = saxReader.read(new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")));
			//�õ����ڵ������е�HotWord�ڵ�ļ���
			List<Element> words = doc.selectNodes("//HotWords/HotWord");
			//��õ�����
			Iterator<Element> iterator = words.iterator();
			while(iterator.hasNext())
			{
				//��ȡHotWordԪ��
				Element word = iterator.next();
				//��ȡָ�������Բ��ж�
				Attribute attr = word.attribute(attribute);
				//�ж��Ƿ�Ϊ��Ҫ�ģ�����ǣ�ֱ����ӵ�List
				if(attr.getText().contains(value))
				{
					//�½�hotword�������������
					HotWord hotWord = new HotWord(word.attribute("Word").getText(), 
							word.attribute("Href").getText(),
							word.attribute("RecDate").getText());
					hotWords.add(hotWord);
				}
			}

			//���ĵ�����д��
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
	 * �����ݿ������ʷ��¼��Ϣ
	 */
	public void AddHistoryRecord(History history)
	{
		SAXReader saxReader = new SAXReader();
		Document doc = null;
		File file = new File(this.filePath);

		try
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
			String today = df.format(new Date());

			//��ȡ�ļ�
			doc = saxReader.read(new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")));
			//�õ����ڵ�
			Element root = doc.getRootElement();
			//�����ʷ��¼
			Element newHistory = root.addElement("History");
			//���������Ԫ�ص�����
			newHistory.addAttribute("Title", history.getTitle());
			newHistory.addAttribute("Href", history.getHref());
			newHistory.addAttribute("Description", history.getDescription());
			newHistory.addAttribute("RecDate", today);

			//���ĵ�����д��
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
	 * ��������ǰ������
	 */
	public void DeleteData(String[] databases, String[] roots)
	{
		SAXReader saxReader = new SAXReader();
		Document doc = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
		long dayBeforeYestoday = new Date().getTime() - 24 * 60 * 60 * 1000 * 2;

		try
		{
			int size = databases.length;
			for(int i = 0; i < size; i++)
			{
				File file = new File(databases[i] + ".xml");
				if(!file.exists())
					continue;

				//��ȡ�ļ�
				doc = saxReader.read(new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")));
				//�õ����ڵ�
				Element root = doc.getRootElement();
				//�õ����ڵ������е�user�ڵ�ļ���
				List<Element> list = doc.selectNodes("//" + databases[i] + "/" + roots[i]);
				//��õ�����
				Iterator<Element> iterator = list.iterator();
				while(iterator.hasNext())
				{
					//��ȡuserԪ��
					Element e = iterator.next();
					//��ȡID���ж�
					Attribute attr = e.attribute("RecDate");
					//�ж��Ƿ�Ϊ��Ҫ�ģ�����ǣ�ֱ��ɾ��
					if(dayBeforeYestoday > df.parse(attr.getText()).getTime())
					{
						System.out.println("ɾ�������ݣ�" + e.attribute("Href").getText());
						root.remove(e);
					}
				}

				//���ĵ�����д��
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
