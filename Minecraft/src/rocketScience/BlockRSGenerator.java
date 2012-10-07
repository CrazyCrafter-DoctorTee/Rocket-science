package rocketScience;

import java.util.ArrayList;
import java.util.Random;

import ic2.common.*;
import ic2.platform.Platform;
import net.minecraft.src.*;
import net.minecraft.src.forge.*;

public class BlockRSGenerator extends BlockMultiID{

	public BlockRSGenerator(int i) 
	{
		super(i, Material.iron);
		setHardness(2);
	}

	protected int damageDropped(int i)
    {
		return 0;
    }
	
	public Integer getGui(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        TileEntity tileentity = world.getBlockTileEntity(i, j, k);
        switch(world.getBlockMetadata(i, j, k))
        {
        	default:
        		return Integer.valueOf(mod_ZZZMissile.guiFusionID);
        }
    }
	
	public TileEntityBlock getBlockEntity(int i)
    {
        switch(i)
        {
        	default:
        		return new TileEntityFusion();
        }
    }
	
	public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
    }
	
	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
		int l = world.getBlockMetadata(i, j, k);
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
	
	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
		TileEntityFusion titty=(TileEntityFusion)world.getBlockTileEntity(i, j, k);
		ModLoader.getMinecraftInstance().ingameGUI.addChatMessage("Heat: "+titty.heat+", fuel: "+titty.fuel+", ignited: "+titty.ignited()+", fuel type: "+titty.fueltype+", production: "+titty.production+", added to grid: "+titty.addedToEnergyNet+", storage: "+titty.storage+", breeding: "+titty.bred);
		//titty.bred=9499;
    }
	
	public String getTextureFile() 
	{
		return "/rocketScience/blocks.png";
	}
	
	public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l)
	 {
		if(l==1||l==0)
		{
			if(((TileEntityFusion)iblockaccess.getBlockTileEntity(i, j, k)).isConverting())
				return 6;
			else
				return 7;
		}
		else
			return 1;
	 }
	
	public int getBlockTextureFromSideAndMetadata(int i, int j)
    {
		if(i==1||i==0)
			return 7;
		return 1;
    }
	
	public void addCreativeItems(ArrayList arraylist)
	{
		arraylist.add(new ItemStack(this, 1, 0));
	}
	
}
