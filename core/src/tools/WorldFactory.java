package tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * This stores the Box2D world for our game
 *
 * Created by Hongyu Wang on 5/5/2016.
 */
public final class WorldFactory {
    private static World world;

    public static World getWorld(){
        if (world == null){
            world = new World(new Vector2(), true);
        }
        return world;
    }

}
