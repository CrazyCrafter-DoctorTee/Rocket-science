// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package rocketScience;

import net.minecraft.src.*;

import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            EntityFX, World, Tessellator

public class NukeFX extends EntityFX
{
	
	RenderEngine engine;

    public NukeFX(RenderEngine renderengine, World world, double d, double d1, double d2, 
            double d3, double d4, double d5)
    {
        super(world, d, d1, d2, d3, d4, d5);
        motionX = 0;
        motionY = 0;
        motionZ = 0;
        particleRed = 255;
        particleGreen = 142;
        particleBlue = 5;
        particleScale = 100F;
        particleMaxAge = 10000;
        engine=renderengine;
    }

    public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5)
    {
    	engine.bindTexture(engine.getTexture("/rocketScience/nuke.png"));
        super.renderParticle(tessellator, f, f1, f2, f3, f4, f5);
    }

    public void onUpdate()
    {
        if(particleAge++ >= particleMaxAge)
        {
            setDead();
        }
    }
}
