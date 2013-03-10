package compactMobs.Items;

import java.util.List;

import net.minecraft.entity.EntityList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import compactMobs.DefaultProps;

public class FullMobHolder extends Item {

    public FullMobHolder(int par1) {
        super(par1);
        maxStackSize = 1;
        //this.setTabToDisplayOn(CreativeTabs.tabRedstone);
        if (!this.getShareTag()) {
        }
    }

    @Override
    public boolean getShareTag() {
        return true;
    }
    
    @SuppressWarnings({ "all" })
	// @Override (client only)
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean advanced) {
		if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("FertilityVisiable")) {
			NBTTagCompound nbttag = itemstack.getTagCompound();
	        if (nbttag != null) {
	            if (nbttag.hasKey("entityGrowingAge")) {
                    float age = nbttag.getInteger("entityGrowingAge");
                    int percent = 100;
                	if (age >= 0) {
                        percent = (int) Math.floor(((6000 - age) / 6000) * 100);
                        list.add("Fertility " + String.valueOf(percent) + "%");
                    }
                    if (age < 0) {
                        percent = (int) Math.floor((((-24000 - age) / -24000) * 100));
                        list.add("Growth " + String.valueOf(percent) + "%");
                    }
                    
                }
	        }
			
		}
	}

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemDisplayName(ItemStack stack) {
        NBTTagCompound nbttag = stack.getTagCompound();
        if (nbttag != null) {
            if (nbttag.hasKey("name")) {
                //String name = EntityList.getStringFromID(stack.getItemDamage());//nbttag.getString("name");
                if (nbttag.hasKey("entityGrowingAge")) {
                    
                    if (nbttag.getInteger("entityGrowingAge") < 0) {

                        return "Compact Baby " + nbttag.getString("name");
                    }
                }
                return "Compact " + nbttag.getString("name");
            } else {
                if (stack.getItemDamage() > 0) {
                    return "Sort By Compact " + EntityList.getStringFromID(stack.getItemDamage());
                }
                return "Full Mob Holder (BAD ID)";
            }
        }

        return "Full Mob Holder (BAD ID)";
    }

    public String getTextureFile() {
        return DefaultProps.ITEM_TEXTURES + "/items.png";
    }
    
    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
    	ItemStack stack2;
    	for (int i = 0; i<player.inventory.getSizeInventory();i++)
    	{
    		stack2 = player.inventory.getStackInSlot(i);
    		if (stack2 != null)
    		{
	    		if (stack2.getItem()==CompactMobsItems.handDecompactor)
	    		{
	    			NBTTagCompound nbttag = stack2.getTagCompound();
	    			if (nbttag == null)
	    			{
	    				nbttag = new NBTTagCompound();
	    			}
	    			nbttag.setBoolean("targeted", true);
	    			NBTTagCompound tag = new NBTTagCompound();
	    			stack.writeToNBT(tag);
	    			nbttag.setTag("target", tag);
	    			stack2.setTagCompound(nbttag);
	    			stack2.setItemDamage(1);
	    		}
    		}
    	}
    	return stack;
    }
    /*
     * @Override public ItemStack onItemRightClick(ItemStack itemStack, World
     * world, EntityPlayer player) { ///NBTBase nbtbace =
     * itemStack.getTagCompound().getTag("tag"); NBTTagCompound nbttag =
     * itemStack.stackTagCompound;
     *
     * if (nbttag != null) {
     *
     * if (nbttag.hasKey("name")) { String name = nbttag.getString("name");
     * CompactMobsCore.instance.cmLog.info(String.valueOf(name)); } else { int
     * id = nbttag.getInteger("entityId");
     * CompactMobsCore.instance.cmLog.info(String.valueOf(id)); } } else {
     * CompactMobsCore.instance.cmLog.info("No Id");
     *
     * }
     * return itemStack;
	}
     */
}