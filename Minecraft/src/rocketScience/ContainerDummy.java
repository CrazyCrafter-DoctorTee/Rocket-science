package rocketScience;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.TileEntity;
import ic2.common.ContainerIC2;

public class ContainerDummy extends ContainerIC2
{
	public ContainerDummy(InventoryPlayer inv, TileEntity titty)
	{
		
	}

	public int guiInventorySize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getInput() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void updateProgressBar(int i, int j) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		// TODO Auto-generated method stub
		return true;
	}
}
