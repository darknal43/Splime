package server.event;

import entities.GameEntity;

/**
 * Created by Hairuo on 2016-05-06.
 */
public class FireEvent extends Event {
    float x;
    float y;
    GameEntity player;
    public FireEvent(int[] data) {
        super();
        this.x = data[0];
        this.y = data[1];
        this.player = game.getActorMap().get((long)data[0]);
    }

    @Override
    public void execute() {


    }
}
