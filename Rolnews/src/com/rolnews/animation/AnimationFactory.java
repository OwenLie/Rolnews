package com.rolnews.animation;

/**
 * ����������ͳһ����FlashUI����
 * 
 * @author Owen Lie
 */
public class AnimationFactory {

	public FlashUI getBottomToTop()
	{
		return new BottomToTop();
	}
	
	public FlashUI getTopToBottom()
	{
		return new TopToBottom();
	}
	
	public Stretch getStretch()
	{
		return new Stretch();
	}
	
	public Shrink getShrink()
	{
		return new Shrink();
	}
}
