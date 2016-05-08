package tools;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * Created by hongy on 5/7/2016.
 */
public interface AnimationManager{
    Sprite [] getAnimationArray();
    int getKeyFrameIndex();
    void update(float delta, Vector2 travelVector);
}