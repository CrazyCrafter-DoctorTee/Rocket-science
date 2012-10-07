package ic2.platform;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.*;
import ic2.common.*;
import ic2.platform.*;
import rocketScience.*;

public class GUIAutoMiner extends GuiContainer {

	public GUIAutoMiner(InventoryPlayer inventoryplayer, TileEntityAutoMiner tileentityminer)
    {
        super(new ContainerAutoMiner(inventoryplayer, tileentityminer));
        tileentity = tileentityminer;
    }
	
	public GUIAutoMiner(ContainerAutoMiner container){
		super(container);
	}

    protected void drawGuiContainerForegroundLayer()
    {
        fontRenderer.drawString("Mobile Autominer", 48, 6, 0x404040);
        fontRenderer.drawString("Inventory", 8, (ySize - 96) + 2, 0x404040);
    }

    protected void drawGuiContainerBackgroundLayer(float f, int secks, int vylence)
    {
        int i = mc.renderEngine.getTexture("/rocketScience/GUIAutoMiner.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
        /*if(tileentity.energy > 0)
        {
            int l = tileentity.gaugeEnergyScaled(14);
            drawTexturedModalRect(j + 81, (k + 41 + 14) - l, 176, 14 - l, 14, l);
        }*/
        //Fix the little lightning bolt here
    }

    public TileEntityAutoMiner tileentity;
	
}
