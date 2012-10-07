package ic2.platform;

import ic2.common.*;
import net.minecraft.src.*;
import rocketScience.*;

import org.lwjgl.opengl.GL11;

public class GUISeparator extends GuiContainer{
		public GUISeparator(InventoryPlayer inventoryplayer, TileEntityIsotope tileentitymacerator)
	    {
	        super(new ContainerSeparator(inventoryplayer, tileentitymacerator));
	        tileentity = tileentitymacerator;
	    }
		
		public GUISeparator(ContainerSeparator container){
			super(container);
		}

	    protected void drawGuiContainerForegroundLayer()
	    {
	        fontRenderer.drawString("Isotopic Separator", 42, 6, 0x404040);
	        if(tileentity.getMaterial(0)==1)
	        	fontRenderer.drawString("Li-6 extraction: "+tileentity.percent+"%", 70, (ySize - 96) + 2, 0x404040);
	        else if(tileentity.getMaterial(0)==2)
	        	fontRenderer.drawString("H-2 extraction: "+tileentity.percent+"%", 70, (ySize - 96) + 2, 0x404040);
	        fontRenderer.drawString("Inventory", 8, (ySize - 96) + 2, 0x404040);
	    }

	    protected void drawGuiContainerBackgroundLayer(float f, int secks, int vylence)
	    {
	        int i = mc.renderEngine.getTexture("/rocketScience/GUISeparator.png");
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        mc.renderEngine.bindTexture(i);
	        int j = (width - xSize) / 2;
	        int k = (height - ySize) / 2;
	        drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
	        if(tileentity.energy > 0)
	        {
	            int l = (int)(14F*tileentity.getChargeLevel());
	            drawTexturedModalRect(j + 56, (k + 36 + 14) - l, 176, 14 - l, 14, l);
	        }
	        int i1 = (int)(24F*tileentity.getProgress());
	        drawTexturedModalRect(j + 79, k + 34, 176, 14, i1 + 1, 16);
	    }

	    public TileEntityIsotope tileentity;
}
