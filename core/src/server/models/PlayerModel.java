package server.models;
import entities.Player;

/**
 *
 * Created by Hairuo on 2016-05-06.
 */
public class PlayerModel extends GameModel {

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    private String className = Player.class.getName();

    private float x, y;

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
