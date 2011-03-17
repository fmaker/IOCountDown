package com.handycodeworks.iocountdown;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;



public class Panel extends SurfaceView implements SurfaceHolder.Callback{
	private CanvasThread canvasthread;
	
	private int[][] dotMatrix;
	private final int DIGIT_WIDTH = 4;
	private final int DIGIT_HEIGHT = 7;
	private final int NUM_DIGITS = 9;
	private final int NUM_X = DIGIT_WIDTH*NUM_DIGITS;
	private final int NUM_Y = DIGIT_HEIGHT;
	private final int DIGIT_SPACING = 1;
	private final int RADIUS = 6;
	private final int PADDING = 1;
	
	// Paint positions
	int originX = 0, originY = 0;
	int centerX = 0, centerY = 0;
	
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
		// Fill canvas
		canvas.drawColor(Palette.FILL_COLOR);
		
		Paint paint = new Paint();
		paint.setAntiAlias(true);

		int dotX = originX,dotY = originY;
		for(int j=0;j<NUM_Y;j++){
			dotX = originX;
			for(int i=0;i<NUM_X;i++){
				paint.setColor(dotMatrix[i][j]);
				canvas.drawCircle(dotX, dotY, RADIUS, paint);
				dotX += RADIUS*2 + PADDING;
			}
			dotY -= RADIUS*2 + PADDING;
		}
		
		// Center reference point
//		paint.setColor(Color.BLACK);
//		canvas.drawCircle(centerX, centerY, 1, paint);
	}
	 
	private void clearMatrix(){
		for(int i=0;i<NUM_X;i++){
			for(int j=0;j<NUM_Y;j++){
				dotMatrix[i][j] = Palette.LT_GREY;
			}
		}
	}
	 
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	    centerX = width/2;
	    centerY = height/2;
	    
	    originX = width/2 - (NUM_X/2)*(PADDING + 2*RADIUS) + RADIUS + PADDING/2;
	    originY = height/2 + (NUM_Y/2)*(RADIUS*2 + PADDING);
		
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// Create matrix
	    dotMatrix = new int[NUM_X][NUM_Y];
	    clearMatrix();
	    
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
		
		dotMatrix = null; // Free memory

	}


}   