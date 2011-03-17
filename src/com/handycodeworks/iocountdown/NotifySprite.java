package com.handycodeworks.iocountdown;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

public class NotifySprite extends Sprite {
	private final IPositionChangedListener mListener;

	public NotifySprite(float pX, float pY, TextureRegion pTextureRegion, IPositionChangedListener listener) {
		super(pX, pY, pTextureRegion);
		mListener = listener;
	}

	@Override
	public void setPosition(float pX, float pY) {
		super.setPosition(pX, pY);
		mListener.onPositionChanged(this, pX, pY);
	}
	
	
	
}
