package rocketScience;

import ic2.api.Direction;
import ic2.api.IEnergySink;
import ic2.api.IEnergySource;
import ic2.common.EnergyNet;

import java.util.List;
import net.minecraft.src.*;

import java.util.Random;

public class MissileTileEntity extends TileEntity implements IEnergySource, IEnergySink
{
	
    public MissileTileEntity()
    {
        targetX=0;
        targetZ=0;
        addedToEnergyNet=false;
    }

    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        targetX = nbttagcompound.getInteger("TargetX");
        targetZ = nbttagcompound.getInteger("TargetZ");
    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger("TargetX", targetX);
        nbttagcompound.setInteger("TargetZ", targetZ);
    }
    
    public void launch(EntityPlayer player)
    {
    			
    			if(!ModLoader.getMinecraftInstance().isMultiplayerWorld()&&player!=null&&(worldObj.getBlockMetadata(xCoord,yCoord,zCoord)==12||worldObj.getBlockMetadata(xCoord,yCoord,zCoord)==15||worldObj.getBlockMetadata(xCoord,yCoord,zCoord)==0))
    			{
    				MissilePassengerBoosterEntity tittytoo=new MissilePassengerBoosterEntity(worldObj,xCoord,yCoord,zCoord);
    				tittytoo.targetX=targetX;
    				tittytoo.targetZ=targetZ;
    				tittytoo.missileType=worldObj.getBlockMetadata(xCoord,yCoord,zCoord);
    				
    				worldObj.spawnEntityInWorld(tittytoo);
    				tittytoo.playerGetInRocket(player);
    			}
    			
    			
    			//else if(tempPlayer!=null&&world.getBlockMetadata(i,j,k)==14)
    			//{
    				//Teleport to the moon for now
    				/*System.out.println("Toggling dimension!!");
    				EntityPlayerSP thePlayer=ModLoader.getMinecraftInstance().thePlayer;
    				if(thePlayer.dimension == -4)
    		        {
    		            thePlayer.dimension = 0;
    		        } else if(thePlayer.dimension==0)
    		        {
    		            thePlayer.dimension = -4;
    		        } else
    		        	return;
    		        world.setEntityDead(thePlayer);
    		        thePlayer.isDead = false;
    		        double d = thePlayer.posX;
    		        double d1 = thePlayer.posZ;
    		        double d2 = 16D;
    		        if(thePlayer.dimension == -4)
    		        {
    		            d /= d2;
    		            d1 /= d2;
    		            thePlayer.setLocationAndAngles(d, thePlayer.posY, d1, thePlayer.rotationYaw, thePlayer.rotationPitch);
    		            if(thePlayer.isEntityAlive())
    		            {
    		                world.updateEntityWithOptionalForce(thePlayer, false);
    		            }
    		            World newWorld = null;
    		            newWorld = new World(world, new WorldProviderMoon());
    		            ModLoader.getMinecraftInstance().changeWorld(newWorld, "Traveling to the moon...", thePlayer);
    		        } else
    		        {
    		            d *= d2;
    		            d1 *= d2;
    		            thePlayer.setLocationAndAngles(d, thePlayer.posY, d1, thePlayer.rotationYaw, thePlayer.rotationPitch);
    		            if(thePlayer.isEntityAlive())
    		            {
    		                world.updateEntityWithOptionalForce(thePlayer, false);
    		            }
    		            World world1 = null;
    		            world1 = new World(world, WorldProvider.getProviderForDimension(0));
    		            ModLoader.getMinecraftInstance().changeWorld(world1, "Returning to Earth...", thePlayer);
    		        }
    		        world=ModLoader.getMinecraftInstance().theWorld;
    		        thePlayer.worldObj = world;
    		        if(thePlayer.isEntityAlive())
    		        {
    		            thePlayer.setLocationAndAngles(d, thePlayer.posY, d1, thePlayer.rotationYaw, thePlayer.rotationPitch);
    		            world.updateEntityWithOptionalForce(thePlayer, false);
    		        }*/
    			//}
    			else if(!ModLoader.getMinecraftInstance().isMultiplayerWorld())
    			{
    				MissileBoosterEntity titty=new MissileBoosterEntity(worldObj,xCoord,yCoord,zCoord);//,titty.targetX,titty.targetZ,world.getBlockMetadata(i,j,k));
    					titty.targetX=targetX;
    					titty.targetZ=targetZ;
    					titty.missileType=worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
    				worldObj.spawnEntityInWorld(titty);
    			}
    			

				worldObj.setBlockWithNotify(xCoord, yCoord+1, zCoord, 0);
				worldObj.setBlockWithNotify(xCoord, yCoord, zCoord, 0);
    			
    			//Start fires!
    			for(int a=-1;a<2;a++)
    			{
    				for(int b=-1;b<2;b++)
    				{
    					for(int c=-1;c<2;c++)
    					{
    						if(worldObj.getBlockId(xCoord+a, yCoord+b, zCoord+c)==0 && worldObj.getBlockId(xCoord+a, yCoord+b-1, zCoord+c)!=0)
    						{
    							worldObj.setBlock(xCoord+a, yCoord+b, zCoord+c, 51);
    						}
    					}
    				}
    			}
    }

    public int targetX;
    public int targetZ;
    boolean addedToEnergyNet;
    
    public void updateEntity()
    {
		super.updateEntity();
		if (!addedToEnergyNet)
        {
            EnergyNet.getForWorld(worldObj).addTileEntity(this);
            addedToEnergyNet = true;
        }
    }
    
	@Override
	public boolean emitsEnergyTo(TileEntity tileentity, Direction direction) {
		return true;
	}

	@Override
	public boolean isAddedToEnergyNet() {
		return addedToEnergyNet;
	}

	@Override
	public boolean acceptsEnergyFrom(TileEntity tileentity, Direction direction) {
		return true;
	}

	@Override
	public boolean demandsEnergy() {
		return false;
	}

	@Override
	public int injectEnergy(Direction direction, int i) {
		return 0;
	}

	@Override
	public int getMaxEnergyOutput() {
		return 0;
	}
}
