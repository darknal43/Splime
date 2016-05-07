package server.event;

import entities.Player;
import server.models.PlayerModel;

/**
 * Created by Hairuo on 2016-05-07.
 */
public class CreatePlayerEvent extends Event{
    private PlayerModel player;
    public CreatePlayerEvent(PlayerModel player) {
        super();

    }

    @Override
    public void execute() {
        Player newPlayer = new Player();
        newPlayer.setX(player.getX());
        newPlayer.setY(player.getY());
        newPlayer.setSize(player.getSize()*2, player.getSize()*2);

    }
}
