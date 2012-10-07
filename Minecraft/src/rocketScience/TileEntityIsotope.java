package rocketScience;

import java.util.HashMap;
import net.minecraft.src.*;
import ic2.common.*;

public class TileEntityIsotope extends TileEntityElectricMachine {
	
	public TileEntityIsotope()
    {
        super(4, 5, 100, 32);
        percent=0;
        material=0;
    }

    public ItemStack getResultFor(ItemStack itemstack)
    {
        return (ItemStack)recipes.get(Integer.valueOf(itemstack.itemID));
    }

    public static void initRecipes()
    {
        recipes = new HashMap();
        addRecipe(Ic2Items.waterCell.itemID, new ItemStack(mod_ZZZMissile.cellDeuterium, 1));
        addRecipe(mod_ZZZMissile.lithiumCell.shiftedIndex, new ItemStack(mod_ZZZMissile.lithium6Cell,1));
    }

    public static void addRecipe(int i, ItemStack itemstack)
    {
        recipes.put(Integer.valueOf(i), itemstack);
    }

    public String getInvName()
    {
        return "Separator";
    }

    public String getStartSoundFile()
    {
        return "Machines/MaceratorOp.ogg";
    }

    public String getInterruptSoundFile()
    {
        return "Machines/InterruptOne.ogg";
    }

    public Container getGuiContainer(InventoryPlayer inventoryplayer)
    {
        return new ContainerSeparator(inventoryplayer, this);
    }
    
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        percent = nbttagcompound.getShort("percent");
        material = nbttagcompound.getShort("material");
    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setShort("percent", percent);
        nbttagcompound.setShort("material", material);
    }
    
    public void operate()
    {
        if(!canOperate())
        {
            return;
        }
        if(inventory[0].itemID==Ic2Items.waterCell.itemID)
        	material=2;
        else if (inventory[0].itemID==mod_ZZZMissile.lithiumCell.shiftedIndex)
        	material=1;
        
        ItemStack itemstack = getResultFor(inventory[0]);
        inventory[0].stackSize--;
        if(inventory[0].stackSize <= 0)
        {
            inventory[0] = null;
        }
        if (material==2)
        	percent += 10;
        else
        	percent += 5;
        if(percent < 100)
        {
        	if(inventory[3]==null)
            	inventory[3]=new ItemStack(Ic2Items.cell.getItem());
            else
            	inventory[3].stackSize++;
        	return;
        }
        if(inventory[2] == null)
        {
            inventory[2] = itemstack.copy();
        } 
        else
        {
            inventory[2].stackSize += itemstack.stackSize;
        }
        percent=0;
        material=0;
    }
    
    public boolean canOperate()
    {
    	if(inventory[3]!=null)
    	{
    		if(inventory[3].itemID!=Ic2Items.cell.itemID)
    			return false;
    		else if(inventory[3].stackSize>=64)
    			return false;
    	}
    	if(inventory[0]==null)
    		return false;
    	if(material==2&&inventory[0].itemID!=Ic2Items.waterCell.itemID)
    		return false;
    	if(material==1&&inventory[0].itemID!=mod_ZZZMissile.lithiumCell.shiftedIndex)
    		return false;
    	return super.canOperate();
    }
    
    public int getMaterial(int i)
    {
    	return material;
    }

    public static HashMap recipes;
    public short percent;
    public short material;
	@Override
	public ItemStack getResultFor(ItemStack itemstack, boolean flag) {
		if(itemstack.itemID==Ic2Items.waterCell.itemID)
			return new ItemStack(mod_ZZZMissile.cellDeuterium);
		else if(itemstack.itemID==mod_ZZZMissile.lithiumCell.shiftedIndex)
			return new ItemStack(mod_ZZZMissile.lithium6Cell);
		return null;
	}

	@Override
	public String getGuiClassName(EntityPlayer entityplayer) {
		return "GUISeparator";
	}
	
	public boolean canUpdate()
    {
		return true;
    }
}
