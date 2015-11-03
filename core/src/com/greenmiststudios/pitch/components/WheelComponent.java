package com.greenmiststudios.pitch.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.greenmiststudios.pitch.enums.WheelPosition;

/**
 * Created by geoffpowell on 11/2/15.
 */
public class WheelComponent implements Component {
    public WheelPosition wheelPosition;
    public RevoluteJoint wheelJoint;
}