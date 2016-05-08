package server.event;

import com.badlogic.gdx.math.Vector2;
import entities.GameEntity;
import entities.Player;
import entities.projectile.ProjectileBenign;

/**
 * Created by Hairuo on 2016-05-06.
 */
public class FireEvent extends Event {
    float x;
    float y;
    float charge;
    Player player;
    public FireEvent(int[] data) {
        super();
        this.x = data[0];
        this.y = data[1];
        this.player = (Player)game.getActorMap().get((long)data[2]);
        this.charge = data[3];
    }

    @Override
    public void execute() {
        ProjectileBenign projectile = new ProjectileBenign(player, charge, new Vector2(x, y));
        projectile.setKey(database.getAvailableKey());
        database.addModel(projectile.getModel());
        game.getActorMap().putIfAbsent(projectile.getKey(), projectile);
    }
}
