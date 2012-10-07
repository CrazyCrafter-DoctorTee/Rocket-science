package rocketScience;

import java.util.ArrayList;
import net.minecraft.src.*;
import ic2.common.*;
import ic2.common.ElectricItem;
import ic2.platform.Platform;
import java.util.Random;
import ic2.api.*;

public class TileEntityAutoMiner extends TileEntityMiner {
	public static boolean locating=false;
	public static long time=0;
	public static EntityPlayer thatGuy;
	
	public TileEntityAutoMiner()
	{
		super();
		inventory=new ItemStack[27];
		this.maxEnergy=10000;
		howManySquares=1;
	}
	
	public boolean provideEnergy()
	{
		for(int j=0;j<26;j++)
		{
			if(j==1||j==3||inventory[j]==null)
				continue;
        	Item item = inventory[j].getItem();
        	if(item instanceof ItemBattery && inventory[j].getItemDamage()!=item.getMaxDamage()-1 && inventory[j].getItemDamage()!=item.getMaxDamage())
        	{
        		//System.out.println(inventory[j].);
        		energy += ((ItemBattery)item).discharge(inventory[j], maxEnergy - energy, 3,false,false);
            	return true;
        	}
		}
		return false;
	}
	
	int hasPipe()
	{
        for(int i=0;i<26;i++)
        {
        	if(i==1||i==3)
        		continue;
        	if(inventory[i]!=null && inventory[i].itemID==Ic2Items.miningPipe.itemID)
        		return i;
        }
        return -1;
	}
	
	int hasUselessBlock()
	{
		for(int i=0;i<26;i++)
		{
			if(i==1||i==3)
        		continue;
			if(inventory[i]!=null&&inventory[i].itemID<256&&!ItemScanner.isValuable(inventory[i].itemID,inventory[i].getItemDamage())&&inventory[i].itemID!=Ic2Items.miningPipe.itemID)
				return i;
		}
		return -1;
	}
	
	public boolean canOperate()
    {
		if(mineType==2)
			return false;
        if(inventory[3] == null)
        {
            return false;
        }
        if(hasPipe()==-1)
        	return false;
        if(inventory[3].itemID != Ic2Items.miningDrill.itemID && inventory[3].itemID != Ic2Items.diamondDrill.itemID)
        {
            return false;
        } else
        {
            return !isStuck();
        }
    }
	
	public void updateEntity()
    {
		if(Platform.isSimulating()&&locating)
		{
			if(time!=worldObj.getWorldTime())
			{
				locating=false;
				return;
			}
			ModLoader.getMinecraftInstance().ingameGUI.addChatMessage("Autominer located at X="+(int)(xCoord-thatGuy.posX)+", Y="+(int)(yCoord-thatGuy.posY+1)+", Z="+(int)(zCoord-thatGuy.posZ)+" relative to player.");
		}
		if(Platform.isSimulating()&&mineType!=4)
        {

            if(mineType!=0&&energy<=100&&!provideEnergy())
            {
            	//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage("Autominer energy depleted; shutting down.");
            	mineType=4;
            	return;
            }
            boolean flag = isOperating();
            boolean flag1 = false;
            if(isOperating())
            {
                energy--;
                if(inventory[1] != null && (Item.itemsList[inventory[1].itemID] instanceof ItemScanner))
                {
                    energy -= ((ElectricItem)Item.itemsList[inventory[1].itemID]).charge(inventory[1], energy, 4,false,false);
                    energy -= ((ElectricItem)Item.itemsList[inventory[1].itemID]).charge(inventory[1], energy, 4,false,false);
                }
                if(inventory[3] != null && ((Item.itemsList[inventory[3].itemID] instanceof ItemElectricToolDrill) || (Item.itemsList[inventory[3].itemID] instanceof ItemElectricToolDDrill)))
                {
                    energy -= ElectricItem.charge(inventory[3], energy, 4,false, false);
                }
            }
            if(energy <= maxEnergy)
            {
                flag1 = provideEnergy();
            }
            if(flag)
            {
                flag1 = mine();
            }
            
            if(mineType==1&&(!canMine(worldObj.getBlockId(targetX, targetY, targetZ), worldObj.getBlockMetadata(targetX, targetY, targetZ)) || hasPipe()==-1))
            {
            	//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage("Autominer beginning to withdraw pipe...");
            	mineType=2;
            }
            
            if(mineType==2)
            {
                if(energy >= 2 && canWithdraw())
                {
                    targetY = -1;
                    miningTicker+=5;
                    energy -= 2;
                    if(miningTicker >= 20)
                    {
                        miningTicker = 0;
                        flag1 = withdrawPipe();
                    }
                } 
                else if(!canWithdraw())
                {
                	recycleAll();
                	if(inventoryFull())
                	{
                    	//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage("Autominer inventory is full; shutting down.");
                		mineType=4;
                	}
                	else
                	{
                		//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage("Here's where we fly!");
                		mineType=0;
                		int x=this.xCoord;
                		int z=this.zCoord;
                		if(nextDirection==0)
                			x+=9;
                		else if(nextDirection==1)
                			z+=9;
                		else if(nextDirection==2)
                			x-=9;
                		else
                			z-=9;
                		/*int y=worldObj.getTopSolidOrLiquidBlock(x, z);
                		worldObj.setBlockAndMetadata(x, y, z, mod_ZZZMissile.machineID,1);
                		TileEntityAutoMiner newMiner=(TileEntityAutoMiner)worldObj.getBlockTileEntity(x, y, z);
                		newMiner.energy=0;
                		newMiner.inventory=this.inventory.clone();
                		this.inventory=new ItemStack[27];
                		newMiner.mineType=0;*/
                		currentSquare++;
                		if(currentSquare==howManySquares)
                		{
                			currentSquare=0;
                			nextDirection++;
                			if(nextDirection==4)
                				nextDirection=0;
            			
                			if(readyToIncrease)
                			{
                				readyToIncrease=false;
                				howManySquares++;
                			}
                			else
                			{
                				readyToIncrease=true;
                			}
                		}
                		MissileMinerBoosterEntity booster=new MissileMinerBoosterEntity(worldObj,this.xCoord,this.yCoord,this.zCoord);//,x-this.xCoord,z-this.zCoord);
                		booster.targetX=x-this.xCoord;
                		booster.targetZ=z-this.zCoord;
                		booster.nextDirection=nextDirection;
                		booster.howManySquares=howManySquares;
                		booster.currentSquare=currentSquare;
                		booster.readyToIncrease=readyToIncrease;
                		booster.inventory=this.inventory.clone();
                		this.inventory=new ItemStack[27];
                		worldObj.spawnEntityInWorld(booster);
                		/*newMiner.nextDirection=this.nextDirection;
                		newMiner.howManySquares=this.howManySquares;
                		newMiner.readyToIncrease=this.readyToIncrease;
                		newMiner.currentSquare=this.currentSquare;*/
                		worldObj.setBlockWithNotify(xCoord, yCoord, zCoord, 0);
                	}
                    miningTicker = 0;
                }
            }
            setActive(isOperating());
            if(flag != isOperating())
            {
                flag1 = true;
            }
            if(flag1)
            {
                aquireTarget();
            }
        }
    }
	
