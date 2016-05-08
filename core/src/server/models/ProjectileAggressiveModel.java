package server.models;

import entities.projectile.ProjectileAggressive;

/**
 * Created by hongy on 5/8/2016.
 */
public class ProjectileAggressiveModel extends GameModel{
    int playerKey;
    private String className = ProjectileAggressive.class.getName();
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
    float x, y;

    public int getPlayerKey() {
        return playerKey;
    }

    public void setPlayerKey(int playerKey) {
        this.playerKey = playerKey;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
