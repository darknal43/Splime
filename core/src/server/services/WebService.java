package server.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import server.event.*;
import server.models.GameModel;
import server.models.PlayerModel;
import tools.ServerTools.databases.VirtualDatabase;
import tools.ServerTools.databases.VirtualDatabaseFactory;
import tools.ServerTools.generators.Tags;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Set;

/**
 * @author rsang
 */
@Path("/webservice")
public class WebService {

    @GET
    @Path("/param/{param}")
    @Produces("text/plain")
    public String getClichedMessage(@PathParam("param") String msg) {
        return msg;
    }

    /**
     * Using Jackson.
     */
    @GET
    @Path("/updateAll")
    @Produces("*/*")
    public String updateAll() {

        VirtualDatabase database = VirtualDatabaseFactory.createVirtualDatabase();
        Map data = database.getData();
        Set<Long> keys = data.keySet();
        String[] returnList = new String[data.size()];
        ObjectMapper objectMapper = new ObjectMapper();
        String rString = "";
        int counter = 0;

        try {

            for (long i : keys) {

                String className = Tags.ID_TAGS.parseTag(data.get(i).getClass().getCanonicalName());
                returnList[counter] = objectMapper.writeValueAsString(data.get(i)+className);
                counter++;

            }

            rString = objectMapper.writeValueAsString(returnList);

        } catch (Exception e) {

            System.out.println(e);

        }

        return rString;
    }

    /**
    @GET
    @Path("/getServerKey")
    @Produces("application/json")
    public Long getServerKey() {
        MockServer mockServer = ServerInteraction.getServer();
        return mockServer.getSerial();
    }

    @GET
    @Path("/update")
    @Produces("application/json")
    public Long[] update() {
        MockServer mockServer = ServerInteraction.getServer();
        List<Long> list = mockServer.getUpdates();

        return list.toArray(new Long[list.size()]);
    }
    **/

    /**
     * Using Jackson.
     */
    @POST
    @Path("/postServerModel")
    @Consumes("*/*")
    public Response postServerModel(String json) {

        VirtualDatabase database = VirtualDatabaseFactory.createVirtualDatabase();
        ObjectMapper objectMapper = new ObjectMapper();
        String[] models = null;

        try {

            models = objectMapper.readValue(json, String[].class);

            for (String model : models) {

                GameModel realModel;
                int tag = Integer.parseInt(model.substring(model.length() - 4));
                String className = Tags.ID_TAGS.getName(tag);
                model = model.substring(0, model.length() - 4);
                Class name = Class.forName(className);
                realModel = (GameModel) objectMapper.readValue(model, name);
                database.addModel(realModel);

            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/movePlayer")
    @Consumes("*/*")
    public Response movePlayer(String json){

        EventHandler eventHandler = EventHandlerFactory.createEventHandler();

        try{

            ObjectMapper objectMapper = new ObjectMapper();
            int[] values = objectMapper.readValue(json, int[].class);
            eventHandler.add(new MoveEvent(values));

        }catch (Exception e){

            e.printStackTrace();

        }

        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/fireProjectile")
    @Consumes("*/*")
    public Response fireProjectile(String json){

        EventHandler eventHandler = EventHandlerFactory.createEventHandler();

        try{

            ObjectMapper objectMapper = new ObjectMapper();
            int[] values = objectMapper.readValue(json, int[].class);
            eventHandler.add(new FireEvent(values));

        }catch (Exception e){

            e.printStackTrace();

        }

        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/createPlayer")
    @Consumes("*/*")
    public Response createPlayer(String json){

        EventHandler eventHandler = EventHandlerFactory.createEventHandler();

        try{

            ObjectMapper objectMapper = new ObjectMapper();
            PlayerModel newPlayer = objectMapper.readValue(json, PlayerModel.class);
            eventHandler.add(new CreatePlayerEvent(newPlayer));

        }catch (Exception e){

            e.printStackTrace();

        }

        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/leaveGame")
    @Consumes("*/*")
    public Response leaveGame(String json){

        EventHandler eventHandler = EventHandlerFactory.createEventHandler();

        try{

            ObjectMapper objectMapper = new ObjectMapper();
            PlayerModel newPlayer = objectMapper.readValue(json, PlayerModel.class);
            eventHandler.add(new LeaveGameEvent(newPlayer));

        }catch (Exception e){

            e.printStackTrace();

        }

        return Response.status(Response.Status.OK).build();
    }

}