package rocketScience;

import net.minecraft.src.*;

import java.util.List;

public class MissileWarheadEntity extends EntityLiving {
	
	double speed;
	public int type;
	EntityPlayer rider;
	
	/*public MissileWarheadEntity(World world)
	{
		super(world);
		rider=null;
        if(type==12)
        	speed=-.44;
        else
        	speed=-1;
		texture="/rocketScience/MissileModel.png";
	}*/
	
	/*public MissileWarheadEntity(World world, int i, int j, int k, int missileType)
	{
		super(world);
		setPosition(i+.5f,j,k+.5f);
        type=missileType;
		rider=null;
        setSize(1.0f,1.0f);
        if(type==12)
        	speed=-.44;
        else
        	speed=-1;
	}*/
	
	public MissileWarheadEntity(World world, double i, double j, double k)
	{
		super(world);
		setPosition(i+.5f,j,k+.5f);
		rider=null;
        setSize(1.0f,1.0f);
        if(type==12)
        	speed=-.44;
        else
        	speed=-1;
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
    	if(type==14)
    		worldObj.createExplosion(null, posX, posY, posZ, 6);
    	else if (type==4)
    	{
    		for(int a=-13;a<14;a++)
    		{
    			for(int b=-14;b<13;b++)
    			{
    				for(int c=-5;c<6;c++)
    				{
    					int block=worldObj.getBlockId((int)posX+a,(int)posY+c,(int)posZ+b);
    					if((block==0 || block==Block.blockSnow.blockID || block==Block.cactus.blockID || block==Block.cloth.blockID || block==Block.crops.blockID || block==Block.deadBush.blockID || block==Block.leaves.blockID || block==Block.melon.blockID || block==Block.melonStem.blockID || block==Block.mushroomBrown.blockID || block==Block.mushroomCapBrown.blockID || block==Block.mushroomCapRed.blockID || block==Block.mushroomRed.blockID || block==Block.plantRed.blockID || block==Block.plantYellow.blockID || block==Block.pumpkin.blockID || block==Block.pumpkinStem.blockID || block==Block.reed.blockID || block==Block.sapling.blockID || block==Block.snow.blockID || block==Block.tallGrass.blockID || block==Block.vine.blockID || block==Block.web.blockID)&& worldObj.getBlockId((int)posX+a,(int)posY+c-1,(int)posZ+b)!=0)
    					{
    						if(Math.abs(a)<8)
    						{
    							if(Math.abs(b)<8 || Math.random()<.5)
    								worldObj.setBlock((int)posX+a, (int)posY+c, (int)posZ+b, Block.fire.blockID);
    						}
    						else if (Math.abs(b)<8 || Math.random()<.5)
    							worldObj.setBlock((int)posX+a, (int)posY+c, (int)posZ+b, Block.fire.blockID);
    					}
    				}
    			}
    		}
    		worldObj.createExplosion(null, posX, posY, posZ, 1);
    	}
    	else if (type==8)
    	{
    		superCriticalFission(worldObj, (int)posX,(int)posY,(int)posZ, 45, 900);
    		//worldObj.createExplosion(null, posX, posY, posZ, 60);
    	}
    	else if (type==12)
    	{
    		rider.mountEntity(null);
    		MissileDummyEntity.updateFall(rider,0);
    		rider=null;
    		entityDropItem(new ItemStack(mod_ZZZMissile.passengerModule, 1),0);
    	}
    	else if (type==13)
    	{
    		superCriticalFission(worldObj, (int)posX,(int)posY,(int)posZ, 75, 2000);
    	}
    	setDead();
    }
    /**
     * @param world where the explosion occurs
     * @param xcenter x coord. of the explosion
     * @param ycenter y coord. of the explosion
     * @param zcenter z coord. of the explosion
     * @param RADIUS radius of the explosion
     * @param POWER power of the explosion
     * @return None 
     */
    public static void superCriticalFission(World world, int xcenter, int ycenter, int zcenter, int RADIUS, int POWER)
    {
    	world.playSoundEffect(xcenter, ycenter, zcenter, "random.explode", RADIUS/2, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.4F);
    	//ModLoader.getMinecraftInstance().effectRenderer.addEffect(new NukeFX(ModLoader.getMinecraftInstance().renderEngine,world, xcenter, ycenter+30, zcenter, 1, 1, 1));
    	int[][][] nukeVoxels=new int[2*RADIUS+1][2*RADIUS+1][2*RADIUS+1];
    	int[] spherePieces=new int[512]; //These optimize block-resistance calculations for the sphere. It's not exactly realistic, but.
    	int theBlock=0;
    	float resistance=0;
    	int a=0;
    	int b=0;
    	int c=0;
    	int which=0;
    	int block;
    	
    	for(int piece=0;piece<512;piece++)
    	{
    		spherePieces[piece]=POWER;
    	}
    	
    	int playerRadius=RADIUS-1;
    	
    	//Now kill things.
    	List list = world.getEntitiesWithinAABBExcludingEntity(null,AxisAlignedBB.getBoundingBoxFromPool(xcenter-RADIUS, ycenter-RADIUS, zcenter-RADIUS, xcenter+RADIUS, ycenter+RADIUS, zcenter+RADIUS));
        for(int k2 = 0; k2 < list.size(); k2++)
        {
            Entity entity = (Entity)list.get(k2);
            if(entity instanceof EntityPlayer)
            	playerRadius=(int)entity.getDistance(xcenter, ycenter, zcenter);
            if((int)entity.getDistance(xcenter, ycenter, zcenter)!=0 && (int)entity.getDistance(xcenter, ycenter, zcenter)<=RADIUS)
            	entity.attackEntityFrom(DamageSource.explosion, POWER/(int)entity.getDistance(xcenter, ycenter, zcenter)/2);
            else
            	entity.attackEntityFrom(DamageSource.explosion, 99999);
        }
    	
    	for(int i=0;i<RADIUS;i++)
    	{
    		for(int x=-i;x<=i;x++)
    		{
    			for(int y=-i;y<=i;y++)
    			{
    				for(int z=-i;z<=i;z++)
    				{
    					if(nukeVoxels[x+RADIUS][y+RADIUS][z+RADIUS]==0 && (x*x+y*y+z*z)<i*i)
    					{
    						a=x+xcenter;
    						b=y+ycenter;
    						c=z+zcenter;
    						theBlock=world.getBlockId(a, b, c);
    						if(Block.blocksList[theBlock]!=null)
    							resistance=Block.blocksList[theBlock].getExplosionResistance(null);
    						else
    							resistance=0;
    						if(i==0) //full explosion power
    						{
    							nukeVoxels[x+RADIUS][y+RADIUS][z+RADIUS]=POWER;
    						}
    						else
    						{
    							which=0;
    							
    							if(x<-3*i/4)
    								which+=0;
    							else if (x<-i/2)
    								which += 64;
    							else if (x<-i/4)
    								which += 128;
    							else if (x<0)
    								which += 192;
    							else if (x<i/4)
    								which += 256;
    							else if (x<i/2)
    								which += 320;
    							else if (x<3*i/4)
    								which += 384;
    							else
    								which += 448;
    							
    							if(y<-3*i/4)
    								which+=0;
    							else if (y<-i/2)
    								which += 8;
    							else if (y<-i/4)
    								which += 16;
    							else if (y<0)
    								which += 24;
    							else if (y<i/4)
    								which += 32;
    							else if (y<i/2)
    								which += 40;
    							else if (y<3*i/4)
    								which += 48;
    							else
    								which += 56;
    							
    							if(z<-3*i/4)
    								which+=0;
    							else if (z<-i/2)
    								which += 1;
    							else if (z<-i/4)
    								which += 2;
    							else if (z<0)
    								which += 3;
    							else if (z<i/4)
    								which += 4;
    							else if (z<i/2)
    								which += 5;
    							else if (z<3*i/4)
    								which += 6;
    							else
    								which += 7;
    							
    							nukeVoxels[x+RADIUS][y+RADIUS][z+RADIUS]=spherePieces[which];
    							spherePieces[which]-=(int)(resistance*.75);
    						}
    						block=world.getBlockId(a,b,c);
    						if(block!=0)
    						{
    							if(nukeVoxels[x+RADIUS][y+RADIUS][z+RADIUS]>resistance)
    							{
    								world.setBlockWithNotify(a, b, c, 0);
    								if(Block.blocksList[theBlock]!=null)
    									Block.blocksList[theBlock].onBlockDestroyedByExplosion(world, a, b, c);
    							}
    							else if(block==Block.grass.blockID || block==Block.tilledField.blockID)
    							{
    								world.setBlockWithNotify(a,b,c,Block.dirt.blockID);
    							}
    							else if(block==Block.blockSnow.blockID || block==Block.cactus.blockID || block==Block.cloth.blockID || block==Block.crops.blockID || block==Block.deadBush.blockID || block==Block.leaves.blockID || block==Block.melon.blockID || block==Block.melonStem.blockID || block==Block.mushroomBrown.blockID || block==Block.mushroomCapBrown.blockID || block==Block.mushroomCapRed.blockID || block==Block.mushroomRed.blockID || block==Block.plantRed.blockID || block==Block.plantYellow.blockID || block==Block.pumpkin.blockID || block==Block.pumpkinStem.blockID || block==Block.reed.blockID || block==Block.sapling.blockID || block==Block.snow.blockID || block==Block.tallGrass.blockID || block==Block.vine.blockID || block==Block.web.blockID)
    								world.setBlockWithNotify(a,b,c,0);
    						}
    						if(i==playerRadius-1 || i==playerRadius+1)
    						{
    							world.spawnParticle("explode", a, b, c, 0, 1, 0);
    						}
    						
    					}
    				}
    			}
    		}
    	}
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
        return "/rocketScience/MissileModel.png";
    }

	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return 0;
	}
}