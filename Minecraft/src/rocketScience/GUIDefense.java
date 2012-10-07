package ic2.platform;

import ic2.common.ContainerElectricBlock;

import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import rocketScience.*;

import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;

public class GUIDefense extends GuiContainer
{
	TileEntityDefense defense;
	
	public GUIDefense(InventoryPlayer inventoryplayer, TileEntityDefense def)
	{
		super(new ContainerDummy(inventoryplayer,def));
		defense=def;
	}
	
	public GUIDefense(ContainerDummy container){
		super(container);
	}
	
	protected void drawGuiContainerBackgroundLayer(float f, int secks, int vylence)
    {
		xSize=143;
		ySize=109;
        int i = mc.renderEngine.getTexture("/rocketScience/GUIDefense.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
    }
	
	protected void drawGuiContainerForegroundLayer()
    {
        fontRenderer.drawString("Missile Defense System", 32, 34, 0x404040);
        fontRenderer.drawString("Radars", 56, 48, 0x404040);
        fontRenderer.drawString("Online Lasers", 56, 70, 0x404040);
        fontRenderer.drawString("Offline Lasers", 56, 92, 0x404040);
        fontRenderer.drawString("Targeting Systems", 56, 114, 0x404040);
        int on=0; int off=0;
        Iterator it=defense.attachedLasers.iterator();
        while(it.hasNext())
        {
        	if(((TileEntityLaser)it.next()).isOnline())
        		on++;
        	else
        		off++;
        }
        fontRenderer.drawString(on+"", 30, 71, 0x40FF40);
        fontRenderer.drawString(off+"", 30, 93, 0x40FF40);
        on=0;
        it=defense.attachedRadar.iterator();
        while(it.hasNext())
        {
        	if(((TileEntityRadar)it.next()).storage>0)
        		on++;
        }
        fontRenderer.drawString(on+"", 30, 49, 0x40FF40);
        fontRenderer.drawString(defense.attachedOffense.size()+"", 30, 115, 0x40FF40);
    }
}
