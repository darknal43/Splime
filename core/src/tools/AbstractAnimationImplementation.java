package tools;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by hongy on 5/8/2016.
 */
public abstract class AbstractAnimationImplementation implements  AnimationManager{
    protected float playRate;
    protected Animation.PlayMode playMode;
    protected Array<Animation> animations;
    protected Vector2 currentTravelVector;
    private float totalDelta;
    protected float scale;

    public AbstractAnimationImplementation(float scale){
        this.scale = scale;
        init();
    }

    protected void init(){
        animations = new Array<>();
        currentTravelVector = new Vector2();
    }

    @Override
    public Sprite[] getAnimationArray() {
        Sprite [] arr = new Sprite[animations.size];
        int index = 0;
        for (Animation animation : animations){
            arr[index++] = (Sprite)animation.getKeyFrame(totalDelta);
        }

        return arr;
    }
    public int getKeyFrameIndex(){

        return animations.get(0).getKeyFrameIndex(totalDelta);

    }

    public void update(float delta, Vector2 travelVector){
        totalDelta += delta;
        this.currentTravelVector = travelVector;

    }
}
