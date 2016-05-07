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
import server.models.GameModel;
import tools.WorldFactory;

/**
 *
 * Created by Hongyu Wang on 5/5/2016.
 */
public abstract class GameEntity extends Actor {

    protected Sprite sprite;
    protected Vector2 currentLocation;
    protected Vector2 targetLocation;
    protected Vector2 travelVector;
    protected Body body;
    protected Array<Disposable> disposables;
    protected World world;


    public GameEntity(){
        this(0, 0, 100, 100);
    }


    public GameEntity(float x, float y, float width, float height){

        if (width == 0 || height == 0)
            throw new IllegalArgumentException("Game Entity Dimensions Cannot Be Zero");

        setBounds(x, y, width, height);

        init();

    }

    protected void init(){
        disposables = new Array<>();


        world = WorldFactory.getWorld();

        sprite = new Sprite();


        travelVector = new Vector2();
        currentLocation = new Vector2(getX(), getY());
        targetLocation = new Vector2();
        //initBox2D();


    }




    private void initBox2D(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX(), getY());

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth(), getHeight());

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1;


        body.createFixture(fixtureDef);

        shape.dispose();
    }

    @Override
    public boolean remove() {
        for (Disposable disposable : disposables){
            disposable.dispose();
        }
        return super.remove();
    }

    public abstract void serverUpdate();

    public abstract GameModel getModel();
}