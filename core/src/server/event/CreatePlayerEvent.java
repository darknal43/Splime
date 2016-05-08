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
        newPlayer.setKey(database.getAvailableKey());
        newPlayer.setX((float)(Math.random()*50000-100000));
        newPlayer.setY((float)(Math.random()*50000-100000));
        newPlayer.setSize(player.getSize()*2, player.getSize()*2);
        newPlayer.setDatabaseStructure(database);
        database.addModel(player);
        game.getActorMap().putIfAbsent(player.getKey(), newPlayer);

    }
}
