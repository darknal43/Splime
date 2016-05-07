package server.models;

import entities.Player;

/**
 * Created by Hairuo on 2016-05-06.
 */
public class ProjectileModel extends GameModel {
    private float targetX;
    private float targetY;
    private float speed;
    private float size;
    private float xVelo;
    private float turnSpeed;
    private float yVelo;
    private Player player;
    private boolean returning;;

}
