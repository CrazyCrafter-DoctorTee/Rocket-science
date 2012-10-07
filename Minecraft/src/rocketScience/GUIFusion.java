package ic2.platform;

import net.minecraft.src.*;
import ic2.common.*;
import rocketScience.*;

import org.lwjgl.opengl.GL11;

public class GUIFusion extends GuiContainer {

	public GUIFusion(InventoryPlayer inventoryplayer, TileEntityFusion fusion)
    {
        super(new ContainerFusion(inventoryplayer, fusion));
        tileentity = fusion;
    }
	
	public GUIFusion(ContainerFusion container){
		super(container);
	}

    protected void drawGuiContainerForegroundLayer()
    {
        fontRenderer.drawString("Fusion Reactor", 48, 6, 0x404040);
        fontRenderer.drawString("Heat", 17, 27, 0x404040);
        fontRenderer.drawString("Fuel", 150, 55, 0x404040);
        fontRenderer.drawString("Tritium Bred:",110,20,0x404040);
        fontRenderer.drawString((int)(tileentity.bred/95)+"%",130,30,0x404040);
    }

    protected void drawGuiContainerBackgroundLayer(float f, int secks, int vylence)
    {
        int i = mc.renderEngine.getTexture("/rocketScience/GUIFusion.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
        
        int i1 = tileentity.gaugeHeatScaled(24);
        drawTexturedModalRect(j + 15, k + 37, 176, 14, i1, 17);
        
        i1 = tileentity.gaugeFuelScaled(20)+1;
        drawTexturedModalRect(j + 88, k + 38 + 22-i1, 176, 31+22-i1, 23, i1);
    }

    public TileEntityFusion tileentity;

	
}
