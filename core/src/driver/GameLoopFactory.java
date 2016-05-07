package driver;

/**
 * Singleton GameLoop creator for our game.
 *
 * Created by Hongyu Wang on 5/5/2016.
 */
public class GameLoopFactory {
    private static GameLoop gameLoop;

    /**
     * Singleton GameLoop creator.
     */
    public static GameLoop getMainGameLoop(){
        if (gameLoop == null){
            gameLoop = new GameLoop();
        }

        return gameLoop;
    }



}
