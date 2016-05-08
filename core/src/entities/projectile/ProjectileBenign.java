package entities.projectile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import driver.GameLoopFactory;
import entities.GameEntity;
import entities.Player;
import entities.PlayerAnimationImplementation;
import server.models.GameModel;
import server.models.ProjectileBenignModel;
import state.screens.AbstractScreen;
import tools.AbstractAnimationImplementation;
import tools.ServerTools.databases.DatabaseStructure;
import tools.ServerTools.databases.LocalDatabase;
import tools.ServerTools.databases.LocalDatabaseFactory;

/**
 *
 * Created by hongy on 5/7/2016.
 */
public class ProjectileBenign extends GameEntity{

    private Player player;

    private final float DECREASE_AMOUNT = 0.7F/3;
    private final float SMALLEST_CHARGE = 0.3F;

    private float chargeAmount;
    private Sprite [] sprites;
    private Vector2 rotationAngle;

    public static ProjectileBenign initiateFromModel(ProjectileBenignModel model, DatabaseStructure databaseStructure){
        //TODO Add player from localdatabase
        return new ProjectileBenign(
                (Player)databaseStructure.getEntity(model.getPlayerKey()),
                model.getChargeAmount(),
                new Vector2(model.getTargetX(), model.getTargetY())
        );
    }

    public ProjectileBenign(Player player, float chargeAmount, Vector2 targetDirection){
        super(player.getX(), player.getY(), player.getDatabaseStructure());

        System.out.println(chargeAmount);

        this.chargeAmount = chargeAmount;
        this.player = player;

        this.travelVector = new Vector2(targetDirection);
        this.targetLocation = new Vector2(travelVector).setLength(10000).add(currentLocation);


        sprites = new Sprite[2];
        act(0);
    }

    @Override
    protected void init() {
        super.init();
        rotationAngle = new Vector2(1, 0);
        this.animationManager = new ProjectileAnimationImplementation(1);


    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updateSprite(delta);
        despawn();
        move(delta);
        animationManager.update(delta, travelVector);
        chargeAmount -= DECREASE_AMOUNT*delta;
    }

    private void move(float delta){
        float speed = 2500;
        travelVector.setLength(speed *chargeAmount);
        setPosition(getX() + travelVector.x*delta, getY() + travelVector.y*delta);

    }

    private void updateSprite(float delta){

        int keyFrameNumber = animationManager.getKeyFrameIndex();

        if (keyFrameNumber == 0){
            move(delta);
        }

        int index = 0;
        Sprite [] arr = animationManager.getAnimationArray();
        for (Sprite sprite : arr) {
            sprite.setScale(chargeAmount);
            sprite.setPosition(getX() - sprite.getWidth() / 2, getY() - sprite.getHeight() / 2);
            sprite.setOriginCenter();
            sprite.setRotation(index > 0 ? rotationAngle.angle() : travelVector.angle() + 180);
            rotationAngle.rotate(-2F);
            sprites[index++] = sprite;

        }

    }


    private void despawn(){

        if (Math.abs(chargeAmount - SMALLEST_CHARGE) <= 0.01F){
            this.remove();
            AbstractScreen abstractScreen = (AbstractScreen) GameLoopFactory.getMainGameLoop().getScreen();
            ProjectileAggressive aggressive = new ProjectileAggressive(player, getX(), getY(), databaseStructure);
            abstractScreen.getStage().addActor(aggressive);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for (Sprite sprite : sprites){
            sprite.draw(batch);
        }
    }

    @Override
    public GameModel getModel() {
        return null;
    }

    @Override
    public void pushCollision() {

    }
}


class ProjectileAnimationImplementation extends AbstractAnimationImplementation{

    private static TextureAtlas textureAtlas;
    private static Texture [] glowEffects;


    ProjectileAnimationImplementation(float scale){
        super(scale);

    }

    @Override
    protected void init() {
        super.init();
        assetsInit();
        playMode = Animation.PlayMode.LOOP;
        playRate = 1F/8;
        setUpMovement();
        setUpCentre();
    }

    private static void assetsInit(){
        if (textureAtlas != null) return;

        textureAtlas = new TextureAtlas("projectile-packed\\pack.atlas");
        glowEffects = PlayerAnimationImplementation.getGlowEffects();

    }

    private void setUpMovement(){
        Array<Sprite> assets = textureAtlas.createSprites();

        Animation animation = new Animation(playRate, assets);
        animation.setPlayMode(playMode);
        animations.add(animation);
    }

    private void setUpCentre(){
        Array<Sprite> assets = new Array<>();
        for (int i = 0; i < 4; i ++){
            assets.add(new Sprite(glowEffects[(int)(Math.random()*5)]));
        }

        Animation animation = new Animation(playRate, assets);
        animation.setPlayMode(playMode);
        animations.add(animation);

    }



}