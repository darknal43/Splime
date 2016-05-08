package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import driver.GameLoopFactory;
import entities.projectile.ProjectileBenign;
import server.models.GameModel;
import state.screens.AbstractScreen;
import tools.AnimationManager;
import tools.WorldFactory;
import tools.hitboxes.ConicHitbox;

import java.awt.geom.Ellipse2D;

/**
 *
 * Created by Hongyu Wang on 5/5/2016.
 */
public abstract class GameEntity extends Actor implements ConicHitbox{
    protected AnimationManager animationManager;
    protected Vector2 currentLocation;
    protected Vector2 targetLocation;
    protected Vector2 travelVector;


    protected Ellipse2D hitBox;

    protected Array<Disposable> disposables;
    protected World world;


    public GameEntity(){
        this(0, 0, 0, 0);
    }


    public GameEntity(float x, float y, float width, float height){
        

        setBounds(x, y, width, height);

        init();

    }

    protected void init(){
        disposables = new Array<>();


        world = WorldFactory.getWorld();



        travelVector = new Vector2();
        currentLocation = new Vector2(getX(), getY());
        targetLocation = new Vector2();
        hitBox = new Ellipse2D.Double(getX() - getWidth()/2, getY() + getHeight()/2, getWidth(), getHeight());

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updateHitbox();
        animationManager.update(delta, travelVector);
    }





    @Override
    public boolean remove() {
        for (Disposable disposable : disposables){
            disposable.dispose();
        }
        return super.remove();
    }

    @Override
    public boolean findCollision(ConicHitbox other) {
        return hitBox.intersects(other.getHitbox().getBounds());
    }


    @Override
    public void updateHitbox() {
        hitBox.setFrame(getX() - getWidth()/2, getY() - getHeight()/2, getWidth(), getHeight());
    }

    @Override
    public Ellipse2D getHitbox() {
        return hitBox;
    }

    public abstract GameModel getModel();
}