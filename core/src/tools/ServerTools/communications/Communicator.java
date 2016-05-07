package tools.ServerTools.communications;

/**
 * Created by Hairuo on 2016-05-07.
 */
public class Communicator {
    private static Communicator ourInstance = new Communicator();

    public static Communicator getInstance() {
        return ourInstance;
    }

    private Communicator() {

    }
}
