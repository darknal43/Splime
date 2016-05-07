package entities.projectile;

import entities.GameEntity;
import entities.Player;
import server.models.GameModel;

public class Projectile extends GameEntity{

    private Player player;

    public Projectile(Player player){
        super();
        this.player = player;


    }


    @Override
    public void serverUpdate() {

    }

    @Override
    public GameModel getModel() {
        return null;
    }
}






//
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Batch;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.BodyDef;
//import com.badlogic.gdx.physics.box2d.CircleShape;
//import com.badlogic.gdx.physics.box2d.FixtureDef;
//import entities.GameEntity;
//import entities.Player;
//import tools.Utils;
//
///**
// * Created by Hairuo on 2016-05-06.
// */
//public class Projectile extends GameEntity {
//    private float targetX;
//    private float targetY;
//    private float speed;
//    private float size;
//    private float xVelo;
//    private float turnSpeed;
//    private float yVelo;
//    private Player player;
//    private boolean returning;
//
//    public Projectile(Player player, float targetX, float targetY, float size, float speed){
//        super();
//        this.targetX = targetX;
//        this.targetY = targetY;
//        this.speed = speed;
//        this.size = size;
//        this.player = player;
//        this.returning = false;
//        setRotation(0);
//
//
//    }
//
//    public void initSprites(){
//        Texture texture = new Texture("badlogic.jpg");
//        sprite = new Sprite(texture);
//        setBounds(50, 50, 100, 100);
//        sprite.setSize(100, 100);
//        sprite.setOrigin(getOriginX(), getOriginY());
//    }
//
//    public void initBox2D(){
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.type = BodyDef.BodyType.DynamicBody;
//        bodyDef.position.set(getX(), getY());
//        body = world.createBody(bodyDef);
//
//        CircleShape hitbox = new CircleShape();
//        hitbox.setRadius(size);
//
//        FixtureDef fixtureDef = new FixtureDef();
//        fixtureDef.shape = hitbox;
//        fixtureDef.density = 1;
//
//        body.createFixture(fixtureDef);
//
//        hitbox.dispose();
//
//    }
//
//
//    @Override
//    public void act(float delta) {
//        super.act(delta);
//        move();
//
//    }
//
//    @Override
//    public void draw(Batch batch, float parentAlpha) {
//        super.draw(batch, parentAlpha);
//
//    }
//
//    /**
//     * Moves the ship a certain distance based on its state
//     */
//    public void move(){
//
//        float toTarget = Vector2.dst(targetX, targetY, this.getX(), this.getY());
//        float incrementDistance = (float)Math.hypot(xVelo, yVelo);
//
//        if(!returning && toTarget < incrementDistance){
//            returning = true;
//        }
//
//        if(returning){
//            targetX = player.getX();
//            targetY = player.getY();
//        }
//
//        this.setRotation(Utils.track(targetX, targetY, this.getX(), this.getY(),this.getRotation(), turnSpeed));
//
//        this.xVelo =  (float)Math.sin(Math.toRadians(this.getRotation())) * speed;
//        this.yVelo =  (float)Math.cos(Math.toRadians(this.getRotation())) * speed;
//
//        this.setX(this.getX() + xVelo);
//        this.setX(this.getY() + yVelo);
//
//
//
//    }
//}
