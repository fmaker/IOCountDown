package com.handycodeworks.iocountdown;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;

public class TimeDisplay {
	
	protected int[][] mDotMatrix;
	protected static final int DIGIT_WIDTH = 4;
	protected static final int DIGIT_HEIGHT = 7;
	protected static final int NUM_DIGITS = 9;
	protected static final int NUM_COLONS = NUM_DIGITS-1;
	protected static final int NUM_X = DIGIT_WIDTH*NUM_DIGITS+NUM_COLONS;
	protected static final int NUM_Y = DIGIT_HEIGHT;
	protected static final int DIGIT_SPACING = 1;
	protected static final int RADIUS = 6;
	protected static final int PADDING = RADIUS/2;

	private final long MILLI_PER_DAY = 1000*60*60*24;
	private final long MILLI_PER_HOUR = 1000*60*60;
	private final long MILLI_PER_MIN = 1000*60;
	private final long MILLI_PER_SEC = 1000;
	private final long ONE_SECOND = 1000;

	private final long googleIO = 1305043200000L; // 05/10/11 - 14:00:00 GMT (09:00:00 PST)
	private Timer mTimer = new Timer(false);

	private int currDays = 0;
	private int currHours = 0;
	private int currMins = 0;
	private int currSecs = 0;
	
	private TimeChangedListener listener;
	
	private TimerTask task = new TimerTask(){
		public void run(){
			updateMatrix();
			listener.OnTimeChangedListener();
		};
	};
	
	public void setTimeChangedListener(TimeChangedListener l){
		listener = l;
	}
	
	public TimeDisplay(int width, int height){
	    
	    mDotMatrix = new int[NUM_X][NUM_Y];

	    // Initialize matrix
		for(int i=0;i<NUM_X;i++){
			for(int j=0;j<NUM_Y;j++){
				mDotMatrix[i][j] = Palette.CLEAR;
			}
		}
	}
	
	public void start(){
		// Start timer
		mTimer.scheduleAtFixedRate(task, 0, ONE_SECOND);
	}
	
	
	private synchronized void updateMatrix(){
//		Log.d("IO","IO: "+String.valueOf(googleIO));
//		Log.d("IO","Now: "+String.valueOf(System.currentTimeMillis()));
		long timeDelta = googleIO - System.currentTimeMillis();
		
		// All done!
		if(timeDelta < 0){
			timeDelta = 0;
			task.cancel();
		}
		
		// Days
		int days = (int) (timeDelta / MILLI_PER_DAY);
		paintDigit(0,days/100,Palette.BLUE);
		paintDigit(1,(days-days/100)/10,Palette.BLUE);
		paintDigit(2,(days-days/100 - (days/10)*10),Palette.BLUE);
		
		// Hours
		timeDelta = timeDelta - days*MILLI_PER_DAY;
		int hours = (int) (timeDelta /MILLI_PER_HOUR);
		paintDigit(3,hours/10,Palette.CYAN);
		paintDigit(4,(hours -(hours/10)*10),Palette.CYAN);
		
		// Minutes
		timeDelta = timeDelta - hours*MILLI_PER_HOUR;
		int minutes = (int) (timeDelta /MILLI_PER_MIN);
		paintDigit(5,minutes/10,Palette.RED);
		paintDigit(6,(minutes -(minutes/10)*10),Palette.RED);
		
		// Seconds
		timeDelta = timeDelta - minutes*MILLI_PER_MIN;
		int seconds = (int) (timeDelta /MILLI_PER_SEC);
		paintDigit(7,seconds/10,Palette.GREEN);
		paintDigit(8,(seconds -(seconds/10)*10),Palette.GREEN);
		
	}
	
