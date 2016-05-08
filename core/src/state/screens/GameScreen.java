package state.screens;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import driver.GameLoopFactory;
import entities.GameEntity;
import entities.Player;
import tools.WorldFactory;
/**
 *
 * Created by Hongyu Wang on 5/5/2016.
 */
public class GameScreen extends AbstractScreen{
    static float deltax = -10000;
    static float deltay = -10000;
    @Override
    protected void subclassInit() {
        initiateBackground();

        //TODO initplayer information
        initPlayer();

    }



    private void initPlayer(){
        GameEntity mainPlayer;

        stage.addActor(mainPlayer = new Player());
        stage.setKeyboardFocus(mainPlayer);
        OrthographicCamera cam = (OrthographicCamera)stage.getCamera();
        cam.zoom = 3;

        ((OrthographicCamera)stage.getCamera()).zoom =4F;

    }

    private static Texture background;
    private void initiateBackground(){

        background = new Texture("background\\hexTiles.png");
        float x = 0, y = 0;

        for (int i = 0; i < 50; i ++) {
            addImage(background, x, y + i*background.getHeight());
        }


    }


    static Array<Image> images = new Array<>();
    static boolean acted = false;
    private void addImage(Texture texture, float xpos, float ypos){
        if (xpos > 50*texture.getWidth() || ypos > 50*texture.getWidth()){
            return;
        }


        Image image = new Image(texture);
        images.add(image);

        image.setBounds(xpos + deltax, ypos + deltay, texture.getWidth(), texture.getHeight());
        stage.addActor(image);


        addImage(texture, xpos + texture.getWidth(), ypos);
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


        if (!acted) {
            acted = true;
        }

    }

    static public class CameraManager extends InputAdapter {
        static InputAdapter inputManager;
        public static OrthographicCamera getCamera(){
            AbstractScreen screen = (AbstractScreen) GameLoopFactory.getMainGameLoop().getScreen();

            return (OrthographicCamera)(screen).getStage().getCamera();
        }

        public static void updateCamera(Vector2 travelVector){
            Vector2 difference = new Vector2(travelVector).sub(new Vector2(getCamera().position.x, getCamera().position.y));



            getCamera().position.lerp(new Vector3(travelVector, 0), 0.1F);

        }

        public static void resetCameraRatio(){
            getCamera().zoom = 1;
        }

        public static void zoom(int dir){
            if (dir < 0 && getCamera().zoom <= 1F || dir > 0 && getCamera().zoom >= 10F)
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


