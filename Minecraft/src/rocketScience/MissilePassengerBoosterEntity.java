package rocketScience;

import net.minecraft.src.*;

public class MissilePassengerBoosterEntity extends EntityLiving {
	
	double speed;
	int targetX;
	int targetZ;
	int missileType;
	EntityPlayer rider=null;
	
	public MissilePassengerBoosterEntity(World world)
	{
		super(world);
		texture="/rocketScience/MissilePassenger.png";
	}
	
	public MissilePassengerBoosterEntity(World world, double i, double j, double k)//, int x, int z, int type)
	{
		super(world);
		speed=0;
		//targetX=x;
		//targetZ=z;
		setPosition(i+.5f,j,k+.5f);
        //missileType=type;
        texture="/rocketScience/MissilePassenger.png";
	}
	
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
		super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setInteger("Type", missileType);
        nbttagcompound.setInteger("TargetX", targetX);
        nbttagcompound.setInteger("TargetZ", targetZ);
        nbttagcompound.setDouble("Speed", speed);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
    	super.readEntityFromNBT(nbttagcompound);
        missileType = nbttagcompound.getInteger("Type");
        targetX = nbttagcompound.getInteger("TargetX");
        targetZ = nbttagcompound.getInteger("TargetZ");
        speed = nbttagcompound.getDouble("Speed");
    }
    
    protected void entityInit()
    {
    }
    
    public void onUpdate()
    {
    	if(worldObj.getBlockId((int)Math.floor(posX), (int)Math.floor(posY+1), (int)Math.floor(posZ))!=0 || worldObj.getBlockId((int)Math.floor(posX), (int)Math.floor(posY+2), (int)Math.floor(posZ))!=0)
    	{
    		MissilePassengerWarheadEntity getHead=dropWarhead();
    		getHead.explode();
    	}
    	moveEntity(0,speed,0);
    	if(speed<1)
    		speed+=0.02;
    	if(posY>127)
    	{
    		dropWarhead();
    	}
    	worldObj.spawnParticle("flame", posX, posY, posZ, 0, 0, 0);
    }
    
    public boolean canBeCollidedWith()
    {
        return true;
    }
    private MissilePassengerWarheadEntity dropWarhead()
    {
    	int i = MathHelper.floor_float((int)posX+targetX) >> 4;
        int j = MathHelper.floor_float((int)posZ+targetZ) >> 4;
        //FIX THIS!
        /*if(!worldObj.getChunkProvider().chunkExists(i, j))
        {
        	worldObj.getChunkProvider().provideChunk(i,j);
        }*/
        MissilePassengerWarheadEntity warhead = new MissilePassengerWarheadEntity(worldObj, (int)posX+targetX,(int)posY,(int)posZ+targetZ);//,missileType);
        warhead.type=missileType;
    	setDead();
		if(rider!=null&&!ModLoader.getMinecraftInstance().isMultiplayerWorld())
		{
	    	ParachuteEntity theChute=new ParachuteEntity(worldObj,(int)warhead.posX,(int)warhead.posY,(int)warhead.posZ,rider);
			worldObj.spawnEntityInWorld(warhead);
			MissileDummyEntity.updateFall(rider, 0.1f);
			warhead.rider=rider;
			rider.mountEntity(null);
			rider.setPosition(warhead.posX,warhead.posY,warhead.posZ);
			rider.mountEntity(warhead);
			worldObj.updateEntityWithOptionalForce(rider, false);
			worldObj.spawnEntityInWorld(theChute);
	        worldObj.updateEntityWithOptionalForce(warhead, false);
	        rider=null;
	        worldObj.updateEntityWithOptionalForce(theChute, false);
		}
        rider=null;
    	return warhead;
    }
    public void playerGetInRocket(EntityPlayer player)
    {
    	player.mountEntity(this);
    	rider=player;
    }
    
    public void updateRiderPosition()
    {
        if(riddenByEntity == null)
        {
            return;
        } else
        {
            riddenByEntity.setPosition(posX, posY + getMountedYOffset() + riddenByEntity.getYOffset()+1, posZ);
            return;
        }
    }
    
    public String getEntityTexture()
    {
        return "/rocketScience/MissilePassenger.png";
    }

	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return 0;
	}
}
