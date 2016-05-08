package entities.projectile;

import com.badlogic.gdx.math.Vector2;
import entities.Player;
import server.models.GameModel;
import server.models.ProjectileAggressiveModel;
import tools.ServerTools.databases.DatabaseStructure;

/**
 *
 *
 * Created by hongy on 5/7/2016.
 */
public class ProjectileAggressive extends Player {
    private Player mainPlayer;

    public ProjectileAggressive(Player mainPlayer, float x, float y, DatabaseStructure structure){
        super(x, y, structure);
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

    @Override
    public GameModel getModel() {
        ProjectileAggressiveModel projectileAggressiveModel = new ProjectileAggressiveModel();
        projectileAggressiveModel.setKey(getKey());
        projectileAggressiveModel.setX(getX());
        projectileAggressiveModel.setY(getY());
        return projectileAggressiveModel;
    }
}

