package server.event;

import entities.Player;

/**
 * Created by Hairuo on 2016-05-06.
 */
public class MoveEvent extends Event {
    private float xTar;
    private float yTar;
    Player player;

    public MoveEvent(int[] data) {
        super();
        this.xTar = data[1];
        this.yTar = data[0];
        this.player = (Player)game.getActorMap().get((long)data[2]);

    }

    @Override
    public void execute() {
        player.setMouseLocation(xTar, yTar);
    }
}
