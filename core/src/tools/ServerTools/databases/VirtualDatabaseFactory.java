package tools.ServerTools.databases;

/**
 * Created by Hairuo on 2016-05-06.
 */
public class VirtualDatabaseFactory {
    private static VirtualDatabase virtualDatabase;

    private VirtualDatabaseFactory(){}

    public static VirtualDatabase createVirtualDatabase(){
        if(virtualDatabase == null){
            virtualDatabase = new VirtualDatabase();
        }
        return virtualDatabase;
    }
}