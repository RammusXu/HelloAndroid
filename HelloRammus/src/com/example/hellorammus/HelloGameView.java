package com.example.hellorammus;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class HelloGameView extends SurfaceView {
	protected static final String TAG = "HelloGameView";
	private SurfaceHolder _holder;
	private HelloGameThread _gameThread;

	private ArrayList<HelloGameSprite> sprites;
	private List<HelloGameTempSprite> temps = new ArrayList<HelloGameTempSprite>();
	private Bitmap bmpBlood;

	public HelloGameView(Context context) {
		super(context);
		_gameThread = new HelloGameThread(this);

		this.bmpBlood = BitmapFactory.decodeResource(getResources(),
				R.drawable.blood);

		sprites = new ArrayList<HelloGameSprite>();
		sprites.add(new HelloGameSprite(HelloGameView.this,
				createSprite(R.drawable.sprite)));
		sprites.add(new HelloGameSprite(HelloGameView.this,
				createSprite(R.drawable.sprite2)));
		sprites.add(new HelloGameSprite(HelloGameView.this,
				createSprite(R.drawable.sprite3)));
		sprites.add(new HelloGameSprite(HelloGameView.this,
				createSprite(R.drawable.sprite4)));

		_holder = getHolder();
		_holder.addCallback(new SurfaceHolder.Callback() {

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				try {
					_gameThread.setRunning(true);
					_gameThread.start();
				} catch (Exception e) {
					Log.e(TAG, e.toString());
				}
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				boolean bIfRetry = true;
				_gameThread.setRunning(false);
				while (bIfRetry) {
					try {
						_gameThread.join();

					} catch (Exception e) {
						Log.e(TAG, e.toString());
					}
					bIfRetry = false;
				}
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {

			}
		});
	}

	private Bitmap createSprite(int resource) {
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
		return bmp;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		synchronized (getHolder()) {
			for (int i = 0; i < sprites.size(); i++) {
				HelloGameSprite s = sprites.get(i);
				if (s.isTouched(event.getX(), event.getY())) {
					temps.add(new HelloGameTempSprite(temps, this, event.getX(), event.getY(),
							bmpBlood));
					sprites.remove(i);

					if (sprites.size() == 1) {
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								resetAllSprites();
							}
						}, 1000);
					}

					break;
				}
			}
		}

		return super.onTouchEvent(event);
	}

	private void resetAllSprites() {
		sprites = new ArrayList<HelloGameSprite>();
		sprites.add(new HelloGameSprite(HelloGameView.this,
				createSprite(R.drawable.sprite)));
		sprites.add(new HelloGameSprite(HelloGameView.this,
				createSprite(R.drawable.sprite2)));
		sprites.add(new HelloGameSprite(HelloGameView.this,
				createSprite(R.drawable.sprite3)));
		sprites.add(new HelloGameSprite(HelloGameView.this,
				createSprite(R.drawable.sprite4)));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);

		for (HelloGameSprite s : sprites) {
			s.onDraw(canvas);
		}

		for (int i = temps.size() - 1; i >= 0; i--) {
			temps.get(i).onDraw(canvas);
		}

		super.onDraw(canvas);
	}

}
