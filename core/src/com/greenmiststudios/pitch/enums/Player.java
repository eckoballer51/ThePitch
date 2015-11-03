package com.greenmiststudios.pitch.enums;

import com.badlogic.gdx.Input;

/**
 * Created by geoffpowell on 11/2/15.
 */
public enum Player {
    PLAYER1(Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D),
    PLAYER2(Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D),
    PLAYER3(Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D),
    PLAYER4(Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D),
    AI(-1, -2, -3, -4);

    public int accelerate;
    public int decelerate;
    public int left;
    public int right;

    Player(int a, int d, int l, int r) {
        accelerate = a;
        decelerate = d;
        left = l;
        right = r;
    }
}
