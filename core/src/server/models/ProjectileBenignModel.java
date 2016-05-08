package server.models;

/**
 *
 * Created by hongy on 5/8/2016.
 */
public class ProjectileBenignModel extends GameModel{
    float chargeAmount;
    int playerKey;
    float targetX;
    float targetY;
    float x;
    private String className = ProjectileBenignModel.class.getName();
    float y;

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


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
    public float getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(float chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public int getPlayerKey() {
        return playerKey;
    }

    public void setPlayerKey(int playerKey) {
        this.playerKey = playerKey;
    }

    public float getTargetX() {
        return targetX;
    }

    public void setTargetX(float targetX) {
        this.targetX = targetX;
    }

    public float getTargetY() {
        return targetY;
    }

    public void setTargetY(float targetY) {
        this.targetY = targetY;
    }
}
