package com.greenmiststudios.pitch.enums;

import java.util.Arrays;
import java.util.List;

/**
 * Created by geoffpowell on 9/23/15.
 */
public enum WheelPosition {
    LEFTFRONT, RIGHTFRONT, LEFTREAR, RIGHTREAR;

    private static List<WheelPosition> FRONT = Arrays.asList(LEFTFRONT, RIGHTFRONT);
    private static List<WheelPosition> REAR =  Arrays.asList(LEFTREAR, RIGHTREAR);
    private static List<WheelPosition> LEFT =  Arrays.asList(LEFTREAR, LEFTFRONT);
    private static List<WheelPosition> RIGHT =  Arrays.asList(RIGHTREAR, RIGHTFRONT);

    public boolean isRear() {
        return REAR.contains(this);
    }

    public boolean isFront() {
        return FRONT.contains(this);
    }

    public boolean isLeft() {
        return LEFT.contains(this);
    }

    public boolean isRight() {
        return RIGHT.contains(this);
    }
}
