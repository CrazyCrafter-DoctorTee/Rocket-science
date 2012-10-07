package rocketScience;

import java.util.List;
import net.minecraft.src.*;
import ic2.common.*;

public class ContainerFusion extends ContainerIC2
{

    public ContainerFusion(InventoryPlayer inventoryplayer, TileEntityFusion tileentityfusion)
    {
    	fueltype=0;
        heat = 0;
        fuel = 0;
        tileentity = tileentityfusion;
        addSlot(new Slot(tileentityfusion, 0, 19, 61));
        addSlot(new Slot(tileentityfusion, 1, 131, 41));
        addSlot(new Slot(tileentityfusion, 2, 131, 62));
        addSlot(new Slot(tileentityfusion, 3, 91, 63));
        addSlot(new Slot(tileentityfusion, 4, 51, 21));
        addSlot(new Slot(tileentityfusion, 5, 51, 41));
        addSlot(new Slot(tileentityfusion, 6, 51, 61));
        addSlot(new Slot(tileentityfusion, 7, 91, 20));
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

    public void updateCraftingResults()
    {
        super.updateCraftingResults();
    }

    public void updateProgressBar(int i, int j)
    {
        if(i == 0)
        {
            tileentity.heat = (short)j;
        }
        if(i == 1)
        {
            tileentity.fuel = (short)j;
        }
    }

    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return true;
    }

    public int guiInventorySize()
    {
        return 7;
    }

    public TileEntityFusion tileentity;
    public short heat;
    public short fuel;
    public short fueltype;
	@Override
	public int getInput() 
	{
		return 0;
	}
}