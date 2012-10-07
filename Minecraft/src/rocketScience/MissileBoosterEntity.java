package rocketScience;

import net.minecraft.src.*;

public class MissileBoosterEntity extends EntityLiving {
	
	double speed;
	int targetX;
	int targetZ;
	int missileType;
	EntityPlayer rider=null;
	
	public MissileBoosterEntity(World world)
	{
		super(world);
		texture="/rocketScience/MissileModel.png";
	}
	
	/*public MissileBoosterEntity(World world, int i, int j, int k, int x, int z, int type)
	{
		super(world);
		speed=0;
		targetX=x;
		targetZ=z;
		setPosition(i+.5f,j,k+.5f);
        missileType=type;
        texture="/rocketScience/MissileModel.png";
	}*/
	
	public MissileBoosterEntity(World world, double i, double j, double k)
	{
		super(world);
		speed=0;
		//targetX=x;
		//targetZ=z;
		setPosition(i+.5f,j,k+.5f);
        //missileType=type;
        texture="/rocketScience/MissileModel.png";
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
    		MissileWarheadEntity getHead=dropWarhead();
    		getHead.explode();
    	}
    	moveEntity(0,speed,0);
    	if(speed<1)
    		speed+=0.02;
    	if(posY>worldObj.getHeight())
    	{
    		if(!shootdown())
    			dropWarhead();
    		else
    		{
    			worldObj.createExplosion(null, posX+targetX, posY, posZ+targetZ, 6);
    			setDead();
    		}
    	}
    	worldObj.spawnParticle("flame", posX, posY, posZ, 0, 0, 0);
    }
    
    public boolean canBeCollidedWith()
    {
        return true;
    }
    
    public boolean shootdown()
    {
    	int x=0;
    	int z=0;
    	for(int ecks=-50;ecks<=50;ecks++)
    	{
    		for(int zee=-50;zee<=50;zee++)
    		{
    			if(zee*zee+ecks*ecks>2500)
    				continue;
    			x=(int)posX+targetX+ecks; z=(int)posZ+targetZ+zee;
    			int k;
    	        for (k = worldObj.getHeight()-1; k>0&&worldObj.getBlockId(x, k, z)==0; k--) {}
    	        if(worldObj.getBlockId(x, k, z)==mod_ZZZMissile.machineID&&worldObj.getBlockMetadata(x,k,z)==5)
    	        {
    	        	if(((TileEntityRadar)worldObj.getBlockTileEntity(x, k, z)).shootdown(this))
    	        		return true;
    	        }
    		}
    	}
    	return false;
    }
    
    private MissileWarheadEntity dropWarhead()
    {
    	MissileWarheadEntity warhead = new MissileWarheadEntity(worldObj, (int)posX+targetX,(int)posY,(int)posZ+targetZ);//,missileType);
    	//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage("Client: missile targeted on "+(int)(targetX)+", "+(int)(targetZ));
    	warhead.type=missileType;
    	warhead.setAngles(180,180);
    	if(!ModLoader.getMinecraftInstance().isMultiplayerWorld())
    		worldObj.spawnEntityInWorld(warhead);
    	setDead();
    	if(missileType==12)
    	{
    		warhead.rider=rider;
    		rider.mountEntity(null);
    		rider.posX=warhead.posX;
    		rider.posZ=warhead.posZ;
    		rider.mountEntity(warhead);
    		rider=null;
    	}
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
            double d = Math.cos(((double)rotationYaw * 3.1415926535897931D) / 180D) * 0.40000000000000002D;
            double d1 = Math.sin(((double)rotationYaw * 3.1415926535897931D) / 180D) * 0.40000000000000002D;
            riddenByEntity.setPosition(posX + d, posY + getMountedYOffset() + riddenByEntity.getYOffset(), posZ + d1);
            return;
        }
    }
    
    public String getEntityTexture()
    {
        return "/rocketScience/MissileModel.png";
    }

	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return 0;
	}
}
