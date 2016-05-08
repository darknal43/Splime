package tools.ServerTools.communications;

import server.models.PlayerModel;
import server.webservices.PostObject;
import server.webservices.UpdateObject;

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

    public void sendMovePlayerRequest(int[] data){
        PostObject postObject = PostObject.newInstance();
        postObject.setMovePlayer(data);
        postObject.sendPost();

    }
    public void sendFireRequest(int[] data){
        PostObject postObject = PostObject.newInstance();
        postObject.setFireProjectile(data);
        postObject.sendPost();
    }

    public void sendLeaveGameRequest(PlayerModel model){
        PostObject postObject = PostObject.newInstance();
        postObject.setLeaveGame(model);
        postObject.sendPost();
    }

    public void sendCreatePlayerRequest(PlayerModel player){
        PostObject postObject = PostObject.newInstance();
        postObject.setCreatePlayer(player);
        postObject.sendPost();
    }

    public void updateAll(){
        UpdateObject updateObject = UpdateObject.newInstance();
        updateObject.updateAll();
    }

}
