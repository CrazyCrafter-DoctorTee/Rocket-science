package rocketScience;

import net.minecraft.src.*;

public class EntityDefenseLaser extends Entity
{
	private int boltLivingTime;
	
	public EntityDefenseLaser(World theWorld, int X1, int Y1, int Z1, int X2, int Y2, int Z2)
	{
		super(theWorld);
		//double zee=(Z2-Z1);
		//double panda=(Math.sqrt((X2-X1)*(X2-X1)+(Z2-Z1)*(Z2-Z1)));
		setPositionAndRotation((double)X1, (double)Y1, (double)Z1, 0,0); //(float)Math.atan((X2-X1)/zee)+3.14f, (float)Math.atan((Y2-Y1)/panda)+3.14f);
		boltLivingTime=7;
	}
	
	public void onUpdate()
	{
		super.onUpdate();
		boltLivingTime--;
		if(boltLivingTime<=0)
			setDead();
	}

	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isInRangeToRenderVec3D(Vec3D par1Vec3D)
    {
        return true;
    }
	
}
