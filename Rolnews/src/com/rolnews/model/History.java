package com.rolnews.model;
/**
 * ��ʷ��¼ģ�ͣ��洢��ʷ����
 * @author Owen Lie
 * @version 1.0.0
 */
public class History {

	private String title;  //���ű���
	private String href;  //���ų�����
	private String viewDate;  //�������
	private String description;  //����ժҪ
	
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
