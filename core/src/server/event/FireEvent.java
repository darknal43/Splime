package server.event;

/**
 * Created by Hairuo on 2016-05-06.
 */
public class FireEvent extends Event {

    public FireEvent(int[] data) {
        super(data[0]);
    }

    @Override
    public void execute() {

    }
}
