package com.greenmiststudios.pitch.components;

import com.badlogic.ashley.core.Component;
import com.greenmiststudios.pitch.enums.Player;
import com.greenmiststudios.pitch.enums.Team;

/**
 * Created by geoffpowell on 11/2/15.
 */
public class TeamComponent implements Component {
    public Team team;
    public Player player;
}