	// Digits are left to right from 0->8
	private synchronized void paintDigit(int digitNum,int digitValue,int color){
		int[][] digitArray = new int[DIGIT_WIDTH][DIGIT_HEIGHT];
		
		// Fill with default color
		for(int i=0;i<DIGIT_WIDTH;i++){
			for(int j=0;j<DIGIT_HEIGHT;j++){
				digitArray[i][j] = Palette.LT_GREY;
			}
		}
		
		// Fill in number dots
		switch(digitValue){
		case 0:
			digitArray[0][0] = color;
			digitArray[1][0] = color;
			digitArray[2][0] = color;
			digitArray[3][0] = color;
			digitArray[0][1] = color;
			digitArray[3][1] = color;
			digitArray[0][2] = color;
			digitArray[3][2] = color;
			digitArray[0][3] = color;
			digitArray[3][3] = color;
			digitArray[0][4] = color;
			digitArray[3][4] = color;
			digitArray[0][5] = color;
			digitArray[3][5] = color;
			digitArray[0][6] = color;
			digitArray[1][6] = color;
			digitArray[2][6] = color;
			digitArray[3][6] = color;
			break;
		case 1:
			digitArray[3][6] = color;
			digitArray[3][5] = color;
			digitArray[3][4] = color;
			digitArray[3][3] = color;
			digitArray[3][2] = color;
			digitArray[3][1] = color;
			digitArray[3][0] = color;
			break;
		case 2:
			digitArray[3][6] = color;
			digitArray[3][5] = color;
			digitArray[3][4] = color;
			digitArray[3][3] = color;
			digitArray[3][0] = color;
			digitArray[2][0] = color;
			digitArray[2][3] = color;
			digitArray[2][6] = color;
			digitArray[1][0] = color;
			digitArray[1][3] = color;
			digitArray[1][6] = color;
			digitArray[0][0] = color;
			digitArray[0][1] = color;
			digitArray[0][2] = color;
			digitArray[0][3] = color;
			digitArray[0][6] = color;
			break;
		case 3:
			digitArray[3][6] = color;
			digitArray[3][5] = color;
			digitArray[3][4] = color;
			digitArray[3][3] = color;
			digitArray[3][2] = color;
			digitArray[3][1] = color;
			digitArray[3][0] = color;
			digitArray[2][0] = color;
			digitArray[2][3] = color;
			digitArray[2][6] = color;
			digitArray[1][0] = color;
			digitArray[1][3] = color;
			digitArray[1][6] = color;
			digitArray[0][0] = color;
			digitArray[0][3] = color;
			digitArray[0][6] = color;
			break;
		case 4:
			digitArray[3][6] = color;
			digitArray[3][5] = color;
			digitArray[3][4] = color;
			digitArray[3][3] = color;
			digitArray[3][2] = color;
			digitArray[3][1] = color;
			digitArray[3][0] = color;
			digitArray[2][3] = color;
			digitArray[1][3] = color;
			digitArray[0][3] = color;
			digitArray[0][4] = color;
			digitArray[0][5] = color;
			digitArray[0][6] = color;
			break;
		case 5:
			digitArray[3][6] = color;
			digitArray[3][3] = color;
			digitArray[3][1] = color;
			digitArray[3][2] = color;
			digitArray[3][0] = color;
			digitArray[2][0] = color;
			digitArray[2][3] = color;
			digitArray[2][6] = color;
			digitArray[1][0] = color;
			digitArray[1][3] = color;
			digitArray[1][6] = color;
			digitArray[0][0] = color;
			digitArray[0][3] = color;
			digitArray[0][5] = color;
			digitArray[0][4] = color;
			digitArray[0][6] = color;
		break;
		case 6:
			digitArray[3][6] = color;
			digitArray[3][3] = color;
			digitArray[3][1] = color;
			digitArray[3][2] = color;
			digitArray[3][0] = color;
			digitArray[2][0] = color;
			digitArray[2][3] = color;
			digitArray[2][6] = color;
			digitArray[1][0] = color;
			digitArray[1][3] = color;
			digitArray[1][6] = color;
			digitArray[0][0] = color;
			digitArray[0][1] = color;
			digitArray[0][2] = color;
			digitArray[0][3] = color;
			digitArray[0][5] = color;
			digitArray[0][4] = color;
			digitArray[0][6] = color;
		break;
		case 7:
			digitArray[3][6] = color;
			digitArray[3][5] = color;
			digitArray[3][4] = color;
			digitArray[3][3] = color;
			digitArray[3][2] = color;
			digitArray[3][1] = color;
			digitArray[3][0] = color;
			digitArray[2][6] = color;
			digitArray[1][6] = color;
			digitArray[0][6] = color;
			break;
		case 8:
			digitArray[3][6] = color;
			digitArray[3][5] = color;
			digitArray[3][4] = color;
			digitArray[3][3] = color;
			digitArray[3][1] = color;
			digitArray[3][2] = color;
			digitArray[3][0] = color;
			digitArray[2][0] = color;
			digitArray[2][3] = color;
			digitArray[2][6] = color;
			digitArray[1][0] = color;
			digitArray[1][3] = color;
			digitArray[1][6] = color;
			digitArray[0][0] = color;
			digitArray[0][1] = color;
			digitArray[0][2] = color;
			digitArray[0][3] = color;
			digitArray[0][5] = color;
			digitArray[0][4] = color;
			digitArray[0][6] = color;
		break;
		case 9:
			digitArray[3][6] = color;
			digitArray[3][5] = color;
			digitArray[3][4] = color;
			digitArray[3][3] = color;
			digitArray[3][1] = color;
			digitArray[3][2] = color;
			digitArray[3][0] = color;
			digitArray[2][3] = color;
			digitArray[2][6] = color;
			digitArray[1][3] = color;
			digitArray[1][6] = color;
			digitArray[0][3] = color;
			digitArray[0][5] = color;
			digitArray[0][4] = color;
			digitArray[0][6] = color;
			break;
		}
		
		// Copy digit to matrix
		for(int i=0;i<DIGIT_WIDTH;i++){
			for(int j=0;j<DIGIT_HEIGHT;j++){
				mDotMatrix[i+digitToMatrixOffset(digitNum)][j] = digitArray[i][j];
			}
		}
	}
	
	private int digitToMatrixOffset(int digitIndex){
		int col = digitIndex*DIGIT_WIDTH;
		col += col/(DIGIT_WIDTH); // For columns
		return col;
	}
	
//	private void drawDisplay(){
//
//		int dotX = originX,dotY = originY;
//		for(int j=0;j<NUM_Y;j++){
//			dotX = originX;
//			for(int i=0;i<NUM_X;i++){
//				
//				canvas.drawCircle(dotX, dotY, RADIUS, paint);
//				dotX += RADIUS*2 + PADDING;
//			}
//			dotY -= RADIUS*2 + PADDING;
//		}
//	}

	public interface TimeChangedListener{
		public void OnTimeChangedListener();
	}
	
}
