package rocketScience;

import net.minecraft.src.*;
import ic2.common.*;
import ic2.api.*;

public class ItemPassengerDepleted extends ItemRS implements IElectricItem
{
	int transfer = 50000;
	int ratio = 10;
	public ItemPassengerDepleted(int i, int j) {
		super(i, j);
		this.setMaxStackSize(64);
		this.setHasSubtypes(false);
	}
	
	public int giveEnergyTo(ItemStack itemstack, int i, int j, boolean flag)
    {
        if(itemstack.getItemDamage() == 1)
        {
        	itemstack.itemID=mod_ZZZMissile.boosterID;
        	itemstack.setItemDamage(14);
            return 0;
        }
        int k = (itemstack.getItemDamage() - 1) * ratio;
        if(!flag && transfer != 0 && i > transfer)
        {
            i = transfer;
        }
        if(k < i)
        {
            i = k;
        }
        for(; i % ratio != 0; i--) { }
        itemstack.setItemDamage(itemstack.getItemDamage() - i / ratio);
        if(itemstack.getItemDamage()<=10||itemstack.getTagCompound().getInteger("charge")>=100000)
        {
        	itemstack.itemID=mod_ZZZMissile.boosterID;
        	itemstack.setItemDamage(14);
        }
        return i;
    }

	@Override
	public boolean canProvideEnergy() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getChargedItemId() {
		// TODO Auto-generated method stub
		return mod_ZZZMissile.boosterID;
	}

	@Override
	public int getEmptyItemId() {
		// TODO Auto-generated method stub
		return shiftedIndex;
	}

	@Override
	public int getMaxCharge() {
		// TODO Auto-generated method stub
		return 100000;
	}

	@Override
	public int getTier() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getTransferLimit() {
		// TODO Auto-generated method stub
		return 50000;
	}
}
