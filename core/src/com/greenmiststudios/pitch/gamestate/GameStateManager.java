package com.greenmiststudios.pitch.gamestate;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by geoffpowell on 9/21/15.
 */
public class GameStateManager extends ApplicationAdapter {
    Map<String, GameState> gameStates;
    private SpriteBatch spriteBatch;

    private GameState currentState;
    private GameState transitionState;
    private boolean wasCreated;

    private static GameStateManager instance;

    public GameStateManager() {
        gameStates = new HashMap<String, GameState>();
    }

    public static GameStateManager getInstance() {
        if (instance == null) instance = new GameStateManager();
        return instance;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void addGameState(GameState gameState)
    {
        if (gameState == null) return;
        if (gameStates.get(gameState.getTag()) != null) {
            System.out.println(String.format("GameState (%s) Already Exists", gameState.getTag()));
            return;
        }
        if (wasCreated) gameState.create();

        gameState.setManager(this);
        if (currentState == null) currentState = gameState;
        gameStates.put(gameState.getTag(), gameState);
    }

    public void setSpriteBatch(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    @Override
    public void create() {
        super.create();
        for (GameState state : gameStates.values()) {
            if (!state.wasCreated()) state.create();
        }
        wasCreated = true;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        if (currentState == null) return;
        currentState.resize(width, height);
    }

    @Override
    public void render() {
        super.render();
        if (currentState == null) return;
        currentState.render();
        if (currentState.needsUpdate) currentState.update(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void pause() {
        super.pause();
        if (currentState == null) return;
        currentState.pause();
    }

    @Override
    public void resume() {
        super.resume();
        if (currentState == null) return;
        currentState.resume();
    }

}
