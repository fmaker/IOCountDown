package com.handycodeworks.iocountdown;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import org.anddev.andengine.examples.launcher.Example;

public class CountdownActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Example physics = Example.PHYSICS;
		this.startActivity(new Intent(this, physics.CLASS));
    }
}