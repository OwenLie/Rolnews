package com.rolnews.values;

/**
 * 新闻字体枚举类
 * 
 * @author Owen Lie
 */
public enum NewsFont {

	//新闻字体
	SONG(new LabelFont("宋体", 0, 15, 1)),
	HUAWENXIHEI(new LabelFont("华文细黑", 0, 17, -1)),
	HUAWENXINWEI(new LabelFont("华文新魏", 0, 20, 0)),
	FANGSONG(new LabelFont("仿宋", 1, 17, 1)),
	KAITI(new LabelFont("楷体", 0, 17, 0)),
	LISHU(new LabelFont("隶书", 0, 20, 0)),
	HUAWENXINGKAI(new LabelFont("华文行楷", 0, 20, 0)),
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
