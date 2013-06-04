package compactMobs.Items;

import java.util.Iterator;
import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import compactMobs.DefaultProps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Upgrade extends Item {

	protected Icon playerUpgrade; 
	protected Icon instablityUpgrade; 
	protected Icon hatUpgrade; 
    public Upgrade(int par1) {
        super(par1);
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    @Override
    public String getItemDisplayName(ItemStack stack) {
    	if (stack.getItemDamage() == 0)
    	{
    		return "Catalyst Upgrade: Player Damage";
    	}
    	if (stack.getItemDamage() == 1)
    	{
    		return "Catalyst Upgrade: Instablity";
    	}
    	if (stack.getItemDamage() == 2)
    	{
    		return "Equiper Upgrade: Block Heads";
    	}
    	return "Generic Upgrade";
        
    }
    
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
    	if (stack.getItemDamage() == 0)
    	{
    		list.add("Allow For Rare Drops");
    	}
    	if (stack.getItemDamage() == 1)
    	{
    		list.add("Between 1 and 3 Drops");
    	}
    	if (stack.getItemDamage() == 2)
    	{
    		list.add("Blocks Make Silly Hats For Mobs");
    	}
    }

    public String getTextureFile() {
        return DefaultProps.ITEM_TEXTURES + "/items.png";
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.playerUpgrade = par1IconRegister.registerIcon("compactMobs:PlayerUpgrade");
        this.instablityUpgrade = par1IconRegister.registerIcon("compactMobs:InstablityUpgrade");
        this.hatUpgrade = par1IconRegister.registerIcon("compactMobs:HatUpgrade");
    }
    
    @Override
	public Icon getIconFromDamage(int itemDamage) {
		switch(itemDamage)
		{
		case 0: return playerUpgrade;
		case 1: return instablityUpgrade;
		case 2: return hatUpgrade;
		default: return playerUpgrade;
		}
	}
    
    @SideOnly(Side.CLIENT)
    @Override
    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(int i, CreativeTabs par2CreativeTabs, List l)
    {
    	l.add(new ItemStack(i, 1, 0));
    	l.add(new ItemStack(i, 1, 1));
    	l.add(new ItemStack(i, 1, 2));
    }
}