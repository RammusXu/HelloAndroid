package com.example.hellorammus;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class HelloGameSprite {
	private HelloGameView _gameView;
	private Bitmap _bitmap;

	private int x = 0;
	private int speedX = 10;
	private int y = 0;
	private int speedY = 10;
	private int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
	private static final int MAX_SPEED = 5;

	private int intROWs = 4;
	private int intCOLUMNs = 3;
	private int _width;
	private int _height;
	private int currentFrame = 0;
	private int flagDirection = 1;

	public HelloGameSprite(HelloGameView _gameView, Bitmap _bitmap) {
		this._gameView = _gameView;
		this._bitmap = _bitmap;
		this._width = _bitmap.getWidth() / intCOLUMNs;
		this._height = _bitmap.getHeight() / intROWs;
		this.speedX = getRand(10, 21);
		Random rnd = new Random();
		this.speedX = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
		this.speedY = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
	}

	private void update() {
		// Set X,Y
		if (x > (_gameView.getWidth() - _width) || x < 0) {
			speedX = -speedX;
		}
		x = x + speedX;
		if (y > (_gameView.getHeight() - this._height) || y + speedY <= 0) {
			speedY = -speedY;
		}
		y = y + speedY;

		currentFrame = ++currentFrame % intCOLUMNs;

		if (speedX > 0) {
			flagDirection = 2;
		} else {
			flagDirection = 1;
		}
	}

	public boolean isTouched(float x2, float y2) {
		return x2 > x && x2 < (x + this._width) && y2 > y
				&& y2 < (y + this._height);

	}

	public void onDraw(Canvas canvas) {
		update();

		int srcX = currentFrame * this._width; // 0,1,2
		int srcY = getAnimationRow() * this._height;
		Rect src = new Rect(srcX, srcY, srcX + this._width, srcY + this._height);
		Rect dst = new Rect(x, y, x + this._width, y + this._height);
		canvas.drawBitmap(_bitmap, src, dst, null);

	}

	private int getRand(int min, int max) {
		// 0,1,2
		// int min = 0;
		// int max = 3;
		Random random = new Random();
		int randomNumber = random.nextInt(max - min) + min;
		return randomNumber;
	}

	private int getAnimationRow() {
		double dirDouble = (Math.atan2(speedX, speedY) / (Math.PI / 2) + 2);
		int direction = (int) Math.round(dirDouble) % intROWs;
		return DIRECTION_TO_ANIMATION_MAP[direction];
	}

}
