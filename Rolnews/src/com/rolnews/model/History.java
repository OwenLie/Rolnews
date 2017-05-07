package com.rolnews.model;
/**
 * 历史记录模型，存储历史数据
 * @author Owen Lie
 * @version 1.0.0
 */
public class History {

	private String title;  //新闻标题
	private String href;  //新闻超链接
	private String viewDate;  //浏览日期
	private String description;  //新闻摘要
	
	public History(String title, String href, String description)
	{
		this.title = title;
		this.href = href;
		this.description = description;
	}
	
	public History(String title, String href, String description, String viewDate)
	{
		this.title = title;
		this.href = href;
		this.description = description;
		this.viewDate = viewDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getViewDate() {
		return viewDate;
	}

	public void setViewDate(String viewDate) {
		this.viewDate = viewDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
