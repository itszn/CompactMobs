package compactMobs.Items;

import compactMobs.DefaultProps;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class CatalystCore extends Item {

    public CatalystCore(int par1) {
        super(par1);
        this.maxStackSize = 1;
        this.setMaxDamage(150);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    @Override
    public String getItemDisplayName(ItemStack stack) {
        return "Catalyst Core";
    }

    public String getTextureFile() {
        return DefaultProps.ITEM_TEXTURES + "/items.png";
    }
}