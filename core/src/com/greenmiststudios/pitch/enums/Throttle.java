package com.greenmiststudios.pitch.enums;

import java.util.Arrays;
import java.util.List;

/**
 * Created by geoffpowell on 9/22/15.
 */
public enum Throttle {
    NONE, FORWARD, BACKWARD;

    public static List<Throttle> BOTH = Arrays.asList(FORWARD, BACKWARD);
}
