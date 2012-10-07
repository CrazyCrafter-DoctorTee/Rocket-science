package rocketScience;

import ic2.api.Direction;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.src.*;

public class BlockSuperconductor extends BlockMultiID
{

 public BlockSuperconductor(int i)
 {
     super(i,Material.sand);
     setHardness(0.4F);
 }

 public String getTextureFile() 
	{
		return "/rocketScience/blocks.png";
	}

 public int idDropped(int i, Random random, int j)
 {
     return mod_RocketScience.superconductorID;
 }
 
 public int quantityDropped(int i, int j, Random random)
 {
	 return 1;
 }
 
 public ArrayList getBlockDropped(World world, int i, int j, int k, int l, int i1)
 {
     ArrayList arraylist = new ArrayList();
     arraylist.add(new ItemStack(mod_RocketScience.superconductor));
     return arraylist;
 }

 public TileEntityBlock getBlockEntity()
 {
     return new TileEntitySuperconductor();
 }

 public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer)
 {
 }
 
 public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l)
 {
	 if(l==1||l==0)
		 return 13;
	 return 12;
 }
 
 public int getBlockTextureFromSideAndMetadata(int i, int j)
 {
	return 1; 
 }
 
 public void addCreativeItems(ArrayList arraylist)
	{
		arraylist.add(new ItemStack(this, 1, 0));
	}
 
 public MovingObjectPosition collisionRayTrace(World world, int i, int j, int k, Vec3D vec3d, Vec3D vec3d1)
 {
     TileEntity tileentity = world.getBlockTileEntity(i, j, k);
     if(!(tileentity instanceof TileEntityCable))
     {
         return null;
     }
     TileEntityCable tileentitycable = (TileEntityCable)tileentity;
     Vec3D vec3d2 = Vec3D.createVectorHelper(vec3d1.xCoord - vec3d.xCoord, vec3d1.yCoord - vec3d.yCoord, vec3d1.zCoord - vec3d.zCoord);
     double d = vec3d2.lengthVector();
     double d1 = (double)tileentitycable.getCableThickness() / 2D;
     boolean flag = false;
     Vec3D vec3d3 = Vec3D.createVectorHelper(0.0D, 0.0D, 0.0D);
     Direction direction = AabbUtil.getIntersection(vec3d, vec3d2, AxisAlignedBB.getBoundingBox(((double)i + 0.5D) - d1, ((double)j + 0.5D) - d1, ((double)k + 0.5D) - d1, (double)i + 0.5D + d1, (double)j + 0.5D + d1, (double)k + 0.5D + d1), vec3d3);
     if(direction != null && vec3d3.distanceTo(vec3d) <= d)
     {
         flag = true;
     }
     Direction adirection[] = Direction.values();
     int l = adirection.length;
     for(int i1 = 0; i1 < l; i1++)
     {
         Direction direction1 = adirection[i1];
         if(flag)
         {
             break;
         }
         TileEntity tileentity1 = direction1.applyToTileEntity(tileentitycable);
         if(!tileentitycable.canInteractWith(tileentity1))
         {
             continue;
         }
         AxisAlignedBB axisalignedbb = null;

         switch(_cls2.SwitchMap[direction1.ordinal()])
         {
         case 1: // '\001'
             axisalignedbb = AxisAlignedBB.getBoundingBox(i, ((double)j + 0.5D) - d1, ((double)k + 0.5D) - d1, (double)i + 0.5D, (double)j + 0.5D + d1, (double)k + 0.5D + d1);
             break;

         case 2: // '\002'
             axisalignedbb = AxisAlignedBB.getBoundingBox((double)i + 0.5D, ((double)j + 0.5D) - d1, ((double)k + 0.5D) - d1, (double)i + 1.0D, (double)j + 0.5D + d1, (double)k + 0.5D + d1);
             break;

         case 3: // '\003'
             axisalignedbb = AxisAlignedBB.getBoundingBox(((double)i + 0.5D) - d1, j, ((double)k + 0.5D) - d1, (double)i + 0.5D + d1, (double)j + 0.5D, (double)k + 0.5D + d1);
             break;

         case 4: // '\004'
             axisalignedbb = AxisAlignedBB.getBoundingBox(((double)i + 0.5D) - d1, (double)j + 0.5D, ((double)k + 0.5D) - d1, (double)i + 0.5D + d1, (double)j + 1.0D, (double)k + 0.5D + d1);
             break;

         case 5: // '\005'
             axisalignedbb = AxisAlignedBB.getBoundingBox(((double)i + 0.5D) - d1, ((double)j + 0.5D) - d1, k, (double)i + 0.5D + d1, (double)j + 0.5D, (double)k + 0.5D);
             break;

         case 6: // '\006'
             axisalignedbb = AxisAlignedBB.getBoundingBox(((double)i + 0.5D) - d1, ((double)j + 0.5D) - d1, (double)k + 0.5D, (double)i + 0.5D + d1, (double)j + 0.5D + d1, (double)k + 1.0D);
             break;
         }
         direction = AabbUtil.getIntersection(vec3d, vec3d2, axisalignedbb, vec3d3);
         if(direction != null && vec3d3.distanceTo(vec3d) <= d)
         {
             flag = true;
         }
     }

     if(flag)
     {
         return new MovingObjectPosition(i, j, k, direction.toSideValue(), vec3d3);
     } else
     {
         return null;
     }
 }

 public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k, int l)
 {
     double d = (double)TileEntityCable.getCableThickness(l) / 2D;
     return AxisAlignedBB.getBoundingBoxFromPool(((double)i + 0.5D) - d, ((double)j + 0.5D) - d, ((double)k + 0.5D) - d, (double)i + 0.5D + d, (double)j + 0.5D + d, (double)k + 0.5D + d);
 }

 public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
 {
     TileEntity tileentity = world.getBlockTileEntity(i, j, k);
     if(tileentity == null || !(tileentity instanceof TileEntityCable))
     {
         return getCollisionBoundingBoxFromPool(world, i, j, k, 3);
     }
     TileEntityCable tileentitycable = (TileEntityCable)tileentity;
     double d = (double)tileentitycable.getCableThickness() / 2D;
     double d1 = ((double)i + 0.5D) - d;
     double d2 = ((double)j + 0.5D) - d;
     double d3 = ((double)k + 0.5D) - d;
     double d4 = (double)i + 0.5D + d;
     double d5 = (double)j + 0.5D + d;
     double d6 = (double)k + 0.5D + d;
     if(tileentitycable.canInteractWith(world.getBlockTileEntity(i - 1, j, k)))
     {
         d1 = i;
     }
     if(tileentitycable.canInteractWith(world.getBlockTileEntity(i, j - 1, k)))
     {
         d2 = j;
     }
     if(tileentitycable.canInteractWith(world.getBlockTileEntity(i, j, k - 1)))
     {
         d3 = k;
     }
     if(tileentitycable.canInteractWith(world.getBlockTileEntity(i + 1, j, k)))
     {
         d4 = i + 1;
     }
     if(tileentitycable.canInteractWith(world.getBlockTileEntity(i, j + 1, k)))
     {
         d5 = j + 1;
     }
     if(tileentitycable.canInteractWith(world.getBlockTileEntity(i, j, k + 1)))
     {
         d6 = k + 1;
     }
     return AxisAlignedBB.getBoundingBoxFromPool(d1, d2, d3, d4, d5, d6);
 }

 public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
 {
     return getCollisionBoundingBoxFromPool(world, i, j, k);
 }

 public boolean isBlockNormalCube(World world, int i, int j, int k)
 {
     return false;
 }
 
 public Integer getGui(World world, int i, int j, int k, EntityPlayer entityplayer)
 {
     return null;
 }
 
 public boolean renderAsNormalBlock()
 {
     return false;
 }

 public int getRenderType()
 {
     return mod_RocketScience.cableRenderId;
 }

 public boolean isOpaqueCube()
 {
     return false;
 }
 
 public boolean isPoweringTo(IBlockAccess iblockaccess, int i, int j, int k, int l)
 {
     return false;
 }

@Override
public TileEntityBlock getBlock(int i) {
	return new TileEntitySuperconductor();
}
}

class _cls2
{

    static final int SwitchMap[];

    static 
    {
    	SwitchMap = new int[Direction.values().length];
        try
        {
        	SwitchMap[Direction.XN.ordinal()] = 1;
        }
        catch(NoSuchFieldError nosuchfielderror) { }
        try
        {
        	SwitchMap[Direction.XP.ordinal()] = 2;
        }
        catch(NoSuchFieldError nosuchfielderror1) { }
        try
        {
        	SwitchMap[Direction.YN.ordinal()] = 3;
        }
        catch(NoSuchFieldError nosuchfielderror2) { }
        try
        {
        	SwitchMap[Direction.YP.ordinal()] = 4;
        }
        catch(NoSuchFieldError nosuchfielderror3) { }
        try
        {
        	SwitchMap[Direction.ZN.ordinal()] = 5;
        }
        catch(NoSuchFieldError nosuchfielderror4) { }
        try
        {
        	SwitchMap[Direction.ZP.ordinal()] = 6;
        }
        catch(NoSuchFieldError nosuchfielderror5) { }
    }
}
