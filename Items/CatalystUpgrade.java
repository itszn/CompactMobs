package compactMobs.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import compactMobs.DefaultProps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CatalystUpgrade extends Item {

	protected Icon playerUpgrade; 
    public CatalystUpgrade(int par1) {
        super(par1);
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    @Override
    public String getItemDisplayName(ItemStack stack) {
    	return "Catalyst Upgrade";
        
    }
    
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
    	if (stack.getItemDamage() == 0)
    	{
    		list.add("Player Damage Source");
    	}
    }

    public String getTextureFile() {
        return DefaultProps.ITEM_TEXTURES + "/items.png";
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.playerUpgrade = par1IconRegister.registerIcon("compactMobs:PlayerUpgrade");
    }
    
    @Override
	public Icon getIconFromDamage(int itemDamage) {
		switch(itemDamage)
		{
		case 0: return playerUpgrade;
		default: return playerUpgrade;
		}
	}
}