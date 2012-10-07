package rocketScience;

import ic2.common.Ic2Items;
import net.minecraft.src.*;

public class ItemHandPump extends ItemRS {
	
	public ItemHandPump(int i, int j)
	{
		super(i,j);
        maxStackSize = 1;
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
    	int cellSlot=-1;
    	int firstEmptySlot=-1;
    	int waterSlot=-1;
    	ItemStack[] theList=entityplayer.inventory.mainInventory;
    	for(int i=0;i<theList.length;i++)
    	{
    		if(theList[i]!=null&&theList[i].itemID==Ic2Items.cell.itemID&&theList[i].stackSize<64)
    			cellSlot=i;
    		else if(theList[i]==null&&firstEmptySlot==-1)
    			firstEmptySlot=i;
    		else if(theList[i]!=null&&theList[i].itemID==Ic2Items.waterCell.itemID)
    			waterSlot=i;
    	}
    	
    	if(waterSlot==-1 || (cellSlot==-1 && firstEmptySlot==-1))
    		return itemstack;
    	
    	float f = 1.0F;
        float f1 = entityplayer.prevRotationPitch + (entityplayer.rotationPitch - entityplayer.prevRotationPitch) * f;
        float f2 = entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * f;
        double d = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * (double)f;
        double d1 = (entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * (double)f + 1.6200000000000001D) - (double)entityplayer.yOffset;
        double d2 = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * (double)f;
        Vec3D vec3d = Vec3D.createVector(d, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.01745329F - 3.141593F);
        float f4 = MathHelper.sin(-f2 * 0.01745329F - 3.141593F);
        float f5 = -MathHelper.cos(-f1 * 0.01745329F);
        float f6 = MathHelper.sin(-f1 * 0.01745329F);
        float f7 = f4 * f5;
        float f8 = f6;
        float f9 = f3 * f5;
        double d3 = 5D;
        Vec3D vec3d1 = vec3d.addVector((double)f7 * d3, (double)f8 * d3, (double)f9 * d3);
        MovingObjectPosition movingobjectposition = world.rayTraceBlocks_do(vec3d, vec3d1, true);
        if(movingobjectposition == null)
        {
            return itemstack;
        }
        if(movingobjectposition.typeOfHit == EnumMovingObjectType.TILE)
        {
            int i = movingobjectposition.blockX;
            int j = movingobjectposition.blockY;
            int k = movingobjectposition.blockZ;
            if(!world.canMineBlock(entityplayer, i, j, k))
            {
                return itemstack;
            }
            if(movingobjectposition.sideHit == 0)
            {
                j--;
            }
            if(movingobjectposition.sideHit == 1)
            {
                j++;
            }
            if(movingobjectposition.sideHit == 2)
            {
                k--;
            }
            if(movingobjectposition.sideHit == 3)
            {
                k++;
            }
            if(movingobjectposition.sideHit == 4)
            {
                i--;
            }
            if(movingobjectposition.sideHit == 5)
            {
                i++;
            }
            if(!entityplayer.canPlayerEdit(i, j, k))
            {
                return itemstack;
            }
            if(world.isAirBlock(i, j, k) || !world.getBlockMaterial(i, j, k).isSolid())
            {
                if(world.worldProvider.isHellWorld)
                {
                    world.playSoundEffect(d + 0.5D, d1 + 0.5D, d2 + 0.5D, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
                    for(int l = 0; l < 8; l++)
                    {
                        world.spawnParticle("largesmoke", (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0D, 0.0D, 0.0D);
                    }

                } else
                {
                    world.setBlockAndMetadataWithNotify(i, j, k, Block.waterMoving.blockID, 0);
                }
                entityplayer.inventory.consumeInventoryItem(Ic2Items.waterCell.itemID);
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Ic2Items.cell.getItem()));
            }
        }
        return itemstack;
    }
}
