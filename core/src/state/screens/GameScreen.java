package state.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import driver.GameLoopFactory;
import entities.GameEntity;
import entities.Player;
import tools.WorldFactory;
/**
 *
 * Created by Hongyu Wang on 5/5/2016.
 */
public class GameScreen extends AbstractScreen{


    @Override
    protected void subclassInit() {
        initiateBackground();

        initPlayer();

    }

    private void initPlayer(){
        GameEntity mainPlayer;

        stage.addActor(mainPlayer = new Player());
        stage.setKeyboardFocus(mainPlayer);

        ((OrthographicCamera)stage.getCamera()).zoom = 1F;

    }

    private void initiateBackground(){
        Texture tex;
        Image image;
        image = new Image(tex = new Texture("BackgroundConcepts\\granit_texture4410.jpg"));
        image.setPosition(0, 0);
        disposables.add(tex);
        stage.addActor(image);
    }



    @Override
    public void show() {
        super.show();

        inputMultiplexer.addProcessor(0, CameraManager.getInputManager());
    }


    @Override
    protected void update(float delta) {
        super.update(delta);

        WorldFactory.getWorld().step(delta, 8, 3);


    }

    static public class CameraManager extends InputAdapter {
        static InputAdapter inputManager;
        private static OrthographicCamera getCamera(){
            AbstractScreen screen = (AbstractScreen) GameLoopFactory.getMainGameLoop().getScreen();

            return (OrthographicCamera)(screen).getStage().getCamera();
        }

        public static void updateCamera(Vector2 travelVector){

            getCamera().position.lerp(new Vector3(travelVector, 0), 0.5F);
        }

        public static void resetCameraRatio(){
            getCamera().zoom = 1;
        }

        public static void zoom(int dir){
            if (dir < 0 && getCamera().zoom <= 0.4F || dir > 0 && getCamera().zoom >= 4F)
                return;
            getCamera().zoom += .2F*dir;
        }

        static InputAdapter getInputManager(){
            if (inputManager == null){
                inputManager = new CameraManager();
            }

            return inputManager;
        }


        @Override
        public boolean scrolled(int amount) {
            zoom(amount > 0 ? 1 : -1);
            return true;
        }

        @Override
        public boolean keyDown(int keycode) {
            if (keycode == Input.Keys.R){
                resetCameraRatio();
                return true;
            }

            return false;
        }

    }
}


