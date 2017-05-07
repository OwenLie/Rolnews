package com.rolnews.values;

import java.awt.Color;
import java.awt.Font;
/**
 * 滚动新闻字体样式
 * 
 * @author Owen Lie
 */
public class LabelFont {

	private String fontName = "Dialog";
	private int fontStyle = 0;
	private int fontSize = 15;
	private Color fontColor = Color.white;
	
	private int lableY = 1;
	
	public LabelFont(String fontName, int fontStyle, int fontSize, int labelY, Color fontColor)
	{
		this.fontName = fontName;
		this.fontStyle = fontStyle;
		this.fontSize = fontSize;
		this.lableY = labelY;
		this.fontColor = fontColor;
	}
	
	public LabelFont(String fontName, int fontStyle, int fontSize, int labelY)
	{
		this.fontName = fontName;
		this.fontStyle = fontStyle;
		this.fontSize = fontSize;
		this.lableY = labelY;
	}
	
	public LabelFont()
	{
	}

	public Font getFont()
	{
		return new Font(this.fontName, this.fontStyle, this.fontSize);
	}
	
	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public int getFontStyle() {
		return fontStyle;
	}

	public void setFontStyle(int fontStyle) {
		this.fontStyle = fontStyle;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public int getLableY() {
		return lableY;
	}

	public void setLableY(int lableY) {
		this.lableY = lableY;
	}

	public Color getFontColor() {
		return fontColor;
	}

	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}
	
}
