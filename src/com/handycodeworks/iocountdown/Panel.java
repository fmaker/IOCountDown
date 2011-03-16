package com.handycodeworks.iocountdown;

import android.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;



public class Panel extends SurfaceView implements SurfaceHolder.Callback{
	private CanvasThread canvasthread;
	
    public Panel(Context context, AttributeSet attrs) {
		super(context, attrs); 
		
	    getHolder().addCallback(this);
	    canvasthread = new CanvasThread(getHolder(), this);
	    setFocusable(true);
	}

	 


	 public Panel(Context context) {
		   super(context);
		    getHolder().addCallback(this);
		    canvasthread = new CanvasThread(getHolder(), this);
		    setFocusable(true);

	    }


	@Override
	public void onDraw(Canvas canvas) {
		Paint paint = new Paint();

		canvas.drawColor(Palette.FILL_COLOR);
		
	}
	 
	 
	 
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	    canvasthread.setRunning(true);
	    canvasthread.start();

		
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		boolean retry = true;
		canvasthread.setRunning(false);
		while (retry) {
			try {
				canvasthread.join();
				retry = false;
			} catch (InterruptedException e) {
				// we will try it again and again...
			}
		}

	}


}   