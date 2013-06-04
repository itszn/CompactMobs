package compactMobs.Items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
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
		if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("infoVisable")) {
			
			if (itemstack.getTagCompound().hasKey("entityTags")) {
				NBTTagCompound nbttag = itemstack.getTagCompound().getCompoundTag("entityTags");
				if (nbttag != null) {
					if (nbttag.hasKey("Health"))
						list.add("Health: "+nbttag.getShort("Health"));
					if (nbttag.hasKey("CustomName"))
						if (!nbttag.getString("CustomName").equals(""))
							list.add("Custom Name: "+nbttag.getString("CustomName"));
					if (nbttag.hasKey("Owner"))
						list.add((nbttag.getString("Owner").equals("")?"Wild":"Owner: "+nbttag.getString("Owner")));
					if (nbttag.hasKey("powered"))
						list.add("Lightning Charged");
					
					if (nbttag.hasKey("CatType")) {
						int c = nbttag.getInteger("CatType");
						String[] cats = {"Wild Ocelot","Tuxuedo","Tabby","Siamese"};
						list.add("Cat Type: "+cats[c]);
					}
					if (nbttag.hasKey("Saddle")&&nbttag.getBoolean("Saddle"))
						list.add("Saddled");
					if (nbttag.hasKey("Sheared")&&nbttag.getBoolean("Sheared"))
						list.add("Sheared");
					if (nbttag.hasKey("Color")) {
						String[] colors = {"White","Orange","Magenta","Light Blue", "Yellow","Lime","Pink","Gray","Light Gray","Cyan","Purple","Blue","Brown","Green","Red","Black"};
						list.add("Color: "+colors[nbttag.getByte("Color")]);
					}
					if (nbttag.hasKey("Size")) {
						String[] sizes = {"Small","Medium","Large"};
						int s = nbttag.getInteger("Size");
						list.add("Size: "+(s<3?sizes[s]:"Huge"));
					}
					if (nbttag.hasKey("Angry")&&nbttag.getBoolean("Angry"))
							list.add("Hostile");
					if (nbttag.hasKey("Anger")&&nbttag.getShort("Anger")!=0)
						list.add("Hostile");
					
					if (nbttag.hasKey("Profession")) {
						int p = nbttag.getInteger("Profession");
						String[] jobs = {"Farmer","Librarian","Priest","Blacksmith","Butcher","Default"};
						list.add("Profession: "+jobs[p]);
					}
					/*if (nbttag.hasKey("Offers")) {
						NBTTagCompound offers = nbttag.getCompoundTag("Offers");
						NBTTagList list2 = offers.getTagList("Recipes");
						
						list.add("Trades: "+list2.tagCount());
					}*/
				}
				
			}
		}
		if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("FertilityVisiable")||itemstack.getTagCompound().hasKey("infoVisable")) {
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
            	String name = nbttag.getString("name");
            	
            	if (name.startsWith("entity.SoulShards.Spawned"))
            	{
            		name = name.split("entity.SoulShards.Spawned")[1].split(".name")[0];
            	}
            	if (name.equals("entity.Cat.name"))
            	{
            		name = "Cat";
            	}
                //String name = EntityList.getStringFromID(stack.getItemDamage());//nbttag.getString("name");
                if (nbttag.hasKey("entityGrowingAge")) {
                    
                    if (nbttag.getInteger("entityGrowingAge") < 0) {

                        return "Compact Baby " + name;
                    }
                }
                return "Compact " + name;
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
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("compactMobs:ItemFullMobHolder"); 
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