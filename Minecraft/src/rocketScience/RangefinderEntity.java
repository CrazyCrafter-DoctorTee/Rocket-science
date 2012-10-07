package rocketScience;

import net.minecraft.src.*;

import java.util.List;

public class RangefinderEntity extends Entity{

	EntityPlayer player;
	
	public RangefinderEntity(World world, EntityLiving entityliving, float f)
    {
        super(world);
        player=(EntityPlayer)entityliving;
        setSize(0.5F, 0.5F);
        setLocationAndAngles(entityliving.posX, entityliving.posY + (double)entityliving.getEyeHeight(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
        posX -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
        posY -= 0.10000000149011612D;
        posZ -= MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
        setPosition(posX, posY, posZ);
        yOffset = 0.0F;
        motionX = -100*MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
        motionZ = 100*MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
        motionY = -100*MathHelper.sin((rotationPitch / 180F) * 3.141593F);
    }

	protected void entityInit() 
	{
	}

	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) 
	{
	}

	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) 
	{
	}
	
	public void onUpdate()
    {
        super.onUpdate();
        int i = worldObj.getBlockId((int)posX, (int)posY, (int)posZ);
        if(i > 0)
        {
            reportRange(0,0,0,i);
        }
        Vec3D vec3d = Vec3D.createVector(posX, posY, posZ);
        Vec3D vec3d1 = Vec3D.createVector(posX + motionX, posY + motionY, posZ + motionZ);
        MovingObjectPosition movingobjectposition = worldObj.rayTraceBlocks_do_do(vec3d, vec3d1, false, true);
        vec3d = Vec3D.createVector(posX, posY, posZ);
        vec3d1 = Vec3D.createVector(posX + motionX, posY + motionY, posZ + motionZ);
        if(movingobjectposition != null)
        {
            vec3d1 = Vec3D.createVector(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
            reportRange((int)vec3d1.xCoord,(int)vec3d1.yCoord,(int)vec3d1.zCoord, worldObj.getBlockId((int)vec3d1.xCoord, (int)vec3d1.yCoord, (int)vec3d1.zCoord));
        }
        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        setPosition(posX, posY, posZ);
    }
	
	private void reportRange(int x, int y, int z, int block)
	{
		String name="";
		if(Block.blocksList[block]!=null)
		{
			name=Block.blocksList[block].getBlockName();
			ModLoader.getMinecraftInstance().ingameGUI.addChatMessage("Distance to block "+name+" is (X="+(x-(int)player.posX)+", Z="+(z-(int)player.posZ)+")");
		}
		else
			ModLoader.getMinecraftInstance().ingameGUI.addChatMessage("Distance to block is (X="+(x-(int)player.posX)+", Z="+(z-(int)player.posZ)+")");
		setDead();
		RangefinderItem.ecks=(x-(int)player.posX);
		RangefinderItem.zee=(z-(int)player.posZ);
	}
}
