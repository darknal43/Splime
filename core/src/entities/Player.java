package entities;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import driver.GameLoop;
import driver.GameLoopFactory;
import entities.effects.SlimeGlow;
import entities.projectile.ProjectileBenign;
import server.models.GameModel;
import state.screens.AbstractScreen;
import state.screens.GameScreen;
import tools.AnimationManager;
import tools.Constants;

/**
 *
 * Created by Hongyu Wang on 5/5/2016.
 */
public class Player extends GameEntity {
    private InputHandler inputHandler;
    private Sprite [] sprites;
    private boolean charging;

    private float chargeAmount;

    //Test
    private SlimeGlow glow;

    private Vector2 rotationAngle;

    private float previous;

    private float totalDelta;

    private boolean cameraReset;

    public Player(){
        super();
        cameraReset = true;
    }

    public Player(float x, float y, float width, float height){
        super(x, y, width, height);
        cameraReset = false;
    }



    //Test
    private void initiateEffects(){
        this.glow = new SlimeGlow(this.getX(), this.getY(), Color.WHITE);
    }

    private void initiateSprite(){
        sprites = new Sprite[3];
        updateSprite(0);

    }


    @Override
    protected void init() {
        super.init();
        rotationAngle = new Vector2(1, 0);
        charging = false;
        inputHandler = new InputHandler(this);
        animationManager = new PlayerAnimationImplementation(getScaleX());
        initiateSprite();
        initiateEffects();
        chargeAmount = 0.5F;

    }

    void charge(){
        charging = true;
        chargeAmount = 0.5F;
    }

    void shoot(){
        AbstractScreen abstractScreen = (AbstractScreen)GameLoopFactory.getMainGameLoop().getScreen();
        ProjectileBenign newShot = new ProjectileBenign(this, chargeAmount, travelVector);
        abstractScreen.getStage().addActor(newShot);
        charging = false;
    }


    //------------EVENT HANDLERS ------------------------------------------------------
    @Override
    public boolean fire(Event event) {
        return inputHandler.handleInput(event);
    }





    //-------- Your Update Loops ------------------------------------------------------
    @Override
    public void act(float delta) {
        super.act(delta);
        updateSprite(delta);
        updateActor();
        glow.update(this.getX(), this.getY());
        if (charging) {
            chargeAmount += 1 / 6F * delta;
            if (Math.abs(chargeAmount - 1) < 0.01){
                charging = false;
                chargeAmount = 1;
            }
        }
    }



    private void move(){
        travelVector.setLength(130/3F);
        if (totalDelta !=  previous) {
            this.addAction(Actions.moveBy(travelVector.x, travelVector.y));
            previous = totalDelta;
        }
    }

    private Vector2 [] points = {
        new Vector2(250, 250),
        new Vector2(),
        new Vector2(204, 312),
        new Vector2(),
        new Vector2(),
    };

    private void setSize(int keyframe){

    }

    private void updateSprite(float delta){

        int keyFrameNumber = animationManager.getKeyFrameIndex();

        if (keyFrameNumber == 0){
            move();
            setSize(keyFrameNumber);
        }



        int index = 0;
        Sprite [] arr = animationManager.getAnimationArray();
        for (Sprite sprite : arr) {
            sprite.setPosition(getX() - sprite.getWidth() / 2, getY() - sprite.getHeight() / 2);
            sprite.setOriginCenter();


            sprite.setRotation(index > 0 ? rotationAngle.angle() : travelVector.angle() + 180);
            rotationAngle.rotate(-1.2F);
            sprites[index++] = sprite;

        }

        totalDelta += delta;

    }




    private void updateActor() {
        currentLocation.set(getX(), getY());
        if (cameraReset)
            GameScreen.CameraManager.updateCamera(currentLocation);
    }

    public void setTargetLocation(float x, float y){
        targetLocation = new Vector2(x, y);

        travelVector = targetLocation.sub(currentLocation);
    }


    //------------------------------------------------------------------------------------

    @Override
    public boolean remove() {

        return super.remove();
    }

    @Override
    public GameModel getModel() {
        return null;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        int counter = 0;

        for (Sprite sprite : sprites){

            sprite.setScale(getScaleX());

            if(counter > 0) {
                int srcFunc = batch.getBlendSrcFunc();
                int dstFunc = batch.getBlendDstFunc();

                // Let's enable blending
                batch.end();
                batch.enableBlending();
                batch.begin();

                // blend them
                batch.setBlendFunction( GL20.GL_SRC_ALPHA, GL20.GL_ONE);

                sprite.draw(batch);

                batch.end();
                batch.begin();
                batch.setBlendFunction(srcFunc, dstFunc);

            }else{
                sprite.draw(batch);
            }

            counter++;
        }

    }


}







class InputHandler implements Constants {
    private Player player;


    InputHandler(Player player){
        this.player = player;
    }

    boolean handleInput(Event event){

        return event instanceof InputEvent && (Gdx.app.getType() == Application.ApplicationType.Desktop
                ? windowsHandleInput((InputEvent)event) : phoneHandleInput(event));
    }


    private boolean windowsHandleInput(InputEvent event){

        //This handles movement.
        if (event.getType() == InputEvent.Type.mouseMoved){
            player.setTargetLocation(event.getStageX(), event.getStageY());
            return true;
        }

        //This handles keyboard commands
        if (event.getType() == InputEvent.Type.keyDown){
            if (event.getKeyCode() == Input.Keys.SPACE){
                player.charge();
            }
        }

        if (event.getType() == InputEvent.Type.keyUp){
            if (event.getKeyCode() == Input.Keys.SPACE){
                player.shoot();
            }
        }


        return false;
    }



    /**
     * This should never be used for now
     * @param event The event to be handled
     * @return If it was handled or not.
     */
    private boolean phoneHandleInput(Event event){
        throw new UnsupportedClassVersionError();
    }
}

