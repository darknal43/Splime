package driver;

import box2dLight.RayHandler;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import state.screens.GameScreen;
import state.stateManager.ScreenShell;
import tools.RayFactory;
import tools.WorldFactory;

/**
 * This is the primary driver class for this game.
 *
 * Created by Hongyu Wang on 5/5/2016.
 */
public class GameLoop extends Game {
    private World world;
    private RayHandler rayHandler;

    @Override
    public void create () {
        ScreenShell.initiate();
        this.world = WorldFactory.getWorld();
        this.rayHandler = RayFactory.getRayHandler();


    }



    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();

        world.step(1/60, 0, 0);
        rayHandler.update();
        rayHandler.setCombinedMatrix(GameScreen.CameraManager.getCamera());

        rayHandler.render();




    }

    @Override
    public void dispose() {

        screen.dispose();

        StaticDisposables.dispose();
    }


    static public class StaticDisposables{
        static Array<Disposable> disposables;

        public static void addDisposable(Disposable e){
            if (disposables == null)
                disposables = new Array<>();

            disposables.add(e);
        }

        static void dispose(){
            for (Disposable e : disposables){
                e.dispose();
            }
        }

    }



}