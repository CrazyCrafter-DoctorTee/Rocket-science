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
import ic2.common.TileEntityBlock;

public class TileEntityOffense extends TileEntityBlock implements IEnergySink, IEnergySource
{
	public boolean addedToEnergyNet;
	public List attachedDefense;
	public List attachedMissiles;
	public int ecks; public int zee;
	int updateTimer;
	
	public TileEntityOffense()
	{
		addedToEnergyNet=false;
		attachedDefense=new LinkedList();
		attachedMissiles=new LinkedList();
		ecks=0; zee=0;
		updateTimer=29;
	}
    
	public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        ecks=nbttagcompound.getInteger("xTarget");
        zee=nbttagcompound.getInteger("zTarget");
    }
	
	public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger("xTarget", ecks);
        nbttagcompound.setInteger("zTarget", zee);
    }
	
	public void updateEntity()
    {
		if (!addedToEnergyNet)
        {
            EnergyNet.getForWorld(worldObj).addTileEntity(this);
            addedToEnergyNet = true;
        }
		updateTimer++;
		if(updateTimer>=30)
		{
			updateTimer=0;
			updateAttachedMissiles();
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
		return false;
	}

	public int injectEnergy(Direction direction, int i) {
		return 0;
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
	
	public void updateAttachedMissiles()
	{
		attachedMissiles.clear();
		List allDevices=EnergyNet.getForWorld(worldObj).discoverTargets(this, true, 9999);
    	Iterator it=allDevices.iterator();
    	while(it.hasNext())
    	{
    		Object attachedDevice=it.next();
    		if(attachedDevice instanceof MissileTileEntity)
    		{
    			attachedMissiles.add(attachedDevice);
            	((MissileTileEntity)attachedDevice).targetX=ecks;
            	((MissileTileEntity)attachedDevice).targetZ=zee;
    		}
    	}
	}
	
	public void fireMissiles()
	{
		Object[] missiles=attachedMissiles.toArray();
		for(int i=0;i<missiles.length;i++)
		{
			((MissileTileEntity)missiles[i]).launch(null);
		}
		
		/*Iterator it=attachedMissiles.iterator();
		while(it.hasNext())
		{
			MissileTileEntity tentity=(MissileTileEntity)it.next();
			tentity.launch(null);
		}*/
	}
	
	public boolean canUpdate()
    {
		return true;
    }
}