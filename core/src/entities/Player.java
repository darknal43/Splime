package entities;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
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
import com.badlogic.gdx.utils.Queue;
import driver.GameLoop;
import driver.GameLoopFactory;
import entities.effects.SlimeGlow;
import entities.projectile.ProjectileBenign;
import server.models.GameModel;
import server.models.PlayerModel;
import state.screens.AbstractScreen;
import state.screens.GameScreen;
import tools.AnimationManager;
import tools.Constants;
import tools.ServerTools.databases.DatabaseStructure;
import tools.ServerTools.databases.LocalDatabase;
import tools.ServerTools.databases.LocalDatabaseFactory;

/**
 *
 * Created by Hongyu Wang on 5/5/2016.
 */
public class Player extends GameEntity {
    private InputHandler inputHandler;
    private Sprite [] sprites;
    private boolean charging;
    private Arrow arrow;
    private float chargeAmount;

    //Test
    private SlimeGlow glow;

    private Vector2 rotationAngle;

    private float previous;
    private boolean shot = false;
    private float totalDelta;

    private boolean cameraReset;

    public static Player initiateFromModel(PlayerModel playerModel, DatabaseStructure databaseStructure){
        return new Player(playerModel.getX(), playerModel.getY(), databaseStructure);
    }

    public Player(){
        super();
        cameraReset = true;
    }
    private Sound sound;
    public Player(float x, float y, DatabaseStructure databaseStructure){
        super(x, y, databaseStructure);
        cameraReset = false;

    }



    //Test
    private void initiateEffects(){
        this.glow = new SlimeGlow(this.getX(), this.getY(), Color.WHITE);
    }

    private void initiateSprite(){
        initializeTrail();
        sprites = new Sprite[3];
        updateSprite(0);
        sound = Gdx.audio.newSound(new FileHandle("sounds\\sounds\\Spit_Splat-Mike_Koenig-1170500447.wav"));
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
        shot = true;
        chargeAmount = 0.5F;
    }

    void shoot(){

        AbstractScreen abstractScreen = (AbstractScreen)GameLoopFactory.getMainGameLoop().getScreen();
        ProjectileBenign newShot = new ProjectileBenign(this, chargeAmount);
        abstractScreen.getStage().addActor(newShot);
        charging = false;
        sound.play();
        shot = false;
    }


    //------------EVENT HANDLERS ------------------------------------------------------
    @Override
    public boolean fire(Event event) {
        return inputHandler.handleInput(event);
    }





    //-------- Your Update Loops ------------------------------------------------------
    @Override
    public void act(float delta) {
        if (arrow == null){

            arrow = new Arrow(this);
        }
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
        removeTrail();
        arrow.act(delta);
    }




    private void move(){
        updateTrail();

        travelVector.setLength(getScaleX() == 1.0 ? 130/3F: 130/2.3F);
        if (totalDelta !=  previous) {
            this.addAction(Actions.moveBy(travelVector.x, travelVector.y));
            previous = totalDelta;

        }
    }

    private static Vector2 [] sizes;

    private void setSize(int keyframe){
        if (sizes == null)
            sizes = new Vector2[]{
                    new Vector2(250, 250),
                    new Vector2(211, 300),
                    new Vector2(204, 312),
                    new Vector2(275, 234),
                    new Vector2(334, 200),
            };
        setSize(sizes[keyframe].x*getScaleX(), sizes[keyframe].y*getScaleX());
    }

    private void updateSprite(float delta){

        int keyFrameNumber = animationManager.getKeyFrameIndex();

        if (keyFrameNumber == 0){
            move();
        }

        setSize(keyFrameNumber);



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
        PlayerModel model = new PlayerModel();
        model.setX(getX());
        model.setY(getY());

        model.setKey(getKey());
        return model;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        drawTrail(batch);
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

        if (shot) arrow.draw(batch, 1);

        //TODO THIS IS THE HITBOX CHECK
        //drawHitBox(batch);

    }


    @Override
    public void pushCollision() {

    }
    private Queue<Sprite> queue;
    static Texture trail;
    Vector2 rot;
    private void initializeTrail(){
        rot = new Vector2(1, 0);
        queue = new Queue<>();
        if (trail == null) trail = new Texture("player\\trail.png");
        for (int i = 0; i < 10; i ++){
            updateTrail();

        }
    }

    public float getChargeAmount() {
        return chargeAmount;
    }

    private void drawTrail(Batch batch){
        int index = 0;
        int length = queue.size;
        for (Sprite sprite : queue){
            sprite.setScale(getScaleX());
            sprite.setAlpha((float)(length - index++)/length);
            sprite.draw(batch);

        }
    }
    private int count = 0;
    private void removeTrail(){
        if (count++%6 == 0) {
            queue.removeLast();
        }
    }

    private void updateTrail(){
        rot.lerp(travelVector, 0.1F).nor();

        Sprite sprite = new Sprite(trail);
        sprite.setBounds(getX()-trail.getWidth()/2, getY()-trail.getHeight()/2, trail.getWidth(), trail.getHeight());
        sprite.setRotation(rot.angle());
        queue.addFirst(sprite);

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

