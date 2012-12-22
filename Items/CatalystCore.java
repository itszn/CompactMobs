package compactMobs.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import compactMobs.DefaultProps;

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