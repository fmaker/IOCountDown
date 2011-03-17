package com.handycodeworks.iocountdown;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.examples.BaseExample;
import org.anddev.andengine.extension.physics.box2d.PhysicsConnector;
import org.anddev.andengine.extension.physics.box2d.PhysicsFactory;
import org.anddev.andengine.extension.physics.box2d.PhysicsWorld;
import org.anddev.andengine.extension.physics.box2d.util.Vector2Pool;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.BuildableTexture;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.sensor.accelerometer.AccelerometerData;
import org.anddev.andengine.sensor.accelerometer.IAccelerometerListener;

import android.graphics.Paint;
import android.hardware.SensorManager;
import android.widget.Toast;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;


public class Countdown extends BaseExample implements IAccelerometerListener, IOnSceneTouchListener {
	// ===========================================================
	// Constants
	// ===========================================================

	@Override
	public void runOnUpdateThread(Runnable pRunnable) {
		super.runOnUpdateThread(pRunnable);
	}

	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;

	private static final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);

	// ===========================================================
	// Fields
	// ===========================================================

//	private Texture mTexture;

	private TextureRegion mLtGreyBall;
	private TextureRegion mBlueBall;

	private PhysicsWorld mPhysicsWorld;

	private TimeDisplay mTime;
	
	@Override
	public Engine onLoadEngine() {
		Toast.makeText(this, "Touch the screen to add objects.", Toast.LENGTH_LONG).show();
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

		/* TextureRegions. */
		TextureRegionFactory.setAssetBasePath("balls/");
		this.mBlueBall = TextureRegionFactory.createFromAsset(blueTexture, this, "blue.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(blueTexture);
		this.mLtGreyBall = TextureRegionFactory.createFromAsset(ltGreyTexture, this, "grey3.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(ltGreyTexture);

//		this.enableAccelerometerSensor(this);
	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene(2);
		scene.setBackground(new ColorBackground(211, 211, 211));
		scene.setOnSceneTouchListener(this);

		this.mPhysicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_EARTH), false);
		
		// Time display
		this.mTime = new TimeDisplay(CAMERA_WIDTH,CAMERA_HEIGHT);
	    originX = CAMERA_WIDTH/2 - (TimeDisplay.NUM_X/2)*(TimeDisplay.PADDING + 2*TimeDisplay.RADIUS) + TimeDisplay.RADIUS + TimeDisplay.PADDING/2;
	    originY = CAMERA_HEIGHT/2 + (TimeDisplay.NUM_Y/2)*(TimeDisplay.RADIUS*2 + TimeDisplay.PADDING);

		final Shape ground = new Rectangle(0, CAMERA_HEIGHT - 2, CAMERA_WIDTH, 2);
		final Shape roof = new Rectangle(0, 0, CAMERA_WIDTH, 2);
		final Shape left = new Rectangle(0, 0, 2, CAMERA_HEIGHT);
		final Shape right = new Rectangle(CAMERA_WIDTH - 2, 0, 2, CAMERA_HEIGHT);

		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f);
		PhysicsFactory.createBoxBody(this.mPhysicsWorld, ground, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mPhysicsWorld, roof, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mPhysicsWorld, left, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mPhysicsWorld, right, BodyType.StaticBody, wallFixtureDef);

		scene.getFirstChild().attachChild(ground);
		scene.getFirstChild().attachChild(roof);
		scene.getFirstChild().attachChild(left);
		scene.getFirstChild().attachChild(right);

		scene.registerUpdateHandler(this.mPhysicsWorld);

		return scene;
	}

	@Override
	public void onLoadComplete() {
	    loadDots();
	}

	@Override
	public boolean onSceneTouchEvent(final Scene pScene, final TouchEvent pSceneTouchEvent) {
//		if(this.mPhysicsWorld != null) {
//			if(pSceneTouchEvent.isActionDown()) {
//				this.addBall(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
//				return true;
//			}
//		}
		return false;
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
//				canvas.drawCircle(dotX, dotY, TimeDisplay.RADIUS, paint);
				addBall(dotX, dotY, mTime.mDotMatrix[i][j]);
				dotX += TimeDisplay.RADIUS*2 + TimeDisplay.PADDING;
			}
			dotY -= TimeDisplay.RADIUS*2 + TimeDisplay.PADDING;
		}
		
		// Center reference point
//		paint.setColor(Color.BLACK);
//		canvas.drawCircle(centerX, centerY, 1, paint);
	}
	
	protected void addBall(final float pX, final float pY, int ballColor) {
		final Scene scene = this.mEngine.getScene();

		final Sprite face;
		final Body body;

		TextureRegion ballTexture = null;
		switch(ballColor){
		case Palette.LT_GREY:
			ballTexture = mLtGreyBall;
			break;
		case Palette.BLUE:
			ballTexture = mBlueBall;
			break;
		}
		face = new Sprite(pX, pY, ballTexture);
		body = PhysicsFactory.createCircleBody(this.mPhysicsWorld, face, BodyType.StaticBody, FIXTURE_DEF);

		scene.getLastChild().attachChild(face);
		this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(face, body, true, true));
	}
}