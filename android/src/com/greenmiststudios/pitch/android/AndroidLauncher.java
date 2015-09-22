package com.greenmiststudios.pitch.android;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.greenmiststudios.pitch.PitchGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		PitchGame.DEBUG = BuildConfig.DEBUG;
		initialize(new PitchGame(), config);
	}
}
