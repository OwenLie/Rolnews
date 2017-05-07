package com.rolnews.animation;

/**
 * 动画工厂，统一生产FlashUI对象
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
