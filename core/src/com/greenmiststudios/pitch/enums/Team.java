package com.greenmiststudios.pitch.enums;

import com.badlogic.gdx.graphics.*;


/**
 * Created by geoffpowell on 9/30/15.
 */
public enum Team {
    RED(Color.RED), BLUE(Color.BLUE);

    public Color primaryColor = Color.WHITE;

    Team(Color primaryColor) {
        this.primaryColor = primaryColor;
    }
}
