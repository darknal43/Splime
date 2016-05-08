package tools.hitboxes;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.awt.geom.Ellipse2D;

/**
 *
 * Created by hongy on 5/8/2016.
 */
public interface ConicHitbox {

    Ellipse2D getHitbox();

    boolean findCollision(ConicHitbox other);


    void updateHitbox();
}

