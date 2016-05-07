package entities;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
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
import server.models.GameModel;
import state.screens.GameScreen;
import tools.Constants;

/**
 *
 * Created by Hongyu Wang on 5/5/2016.
 */
public class Player extends GameEntity {
    private InputHandler inputHandler;
    private AnimationManager animationManager;
    private Sprite [] sprites;

    private float previous;



    //TODO Adjust this value based on size?
    private float speed = 10;


    private float totalDelta;

    private Player getPlayer(){
        return this;
    }

    private Vector2 getTravelVector(){
        return travelVector;
    }






    private void initiateSprite(){
        totalDelta = 0;
        sprites = new Sprite[3];
        updateSprite(0);
    }


    @Override
    protected void init() {
        super.init();
        inputHandler = new InputHandler(this);
        animationManager = new AnimationImplementation();


        initiateSprite();
    }


    public void shoot(){

    }


    //------------EVENT HANDLERS ------------------------------------------------------
    @Override
    public boolean fire(Event event) {
        return inputHandler.handleInput(event);
    }




    //-------- Your Update Loops ------------------------------------------------------
    @Override
    public void act(float delta) {
        updateSprite(delta);
        updateActor();
        super.act(delta);
    }



    private void move(){
        System.out.println(totalDelta);
        travelVector.setLength(130/3F);
        if (totalDelta !=  previous) {
            this.addAction(Actions.moveBy(travelVector.x, travelVector.y));
            previous = totalDelta;
        }
    }

    private void updateSprite(float delta){

        int keyFrameNumber = animationManager.getKeyFrameIndex();

        if (keyFrameNumber == 0){
            move();
        }

        int index = 0;
        Sprite [] arr = animationManager.getAnimationArray();
        for (Sprite sprite : arr) {

            sprite.setPosition(getX() - sprite.getWidth() / 2, getY() - sprite.getHeight() / 2);
            sprite.setOriginCenter();
            sprite.setRotation(travelVector.angle() + 180);

            sprites[index++] = sprite;

            //sprite.setColor(1, 0, 0, 1);
        }

        totalDelta += delta;
        animationManager.update(delta, travelVector);
    }




    private void updateActor() {
        currentLocation.set(getX(), getY());
        GameScreen.CameraManager.updateCamera(currentLocation);
    }

    public void setMouseLocation(float x, float y){
        targetLocation = new Vector2(x, y);

        travelVector = targetLocation.sub(currentLocation);
    }


    //------------------------------------------------------------------------------------

    @Override
    public boolean remove() {

        return super.remove();
    }

    @Override
    public void serverUpdate() {

    }

    @Override
    public GameModel getModel() {
        return null;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        for (Sprite sprite : sprites){

            sprite.draw(batch);

        }

    }


}

interface AnimationManager{
    Sprite [] getAnimationArray();
    int getKeyFrameIndex();
    void update(float delta, Vector2 travelVector);
}

class AnimationImplementation implements AnimationManager{
    private static TextureAtlas textureAtlas;
    private static Texture glowEffect;
    private static Texture testBlank;
    private float playRate;
    private Animation.PlayMode playMode;
    private Array<Animation> animations;
    private Vector2 currentTravelVector;

    public AnimationImplementation(){
        init();
        playMode = Animation.PlayMode.LOOP;
    }

    private void init(){
        playRate = 1F/8;
        animations = new Array<>();
        currentTravelVector = new Vector2();
        initiateBaseAssets();
        initBackSlimeAnimation();
        initFrontSlimeAnimation();
        initiateDefaultMoveAnimation();

    }


    private int totalDelta;

    private static void initiateBaseAssets(){
        if (textureAtlas != null) return;

        textureAtlas = new TextureAtlas("player\\baseAnimation-packed\\pack.atlas");
        glowEffect = new Texture("player\\testGlowLayer.png");
        testBlank = new Texture("player\\testBlank.png");

        GameLoop.StaticDisposables.addDisposable(glowEffect);
        GameLoop.StaticDisposables.addDisposable(testBlank);
        GameLoop.StaticDisposables.addDisposable(textureAtlas);

    }

    public Sprite [] getAnimationArray(){
        Sprite [] arr = new Sprite[animations.size];
        int index = 0;
        for (Animation animation : animations){
            arr[index++] = (Sprite)animation.getKeyFrame(totalDelta);
        }

        return arr;
    }