	public void mineBlock()
    {
        ItemElectricTool itemelectrictool = (ItemElectricTool)Item.itemsList[inventory[3].itemID];
        ItemElectricTool _tmp = itemelectrictool;
        ElectricItem.use(inventory[3], 50, null);
        int i = worldObj.getBlockId(targetX, targetY, targetZ);
        int j = worldObj.getBlockMetadata(targetX, targetY, targetZ);
        boolean flag = false;
        if(i == Block.waterMoving.blockID || i == Block.waterStill.blockID || i == Block.lavaMoving.blockID || i == Block.lavaStill.blockID)
        {
            flag = true;
            if(j != 0)
            {
                i = 0;
            }
        }
        if(i != 0)
        {
            if(!flag)
            {
                Block block = Block.blocksList[i];
                ArrayList list=block.getBlockDropped(worldObj, xCoord, yCoord, zCoord, j, 0);
                addToInventory(list);
            } else
            {
                if(i == Block.waterMoving.blockID || i == Block.waterStill.blockID)
                {
                    usePump(Block.waterStill.blockID);
                }
                if(i == Block.lavaMoving.blockID || i == Block.lavaStill.blockID)
                {
                    usePump(Block.lavaStill.blockID);
                }
            }
            worldObj.setBlockWithNotify(targetX, targetY, targetZ, 0);
            energy -= 2 * (yCoord - targetY);
        }
        if(targetX == xCoord && targetZ == zCoord)
        {
            worldObj.setBlock(targetX, targetY, targetZ, Ic2Items.miningPipe.itemID);
            int pipe=hasPipe();
            if(pipe==-1)
            	return;
            inventory[pipe].stackSize--;
            if(inventory[pipe].stackSize == 0)
            {
                inventory[pipe] = null;
            }
            energy -= 10;
        }
        updateMineTip(targetY);
        targetY = -1;
    }
	
	public boolean withdrawPipe()
    {
        int i = getPipeTip();
        worldObj.setBlockWithNotify(xCoord, i, zCoord, 0);
        int useless=hasUselessBlock();
        if(useless!=-1 && inventory[useless] != null && inventory[useless].itemID < 256)
        {
            worldObj.setBlockWithNotify(xCoord, i, zCoord, inventory[useless].itemID);
            inventory[useless].stackSize--;
            if(inventory[useless].stackSize == 0)
            {
                inventory[useless] = null;
            }
            updateMineTip(i + 1);
            ArrayList arraylist = new ArrayList();
            arraylist.add(new ItemStack(Ic2Items.miningPipe.getItem()));
            addToInventory(arraylist);
            return true;
        } else
        {
            updateMineTip(i + 1);
            ArrayList arraylist = new ArrayList();
            arraylist.add(new ItemStack(Ic2Items.miningPipe.getItem()));
            addToInventory(arraylist);
            return false;
        }
    }
	
