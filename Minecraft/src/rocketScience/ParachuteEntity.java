package rocketScience;

import net.minecraft.src.*;

public class ParachuteEntity extends EntityLiving {

	EntityPlayer player=null;
	
	public ParachuteEntity(World world)
	{
		super(world);
		texture="/rocketScience/Parachute.png";
	}
	
	public ParachuteEntity(World world, int i, int j, int k, EntityPlayer play)
	{
		super(world);
		setPosition(i+.5f,j,k+.5f);
		player=play;
        texture="/rocketScience/Parachute.png";
	}
	
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        player=ModLoader.getMinecraftInstance().thePlayer;
    }
    
    protected void entityInit()
    {
    }
    
    public void onUpdate()
    {
    	if(player==null||MissileDummyEntity.isFallDistance(player, 0))
    	{
    		setDead();
    		return;
    	}
    	posX=player.posX;
    	posY=player.posY-2;
    	posZ=player.posZ;
    }
    
    public boolean canBeCollidedWith()
    {
        return false;
    }
    
    public String getEntityTexture()
    {
        return "/rocketScience/Parachute.png";
    }

	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
