package entities.effects;

import box2dLight.ConeLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import entities.GameEntity;
import entities.Player;
import tools.RayFactory;

/**
 * Created by Hairuo on 2016-05-06.
 */
public class SlimeGlow{
    private float xPos;
    private float yPos;
    private float degreeOne;
    private float degreeTwo;
    private float degreeThree;
    private Color colour;
    private RayHandler rayHandler;

    public SlimeGlow(float x, float y, Color colour){
        this.xPos = x;
        this.yPos = y;
        this.colour = colour;
        this.rayHandler = RayFactory.getRayHandler();

        float degreeOne = 60;
        ConeLight lightOne = new ConeLight(rayHandler,100, colour, 100, xPos, yPos, degreeOne, 60);
        float degreeTwo = 180;
        ConeLight lightTwo = new ConeLight(rayHandler,100, colour, 100, xPos, yPos, degreeTwo, 60);
        float degreeThree= 300;
        ConeLight lightThree = new ConeLight(rayHandler,100, colour, 100, xPos, yPos, degreeThree, 60);
    }

    public void update(float x, float y){
        this.xPos = x;
        this.yPos = y;

        animate();
    }

    public void animate(){
        degreeOne++;
        degreeTwo++;
        degreeThree++;
    }

}
