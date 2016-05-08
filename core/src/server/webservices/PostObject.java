package server.webservices;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.fasterxml.jackson.databind.ObjectMapper;
import server.models.PlayerModel;
import tools.ServerTools.databases.LocalDatabase;

/**
 * Created by Hairuo on 2016-03-20.
 */

/**
 * Created by Hairuo on 2016-03-20.
 */
public class PostObject implements Net.HttpResponseListener {
    Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.POST);
    private ObjectMapper objectMapper = new ObjectMapper();


    public static PostObject newInstance(){
        return new PostObject();
    }


    /**

    public void addModel(GameModel[] model){
        String[] postList = new String[model.length];
        httpPost.setUrl("http://"+ LocalDatabase.ipAddress+":8081/webservice/postServerModel");
        String postString = "";
        try {
            for (int i = 0; i < model.length; i++) {
                postList[i] = objectMapper.writeValueAsString(model[i])+ Tags.ID_TAGS.parseTag(model[i].getClass().getCanonicalName());
            }
            postString = objectMapper.writeValueAsString(postList);
        }catch(Exception e){
            System.out.println(e);
        }

        httpPost.setContent(postString);
    }
    **/

    public void setLeaveGame(PlayerModel model){

        httpPost.setUrl("http://"+ LocalDatabase.ipAddress+":8081/webservice/LeaveGame");
        String postString = "";

        try {
            postString = objectMapper.writeValueAsString(model);
        }catch(Exception e){
            System.out.println(e);
        }

        httpPost.setContent(postString);


    }

    public void setMovePlayer(int[] data){
        httpPost.setUrl("http://"+ LocalDatabase.ipAddress+":8081/webservice/LeaveGame");
        String postString = "";

        try {
            postString = objectMapper.writeValueAsString(data);
        }catch(Exception e){
            System.out.println(e);
        }

        httpPost.setContent(postString);

    }

    public void setFireProjectile(int[] data){
        httpPost.setUrl("http://"+ LocalDatabase.ipAddress+":8081/webservice/LeaveGame");
        String postString = "";

        try {
            postString = objectMapper.writeValueAsString(data);
        }catch(Exception e){
            System.out.println(e);
        }

        httpPost.setContent(postString);

    }

    public void sendPost(){
        Gdx.net.sendHttpRequest(httpPost,this);
    }


    @Override
    public void cancelled() {
        System.out.println("POSTOBJECT CANCELLED: ");
    }

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        System.out.println(httpResponse.getStatus());
    }

    @Override
    public void failed(Throwable t) {
        System.out.println(t.getMessage());
    }



}

