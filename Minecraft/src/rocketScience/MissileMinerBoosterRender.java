package rocketScience;

import net.minecraft.src.*;

public class MissileMinerBoosterRender extends RenderLiving
{

    public MissileMinerBoosterRender(float f)
    {
        super(new MissileMinerBoosterModel(), f);
    }

    public void renderMissileMiner(EntityLiving entityliving, double d, double d1, double d2, 
            float f, float f1)
    {
        super.doRenderLiving(entityliving, d, d1, d2, f, f1);
    }

    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, 
            float f, float f1)
    {
        renderMissileMiner(entityliving, d, d1, d2, f, f1);
    }

    public void doRender(Entity entity, double d, double d1, double d2, 
            float f, float f1)
    {
        renderMissileMiner((EntityLiving)entity, d, d1, d2, f, f1);
    }
}
