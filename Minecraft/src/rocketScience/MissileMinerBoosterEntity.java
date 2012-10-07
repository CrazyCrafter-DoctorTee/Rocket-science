package rocketScience;

import net.minecraft.src.*;

public class MissileMinerBoosterEntity extends EntityLiving {
	
	double speed;
	int targetX;
	int targetZ;
	ItemStack[] inventory;
	boolean upOrDown=false;
	public int nextDirection;
	public int howManySquares;
	public int currentSquare;
	public boolean readyToIncrease;
	
	public MissileMinerBoosterEntity(World world)
	{
		super(world);
		texture="/rocketScience/MissileMiner.png";
		inventory=new ItemStack[27];
	}
	
	public MissileMinerBoosterEntity(World world, double i, double j, double k)
	{
		super(world);
		speed=0;
		//targetX=x;
		//targetZ=z;
		setPosition(i+.5f,j,k+.5f);
        texture="/rocketScience/MissileMiner.png";
		inventory=new ItemStack[27];
	}
	
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
		super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setInteger("TargetX", targetX);
        nbttagcompound.setInteger("TargetZ", targetZ);
        nbttagcompound.setDouble("Speed", speed);
        nbttagcompound.setInteger("nextDirection", nextDirection);
        nbttagcompound.setInteger("howManySquares", howManySquares);
        nbttagcompound.setInteger("currentSquare", currentSquare);
        nbttagcompound.setBoolean("upOrDown", upOrDown);
        nbttagcompound.setBoolean("readyToIncrease", readyToIncrease);
        NBTTagList nbttaglist = new NBTTagList();
        for(int i = 0; i < inventory.length; i++)
        {
            if(inventory[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                inventory[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        nbttagcompound.setTag("inventory", nbttaglist);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
    	super.readEntityFromNBT(nbttagcompound);
        targetX = nbttagcompound.getInteger("TargetX");
        targetZ = nbttagcompound.getInteger("TargetZ");
        speed = nbttagcompound.getDouble("Speed");
        nextDirection = nbttagcompound.getInteger("nextDirection");
        howManySquares = nbttagcompound.getInteger("howManySquares");
        currentSquare = nbttagcompound.getInteger("currentSquare");
        upOrDown = nbttagcompound.getBoolean("upOrDown");
        readyToIncrease = nbttagcompound.getBoolean("readyToIncrease");
        NBTTagList nbttaglist = nbttagcompound.getTagList("inventory");
        for(int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 0xff;
            if(j >= 0 && j < inventory.length)
            {
                inventory[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
    }
    
    protected void entityInit()
    {
    }
    
    public void onUpdate()
    {
    	if(worldObj.getBlockId((int)Math.floor(posX), (int)Math.floor(posY+1), (int)Math.floor(posZ))!=0 || worldObj.getBlockId((int)Math.floor(posX), (int)Math.floor(posY+2), (int)Math.floor(posZ))!=0)
    	{
    		dropWarhead();
    	}
    	if(!upOrDown)
    	{
    		moveEntity(0,speed,0);
    		if(speed<1)
    			speed+=0.02;
    		if(posY>127)
    		{
    			if(!ModLoader.getMinecraftInstance().isMultiplayerWorld())
    				dropWarhead();
    			else
    				setDead();
    		}
    		worldObj.spawnParticle("flame", posX, posY, posZ, 0, 0, 0);
    	}
    	else
    	{
    		moveEntity(0,speed,0);
    		if((posY<=0)||(worldObj.getBlockId((int)Math.floor(posX), (int)Math.floor(posY-1), (int)Math.floor(posZ))!=0 || worldObj.getBlockId((int)Math.floor(posX), (int)Math.floor(posY), (int)Math.floor(posZ))!=0))
        	{
    			worldObj.setBlockAndMetadataWithNotify((int)Math.floor(posX), (int)Math.floor(posY), (int)Math.floor(posZ), mod_ZZZMissile.machineID, 1);
    			TileEntityAutoMiner newMiner=(TileEntityAutoMiner)worldObj.getBlockTileEntity((int)Math.floor(posX), (int)Math.floor(posY), (int)Math.floor(posZ));
    			newMiner.currentSquare=currentSquare;
    			newMiner.energy=0;
    			newMiner.howManySquares=howManySquares;
    			newMiner.inventory=inventory.clone();
    			this.inventory=new ItemStack[27];
    			newMiner.mineType=1;
    			newMiner.nextDirection=(short)nextDirection;
    			newMiner.readyToIncrease=readyToIncrease;
    			setDead();
        	}
    	}
    }
    
    public boolean canBeCollidedWith()
    {
        return true;
    }
    private void dropWarhead()
    {
    	int i = MathHelper.floor_float((int)posX+targetX) >> 4;
        int j = MathHelper.floor_float((int)posZ+targetZ) >> 4;
        //FIX THIS!
        /*if(!worldObj.getChunkProvider().chunkExists(i, j))
        {
        	worldObj.getChunkProvider().provideChunk(i,j);
        }*/
        setPosition(posX+targetX,posY,posZ+targetZ);
        upOrDown=true;
        speed=-0.5;
        worldObj.updateEntityWithOptionalForce(this, false);
    }
    
    public String getEntityTexture()
    {
        return "/rocketScience/MissileMiner.png";
    }
    
    public World getWorld()
    {
    	return this.worldObj;
    }

	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return 0;
	}
}
