package rocketScience;

import net.minecraft.src.*;
import ic2.api.IElectricItem;
import ic2.common.*;
import ic2.platform.Platform;

public class ItemVacuum extends ItemRS implements IElectricItem{
	
	public ItemVacuum(int i, int j)
    {
        super(i,j);
        maxStackSize = 1;
        setMaxDamage(202);
    }

    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
    	//chargeFromBatpack(itemstack, entityplayer);
    	if(itemstack.getItemDamage()>=201)
    		return itemstack;
    	int cellSlot=-1;
    	int firstEmptySlot=-1;
    	int sandSlot=-1;
    	int gravelSlot=-1;
    	int lavaSlot=-1;
    	int waterSlot=-1;
    	ItemStack[] theList=entityplayer.inventory.mainInventory;
    	for(int i=0;i<theList.length;i++)
    	{
    		if(theList[i]!=null&&theList[i].itemID==Ic2Items.cell.itemID)
    			cellSlot=i;
    		else if(theList[i]==null&&firstEmptySlot==-1)
    			firstEmptySlot=i;
    		else if(theList[i]!=null&&theList[i].itemID==Block.sand.blockID&&theList[i].stackSize<64)
    			sandSlot=i;
    		else if(theList[i]!=null&&theList[i].itemID==Block.gravel.blockID&&theList[i].stackSize<64)
    			gravelSlot=i;
    		else if(theList[i]!=null&&theList[i].itemID==Ic2Items.lavaCell.itemID&&theList[i].stackSize<64)
    			lavaSlot=i;
    		else if(theList[i]!=null&&theList[i].itemID==Ic2Items.waterCell.itemID&&theList[i].stackSize<64)
    			waterSlot=i;
    	}
    	
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
            if(true)
            {
                if(!entityplayer.canPlayerEdit(i, j, k))
                {
                    return itemstack;
                }
                if(world.getBlockMaterial(i, j, k) == Material.water && cellSlot!=-1 && (waterSlot!=-1 || firstEmptySlot!=-1))
                {
                	meta=world.getBlockMetadata(i, j, k);
                    world.setBlockWithNotify(i, j, k, 0);
            		entityplayer.inventory.addItemStackToInventory(new ItemStack(Ic2Items.waterCell.getItem()));
            		entityplayer.inventory.consumeInventoryItem(Ic2Items.cell.itemID);
            		use(itemstack,1,entityplayer);
                }
                else if(world.getBlockMaterial(i, j, k) == Material.lava && cellSlot!=-1 && (lavaSlot!=-1 || firstEmptySlot!=-1))
                {
                	meta=world.getBlockMetadata(i, j, k);
                    world.setBlockWithNotify(i, j, k, 0);
            		entityplayer.inventory.addItemStackToInventory(new ItemStack(Ic2Items.lavaCell.getItem()));
            		entityplayer.inventory.consumeInventoryItem(Ic2Items.cell.itemID);
            		use(itemstack,1,entityplayer);
                }
                else if(world.getBlockId(i, j, k) == Block.sand.blockID  && (sandSlot!=-1 || firstEmptySlot!=-1))
                {
                	meta=world.getBlockMetadata(i, j, k);
                    world.setBlockWithNotify(i, j, k, 0);
            		entityplayer.inventory.addItemStackToInventory(new ItemStack(Block.sand));
            		use(itemstack,1,entityplayer);
                }
                else if(world.getBlockId(i, j, k) == Block.gravel.blockID && (gravelSlot!=-1 || firstEmptySlot!=-1))
                {
                	meta=world.getBlockMetadata(i, j, k);
                    world.setBlockWithNotify(i, j, k, 0);
            		entityplayer.inventory.addItemStackToInventory(new ItemStack(Block.gravel));
            		use(itemstack,1,entityplayer);
                }
            }
        }
        return itemstack;
    }
    
    private int meta;
    
	public int giveEnergyTo(ItemStack itemstack, int i, int j) 
	{
		if(itemstack.getItemDamage() == 1)
        {
            return 0;
        }
        int k = (itemstack.getItemDamage() - 1) * 50;
        if(i > 100)
        {
            i = 100;
        }
        if(k < i)
        {
            i = k;
        }
        for(; i % 50 != 0; i--) { }
        itemstack.setItemDamage(itemstack.getItemDamage() - i / 50);
        return i;
	}
	
	private void use(ItemStack itemstack, int i, EntityPlayer entityplayer)
	{
		//chargeFromBatpack(itemstack, entityplayer);
		if(itemstack.getItemDamage() + i > itemstack.getMaxDamage() - 1)
        {
            if(Platform.isSimulating())
            {
                itemstack.setItemDamage(itemstack.getMaxDamage() - 1);
            }
            return;
        }
        if(Platform.isSimulating())
        {
            itemstack.setItemDamage(itemstack.getItemDamage() + i);
        }
	}
	
	/*public static void chargeFromBatpack(ItemStack itemstack, EntityPlayer entityplayer)
    {
        if(entityplayer == null || entityplayer.inventory.armorInventory[2] == null || (entityplayer.inventory.armorInventory[2].itemID != Ic2Items.batPack.itemID || entityplayer.inventory.armorInventory[2].itemID != Ic2Items.lapPack.itemID))
        {
            return;
        } else
        {
            ((ItemArmorBatpack)entityplayer.inventory.armorInventory[2].getItem()).useBatpackOn(itemstack, entityplayer.inventory.armorInventory[2]);
            return;
        }
    }*/

	@Override
	public boolean canProvideEnergy() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getChargedItemId() {
		// TODO Auto-generated method stub
		return shiftedIndex;
	}

	@Override
	public int getEmptyItemId() {
		// TODO Auto-generated method stub
		return shiftedIndex;
	}

	@Override
	public int getMaxCharge() {
		// TODO Auto-generated method stub
		return 10000;
	}

	@Override
	public int getTier() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getTransferLimit() {
		// TODO Auto-generated method stub
		return 100;
	}
}
