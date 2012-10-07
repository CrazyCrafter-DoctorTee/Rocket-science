package rocketScience;

import net.minecraft.src.forge.ITextureProvider;
import ic2.platform.ItemCommon;

public class ItemRS extends ItemCommon
    implements ITextureProvider
{

    public ItemRS(int i, int j)
    {
        super(i);
        setIconIndex(j);
    }

    public String getTextureFile()
    {
        return "/rocketScience/items.png";
    }
}