	public void readFromNBT(NBTTagCompound nbttagcompound)
    {
		super.readFromNBT(nbttagcompound);
		nextDirection=nbttagcompound.getShort("nextDirection");
		howManySquares=nbttagcompound.getInteger("howManySquares");
		currentSquare=nbttagcompound.getInteger("currentSquare");
		mineType=nbttagcompound.getInteger("mineType");
		readyToIncrease=nbttagcompound.getBoolean("readyToIncrease");
    }
	
	public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setShort("nextDirection", nextDirection);
        nbttagcompound.setInteger("howManySquares", howManySquares);
        nbttagcompound.setInteger("currentSquare", currentSquare);
        nbttagcompound.setInteger("mineType",mineType);
        nbttagcompound.setBoolean("readyToIncrease", readyToIncrease);
    }
	
	public String getInvName()
    {
        return "Auto-Miner";
    }
	
	public Container getGuiContainer(InventoryPlayer inventoryplayer)
    {
        return new ContainerAutoMiner(inventoryplayer, this);
    }
	
	public boolean mine()
    {
		boolean mine=super.mine();
		miningTicker+=1;
		if(mine&&mineType==0)
			mineType=1;
		return mine;
    }
	
	void addToInventory(ArrayList list)
	{
		if(list.size()==0)
			return;
		ItemStack stack=(ItemStack)list.get(0);
		int redstoneSucks=1;
		while(redstoneSucks<list.size())
		{
			stack.stackSize++;
			redstoneSucks++;
		}
		int id=stack.itemID;
		for(int i=0;i<26;i++)
		{
			if(i==1||i==3)
        		continue;
			if(inventory[i]!=null && inventory[i].itemID==stack.itemID)
			{
				if(inventory[i].stackSize+stack.stackSize<=64)
				{
					inventory[i].stackSize+=stack.stackSize;
					return;
				}
				else
				{
					stack.stackSize-=(64-inventory[i].stackSize);
					inventory[i].stackSize=64;
				}
			}
		}
		for(int i=0;i<26;i++)
		{
			if(inventory[i]==null)
			{
				inventory[i]=stack;
				return;
			}
		}
		recycleAll();
		for(int i=0;i<26;i++)
		{
			if(i==1||i==3)
        		continue;
			if(inventory[i]!=null && inventory[i].itemID==stack.itemID)
			{
				if(inventory[i].stackSize+stack.stackSize<=64)
				{
					inventory[i].stackSize+=stack.stackSize;
					return;
				}
				else
				{
					stack.stackSize-=(64-inventory[i].stackSize);
					inventory[i].stackSize=64;
				}
			}
		}
		for(int i=0;i<26;i++)
		{
			if(inventory[i]==null)
			{
				inventory[i]=stack;
				return;
			}
		}
		StackUtil.distributeDrop(this,list);
	}
	
	public boolean recycleAll()
	{
		if(inventory[26]==null || inventory[26].itemID!=Ic2Items.machine.itemID||inventory[26].getItemDamage()!=11)
			return false;
		int theblock=0;
		boolean toreturn=true;
		for(int i=0;i<26;i++)
		{
			if(i==1 || i==3||inventory[i]==null)
				continue;
			theblock=inventory[i].itemID;
			if(theblock==Block.cobblestone.blockID||theblock==Block.sand.blockID||theblock==Block.gravel.blockID||theblock==Block.dirt.blockID)
			{
				if(!recycleSlot(i))
					toreturn=false;
			}
		}
		return toreturn;
	}
	
	public boolean recycleSlot(int toRecycle)
	{
		ArrayList list=new ArrayList();
		list.add(StackUtil.copyWithSize(Ic2Items.scrap, 0));
		ItemStack theStuff=inventory[toRecycle];
		Random random=new Random();
		if(theStuff==null)
			return false;
		for(int i=0;i<theStuff.stackSize;i++)
		{
			if(random.nextInt(TileEntityRecycler.recycleChance())==0)
			{
				((ItemStack)list.get(0)).stackSize++;
			}
			energy-=100;
		}
		inventory[toRecycle]=null;
		if(((ItemStack)list.get(0)).stackSize==0)
			return false;
		addToInventory(list);
		return true;
	}
	
	public boolean inventoryFull()
	{
		for(int i=0;i<26;i++)
		{
			if(i==1||i==3)
				continue;
			if(inventory[i]==null)
				return false;
		}
		return true;
	}
	
	public boolean canUpdate()
    {
		return true;
    }
	
	public short nextDirection=0;
	public int howManySquares=0;
	public int currentSquare=0;
	public int mineType=0; //0 hasn't mined yet, 1 mining, 2 withdrawing, 3 flying, 4 off
	public boolean readyToIncrease=false;
}
