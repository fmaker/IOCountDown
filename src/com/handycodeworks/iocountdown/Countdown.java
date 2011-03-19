package com.handycodeworks.iocountdown;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.examples.BaseExample;
import org.anddev.andengine.extension.physics.box2d.PhysicsConnector;
import org.anddev.andengine.extension.physics.box2d.PhysicsFactory;
import org.anddev.andengine.extension.physics.box2d.PhysicsWorld;
import org.anddev.andengine.extension.physics.box2d.util.Vector2Pool;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.sensor.accelerometer.AccelerometerData;
import org.anddev.andengine.sensor.accelerometer.IAccelerometerListener;

import android.hardware.SensorManager;
import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.handycodeworks.iocountdown.TimeDisplay.TimeChangedListener;


public class Countdown extends BaseExample implements IAccelerometerListener, TimeChangedListener, IPositionChangedListener{
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;
	
	private static final int LOGO_WIDTH = 287;
	private static final int LOGO_HEIGHT = 70;

	private static final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);

	// ===========================================================
	// Fields
	// ===========================================================

//	private Texture mTexture;

	Texture mTexture;
	private TextureRegion mLtGreyBall, mBlueBall, mDarkGreyBall, mCyanBall, mRedBall, mGreenBall, logoTextureRegion;
	private Sprite[][] mBallMatrix = new Sprite[TimeDisplay.NUM_X][TimeDisplay.NUM_Y];

	private PhysicsWorld mPhysicsWorld;

	private TimeDisplay mTime;
	
	@Override
	public Engine onLoadEngine() {
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		engineOptions.getTouchOptions().setRunOnUpdateThread(true);
		return new Engine(engineOptions);
	}

	@Override
	public void onLoadResources() {
		/* Textures. */
		Texture blueTexture = new Texture(16, 16, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		Texture ltGreyTexture = new Texture(16, 16, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		Texture darkGreyTexture = new Texture(16, 16, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		Texture cyanTexture = new Texture(16, 16, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		Texture redTexture = new Texture(16, 16, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		Texture greenTexture = new Texture(16, 16, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		/* TextureRegions. */
		TextureRegionFactory.setAssetBasePath("balls/");
		this.mBlueBall = TextureRegionFactory.createFromAsset(blueTexture, this, "blue.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(blueTexture);
		this.mLtGreyBall = TextureRegionFactory.createFromAsset(ltGreyTexture, this, "grey3.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(ltGreyTexture);
		this.mDarkGreyBall = TextureRegionFactory.createFromAsset(ltGreyTexture, this, "grey2.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(darkGreyTexture);
		this.mCyanBall = TextureRegionFactory.createFromAsset(cyanTexture, this, "cyan.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(cyanTexture);
		this.mRedBall = TextureRegionFactory.createFromAsset(redTexture, this, "red.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(redTexture);
		this.mGreenBall = TextureRegionFactory.createFromAsset(greenTexture, this, "green.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(greenTexture);

		this.enableAccelerometerSensor(this);
	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene(2);
		scene.setBackground(new ColorBackground(211, 211, 211));

		this.mPhysicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_EARTH), false);
		
		// Time display
		this.mTime = new TimeDisplay(CAMERA_WIDTH,CAMERA_HEIGHT);
		mTime.setTimeChangedListener(this);
		mTime.start();
	    originX = CAMERA_WIDTH/2 - (TimeDisplay.NUM_X/2)*(TimeDisplay.PADDING + 2*TimeDisplay.RADIUS) - TimeDisplay.RADIUS;// + TimeDisplay.PADDING/2;
	    originY = CAMERA_HEIGHT/2 + (TimeDisplay.NUM_Y/2)*(TimeDisplay.RADIUS*2 + TimeDisplay.PADDING);

		final Shape ground = new Rectangle(0, CAMERA_HEIGHT - 2, CAMERA_WIDTH, 2);
		final Shape roof = new Rectangle(0, 0, CAMERA_WIDTH, 2);
		final Shape left = new Rectangle(0, 0, 2, CAMERA_HEIGHT);
		final Shape right = new Rectangle(CAMERA_WIDTH - 2, 0, 2, CAMERA_HEIGHT);

		//final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f);
		//PhysicsFactory.createBoxBody(this.mPhysicsWorld, ground, BodyType.StaticBody, wallFixtureDef);
		//PhysicsFactory.createBoxBody(this.mPhysicsWorld, roof, BodyType.StaticBody, wallFixtureDef);
		//PhysicsFactory.createBoxBody(this.mPhysicsWorld, left, BodyType.StaticBody, wallFixtureDef);
		//PhysicsFactory.createBoxBody(this.mPhysicsWorld, right, BodyType.StaticBody, wallFixtureDef);

		scene.getFirstChild().attachChild(ground);
		scene.getFirstChild().attachChild(roof);
		scene.getFirstChild().attachChild(left);
		scene.getFirstChild().attachChild(right);

		scene.registerUpdateHandler(this.mPhysicsWorld);

		return scene;
	}

	@Override
	public void onLoadComplete() {
		// I/O logo
		mTexture = new Texture(512, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		TextureRegionFactory.setAssetBasePath("gfx/");
		final TextureRegion logoTextureRegion = TextureRegionFactory.createFromAsset(mTexture, this, "io_logo.png", 0, 0);
		mEngine.getTextureManager().loadTexture(mTexture);
		final Scene scene = this.mEngine.getScene();

		runOnUpdateThread(new Runnable(){
			@Override
			public void run() {
				scene.getLastChild().attachChild(new Sprite(CAMERA_WIDTH/2-LOGO_WIDTH/2, TimeDisplay.PADDING*15, logoTextureRegion));
			}
			
		});
		
	    loadDots();
	}

	@Override
	public void onAccelerometerChanged(final AccelerometerData pAccelerometerData) {
		final Vector2 gravity = Vector2Pool.obtain(pAccelerometerData.getY(), pAccelerometerData.getX());
		this.mPhysicsWorld.setGravity(gravity);
		Vector2Pool.recycle(gravity);
	}
	
	private int originX = 0, originY = 0;
	
	private void loadDots(){

		int dotX = originX,dotY = originY;
		for(int j=0;j<TimeDisplay.NUM_Y;j++){
			dotX = originX;
			for(int i=0;i<TimeDisplay.NUM_X;i++){
				Sprite b = addBall(dotX, dotY, mTime.mDotMatrix[i][j]);
				mBallMatrix[i][j] = b; // Save reference for changing later
				dotX += TimeDisplay.RADIUS*2 + TimeDisplay.PADDING;
			}
			dotY -= TimeDisplay.RADIUS*2 + TimeDisplay.PADDING;
		}
	}
	
	protected synchronized Sprite addBall(final float pX, final float pY, int ballColor) {
		final Scene scene = this.mEngine.getScene();

		final NotifySprite ball;

		TextureRegion ballTexture = null;
		switch(ballColor){
		case Palette.GREEN:
			ballTexture = mGreenBall;
			break;
		case Palette.RED:
			ballTexture = mRedBall;
			break;
		case Palette.CYAN:
			ballTexture = mCyanBall;
			break;
		case Palette.DARK_GREY:
			ballTexture = mDarkGreyBall;
			break;
		case Palette.LT_GREY:
			ballTexture = mLtGreyBall;
			break;
		case Palette.BLUE:
			ballTexture = mBlueBall;
			break;
		case Palette.CLEAR:
		default:
			// Don't draw anything
			return null;
		}
		ball = new NotifySprite(pX, pY, ballTexture, this);
		ball.setUserData(ballColor); // Add ball color for update usage

		scene.getLastChild().attachChild(ball);
		return ball;
	}

	@Override
	public synchronized void OnTimeChangedListener() {
		final Scene scene = this.mEngine.getScene();
		for(int i=0;i<TimeDisplay.NUM_X;i++){
			for(int j=0;j<TimeDisplay.NUM_Y;j++){
				if(mBallMatrix[i][j]!=null){ // Clear sprites are null
					final int currColor = (Integer) mBallMatrix[i][j].getUserData();
					final int newColor = mTime.mDotMatrix[i][j];
					if(currColor != newColor){
//						Log.d("IO",String.format("Ball[%d][%d] has changed from %d to %d",i,j,(Integer) mBallMatrix[i][j].getUserData(),mTime.mDotMatrix[i][j]));
						
						// Existing sprite
						final Sprite ball = mBallMatrix[i][j];
						
						// Remove existing sprite from world
						runOnUpdateThread(new Runnable(){
							@Override
							public void run(){
								
								// See ball free
								if(currColor != Palette.LT_GREY){
									final Body body = PhysicsFactory.createCircleBody(mPhysicsWorld, ball, BodyType.DynamicBody, FIXTURE_DEF);
									mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(ball, body, true, true));
//									mFreeBalls.add(ball);
								}
								else{
									scene.getLastChild().detachChild(ball);
								}
//								mPhysicsWorld.unregisterPhysicsConnector(ballConnector);
								//mPhysicsWorld.destroyBody(ballConnector.getBody());
							}
						});

						// Add new sprite
						Sprite newBall = addBall(ball.getX(),ball.getY(),newColor);
						newBall.setZIndex(1);
						mBallMatrix[i][j]=newBall;
					}
				}
			}
		}
	}

	@Override
	public void onPositionChanged(Sprite s, float posX, float posY) {
		if(posX > CAMERA_WIDTH || posX < 0 ||
		   posY > CAMERA_HEIGHT || posY < 0){
			final Scene scene = this.mEngine.getScene();
			final PhysicsConnector ballConnector = mPhysicsWorld.getPhysicsConnectorManager().findPhysicsConnectorByShape(s);
			mPhysicsWorld.unregisterPhysicsConnector(ballConnector);
			mPhysicsWorld.destroyBody(ballConnector.getBody());
			scene.getLastChild().detachChild(s);
		}
	}
}
