package com.greenmiststudios.pitch;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.greenmiststudios.pitch.entity.CarEntity;
import com.greenmiststudios.pitch.enums.Player;
import com.greenmiststudios.pitch.gamestate.GameStateManager;
import com.greenmiststudios.pitch.gamestate.MainGameState;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.greenmiststudios.pitch.systems.CameraSystem;
import com.greenmiststudios.pitch.systems.PhysicsSystem;
import com.greenmiststudios.pitch.systems.CarSystem;
import com.greenmiststudios.pitch.systems.SimpleRenderingSystem;


public class PitchGame extends ApplicationAdapter {
	SpriteBatch batch;
    GameStateManager manager;
    public static boolean DEBUG = false;
    public static AssetManager ASSETMANAGER = new AssetManager();
    private Engine engine;

	@Override
	public void create () {
        PitchGame.ASSETMANAGER.load("field.jpg", Texture.class);
        PitchGame.ASSETMANAGER.load("arrow.png", Texture.class);
        PitchGame.ASSETMANAGER.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader( new InternalFileHandleResolver()));
        PitchGame.ASSETMANAGER.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader( new InternalFileHandleResolver()));

        FreeTypeFontLoaderParameter params = new FreeTypeFontLoaderParameter();
        params.fontFileName = "walkway_bold.ttf";
        params.fontParameters.size = 20;

        PitchGame.ASSETMANAGER.load("walkway_bold.ttf", BitmapFont.class, params);

        PitchGame.ASSETMANAGER.finishLoading();
		batch = new SpriteBatch();
        manager = GameStateManager.getInstance();
        manager.setSpriteBatch(batch);
        manager.addGameState(new MainGameState());
        manager.create();
        engine = new Engine();
        engine.addSystem(new CameraSystem());
        engine.addSystem(new PhysicsSystem(new World(Vector2.Zero, true)));
        engine.addSystem(new SimpleRenderingSystem(new SpriteBatch()));
        engine.addSystem(new CarSystem());
        engine.addEntity(new CarEntity(Player.PLAYER1));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        engine.update(Gdx.graphics.getDeltaTime());
        //GameStateManager.getInstance().render();
	}

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        //GameStateManager.getInstance().resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
        //GameStateManager.getInstance().pause();
    }

    @Override
    public void resume() {
        super.resume();
        //GameStateManager.getInstance().resume();
    }
}
