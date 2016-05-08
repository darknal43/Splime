package tools.hitboxes;

import javafx.scene.shape.Ellipse;

/**
 *
 * Created by hongy on 5/8/2016.
 */
public interface ConicHitbox {

    Ellipse getHitbox();

    boolean findCollision(ConicHitbox other);

    void pushCollision();

    void updateHitbox();
}

