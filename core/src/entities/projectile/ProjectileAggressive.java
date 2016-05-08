package entities.projectile;

import com.badlogic.gdx.math.Vector2;
import entities.Player;

/**
 *
 *
 * Created by hongy on 5/7/2016.
 */
public class ProjectileAggressive extends Player {
    private Player mainPlayer;

    public ProjectileAggressive(Player mainPlayer, float x, float y){
        super(x, y, 0, 0);
        setScale(0.5F);
        this.mainPlayer = mainPlayer;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setTargetLocation(mainPlayer.getX(), mainPlayer.getY());
        despawn();
    }

    private void despawn(){

        if (Math.abs(new Vector2(targetLocation).sub(currentLocation).len()) <= 10){
            this.remove();
        }
    }
}

