package com.rolnews.values;

/**
 * ��������ö����
 * 
 * @author Owen Lie
 */
public enum NewsFont {

	//��������
	SONG(new LabelFont("����", 0, 15, 1)),
	HUAWENXIHEI(new LabelFont("����ϸ��", 0, 17, -1)),
	HUAWENXINWEI(new LabelFont("������κ", 0, 20, 0)),
	FANGSONG(new LabelFont("����", 1, 17, 1)),
	KAITI(new LabelFont("����", 0, 17, 0)),
	LISHU(new LabelFont("����", 0, 20, 0)),
	HUAWENXINGKAI(new LabelFont("�����п�", 0, 20, 0)),
	DEFAULTFONT(new LabelFont());
	
	private LabelFont labelFont;
	
	private NewsFont(LabelFont labelFont)
	{
		this.labelFont = labelFont;
	}
	
	public LabelFont getLabelFont()
	{
		return this.labelFont;
	}
}
