package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import server.models.GameModel;
import tools.AbstractAnimationImplementation;

/**
 *
 * Created by hongy on 5/8/2016.
 */
public class Arrow extends GameEntity{
    private Player player;
    private Sprite spriteRegular, spriteFinal;
    private static Texture texture, texture2;

    private Sprite sprite;
    public Arrow(Player player){
        this.player = player;
        texture = new Texture("player\\arrow\\arrowNormal.png");
        texture2 = new Texture("player\\arrow\\arrowCharged.png");

        currentLocation = new Vector2(player.currentLocation);
        spriteRegular = new Sprite(texture);
        spriteFinal = new Sprite(texture2);
    }

    @Override
    public void act(float delta) {

        updateSprite();
    }


    private void updateSprite(){
        sprite = Math.abs(player.getChargeAmount() - 1.0F) < 0.01 ? spriteFinal : spriteRegular;
        sprite.setX(player.getX() - texture.getWidth()/2);
        sprite.setY(player.getY() - texture.getHeight()/2);
        sprite.setOriginCenter();
        sprite.setRotation(player.getTravelVector().angle());
        sprite.setScale(player.getChargeAmount());

    }

    @Override
    public GameModel getModel() {
        return null;
    }

    @Override
    public void pushCollision() {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.draw(batch);
    }
}
