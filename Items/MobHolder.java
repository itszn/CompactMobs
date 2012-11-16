package compactMobs.Items;

import compactMobs.DefaultProps;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class MobHolder extends Item {

    public MobHolder(int par1) {
        super(par1);
        maxStackSize = 64;
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    @Override
    public String getItemDisplayName(ItemStack stack) {
        return "Empty Mob Holder";
    }

    public String getTextureFile() {
        return DefaultProps.ITEM_TEXTURES + "/items.png";
    }
}