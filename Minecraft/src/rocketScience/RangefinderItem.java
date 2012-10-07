package rocketScience;

import net.minecraft.src.*;

public class RangefinderItem extends ItemRS {

	public RangefinderItem(int i, int j)
    {
        super(i, j);
        maxStackSize = 1;
    }
	
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
		RangefinderEntity entityarrow = new RangefinderEntity(world, entityplayer, 1.0F);
        //if(!world.multiplayerWorld)
        //{
            world.spawnEntityInWorld(entityarrow);
        //}
        return itemstack;
    }
	
	public static int ecks=0;
	public static int zee=0;
}
