package rocketScience;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import ic2.api.Direction;
import ic2.api.IEnergySink;
import ic2.api.IEnergySource;
import ic2.api.INetworkClientTileEntityEventListener;
import ic2.common.EnergyNet;
import ic2.common.IHasGui;
import ic2.common.PositionSpec;
import ic2.common.TileEntityBlock;
import ic2.platform.AudioManager;
import net.minecraft.src.forge.ISidedInventory;

public class TileEntityDefense extends TileEntityBlock implements IEnergySink, IEnergySource
{
    public boolean addedToEnergyNet;
    public List attachedRadar;
    public List attachedLasers;
    public List attachedOffense;
    private int updateTimer;

    public TileEntityDefense()
    {
    	addedToEnergyNet=false;
    	attachedRadar=new LinkedList();
    	attachedLasers=new LinkedList();
    	attachedOffense=new LinkedList();
    	updateTimer=29;
    }
    
	public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
    }
	
	public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
    }
	
	public void updateEntity()
    {
		super.updateEntity();
		if (!addedToEnergyNet)
        {
            EnergyNet.getForWorld(worldObj).addTileEntity(this);
            addedToEnergyNet = true;
            updateAttachedDevices();
        }
		updateTimer++;
		if(updateTimer>=30)
		{
			updateTimer=0;
			updateAttachedDevices();
		}
		//TODO: actually update
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
	
	public void updateAttachedDevices()
	{
		Iterator radars=attachedRadar.iterator();
		while(radars.hasNext())
		{
			Object radar=radars.next();
			((TileEntityRadar)radar).removeDefenseBlock(this);
		}
		Iterator lasers=attachedLasers.iterator();
		while(lasers.hasNext())
		{
			Object laser=lasers.next();
			((TileEntityLaser)laser).removeDefenseBlock(this);
		}
		Iterator offenses=attachedOffense.iterator();
		while(offenses.hasNext())
		{
			Object offense=offenses.next();
			((TileEntityOffense)offense).removeDefenseBlock(this);
		}
    	attachedRadar=new LinkedList();
    	attachedLasers=new LinkedList();
    	attachedOffense=new LinkedList();
    	List allDevices=EnergyNet.getForWorld(worldObj).discoverTargets(this, true, 9999);
    	Iterator it=allDevices.iterator();
    	while(it.hasNext())
    	{
    		Object attachedDevice=it.next();
    		if(attachedDevice instanceof TileEntityRadar)
    		{
    			attachedRadar.add(attachedDevice);
    			((TileEntityRadar)attachedDevice).addDefenseBlock(this);
    		}
    		else if(attachedDevice instanceof TileEntityLaser)
    		{
    			attachedLasers.add(attachedDevice);
    			((TileEntityLaser)attachedDevice).addDefenseBlock(this);
    		}
    		else if(attachedDevice instanceof TileEntityOffense)
    		{
    			attachedOffense.add(attachedDevice);
    			((TileEntityOffense)attachedDevice).addDefenseBlock(this);
    		}
    	}
	}
	
	public void add(TileEntity tentity)
	{
		if(tentity instanceof TileEntityRadar)
		{
			if(!attachedRadar.contains(tentity))
				attachedRadar.add(tentity);
		}
		else if(tentity instanceof TileEntityLaser)
		{
			if(!attachedLasers.contains(tentity))
				attachedLasers.add(tentity);
		}
		else if(tentity instanceof TileEntityOffense)
		{
			if(!attachedOffense.contains(tentity))
				attachedOffense.add(tentity);
		}
	}
	
	public boolean shootdown(MissileBoosterEntity missile)
	{
		//System.out.println("Missile incoming!");
		Iterator mutuallyAssuredDestruction=attachedOffense.iterator();
		while(mutuallyAssuredDestruction.hasNext())
		{
			TileEntityOffense offense=(TileEntityOffense)mutuallyAssuredDestruction.next();
			offense.fireMissiles();
		}
		Iterator it=attachedLasers.iterator();
		while(it.hasNext())
		{
			TileEntityLaser laser=(TileEntityLaser)it.next();
			if(!laser.isOnline())
				continue;
			laser.fireAt(missile);
			if(Math.random()>=0.25)
			{
				//System.out.println("Intercepted!");
				AudioManager.playOnce(this, PositionSpec.Center, "RocketScience/intercept.ogg", true, 6.0f);
				return true;
			}
			//System.out.println("Interception failed!");
		}
		AudioManager.playOnce(this, PositionSpec.Center, "RocketScience/interceptfail.ogg", true, 6.0f);
		return false;
	}
	
	public boolean canUpdate()
    {
		return true;
    }

}
