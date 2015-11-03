package com.greenmiststudios.pitch.box2d;

import java.util.Arrays;
import java.util.List;

/**
 * Created by geoffpowell on 9/23/15.
 */
public class CollisionFlag {
    public static final short GROUND = 0x1;
    public static final short WALL = 0x2;
    public static final short BALL = 0x4;
    public static final short CAR1 = 0x8;
    public static final short CAR2 = 0x16;
    public static final short CAR3 = 0x32;
    public static final short CAR4 = 0x64;
    public static final short CAR5 = 0x128;
    public static final short CAR6 = 0x256;
    public static final short CAR7 = 0x512;
    public static final short CAR8 = 0x1024;

    public static final short ALL = GROUND | WALL | BALL | CAR1 | CAR2 | CAR3 | CAR4 | CAR5 | CAR6 | CAR7 | CAR8;
    public static final List<Short> CARS = Arrays.asList(CAR1 , CAR2 , CAR3 , CAR4 , CAR5 , CAR6 , CAR7 , CAR8);
}
