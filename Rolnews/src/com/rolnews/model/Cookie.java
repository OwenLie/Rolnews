package com.rolnews.model;
/**
 * Cookie���ݣ���¼�û���ϲ�ã��Ա����û��Ƽ���������
 * @author Owen Lie
 * @version 1.0.0
 */
public class Cookie {

	private String type;
	private int count;
	
	public Cookie(String type, int count)
	{
		this.type = type;
		this.count = count;
	}
	
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
	
	public int getCount() { return count; }
	public void setCount(int count) { this.count = count; }
}
