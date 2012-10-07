package rocketScience;

import net.minecraft.src.*;
import ic2.common.*;

import java.util.ArrayList;

public class TileEntityFusion extends TileEntityBaseGenerator {

	boolean reallyAddedToEnergyNet=false;
	
	public TileEntityFusion()
	{
		super(8, 5000, 128);
		heat=0;
		fuel=0;
		fueltype=0;
		production=20;
		bred=0;
	}
	
	public Container getGuiContainer(InventoryPlayer inventoryplayer) {
		return new ContainerFusion(inventoryplayer, this);
	}

	public String getInvName() {
		return "FusionReactor";
	}
	
	public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        fuel = nbttagcompound.getShort("fuel");
        heat = nbttagcompound.getShort("heat");
        energy = nbttagcompound.getShort("energy");
        fueltype = nbttagcompound.getShort("fueltype");
        bred = nbttagcompound.getInteger("bred");
    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setShort("heat", heat);
        nbttagcompound.setShort("fuel", (short) fuel);
        nbttagcompound.setShort("fueltype", fueltype);
        nbttagcompound.setShort("energy", energy);
        nbttagcompound.setInteger("bred", bred);
    }
    
    public int gaugeHeatScaled(int i)
    {
        if(heat <= 0)
        {
            return 0;
        }
        int j = (heat * i) / 5000;
        if(j > i)
        {
            return i;
        } else
        {
            return j;
        }
    }
    
    public int gaugeFuelScaled(int i)
    {
        if(fuel <= 0)
        {
            return 0;
        }
        int j = 0;
        j=(fuel * i) / 10000;
        if(j > i)
        {
            j = i;
        }
        return j;
    }
	
	public short heat;
	public short fueltype; // 1 = D-D, 2 = D-He3, 3 = D-T
	public short energy;
	public int bred;
	
	public short getMaximumStorage() 
	{
		return 5000;
	}
	
	public boolean gainFuel()
    {
        if(inventory[1] == null || inventory[2] == null)
            return false;
        int i=0;
        if(inventory[1].itemID==mod_ZZZMissile.cellDeuterium.shiftedIndex)
        {
        	if(inventory[2].itemID==mod_ZZZMissile.cellDeuterium.shiftedIndex)
        	{
        		fueltype=1;
        		i=10000;
        	}
        	else if(inventory[2].itemID==mod_ZZZMissile.tritiumCell.shiftedIndex)
        	{
        		fueltype=3;
        		i=10000;
        	}
        	else
        		return false;
        }
        else if(inventory[2].itemID==mod_ZZZMissile.cellDeuterium.shiftedIndex)
        {
        	if(inventory[1].itemID==mod_ZZZMissile.cellDeuterium.shiftedIndex)
        	{
        		fueltype=1;
        		i=10000;
        	}

        	else if(inventory[1].itemID==mod_ZZZMissile.tritiumCell.shiftedIndex)
        	{
        		fueltype=3;
        		i=10000;
        	}
        	else
        		return false;
        }
        else
        	return false;
        fuel += i;
        inventory[1].stackSize--;
        inventory[2].stackSize--;
        if(inventory[1].stackSize == 0)
        {
            inventory[1] = null;
        }
        if(inventory[2].stackSize == 0)
        {
            inventory[2] = null;
        }
        return true;
    }
	
	public void updateEntity()
    {
		if (!reallyAddedToEnergyNet&&ignited())
        {
            EnergyNet.getForWorld(worldObj).addTileEntity(this);
            reallyAddedToEnergyNet = true;
        }
		if(fuel<=0)
			gainFuel();
		if(!ignited() && hasCoils() && fuel > 0)
		{
			if(energy <= 0 && inventory[0] != null)
	        {
	            int i = inventory[0].itemID;
	            Item item = Item.itemsList[i];
	            if(item instanceof ItemBattery)
	            {
	                energy += ((ItemBattery)item).discharge(inventory[0], 1000 - energy, 1,false,false);
	            }
	            if(i == Item.redstone.shiftedIndex)
	            {
	                energy += 500;
	                inventory[0].stackSize--;
	                if(inventory[0].stackSize <= 0)
	                {
	                    inventory[0] = null;
	                }
	            }
	            if(i == Ic2Items.suBattery.itemID)
	            {
	                energy += 1000;
	                inventory[0].stackSize--;
	                if(inventory[0].stackSize <= 0)
	                {
	                    inventory[0] = null;
	                }
	            }
	        }
			
			if(energy > 0 && hasHeaters() && hasCoils() && fuel>0)
			{
				for(int panda=4;panda<=6;panda++)
				{
					if(energy>0 && inventory[panda]!=null && inventory[panda].itemID==mod_ZZZMissile.rfHeaterID+256)
					{
						energy-=15;
						heat+=10;
					}
					else if(energy>0 && inventory[panda]!=null && inventory[panda].itemID==mod_ZZZMissile.neutralHeaterID+256)
					{
						energy-=20;
						heat+=10;
					}
					else if(energy>0 && inventory[panda]!=null && inventory[panda].itemID==mod_ZZZMissile.ohmicHeaterID+256)
					{
						int i=(5000-heat)/500;
						energy-=i;
						heat+=i;
					}
				}
			}
		}
		
		if(ignited())
		{
			if(fueltype==0)
				production=0;
			else if (fueltype==1)
				production=32;
			else if (fueltype==3)
				production=128;
			//other fuels here
			
			if(inventory[3]!=null&&inventory[3].itemID!=mod_ZZZMissile.superCoilsID+256)
				production-=10;
			
			if(inventory[7]!=null&&inventory[7].itemID==mod_ZZZMissile.lithium6Cell.shiftedIndex)
			{
				bred+=1;
				if(bred>=9500)
				{
					bred=0;
					inventory[7].stackSize--;
					if(inventory[7].stackSize<=0)
						inventory[7]=null;
					boolean tritty=false;
					if(inventory[1]!=null&&inventory[2]!=null)
					{
						if(inventory[1].itemID==mod_ZZZMissile.cellDeuterium.shiftedIndex&&inventory[2].itemID==mod_ZZZMissile.cellDeuterium.shiftedIndex&&inventory[2].stackSize+inventory[1].stackSize<=64)
						{
							inventory[1].stackSize+=inventory[2].stackSize;
							inventory[2]=new ItemStack(mod_ZZZMissile.tritiumCell);
							tritty=true;
						}
						else if(inventory[1].itemID==mod_ZZZMissile.cellDeuterium.shiftedIndex&&inventory[2].itemID==mod_ZZZMissile.tritiumCell.shiftedIndex&&inventory[2].stackSize<64)
						{
							inventory[2].stackSize++;
							tritty=true;
						}
						else if(inventory[2].itemID==mod_ZZZMissile.cellDeuterium.shiftedIndex&&inventory[1].itemID==mod_ZZZMissile.tritiumCell.shiftedIndex&&inventory[1].stackSize<64)
						{
							inventory[1].stackSize++;
							tritty=true;
						}
					}
					else if(inventory[1]==null)
					{
						if(inventory[2]!=null&&inventory[2].itemID==mod_ZZZMissile.tritiumCell.shiftedIndex&&inventory[2].stackSize<64)
						{
							inventory[2].stackSize++;
							tritty=true;
						}
						else
						{
							inventory[1]=new ItemStack(mod_ZZZMissile.tritiumCell);
							tritty=true;
						}
					}
					else if(inventory[2]==null)
					{
						if(inventory[1]!=null&&inventory[1].itemID==mod_ZZZMissile.tritiumCell.shiftedIndex&&inventory[1].stackSize<64)
						{
							inventory[1].stackSize++;
							tritty=true;
						}
						else
						{
							inventory[2]=new ItemStack(mod_ZZZMissile.tritiumCell);
							tritty=true;
						}
					}
					if(!tritty&&inventory[7]==null)
					{
						inventory[7]=new ItemStack(mod_ZZZMissile.tritiumCell);
						tritty=true;
					}
					//If we haven't placed the tritium yet, drop it.
					if(!tritty)
					{
						ArrayList list=new ArrayList();
						list.add(new ItemStack(mod_ZZZMissile.tritiumCell));
						StackUtil.distributeDrop(this,list);
					}
				}
			}
		}
		else
		{
			production=0;
		}
		
		super.updateEntity();
		
		if((fuel <= 0 || inventory[3]==null || (inventory[3].itemID!=mod_ZZZMissile.superCoilsID+256 && inventory[3].itemID!=mod_ZZZMissile.copperCoilsID+256)) && heat > 0)
		{
			heat -= 100;
			storage += 100;
			if(storage>maxStorage)
				storage=maxStorage;
		}
    }
	
	public boolean isConverting()
    {
        return fuel > 0 && hasCoils() && ignited();
    }

    public boolean needsFuel()
    {
        return fuel <= 20 && storage + production <= maxStorage && hasCoils();
    }
    
    public boolean hasCoils()
    {
    	return inventory[3]!=null&&(inventory[3].itemID==mod_ZZZMissile.copperCoilsID+256||inventory[3].itemID==mod_ZZZMissile.superCoilsID+256);
    }
    
    public boolean ignited()
    {
    	return heat >= 5000;
    }
    
    public boolean hasHeaters()
    {
    	for(int panda=4;panda<=6;panda++)
    	{
    		if(inventory[panda]!=null&&(inventory[panda].itemID==mod_ZZZMissile.ohmicHeaterID+256 || inventory[panda].itemID==mod_ZZZMissile.neutralHeaterID+256 || inventory[panda].itemID==mod_ZZZMissile.rfHeaterID+256))
    			return true;
    	}
    	return false;
    }
    
    public boolean isAddedToEnergyNet()
    {
        return reallyAddedToEnergyNet;
    }

	@Override
	public ContainerIC2 getGuiContainer(EntityPlayer entityplayer) {
		return new ContainerFusion(entityplayer.inventory,this);
	}

	@Override
	public String getGuiClassName(EntityPlayer entityplayer) {
		return "GUIFusion";
	}
	
	public boolean canUpdate()
    {
		return true;
    }
}
