package compactMobs.Items;

import compactMobs.DefaultProps;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;

public class MobHolder extends Item 
{
	public MobHolder(int par1) 
	{
		super(par1);
		maxStackSize = 64;
		this.setTabToDisplayOn(CreativeTabs.tabRedstone);
	}
	
	public String getTextureFile()
    {
		return DefaultProps.ITEM_TEXTURES+"/items.png";
    }
}