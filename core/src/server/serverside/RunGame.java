package server.serverside;

import server.event.EventHandler;
import server.event.EventHandlerFactory;

/**
 * Created by Hairuo on 2016-05-06.
 */
public class RunGame implements Runnable {

    private float deltaOne;
    private float lastTime;
    private float ms;
    private float deltaTwo;
    private ServerSideGame game;
    private EventHandler eventHandler;
    private boolean running;

    public RunGame(){
        this.game = GameFactory.getGame();
        this.eventHandler = EventHandlerFactory.createEventHandler();
        this.running = true;
        this.lastTime = 0;
        this.ms = 1000000;
        this.deltaOne = 0;
        this.deltaTwo = 0;
    }

    @Override
    public void run() {
        while(running){
            long now = System.currentTimeMillis();
            deltaOne += (now - lastTime) / ms;
            deltaTwo = (now-lastTime);
            lastTime = now;
            while (deltaOne >= 1) { //if enough time has elapsed for a frame it paints and moves everything
                eventHandler.executeAll();
                game.update(deltaTwo);
                deltaOne--;
            }
        }
    }
}
