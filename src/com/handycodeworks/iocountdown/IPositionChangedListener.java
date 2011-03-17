package com.handycodeworks.iocountdown;

import org.anddev.andengine.entity.sprite.Sprite;

public interface IPositionChangedListener {
	public void onPositionChanged(Sprite s, final float posX, final float posY);
}
