package state.stateManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import driver.GameLoopFactory;
import tools.WorldFactory;

/**
 *
 * Created by Hongyu Wang on 5/5/2016.
 */
public enum ScreenShell {

    TITLE_SCREEN("TitleScreen"),
    LEVEL_SELECTION_SCREEN("InstructionsScreen"),
    GAME_SCREEN("GameScreen"),
    END_SCREEN("EndScreen");


    private String name;
    private Screen screen;
    private Game game;


    ScreenShell(String name){
        this.name = "state.screens."+name;
    }

    /**
     * This is the creation of the pages.
     */
    private void init(){

        try {
            screen = (Screen)Class.forName(name).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {

            System.out.println(this.getClass() + "-> " + name + " is not initialized properly");
        }


        game = GameLoopFactory.getMainGameLoop();
    }


    public void setAsScreen(){
        game.setScreen(screen);
    }



    /**
     * This method should be called.`3
     */
    public static void initiate(){

        for (ScreenShell screenShell : ScreenShell.values()){
            screenShell.init();
        }


        ScreenShell.TITLE_SCREEN.setAsScreen();
    }

    public static void dispose(){

        WorldFactory.getWorld().dispose();
    }
}
