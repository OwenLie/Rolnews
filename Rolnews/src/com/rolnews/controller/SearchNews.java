package com.rolnews.controller;

/**
 * 搜索新闻，搜索与关键字相关的新闻数据，从本地文件搜索
 * @author Owen Lie
 * @version 1.0.0
 */
public class SearchNews {

	private String keyWord = null;  //关键字
	
	public SearchNews(String keyWord)
	{
		this.keyWord = keyWord;
	}
}
