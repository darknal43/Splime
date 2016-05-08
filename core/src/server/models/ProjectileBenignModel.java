package server.models;

/**
 * Created by hongy on 5/8/2016.
 */
public class ProjectileBenignModel extends GameModel{
    float chargeAmount;
    int playerKey;
    int targetX;
    int targetY;

    private String className = ProjectileBenignModel.class.getName();
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

    public int getTargetX() {
        return targetX;
    }

    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }
}
