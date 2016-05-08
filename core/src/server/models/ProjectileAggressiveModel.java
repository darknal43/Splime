package server.models;

/**
 * Created by hongy on 5/8/2016.
 */
public class ProjectileAggressiveModel extends GameModel{
    int playerKey;

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
