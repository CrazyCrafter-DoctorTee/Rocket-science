package rocketScience;

import cpw.mods.fml.common.FMLCommonHandler;
import ic2.common.Ic2Items;
import net.minecraft.src.*;
import net.minecraft.src.forge.ITextureProvider;


public class MissileBoosterBlock extends BlockContainer implements ITextureProvider {
	
	EntityPlayer tempPlayer=null;
	
	public MissileBoosterBlock(int id, int j)
	{
		super(id, 14, Material.rock);
		this.setBlockUnbreakable();
		this.blockResistance=100F;
	}
	
	public int getBlockTextureFromSide(int side)
    {
    	if(1==side)
    	{
    		return 11;
    	}
    	else if(0==side)
    		return 7;
        return blockIndexInTexture;
    }
	
	public int getBlockTextureFromSideAndMetadata(int i, int j)
	{
		if((j==15||j==0)&&i!=1&&i!=0)
			return 17;
		return getBlockTextureFromSide(i);
	}
	
	public void onBlockDestroyedByExplosion(World world, int i, int j, int k)
    {
		try{
			MissileTileEntity title=(MissileTileEntity)world.getBlockTileEntity(i, j, k);
			title.launch(null);
		} catch(Exception ex){
			System.out.println("RocketScienceError");
			ex.printStackTrace();
			FMLCommonHandler.instance().raiseException(ex,"RocketScienceError",false);
		}
		
    }
	
	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
		world.markBlockNeedsUpdate(i, j, k);
		MissileTileEntity titty=(MissileTileEntity)world.getBlockTileEntity(i, j, k);
		
		if(entityplayer.getCurrentEquippedItem()!=null&&entityplayer.getCurrentEquippedItem().getItem() instanceof RangefinderItem)
		{
			titty.targetX=RangefinderItem.ecks;
			titty.targetZ=RangefinderItem.zee;
		}
		else
		{
			if((int)Math.floor(entityplayer.posX)>i)
				titty.targetX-=5;
			else if ((int)Math.floor(entityplayer.posX)<i)
				titty.targetX+=5;
		
			if((int)Math.floor(entityplayer.posZ)>k)
				titty.targetZ-=5;
			else if ((int)Math.floor(entityplayer.posZ)<k)
				titty.targetZ+=5;
		}

		String name;
		if(world.getBlockMetadata(i,j,k)==12||world.getBlockMetadata(i,j,k)==0||world.getBlockMetadata(i,j,k)==15)
	  		name="Passenger rocket";
	  	else if(world.getBlockMetadata(i,j,k)==8)
        	name="Nuclear missile";
        else if(world.getBlockMetadata(i,j,k)==4)
        	name="Incendiary missile";
	  	else if(world.getBlockMetadata(i,j,k)==13)
        	name="Thermonuclear missile";
        else
        	name="Missile";
		
		ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(name+" targeted at ("+titty.targetX+", "+titty.targetZ+") relative to current position.");
    }
	
	protected int damageDropped(int i)
    {
        return i;
    }

    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
    	int arg =world.getBlockMetadata(i,j,k);
    	if(entityplayer!=null&&entityplayer.getCurrentEquippedItem()!=null&&(entityplayer.getCurrentEquippedItem().itemID==Ic2Items.wrench.itemID || entityplayer.getCurrentEquippedItem().itemID==Ic2Items.electricWrench.itemID))
    	{
    		this.dropBlockAsItem(world, i, j, k, arg, 1);
    		world.setBlockWithNotify(i, j, k, 0);
    		world.setBlockWithNotify(i, j+1, k, 0);
    		return false;
    	}
    	
    	if(arg==12||arg==15||arg==0)
    	{
    		MissileTileEntity titty=(MissileTileEntity)world.getBlockTileEntity(i, j, k);
    		titty.launch(entityplayer);
    	}
        return false;
    }
    
    public void onBlockAdded(World world, int i, int j, int k)
    {
        super.onBlockAdded(world, i, j, k);
        if(world.isBlockIndirectlyGettingPowered(i, j, k)&&world.getBlockMetadata(i,j,k)!=12&& world.getBlockMetadata(i,j,k)!=0&& world.getBlockMetadata(i,j,k)!=15)
        {
            onBlockDestroyedByExplosion(world, i, j, k);
            world.setBlockWithNotify(i, j, k, 0);
        }
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        if(l > 0 && Block.blocksList[l].canProvidePower() && world.isBlockIndirectlyGettingPowered(i, j, k) && world.getBlockMetadata(i,j,k)!=12 && world.getBlockMetadata(i,j,k)!=0 && world.getBlockMetadata(i,j,k)!=15)
        {
            onBlockDestroyedByExplosion(world, i, j, k);
            world.setBlockWithNotify(i, j, k, 0);
        }
    }
    
    public TileEntity getBlockEntity()
    {
        return new MissileTileEntity();
    }
    
    public void onBlockPlaced(World world, int i, int j, int k, int l)
    {
    	world.setBlockWithNotify(i, j+1, k, mod_ZZZMissile.warheadID);
    	world.setBlockMetadata(i,j+1,k,world.getBlockMetadata(i,j,k));
    }
    
    public boolean canPlaceBlockAt(World world, int i, int j, int k)
    {
    	if(!super.canPlaceBlockAt(world, i, j, k))
    		return false;
   		int l = world.getBlockId(i, j+1, k);
        return l == 0;
    }
    
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public int getRenderType()
    {
        return mod_ZZZMissile.missileModelID;
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }

	public String getTextureFile() 
	{
		return "/rocketScience/blocks.png";
	}
}
