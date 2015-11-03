package com.greenmiststudios.pitch.entity.car;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.greenmiststudios.pitch.enums.Steering;
import com.greenmiststudios.pitch.enums.Team;
import com.greenmiststudios.pitch.enums.Throttle;

/**
 * Created by geoffpowell on 9/22/15.
 */
public class PlayerCar extends Car {

    public static final String TAG = "Player";

    private PlayerInputAdapter input;

    public PlayerCar(Team team) {
        super(team, TAG);
    }

    @Override
    public void create() {
        super.create();
        input = new PlayerInputAdapter();
        Gdx.input.setInputProcessor(input);
    }

    class PlayerInputAdapter extends InputAdapter {
        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            if (screenX < Gdx.graphics.getWidth()/ 3) {
                steering.remove(Steering.LEFT);
            } else if (screenX < Gdx.graphics.getWidth()/ 3 * 2) {
                throttle.remove(Throttle.FORWARD);
            } else if (screenX < Gdx.graphics.getWidth()) {
                steering.remove(Steering.RIGHT);
            }
            return true;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            if (screenX < Gdx.graphics.getWidth()/3) {
                steering.add(Steering.LEFT);
            } else if (screenX < Gdx.graphics.getWidth()/ 3 * 2) {
                throttle.add(Throttle.FORWARD);
            } else if (screenX < Gdx.graphics.getWidth()) {
                steering.add(Steering.RIGHT);
            }
            return true;
        }

        @Override
        public boolean keyDown(int keycode) {
            switch (keycode) {
                case Input.Keys.W:
                    throttle.add(Throttle.FORWARD);
                    break;
                case Input.Keys.S:
                    throttle.add(Throttle.BACKWARD);
                    break;
                case Input.Keys.A:
                    steering.add(Steering.LEFT);
                    break;
                case Input.Keys.D:
                    steering.add(Steering.RIGHT);
                    break;
                case Input.Keys.SPACE:
                    for (Wheel wheel : wheels){
                        wheel.setTraction(0.25f);
                    }
                    break;
            }

            return true;
        }

        @Override
        public boolean keyUp(int keycode) {
            switch (keycode) {
                case Input.Keys.W:
                    throttle.remove(Throttle.FORWARD);
                    break;
                case Input.Keys.S:
                    throttle.remove(Throttle.BACKWARD);
                    break;
                case Input.Keys.A:
                    steering.remove(Steering.LEFT);
                    break;
                case Input.Keys.D:
                    steering.remove(Steering.RIGHT);
                    break;
                case Input.Keys.SPACE:
                    for (Wheel wheel : wheels){
                        wheel.setTraction(0.8f);
                    }
                    break;
            }
            return true;
        }
    }


}
