package rocketScience;

import net.minecraft.src.*;
import net.minecraft.src.forge.*;

public class MissileWarheadBlock extends Block implements ITextureProvider{
	
	public int nuke=1;
	public int incendiary=3;
	public int tnt=2;
	public int cartside=4;
	public int carttop=5;
	
	public MissileWarheadBlock(int id)
	{
		super(id, Material.rock);
		this.setBlockUnbreakable();
	}
	
	public int getBlockTextureFromSideAndMetadata(int side, int data)
    {

		if (data==12||data==0||data==15)
		{
			if(side==1||side==0)
				return carttop;
			else
				return cartside;
		}
		
		if(side==1)
			return 11;
		else if (data==14)
			return tnt;
		else if (data==4)
			return incendiary;
		else
			return nuke;
    }
	
	public boolean renderAsNormalBlock()
    {
        return false;
    }

    public int getRenderType()
    {
        return mod_ZZZMissile.warheadModelID;
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
    	mod_ZZZMissile.booster.onBlockClicked(world,i,j-1,k,entityplayer);
    }
    
    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
    	return mod_ZZZMissile.booster.blockActivated(world,i,j-1,k,entityplayer);
    }
    
    public String getTextureFile() 
	{
		return "/rocketScience/blocks.png";
	}
}
