package rocketScience;

import ic2.common.*;
import net.minecraft.src.*;

public class ContainerAutoMiner extends ContainerIC2 {

	public ContainerAutoMiner(InventoryPlayer inventoryplayer, TileEntityAutoMiner tileentityminer)
    {
        miningTicker = 0;
        energy = 0;
        tileentity = tileentityminer;
        addSlot(new Slot(tileentityminer, 3, 8, 16));
        addSlot(new Slot(tileentityminer, 1, 8, 52));
        addSlot(new Slot(tileentityminer, 26, 8, 34));
        
        addSlot(new Slot(tileentityminer,0,26,16));
        addSlot(new Slot(tileentityminer,2,44,16));
        for(int k=4;k<10;k++)
        {
        	addSlot(new Slot(tileentityminer,k,44+((k-3)*18),16));
        }
        for(int k=10;k<18;k++)
        {
        	addSlot(new Slot(tileentityminer,k,26+((k-10)*18),34));
        }for(int k=18;k<26;k++)
        {
        	addSlot(new Slot(tileentityminer,k,26+((k-18)*18),52));
        }
        
        for(int i = 0; i < 3; i++)
        {
            for(int k = 0; k < 9; k++)
            {
                addSlot(new Slot(inventoryplayer, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
            }

        }

        for(int j = 0; j < 9; j++)
        {
            addSlot(new Slot(inventoryplayer, j, 8 + j * 18, 142));
        }

    }

    /*public void updateCraftingResults()
    {
        super.updateCraftingResults();
        for(int i = 0; i < crafters.size(); i++)
        {
            ICrafting icrafting = (ICrafting)crafters.get(i);
            if(miningTicker != tileentity.miningTicker)
            {
                icrafting.updateCraftingInventoryInfo(this, 0, tileentity.miningTicker);
            }
            if(energy != tileentity.energy)
            {
                icrafting.updateCraftingInventoryInfo(this, 1, tileentity.energy & 0xffff);
                icrafting.updateCraftingInventoryInfo(this, 2, tileentity.energy >>> 16);
            }
        }

        miningTicker = tileentity.miningTicker;
        energy = tileentity.energy;
    }

    public void updateProgressBar(int i, int j)
    {
        switch(i)
        {
        case 0: // '\0'
            tileentity.miningTicker = (short)j;
            break;

        case 1: // '\001'
            tileentity.energy = tileentity.energy & 0xffff0000 | j;
            break;

        case 2: // '\002'
            tileentity.energy = tileentity.energy & 0xffff | j << 16;
            break;
        }
    }*/

    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return true;
    }

    public int guiInventorySize()
    {
        return 26;
    }

    public int getInput()
    {
        return 0;
    }

    public TileEntityMiner tileentity;
    public short miningTicker;
    public int energy;
	@Override
	public void updateProgressBar(int i, int j) {
		// TODO Auto-generated method stub
		
	}

}
