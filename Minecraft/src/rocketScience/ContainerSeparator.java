package rocketScience;

import java.util.List;
import net.minecraft.src.*;
import ic2.common.*;

public class ContainerSeparator extends ContainerIC2
{

 public ContainerSeparator(InventoryPlayer inventoryplayer, TileEntityIsotope tileentityiso)
 {
     progress = 0;
     energy = 0;
     tileentity = tileentityiso;
     addSlot(new Slot(tileentityiso, 0, 56, 17));
     addSlot(new SlotFurnace(inventoryplayer.player, tileentityiso, 2, 108, 17));
     addSlot(new SlotFurnace(inventoryplayer.player, tileentityiso, 3, 108, 53));
     addSlot(new Slot(tileentityiso, 1, 56, 53));
     for(int i = 0; i < 3; i++)
     {
         for(int k = 0; k < 9; k++)
         {
             addSlot(new Slot(inventoryplayer, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
         }

     }

     for(int j = 0; j < 9; j++)
     {
         addSlot(new Slot(inventoryplayer, j, 8 + j * 18, 142));
     }

 }

 public void updateCraftingResults()
 {
     super.updateCraftingResults();
     for(int i = 0; i < crafters.size(); i++)
     {
         ICrafting icrafting = (ICrafting)crafters.get(i);
         if(progress != tileentity.progress)
         {
             icrafting.updateCraftingInventoryInfo(this, 0, tileentity.progress);
         }
         if(energy != tileentity.energy)
         {
             icrafting.updateCraftingInventoryInfo(this, 1, tileentity.energy & 0xffff);
             icrafting.updateCraftingInventoryInfo(this, 2, tileentity.energy >>> 16);
         }
     }

     progress = tileentity.progress;
     energy = tileentity.energy;
 }

 public void updateProgressBar(int i, int j)
 {
     switch(i)
     {
     case 0: // '\0'
         tileentity.progress = (short)j;
         break;

     case 1: // '\001'
         tileentity.energy = tileentity.energy & 0xffff0000 | j;
         break;

     case 2: // '\002'
         tileentity.energy = tileentity.energy & 0xffff | j << 16;
         break;
     }
 }

 public boolean canInteractWith(EntityPlayer entityplayer)
 {
     return true;
 }

 public int guiInventorySize()
 {
     return 4;
 }

 public int getInput()
 {
     return firstEmptyFrom(0, 2, tileentity);
 }

 public TileEntityIsotope tileentity;
 public short progress;
 public int energy;
}
