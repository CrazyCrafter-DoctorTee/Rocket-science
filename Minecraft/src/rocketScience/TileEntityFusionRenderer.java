package rocketScience;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntitySpecialRenderer;

public class TileEntityFusionRenderer extends TileEntitySpecialRenderer {

	private ModelFusionReactor Model;
	
	public TileEntityFusionRenderer(){
		Model = new ModelFusionReactor();
	}
	@Override
	public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8) {
		bindTextureByName("rocketScience/FusionSkin.png");
		GL11.glPushMatrix();
		GL11.glTranslatef((float)var2+0.5F, (float)var4+1.5F, (float)var6+0.5F);
		GL11.glRotatef(0, 0.0F,1.0F, 0.0F);
		

	}

}
