package com.greenmiststudios.pitch.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by geoffpowell on 10/30/15.
 */
public class RenderOrderComponent implements Component {

    //Layer ordering: 0:base | 10:front
    public int primaryOrder;
    public int secondaryOrder;

}
