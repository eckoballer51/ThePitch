package com.greenmiststudios.pitch;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.greenmiststudios.pitch.gamestate.GameState;
import com.greenmiststudios.pitch.gamestate.GameStateManager;
import com.greenmiststudios.pitch.gamestate.MainGameState;

public class PitchGame extends ApplicationAdapter {
	SpriteBatch batch;
    GameStateManager manager;
    public static boolean DEBUG = false;

	@Override
	public void create () {
		batch = new SpriteBatch();
        manager = GameStateManager.getInstance();
        manager.setSpriteBatch(batch);
        manager.addGameState(new MainGameState());
        manager.create();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        GameStateManager.getInstance().render();
	}

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        GameStateManager.getInstance().resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
        GameStateManager.getInstance().pause();
    }

    @Override
    public void resume() {
        super.resume();
        GameStateManager.getInstance().resume();
    }
}
