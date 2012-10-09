package compactMobs.Items;

import compactMobs.CompactMobsCore;
import compactMobs.DefaultProps;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityCreature;
import net.minecraft.src.EntityList;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class FullMobHolder extends Item 
{
	
	public FullMobHolder(int par1) 
	{
		super(par1);
		maxStackSize = 1;
		this.setTabToDisplayOn(CreativeTabs.tabRedstone);
	}
	
	public String getTextureFile()
    {
		return DefaultProps.ITEM_TEXTURES+"/items.png";
    }
	
		
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		///NBTBase nbtbace = itemStack.getTagCompound().getTag("tag");
		NBTTagCompound nbttag = itemStack.stackTagCompound;
		if (nbttag != null)
		{
			int id = nbttag.getInteger("entityId");
			CompactMobsCore.instance.cmLog.info(String.valueOf(id));
		} else 
		{
			CompactMobsCore.instance.cmLog.info("No Id");
			
		}
		return itemStack;
	}
	
	
}