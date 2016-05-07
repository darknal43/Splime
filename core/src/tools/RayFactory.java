package tools;

import box2dLight.RayHandler;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Hairuo on 2016-05-06.
 */
public class RayFactory {

    private static RayHandler rayHandler;

    public static RayHandler getRayHandler() {
        if (rayHandler == null) {
            rayHandler = new RayHandler(WorldFactory.getWorld());
        }
        return rayHandler;
    }
}