package com.greenmiststudios.pitch.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.greenmiststudios.pitch.enums.Direction;

/**
 * Created by geoffpowell on 9/22/15.
 */
public class PlayerCar extends Car {

    private PlayerInputAdapter input;

    @Override
    public void create() {
        super.create();
        input = new PlayerInputAdapter();
        Gdx.input.setInputProcessor(input);
    }

    class PlayerInputAdapter extends InputAdapter {
        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return super.touchDragged(screenX, screenY, pointer);
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {

            return super.touchUp(screenX, screenY, pointer, button);
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            return super.touchDown(screenX, screenY, pointer, button);
        }

        @Override
        public boolean keyUp(int keycode) {
            switch (keycode) {
                case Input.Keys.W:
                    direction &= Direction.FORWARD;
                    break;
                case Input.Keys.S:
                    direction &= Direction.BACKWARD;
                    break;
                case Input.Keys.A:
                    direction &= Direction.LEFT;
                    break;
                case Input.Keys.D:
                    direction &= Direction.RIGHT;
                    break;
            }
            return super.keyUp(keycode);
        }

        @Override
        public boolean keyDown(int keycode) {
            switch (keycode) {
                case Input.Keys.W:
                    direction |= Direction.FORWARD;
                    break;
                case Input.Keys.S:
                    direction |= Direction.BACKWARD;
                    break;
                case Input.Keys.A:
                    direction |= Direction.LEFT;
                    break;
                case Input.Keys.D:
                    direction |= Direction.RIGHT;
                    break;
            }

            return super.keyDown(keycode);
        }
    }


}
