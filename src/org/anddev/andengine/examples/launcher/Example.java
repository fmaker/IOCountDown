package org.anddev.andengine.examples.launcher;

import org.anddev.andengine.examples.PhysicsExample;
import org.anddev.andengine.ui.activity.BaseGameActivity;

/**
 * @author Nicolas Gramlich
 * @since 20:42:27 - 16.06.2010
 */
public enum Example {

	PHYSICS(PhysicsExample.class, 0);
	
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	public final Class<? extends BaseGameActivity> CLASS;
	public final int NAMERESID;

	// ===========================================================
	// Constructors
	// ===========================================================

	private Example(final Class<? extends BaseGameActivity> pExampleClass, final int pNameResID) {
		this.CLASS = pExampleClass;
		this.NAMERESID = pNameResID;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}