package tools;

import box2dLight.RayHandler;

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