package com.handycodeworks.iocountdown;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.util.Log;

public class TimeDisplay {
	private final Date googleIO = new Date(2011,05,10,9,00);
	private DateFormat format = new SimpleDateFormat("dd:HH:mm:ss",Locale.US);
	
	protected int[][] mDotMatrix;
	protected static final int DIGIT_WIDTH = 4;
	protected static final int DIGIT_HEIGHT = 7;
	protected static final int NUM_DIGITS = 9;
	protected static final int NUM_COLONS = 3;
	protected static final int COLON_COLS[] = {12,21,30};
	protected static final int NUM_X = DIGIT_WIDTH*NUM_DIGITS+NUM_COLONS;
	protected static final int NUM_Y = DIGIT_HEIGHT;
	protected static final int DIGIT_SPACING = 1;
	protected static final int RADIUS = 7;
	protected static final int PADDING = RADIUS/2;
	
	private final int MILLI_PER_DAY = 1000*60*60*24;
	
	public TimeDisplay(int width, int height){
	    
	    mDotMatrix = new int[NUM_X][NUM_Y];

	    // Initialize matrix
		for(int i=0;i<NUM_X;i++){
			for(int j=0;j<NUM_Y;j++){
				// Colon
				if(i==COLON_COLS[0]||
				   i==COLON_COLS[1]||
				   i==COLON_COLS[2]){
					mDotMatrix[i][j] = Palette.CLEAR;
					continue;
				}
				else{
					mDotMatrix[i][j] = Palette.LT_GREY;
				}
			}
		}
		updateMatrix();
	}
	
	private void updateMatrix(){
		long timeDelta = 0;
//		Date timeLeft = new Date(System.currentTimeMillis() - googleIO.getTime());
//		Log.d("Test",format.format(timeLeft));
		
//		int digit = getDigitNum(2,getDays(timeDelta));
		for(int i=0;i<NUM_DIGITS;i++)
			paintDigit(i,i+1,Palette.BLUE);
		
	}
	
	// Digits order is 2 1 0
	private int getDigitNum(int i,int number){
		return number / (number%(10^(i+1)));
	}
	
	private int getDays(long timeDelta){
		return (int) (timeDelta / MILLI_PER_DAY);
	}
	
	// Digits are left to right from 0->8
	private void paintDigit(int digitNum,int digitValue,int color){
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
		for(int c=0;c<COLON_COLS.length;c++){
			if(col >= COLON_COLS[c])
				col++;
		}
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

}
