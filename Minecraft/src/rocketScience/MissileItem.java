package rocketScience;

import net.minecraft.src.*;

public class MissileItem extends ItemBlock
{
	  public MissileItem(int i)
	  {
	    super(i);
	    setHasSubtypes(true);
	  }
	  
	  public String getItemNameIS(ItemStack itemstack)
	  {
		  	if(itemstack.getItemDamage()==12)
		  		return "Passenger Rocket";
		  	else if(itemstack.getItemDamage()==8)
	        	return "Nuclear Missile";
	        else if(itemstack.getItemDamage()==4)
	        	return "Incendiary Missile";
		  	else if(itemstack.getItemDamage()==13)
	        	return "Thermonuclear Missile";
		  	else if(itemstack.getItemDamage()==14)
	        	return "Reusable Passenger Rocket";
		  	else if(itemstack.getItemDamage()==15)
	        	return "Reusable Passenger Rocket (half charge)";
	        else
	        	return "Missile";
	  }
	  public int getPlacedBlockMetadata(int i)
	  {
		  return i;
	  }
	  public int getMetadata(int i)
	    {
	        return i;
	    }
}
