package tools;

import entities.Player;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by Hairuo on 2016-05-06.
 */
public class Utils {

    /**
     * Calculates the optimal angle to your target points, and adjusts your previous angle towards it
     *
     * @param targetX x coordinate of your target
     * @param targetY y coordinate of your target
     * @param centerX x coordinate of your center
     * @param centerY y coordinate of you center
     * @param currentTrajectory trajectory to be adjusted
     * @param turnSpeed speed at which is the trajectory should be adjusted
     * @return
     */
    public static float track(float targetX, float targetY, float centerX, float centerY, float currentTrajectory,
                              float turnSpeed){

        float xDistance = targetX - centerX;
        float yDistance = targetY - centerY;
        float targetTrajectory = (float)(Math.toDegrees(Math.atan2(yDistance, xDistance))+90+360)%360;

        if(Math.abs(currentTrajectory - targetTrajectory) < turnSpeed){
            currentTrajectory = targetTrajectory;
        }else{

            double difference = currentTrajectory - targetTrajectory;
            double other_difference = 360 - Math.abs(difference);

            if(Math.abs(other_difference) < Math.abs(difference)){
                targetTrajectory += Math.signum(difference)*360;
            }

            if (currentTrajectory < targetTrajectory){
                currentTrajectory += turnSpeed;
            }else{
                currentTrajectory -= turnSpeed;
            }
        }

        currentTrajectory = currentTrajectory%360;

        return currentTrajectory;
    }

    public static <T> List<T> newList(){
        return new ArrayList<T>();
    }

    public static <K, V> Map<K, V> newMap(){
        return new Hashtable<K, V>();
    }
}
