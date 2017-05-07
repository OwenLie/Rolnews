package com.rolnews.model;
/**
 * �����ȴ�ģ��
 * @author Owen Lie
 * @version 1.0.0
 */
public class HotWord {

	private String word;  //�ȴ�
	private String href;  //������
	private String recDate;  //�Ƽ�����
	
	public HotWord(String word, String href, String recDate)
	{
		this.word = word;
		this.href = href;
		this.recDate = recDate;
	}
	
	public HotWord(String word, String href)
	{
		this.word = word;
		this.href = href;
	}
	
	public String getWord() { return word; }
	public void setWord(String word) { this.word = word; }
	
	public String getHref() { return href; }
	public void setHref(String href) { this.href = href; }
	
	public String getRecDate() { return recDate; }
	public void setRecDate(String recDate) { this.recDate = recDate; }
}
