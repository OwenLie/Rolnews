package com.rolnews.model;
/**
 * 搜索热词模型
 * @author Owen Lie
 * @version 1.0.0
 */
public class HotWord {

	private String word;  //热词
	private String href;  //超链接
	private String recDate;  //推荐日期
	
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
