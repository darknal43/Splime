//package server.serverside;
//
//import server.event.EventHandler;
//import server.event.EventHandlerFactory;
//
///**
// * Created by Hairuo on 2016-05-06.
// */
//public class RunGame implements Runnable {
//
//    private float delta;
//    private float lastTime;
//    private float ns;
//    private ServerSideGame game;
//    private EventHandler eventHandler;
//    private boolean running;
//
//    public RunGame(){
//        this.game = GameFactory.getGame();
//        this.eventHandler = EventHandlerFactory.createEventHandler();
//        this.running = true;
//        this.lastTime = 0;
//        this.ns = 1000000000;
//        this.delta = 0;
//    }
//
//    @Override
//    public void run() {
//        while(running){
//            long now = System.nanoTime();
//            delta += (now - lastTime) / ns;
//            lastTime = now;
//            while (delta >= 1) { //if enough time has elapsed for a frame it paints and moves everything
//                eventHandler.executeAll();
//                game.update();
//                delta--;
//            }
//        }
//    }
//}
