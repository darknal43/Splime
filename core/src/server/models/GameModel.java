package server.models;

/**
 * Created by Hairuo on 2016-05-06.
 */
public abstract class GameModel {
    private long key;

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public abstract String getClassName();

    public abstract void setClassName(String className);
}
