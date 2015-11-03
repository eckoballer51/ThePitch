package com.greenmiststudios.pitch.enums;

import java.util.Arrays;
import java.util.List;

/**
 * Created by geoffpowell on 9/23/15.
 */
public enum Steering {
    NONE, LEFT, RIGHT;

    public static List<Steering> BOTH = Arrays.asList(LEFT, RIGHT);
}
