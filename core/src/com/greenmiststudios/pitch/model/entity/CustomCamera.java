package com.greenmiststudios.pitch.model.entity;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.*;

/**
 * Created by geoffpowell on 10/5/15.
 */
public class CustomCamera extends OrthographicCamera {

    boolean yDown;

    @Override
    public void setToOrtho(boolean yDown, float viewportWidth, float viewportHeight) {
        super.setToOrtho(yDown, viewportWidth, viewportHeight);
        this.yDown = yDown;
    }

    public void setAngle(float radians) {
        int direction = yDown ? -1 : 1;
        Vector3 rotated = new Vector3(MathUtils.cos(radians), direction * MathUtils.sin(radians), 0);
        if (!rotated.epsilonEquals(rotated, MathUtils.FLOAT_ROUNDING_ERROR)) return;
        up.set(rotated);
    }

    public void setLerpAngle(float radians, float delta) {
        int direction = yDown ? -1 : 1;
        radians += Math.PI/2;
        Vector3 rotated = new Vector3(MathUtils.cos(radians), direction * MathUtils.sin(radians), 0);
        if (!rotated.epsilonEquals(rotated, MathUtils.FLOAT_ROUNDING_ERROR)) return;
        up.lerp(rotated, delta * 6f);
    }


    public float getAngle() {
        return MathUtils.atan2(up.y, up.x);
    }

    @Override
    public void update() {
        super.update();
    }
}
