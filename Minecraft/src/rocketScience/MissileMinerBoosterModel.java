package rocketScience;

import net.minecraft.src.*;

public class MissileMinerBoosterModel extends ModelBase
{
      public MissileMinerBoosterModel()
      {
        New_Shape1 = new ModelRenderer(this, 0, 0);
        New_Shape1.addBox(0F, 0F, 0F, 16, 16, 16);
        New_Shape1.setRotationPoint(-8F, 8F, -8F);
        New_Shape1.rotateAngleX = 0F;
        New_Shape1.rotateAngleY = 0F;
        New_Shape1.rotateAngleZ = 0F;
        New_Shape1.mirror = false;
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
      {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        New_Shape1.render(f5);
      }

      public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
      {
        super.setRotationAngles(f, f1, f2, f3, f4, f5);
      }

      //fields
        ModelRenderer New_Shape1;
}
