package rocketScience;

import net.minecraft.src.*;

import java.util.List;

public class MissilePassengerWarheadEntity extends EntityLiving {
	
	double speed;
	int type;
	EntityPlayer rider;
	
	public MissilePassengerWarheadEntity(World world)
	{
		super(world);
		rider=null;
        speed=-.44;
		texture="/rocketScience/MissilePassenger.png";
	}
	
	public MissilePassengerWarheadEntity(World world, double i, double j, double k)//, int missileType)
	{
		super(world);
		setPosition(i+.5f,j,k+.5f);
        //type=missileType;
		rider=null;
        setSize(1.0f,1.0f);
        speed=-.44;
	}
	
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
		super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setDouble("Speed", speed);
        nbttagcompound.setInteger("Type", type);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
    	super.readEntityFromNBT(nbttagcompound);
        speed = nbttagcompound.getDouble("Speed");
        type = nbttagcompound.getInteger("Type");
    }
    
    protected void entityInit()
    {
    }
    
    public void onUpdate()
    {
    	moveEntity(0,speed,0);
    	if(worldObj.getBlockId((int)Math.floor(posX), (int)Math.floor(posY-1), (int)Math.floor(posZ))!=0 || worldObj.getBlockId((int)Math.floor(posX), (int)Math.floor(posY), (int)Math.floor(posZ))!=0)
    	{
    		explode();
    	}
    	if(posY<0)
    	{
    		explode();
    	}
    }
    
    public boolean canBeCollidedWith()
    {
        return true;
    }
    
    public void explode()
    {
    	if(rider!=null)
    	{
    		rider.mountEntity(null);
    		MissileDummyEntity.updateFall(rider, 0.1f);
    	}
    	rider=null;
    	if(type==12)
        	entityDropItem(new ItemStack(mod_ZZZMissile.passengerModule, 1),0);
    	else if(type==0)
	    	entityDropItem(new ItemStack(mod_ZZZMissile.booster,1,15),0);
    	else if(type==15)
	    	entityDropItem(new ItemStack(mod_ZZZMissile.passengerDepleted,1,10001),0);
    	setDead();
    }
    
    public void updateRiderPosition()
    {
        if(riddenByEntity == null)
        {
            return;
        } else
        {
            riddenByEntity.setPosition(posX, posY + getMountedYOffset() + riddenByEntity.getYOffset(), posZ);
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