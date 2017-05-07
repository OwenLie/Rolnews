package com.rolnews.values;

import java.awt.Color;
/**
 * ��ɫֵö���࣬���������Ƥ����ɫֵ
 * 
 * @author Owen Lie
 */
public enum ThemeColor {

	//����ɫ
	RED(new Color(192, 0, 0)),
	BLUE(new Color(0, 112, 192)),
	GOLDEN(new Color(255, 192, 0)),
	BROWN(new Color(38, 38, 38)),
	PURPLE(new Color(112, 48, 160)),
	GREEN(new Color(46, 139, 87)),
	
	//������ɫ
	RED_MOUENT(new Color(200, 100, 100)),
	BLUE_MOUENT(new Color(0, 150, 200)),
	GOLDEN_MOUENT(new Color(255, 250, 100)),
	BROWN_MOUENT(new Color(89, 89, 89)),
	PURPLE_MOUENT(new Color(150, 50, 200)),
	GREEN_MOUENT(new Color(70, 200, 95));
	
	private Color color;
	
	private ThemeColor(Color color)
	{
		this.color = color;
	}
	
	public Color getValue()
	{
		return this.color;
	}
}