    public int getKeyFrameIndex(){
        try {
            return animations.get(0).getKeyFrameIndex(totalDelta);
        }catch (NullPointerException e){
            return 0;
        }
    }

    public void update(float delta, Vector2 travelVector){
        totalDelta += delta;
    }

    private void initFrontSlimeAnimation(){
        Animation animation;
        Sprite frame = new Sprite(testBlank);
        Sprite frame4 = new Sprite(glowEffect){//-30
            @Override
            public void setPosition(float x, float y) {
                Vector2 rotationAmount = new Vector2(currentTravelVector).setLength(90);
                super.setPosition(x + rotationAmount.x, y + rotationAmount.y);
            }
        };
        frame4.setScale(0.6F);

        Sprite frame5 = new Sprite(glowEffect){ //-20
            @Override
            public void setPosition(float x, float y) {
                Vector2 rotationAmount = new Vector2(currentTravelVector).setLength(180);
                super.setPosition(x + rotationAmount.x, y + rotationAmount.y);
            }
        };
        frame5.setScale(0.5F);

        Sprite [] specialEffects = new Sprite[]{
//                frame,
                frame,
                frame,
                frame,
                frame4,
                frame5,


        };
        animation = new Animation(playRate, specialEffects);
        animation.setPlayMode(playMode);
        animations.add(animation);
    }

    private void initBackSlimeAnimation(){
        Sprite frame1 = new Sprite(glowEffect);

        Sprite frame2 = new Sprite(glowEffect){//-30
            @Override
            public void setPosition(float x, float y) {
                Vector2 rotationAmount = new Vector2(currentTravelVector).setLength(20);
                super.setPosition(x - rotationAmount.x, y - rotationAmount.y);
            }
        };
        frame2.setScale(0.8F, 1);

        Sprite frame3 = new Sprite(glowEffect){//-30
            @Override
            public void setPosition(float x, float y) {
                Vector2 rotationAmount = new Vector2(currentTravelVector).setLength(20);
                super.setPosition(x - rotationAmount.x, y - rotationAmount.y);
            }
        };
        frame3.setScale(0.7F, 1);


        Sprite frame4 = new Sprite(glowEffect){//-30
            @Override
            public void setPosition(float x, float y) {
                Vector2 rotationAmount = new Vector2(currentTravelVector).setLength(20);
                super.setPosition(x - rotationAmount.x, y - rotationAmount.y);
            }
        };
        frame4.setScale(0.6F);

        Sprite frame5 = new Sprite(glowEffect){//-30
            @Override
            public void setPosition(float x, float y) {
                Vector2 rotationAmount = new Vector2(currentTravelVector).setLength(20);
                super.setPosition(x - rotationAmount.x, y - rotationAmount.y);
            }
        };
        frame5.setScale(0.5F);


        Sprite [] specialEffects = new Sprite[]{
                // frame1,
                frame1,
                frame2,
                frame3,
                frame4,
                frame5,


        };

        Animation animation = new Animation(playRate, specialEffects);
        animation.setPlayMode(playMode);
        animations.add(animation);

    }

    private void initiateDefaultMoveAnimation(){
        Array<Sprite> assets = textureAtlas.createSprites();

        Animation animation = new Animation(playRate, assets);
        animation.setPlayMode(playMode);
        animations.add(animation);
    }


}



class InputHandler implements Constants {
    private Player player;

    public InputHandler(Player player){
        this.player = player;
    }

    public boolean handleInput(Event event){

        return event instanceof InputEvent && (Gdx.app.getType() == Application.ApplicationType.Desktop
                ? windowsHandleInput((InputEvent)event) : phoneHandleInput(event));
    }


    public boolean windowsHandleInput(InputEvent event){

        //This handles movement.
        if (event.getType() == InputEvent.Type.mouseMoved){
            player.setMouseLocation(event.getStageX(), event.getStageY());
            return true;
        }

        //This handles keyboard commands
        if (event.getType() == InputEvent.Type.keyDown){
            switch (event.getKeyCode()){
                //This is the shoot command XD.
                case Input.Keys.SPACE : player.shoot();
                    return true;


            }
        }

        return false;
    }

    /**
     * This should never be used for now
     * @param event The event to be handled
     * @return If it was handled or not.
     */
    boolean phoneHandleInput(Event event){
        throw new UnsupportedClassVersionError();
    }
}

