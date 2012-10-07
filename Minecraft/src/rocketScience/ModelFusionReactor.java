
package rocketScience;

import net.minecraft.src.Entity;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class ModelFusionReactor extends ModelBase
{
  //fields
    ModelRenderer Core;
    ModelRenderer Top;
    ModelRenderer Bottom;
    ModelRenderer RingL;
    ModelRenderer RingLA;
    ModelRenderer RingR;
    ModelRenderer RingRA;
  
  public ModelFusionReactor()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Core = new ModelRenderer(this, 0, 0);
      Core.addBox(-1F, 0F, -1F, 3, 13, 3);
      Core.setRotationPoint(0F, 0F, 0F);
      Core.setTextureSize(64, 32);
      Core.mirror = true;
      setRotation(Core, 0.0174533F, 0F, 0F);
      Top = new ModelRenderer(this, 0, 0);
      Top.addBox(-4F, 0F, -4F, 9, 3, 9);
      Top.setRotationPoint(0F, 0F, 0F);
      Top.setTextureSize(64, 32);
      Top.mirror = true;
      setRotation(Top, 0F, 0F, 0F);
      Bottom = new ModelRenderer(this, 0, 0);
      Bottom.addBox(-4F, 0F, -4F, 9, 3, 9);
      Bottom.setRotationPoint(0F, 10F, 0F);
      Bottom.setTextureSize(64, 32);
      Bottom.mirror = true;
      setRotation(Bottom, 0F, 0F, 0F);
      RingL = new ModelRenderer(this, 0, 0);
      RingL.addBox(0F, 0F, -5F, 2, 2, 11);
      RingL.setRotationPoint(4.966667F, 4F, 0F);
      RingL.setTextureSize(64, 32);
      RingL.mirror = true;
      setRotation(RingL, 0F, 0F, 0F);
      RingLA = new ModelRenderer(this, 0, 0);
      RingLA.addBox(0F, 0F, -5F, 2, 2, 11);
      RingLA.setRotationPoint(-5F, 4F, 0F);
      RingLA.setTextureSize(64, 32);
      RingLA.mirror = true;
      setRotation(RingLA, 0F, 0F, 0F);
      RingR = new ModelRenderer(this, 0, 0);
      RingR.addBox(-5F, 0F, 0F, 11, 2, 2);
      RingR.setRotationPoint(0F, 4F, 4F);
      RingR.setTextureSize(64, 32);
      RingR.mirror = true;
      setRotation(RingR, 0F, 0F, 0F);
      RingRA = new ModelRenderer(this, 0, 0);
      RingRA.addBox(-5F, 0F, 0F, 11, 2, 2);
      RingRA.setRotationPoint(0F, 4F, -5F);
      RingRA.setTextureSize(64, 32);
      RingRA.mirror = true;
      setRotation(RingRA, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    Core.render(f5);
    Top.render(f5);
    Bottom.render(f5);
    RingL.render(f5);
    RingLA.render(f5);
    RingR.render(f5);
    RingRA.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5);
  }

}
