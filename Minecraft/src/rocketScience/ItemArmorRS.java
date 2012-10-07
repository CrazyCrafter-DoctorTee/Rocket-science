package rocketScience;

import net.minecraft.src.EnumArmorMaterial;
import net.minecraft.src.ItemArmor;
import net.minecraft.src.ItemStack;
import net.minecraft.src.forge.*;

public class ItemArmorRS extends ItemArmor
    implements IArmorTextureProvider ,ITextureProvider
{

    public ItemArmorRS(int i, int j, EnumArmorMaterial k, int l, int i1, int j1)
    {
        super(i, k, l, i1);
        setIconIndex(j);
        setMaxDamage(maxDamageArray[i1] * j1);
    }

    public String getTextureFile()
    {
        return "/rocketScience/items.png";
    }

    private static final int damageReduceAmountArray[] = {
        3, 8, 6, 3
    };
    private static final int maxDamageArray[] = {
        11, 16, 15, 13
    };
	@Override
	public String getArmorTextureFile(ItemStack itemstack) {
//TODO add texture
		return null;
	}

}