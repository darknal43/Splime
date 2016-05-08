
package server.event;

import server.models.PlayerModel;

/**
 * Created by Hairuo on 2016-05-07.
 */
public class LeaveGameEvent extends Event {
    PlayerModel player;
    @Override
    public void execute() {
        game.getActorMap().remove(player.getKey());
        database.getData().remove(player.getKey());
    }

    public LeaveGameEvent(PlayerModel player) {
        super();
        this.player = player;
    }
}
