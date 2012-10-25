package compactMobs.Items;

import compactMobs.CompactMobsCore;
import compactMobs.DefaultProps;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

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
		if (!this.getShareTag())
		{
			
		}
	}
	
	@Override
	public boolean getShareTag()
    {
        return true;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getItemDisplayName(ItemStack stack)
	{
		NBTTagCompound nbttag = stack.getTagCompound();
		if (nbttag != null)
		{
			if (nbttag.hasKey("name"))
			{
				//String name = EntityList.getStringFromID(stack.getItemDamage());//nbttag.getString("name");
				if (nbttag.hasKey("entityGrowingAge"))
				{
					if (nbttag.getInteger("entityGrowingAge") < 0)
					{
						return "Compact Baby "+nbttag.getString("name");
					}
				}
				return "Compact "+nbttag.getString("name");
			} else
			{
				return "Full Mob Holder (BAD ID)";
			}
		}
		
		return "Full Mob Holder (BAD ID)";
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
			
			if (nbttag.hasKey("name"))
			{
				String name = nbttag.getString("name");
				CompactMobsCore.instance.cmLog.info(String.valueOf(name));
			} else
			{
				int id = nbttag.getInteger("entityId");
				CompactMobsCore.instance.cmLog.info(String.valueOf(id));
			}
		} else 
		{
			CompactMobsCore.instance.cmLog.info("No Id");
			
		}
		return itemStack;
	}
	
	
}