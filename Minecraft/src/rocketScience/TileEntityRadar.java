package rocketScience;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;

import ic2.api.Direction;
import ic2.api.IEnergySink;
import ic2.api.IEnergySource;
import ic2.api.IWrenchable;
import ic2.common.EnergyNet;
import ic2.common.TileEntityBlock;

public class TileEntityRadar extends TileEntityBlock implements IEnergySink, IEnergySource
{
	public boolean addedToEnergyNet;
	public List attachedDefense;
	public short storage;
	
	public TileEntityRadar()
	{
		addedToEnergyNet=false;
		attachedDefense=new LinkedList();
		storage=0;
	}
    
	public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        storage=nbttagcompound.getShort("storage");
    }
	
	public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setShort("storage", storage);
    }
	
	public void updateEntity()
    {
		if (!addedToEnergyNet)
        {
            EnergyNet.getForWorld(worldObj).addTileEntity(this);
            addedToEnergyNet = true;
        }
		if(storage>0)
			storage-=2;
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
		return storage<10;
	}

	public int injectEnergy(Direction direction, int i) {
		if(storage+i>=10)
		{
			storage=10;
			return i-2;
		}
		else
		{
			storage+=i;
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
	
	public boolean shootdown(MissileBoosterEntity missile)
	{
		if(storage<=0)
			return false;
		Iterator it=attachedDefense.iterator();
		while(it.hasNext())
		{
			if(((TileEntityDefense)it.next()).shootdown(missile))
				return true;
		}
		return false;
	}
	
	public boolean canUpdate()
    {
		return true;
    }
}
