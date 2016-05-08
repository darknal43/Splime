package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import driver.GameLoop;
import tools.AbstractAnimationImplementation;
import tools.AnimationManager;

public class PlayerAnimationImplementation extends AbstractAnimationImplementation{
    private static TextureAtlas textureAtlas;
    private static Texture[] glowEffects;
    private static Texture glowEffect;
    private static Texture testBlank;
    private int count;

    PlayerAnimationImplementation(float scale){
        super(scale);
    }

    protected void init(){
        super.init();
        count = 0;
        playMode = Animation.PlayMode.LOOP;
        playRate = 1F/8;

        initiateBaseAssets();
        initiateDefaultMoveAnimation();
        initBackSlimeAnimation();
        initFrontSlimeAnimation();

    }


    public static Texture [] getGlowEffects(){
        if (glowEffects == null){
            initiateBaseAssets();
        }
        return glowEffects;
    }

    private static void initiateBaseAssets() {
        if (textureAtlas != null) return;
        glowEffects = new Texture[5];
        textureAtlas = new TextureAtlas("player\\baseAnimationMoreSlimey-packed\\pack.atlas");

        for (int i = 0; i < 5; i++){
            glowEffects[i] = new Texture("player\\highlightAnimation\\hilightLayer0000"+i+".png");
            GameLoop.StaticDisposables.addDisposable(glowEffects[i]);
        }
        glowEffect = glowEffects[0];

        testBlank = new Texture("player\\testBlank.png");

        GameLoop.StaticDisposables.addDisposable(testBlank);
        GameLoop.StaticDisposables.addDisposable(textureAtlas);

    }

    public Sprite[] getAnimationArray(){
        if (count++%30 == 0 && Math.random() < 0.5) {
            glowEffect = glowEffects[(int)(Math.random()*5)];
            animations.clear();
            initiateDefaultMoveAnimation();
            initBackSlimeAnimation();
            initFrontSlimeAnimation();
        }

        return super.getAnimationArray();
    }





    private void initFrontSlimeAnimation(){
        Animation animation;
        Sprite frame = new Sprite(testBlank);
        Sprite frame4 = new Sprite(glowEffect){//-30
            @Override
            public void setPosition(float x, float y) {
                Vector2 rotationAmount = getScaleX() == 1F
                        ? new Vector2(currentTravelVector).setLength(90) : new Vector2(currentTravelVector).setLength(50);
                super.setPosition(x + rotationAmount.x, y + rotationAmount.y);
            }
        };
        frame4.setScale(0.6F);

        Sprite frame5 = new Sprite(glowEffect){ //-20
            @Override
            public void setPosition(float x, float y) {
                Vector2 rotationAmount = getScaleX() == 1F
                        ? new Vector2(currentTravelVector).setLength(170) : new Vector2(currentTravelVector).setLength(60);
                super.setPosition(x + rotationAmount.x, y + rotationAmount.y);
            }
        };
        frame5.setScale(0.5F);

        Sprite [] specialEffects = new Sprite[]{
//                frame,
                frame,
                frame,
                frame,
                frame4,
                frame5,


        };



        animation = new Animation(playRate, specialEffects);
        animation.setPlayMode(playMode);
        animations.add(animation);
    }

    private void initBackSlimeAnimation(){
        Sprite frame1 = new Sprite(glowEffect);

        Sprite frame2 = new Sprite(glowEffect){//-30
            @Override
            public void setPosition(float x, float y) {
                Vector2 rotationAmount = new Vector2(currentTravelVector).setLength(20*scale);
                super.setPosition(x - rotationAmount.x, y - rotationAmount.y);
            }
        };
        frame2.setScale(0.8F, 1);

        Sprite frame3 = new Sprite(glowEffect){//-30
            @Override
            public void setPosition(float x, float y) {
                Vector2 rotationAmount = new Vector2(currentTravelVector).setLength(20*scale);
                super.setPosition(x - rotationAmount.x, y - rotationAmount.y);
            }
        };
        frame3.setScale(0.7F, 1);


        Sprite frame4 = new Sprite(glowEffect){//-30
            @Override
            public void setPosition(float x, float y) {
                Vector2 rotationAmount = new Vector2(currentTravelVector).setLength(20*scale);
                super.setPosition(x - rotationAmount.x, y - rotationAmount.y);
            }
        };
        frame4.setScale(0.6F);

        Sprite frame5 = new Sprite(glowEffect){//-30
            @Override
            public void setPosition(float x, float y) {
                Vector2 rotationAmount = new Vector2(currentTravelVector).setLength(20*scale);
                super.setPosition(x - rotationAmount.x, y - rotationAmount.y);
            }
        };
        frame5.setScale(0.5F);


        Sprite [] specialEffects = new Sprite[]{
                // frame1,
                frame1,
                frame2,
                frame3,
                frame4,
                frame5,


        };

        Animation animation = new Animation(playRate, specialEffects);
        animation.setPlayMode(playMode);
        animations.add(animation);

    }

    private void initiateDefaultMoveAnimation(){
        Array<Sprite> assets = textureAtlas.createSprites();

        Animation animation = new Animation(playRate, assets);
        animation.setPlayMode(playMode);
        animations.add(animation);


    }


}