package compactMobs.Items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import compactMobs.DefaultProps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("compactMobs:ItemMobHolder"); 
    }
}