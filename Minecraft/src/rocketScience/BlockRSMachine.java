package rocketScience;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.src.*;
import ic2.common.*;
import ic2.platform.Ic2;
import ic2.platform.Platform;
import net.minecraft.src.forge.*;

public class BlockRSMachine extends BlockMultiID{
	
	public BlockRSMachine(int i)
	{
		super(i, Material.iron);
		setHardness(2);
	}
	
	protected int damageDropped(int i)
    {
		return i;
    }
	
	public Integer getGui(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
		int meta=world.getBlockMetadata(i,j,k);
		if(meta==0)
			return Integer.valueOf(mod_ZZZMissile.guiIsotopeID);
		else if(meta==1)
			return Integer.valueOf(mod_ZZZMissile.guiAutoMinerID);
		else if(meta==2)
			return Integer.valueOf(mod_ZZZMissile.guiDefenseID);
		else if(meta==3)
			return Integer.valueOf(mod_ZZZMissile.guiOffenseID);
		else if(meta==4)
			return Integer.valueOf(mod_ZZZMissile.guiLaserID);
		return null;
    }
	
	public TileEntityBlock getBlockEntity(int i)
    {
		if(i==0)
			return new TileEntityIsotope();
		else if(i==1)
			return new TileEntityAutoMiner();
		else if(i==2)
			return new TileEntityDefense();
		else if(i==3)
			return new TileEntityOffense();
		else if(i==4)
			return new TileEntityLaser();
		else if(i==5)
			return new TileEntityRadar();
		return null;
    }
	
	public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
    }
	
	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        int l = world.getBlockMetadata(i, j, k);
        if(l==5)
        {
        	//System.out.println(((TileEntityRadar)world.getBlockTileEntity(i,j,k)).storage);
            return false;
        } else
        {
        	Integer integer = getGui(world, i, j, k, entityplayer);
            if(integer == null)
            {
                return false;
            }
            if(!Platform.isSimulating())
            {
                return true;
            } else
            {
                ModLoader.openGUI(entityplayer, mod_RocketScience.getGuiForId(entityplayer, integer.intValue(), world.getBlockTileEntity(i, j, k)));
                return true;
            }
        }
    }
	
	public String getTextureFile() 
	{
		return "/rocketScience/blocks.png";
	}
	
	public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l)
	{
		int meta=iblockaccess.getBlockMetadata(i, j, k);
		if(meta==0)
		{
			if(l==0)
				return 11;
			else if(l==1)
				return 8;
			if(l==((TileEntityElecMachine)iblockaccess.getBlockTileEntity(i, j, k)).getFacing())
			{
				if(((TileEntityElecMachine)iblockaccess.getBlockTileEntity(i, j, k)).getActive())
					return 10;
				return 9;
			}
			return 11;
		}
		else if(meta==1)
		{
			if(l==1||l==0)
				return 8;
			return 16;
		}
		else if(meta==2)
		{
			if(l==0||l==1)
				return 21;
			return 22;
		}
		else if(meta==3)
		{
			if(l==0||l==1)
				return 19;
			return 20;
		}
		else if(meta==4)
		{
			if(l==0)
				return 11;
			else if(l==1)
			{
				return 24;
			}
			else
			{
				return 25;
			}
		}
		else if(meta==5)
		{
			if(l==0)
				return 4;
			else if(l==1)
				return 5;
			else
				return 23;
		}
		return 0;
	}
	
	public int getBlockTextureFromSideAndMetadata(int i, int j)
    {
		if(j==0)
		{
			if(i==1||i==0)
				return 8;
			else if(i==3)
				return 9;
			return 11;
		}
		else if(j==1)
		{
			if(i==1||i==0)
				return 8;
			return 16;
		}
		else if(j==2)
		{
			if(i==1||i==0)
				return 21;
			return 22;
		}
		else if(j==3)
		{
			if(i==1||i==0)
				return 19;
			return 20;
		}
		else if(j==4)
		{
			if(i==0)
				return 11;
			else if(i==1)
				return 24;
			else
				return 25;
		}
		else if(j==5)
		{
			if(i==0)
				return 4;
			else if(i==1)
				return 5;
			else
				return 23;
		}
		return 0;
    }
	
	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
		if(!(world.getBlockTileEntity(i, j, k) instanceof TileEntityAutoMiner))
			return;
		((TileEntityAutoMiner)world.getBlockTileEntity(i,j,k)).mineType=0;
		ModLoader.getMinecraftInstance().ingameGUI.addChatMessage("Autominer reset!");
    }
	
	public void addCreativeItems(ArrayList arraylist)
	{
		arraylist.add(new ItemStack(this, 1, 0));
		arraylist.add(new ItemStack(this, 1, 1));
		arraylist.add(new ItemStack(this, 1, 2));
		arraylist.add(new ItemStack(this, 1, 3));
		arraylist.add(new ItemStack(this, 1, 4));
		arraylist.add(new ItemStack(this, 1, 5));
	}
	
    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
    	int meta=world.getBlockMetadata(i, j, k);
    	if(meta==2)
    	{
    		((TileEntityDefense)world.getBlockTileEntity(i, j, k)).updateAttachedDevices();
    	}
    	else if(meta==3)
    	{
    		((TileEntityOffense)world.getBlockTileEntity(i, j, k)).updateAttachedDevices();
    		((TileEntityOffense)world.getBlockTileEntity(i, j, k)).updateAttachedMissiles();
    	}
    	else if(meta==4)
    	{
    		((TileEntityLaser)world.getBlockTileEntity(i, j, k)).updateAttachedDevices();
    	}
    	else if(meta==5)
    	{
    		((TileEntityRadar)world.getBlockTileEntity(i, j, k)).updateAttachedDevices();
    	}
    }
    
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public int getRenderType()
    {
        return mod_ZZZMissile.machineModelID;
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
}
