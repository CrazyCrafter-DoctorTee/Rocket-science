package rocketScience;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;

import ic2.api.Direction;
import ic2.api.IEnergySink;
import ic2.api.IEnergySource;
import ic2.common.EnergyNet;
import ic2.common.PositionSpec;
import ic2.common.TileEntityBlock;
import ic2.platform.AudioManager;

public class TileEntityLaser extends TileEntityBlock implements IEnergySink, IEnergySource
{
	public boolean addedToEnergyNet;
	public List attachedDefense;
	int charge;
	
	public TileEntityLaser()
	{
		addedToEnergyNet=false;
		attachedDefense=new LinkedList();
		charge=0;
	}
    
	public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        charge=nbttagcompound.getInteger("charge");
    }
	
	public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger("charge", charge);
    }
	
	public void updateEntity()
    {
		if (!addedToEnergyNet)
        {
            EnergyNet.getForWorld(worldObj).addTileEntity(this);
            addedToEnergyNet = true;
        }
    }
	
	public boolean acceptsEnergyFrom(TileEntity tileentity, Direction direction) {
		return true;
	}

	public boolean isAddedToEnergyNet() {
		return addedToEnergyNet;
	}

	public boolean emitsEnergyTo(TileEntity tileentity, Direction direction) {
		return true;
	}

	public int getMaxEnergyOutput() {
		return 0;
	}

	public boolean demandsEnergy() {
		return charge<100000;
	}

	public int injectEnergy(Direction direction, int i) {
		if(i+charge>=100000)
		{
			charge=100000;
			return i-(100000-charge);
		}
		else
		{
			charge+=i;
			return 0;
		}
	}
	
	public void addDefenseBlock(TileEntityDefense def)
	{
		attachedDefense.add(def);
	}
	
	public void removeDefenseBlock(TileEntityDefense def)
	{
		attachedDefense.remove(def);
	}
	
	public void updateAttachedDevices()
	{
		List tempList=new LinkedList();
		Iterator it=attachedDefense.iterator();
		while(it.hasNext())
		{
			tempList.add(it.next());
		}
		attachedDefense.clear();
		it=tempList.iterator();
		while(it.hasNext())
		{
			((TileEntityDefense)it.next()).updateAttachedDevices();
		}
	}
	
	public boolean isOnline()
	{
		return charge>=100000;
	}
	public void fireAt(MissileBoosterEntity missile)
	{
		charge=0;
		AudioManager.playOnce(this, PositionSpec.Center, "Tools/MiningLaser/MiningLaserLongRange.ogg", true, 3.0f);
		worldObj.spawnEntityInWorld(new EntityDefenseLaser(worldObj,this.xCoord,this.yCoord,this.zCoord,(int)missile.posX,(int)missile.posY,(int)missile.posZ));
		//TODO: draw graphics ('lightning') towards missile
	}
	
	public boolean canUpdate()
    {
		return true;
    }
}