package state.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import tools.Constants;
import tools.WorldFactory;


/**
 * This is the primary screen superclass.
 *
 * This will store elements common to all screens.
 *
 * Created by Hongyu Wang on 5/5/2016.
 */
public abstract class AbstractScreen implements Screen, Constants {

    protected World world;

    protected Stage stage;

    protected InputMultiplexer inputMultiplexer;


    protected Array<Disposable> disposables;



    public AbstractScreen(){
        disposables = new Array<>();
        world = WorldFactory.getWorld();
        inputMultiplexer = new InputMultiplexer();

    }




    private void init(){
        disposables.clear();
        stage = new Stage(){

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                if (getKeyboardFocus() == null) return false;

                Vector2 screenInfo = new Vector2(screenX, screenY);
                Vector2 stageInfo = this.screenToStageCoordinates(screenInfo);

                InputEvent mouseMovedEvent = new InputEvent();
                mouseMovedEvent.setType(InputEvent.Type.mouseMoved);
                mouseMovedEvent.setStage(this);
                mouseMovedEvent.setStageX(stageInfo.x);
                mouseMovedEvent.setStageY(stageInfo.y);


                getKeyboardFocus().fire(mouseMovedEvent);
                return super.mouseMoved(screenX, screenY);

            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {

                return mouseMoved(screenX, screenY);
            }
        };

        inputMultiplexer.addProcessor(stage);
        disposables.add(stage);
        subclassInit();
    }


    /**
     * All disposable assets should be created here.
     */
    protected abstract void subclassInit();




    /**
     * All drawing should be done here.
     */
    protected void draw() {
        stage.draw();
    }

    /**
     * All updating information should be done here.
     * @param delta the change in delta time.
     */
    protected void update(float delta) {

        stage.act(delta);


    }





    @Override
    public void render(float delta) {

        update(delta);
        draw();
    }

    @Override
    public void show() {
        init();
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        for (Disposable disposable : disposables){
            disposable.dispose();
        }
    }

    public Stage getStage(){
        return stage;
    }




}


