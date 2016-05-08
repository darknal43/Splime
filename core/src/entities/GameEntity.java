package entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import driver.GameLoop;
import javafx.geometry.Bounds;
import javafx.scene.shape.Ellipse;
import server.models.GameModel;
import tools.AnimationManager;
import tools.ServerTools.databases.DatabaseStructure;
import tools.ServerTools.databases.LocalDatabaseFactory;
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
    private int key;

    protected static ShapeRenderer shapeRenderer;

    protected Ellipse hitBox;

    protected Array<Disposable> disposables;
    protected World world;
    protected DatabaseStructure databaseStructure;

    public GameEntity(){
        this(0, 0, null);
    }


    public GameEntity(float x, float y, DatabaseStructure databaseStructure){
        

        setBounds(x, y, 0, 0);

        init();

    }

    protected void init(){
        if (shapeRenderer == null) {
            shapeRenderer = new ShapeRenderer();
            GameLoop.StaticDisposables.addDisposable(shapeRenderer);
        }
        disposables = new Array<>();


        world = WorldFactory.getWorld();



        travelVector = new Vector2();
        currentLocation = new Vector2(getX(), getY());
        targetLocation = new Vector2();
        hitBox = new Ellipse(getX(), getY(), 0, 0);
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
        return hitBox.intersects(other.getHitbox().getLayoutBounds());
    }


    @Override
    public void updateHitbox() {
        hitBox.setCenterX(getX());
        hitBox.setCenterY(getY());
        hitBox.setRadiusX(getWidth()/2);
        hitBox.setRadiusY(getHeight()/2);
        hitBox.setRotate(travelVector.angle());
    }

    @Override
    public Ellipse getHitbox() {
        return hitBox;
    }

    static Texture texture;
    static Sprite sprite;
    protected void drawHitBox(Batch batch){
        float width = (float)hitBox.getRadiusX()*2;
        float height = (float)hitBox.getRadiusY()*2;
        float x = (float) hitBox.getCenterX()-width/2;
        float y = (float) hitBox.getCenterY() - height/2;
        if (texture == null) {
            texture = new Texture("badlogic.jpg");
            sprite = new Sprite(texture);

        }
        sprite.setOriginCenter();
        sprite.setBounds(x, y, width, height);

        sprite.setRotation((float)hitBox.getRotate());
        sprite.draw(batch);
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public DatabaseStructure getDatabaseStructure() {
        return databaseStructure;
    }

    public void setDatabaseStructure(DatabaseStructure databaseStructure) {
        this.databaseStructure = databaseStructure;
    }

    public abstract GameModel getModel();
}