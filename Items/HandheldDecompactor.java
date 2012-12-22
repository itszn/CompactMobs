package compactMobs.Items;

import java.util.Random;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import compactMobs.CompactMobsCore;
import compactMobs.DefaultProps;

public class HandheldDecompactor extends Item{
	public HandheldDecompactor(int par1) {
        super(par1);
        maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
	
	@Override
    public boolean getShareTag() {
        return true;
    }

    @Override
    public String getItemDisplayName(ItemStack stack) {
        NBTTagCompound nbttag;
        nbttag = stack.getTagCompound();
        if (nbttag == null)
        {
        	nbttag = new NBTTagCompound();
        }
        if (nbttag.hasKey("targeted"))
        {
        	if (nbttag.getBoolean("targeted"))
        	{
        		ItemStack tempStack = ItemStack.loadItemStackFromNBT((NBTTagCompound)nbttag.getTag("target"));
				NBTTagCompound nbttag2 = tempStack.getTagCompound();
				if (nbttag2!=null)
				{
					if (nbttag2.hasKey("name"))
					{
						String name = nbttag2.getString("name");
						return "Handheld Decompactor (Targeted "+ name+")";
					}
				}
				
        		return "Handheld Decompactor (Targeted)";
        	}
        }
    	
    	return "Handheld Decompactor";
    }

    public String getTextureFile() {
        return DefaultProps.ITEM_TEXTURES + "/items.png";
    }
    @Override
	public int getIconFromDamage(int itemDamage) {
		if( itemDamage == 1 )
			return 4;
		return 3;
	}
    @Override
    public void onUpdate(ItemStack stack, World par2World, Entity eplayer, int par4, boolean par5) 
    {
    	EntityPlayer player = (EntityPlayer) eplayer;
    	if (stack != null)
    	{
    		NBTTagCompound nbttag = stack.getTagCompound();
    		if (nbttag == null)
    		{
    			nbttag = new NBTTagCompound();
    		}
    		if (nbttag.hasKey("targeted") & nbttag.hasKey("target"))
    		{
    			if (nbttag.getBoolean("targeted"))
    			{
    				ItemStack tempStack = ItemStack.loadItemStackFromNBT((NBTTagCompound)nbttag.getTag("target"));
    				if (!player.inventory.hasItemStack(tempStack))
    				{
    					nbttag.setBoolean("targeted", false);
    					stack.setTagCompound(nbttag);
    					stack.setItemDamage(0);
    				}
    			}
    		}
    	}
    }
    
    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
    	if (stack != null)
    	{
			NBTTagCompound nbttag = stack.getTagCompound();
			if (nbttag == null)
			{
				nbttag= new NBTTagCompound();
			}
			CompactMobsCore.instance.cmLog.info(String.valueOf(nbttag.hasKey("targeted") && nbttag.hasKey("target")));
			
			if (nbttag.hasKey("targeted") && nbttag.hasKey("target"))
			{
				if (nbttag.getBoolean("targeted"))
				{
					
					double xChange, zChange, xVel, zVel;
					xChange = (double)(MathHelper.cos((player.rotationYaw+90) / 180.0F * (float)Math.PI) * 1F);
					zChange = (double)(MathHelper.sin((player.rotationYaw+90) / 180.0F * (float)Math.PI) * 1F);
					ItemStack tempStack = ItemStack.loadItemStackFromNBT((NBTTagCompound)nbttag.getTag("target"));
					EntityLiving entity = null;
					
					CompactMobsCore.instance.cmLog.info(String.valueOf(player.inventory.hasItemStack(tempStack)));
					if (tempStack != null)
					{
						if (player.inventory.hasItemStack(tempStack) && player.inventory.hasItem(Item.coal.shiftedIndex))
						{
							xVel = (double)(MathHelper.cos((player.rotationYaw+90) / 180.0F * (float)Math.PI) * -.05F);
					    	zVel = (double)(MathHelper.sin((player.rotationYaw+90) / 180.0F * (float)Math.PI) * -.05F);
					    	
							ItemStack tempStack2 = null;
							
							boolean found = false;
							boolean space = false;
							boolean part = false;
							int spot = 0;
							int spot2 = 0;
							
							for (int i = 0;i<player.inventory.getSizeInventory();i++)
		                    {
								if (player.inventory.getStackInSlot(i)!=null)
								{
									if (!space)
									{
										if (player.inventory.getStackInSlot(i)==null)
										{
											space = true;
											spot = i;
										}
										else if (player.inventory.getStackInSlot(i).getItem()==Item.coal && player.inventory.getStackInSlot(i).stackSize==1)
										{
											space = true;
											spot = i;
										}
										else if (player.inventory.getStackInSlot(i).getItem()==CompactMobsItems.fullMobHolder)
										{
											space=true;
											spot = i;
										}
									}
									if (!part && player.inventory.getStackInSlot(i)!=null)
									{
										if (player.inventory.getStackInSlot(i).getItem()==CompactMobsItems.mobHolder && player.inventory.getStackInSlot(i).stackSize<64)
										{
											part = true;
											space = true;
											spot = i;
										}
									}
			                    	if (!found && tempStack.isItemEqual(player.inventory.getStackInSlot(i)))
			                    	{
			                    		tempStack2 = player.inventory.getStackInSlot(i);
			                    		//player.inventory.setInventorySlotContents(i, null);
			                    		found = true;
			                    		spot2 = i;
			                    	}
								}
		                    	
		                    }
							
							
							
							
							NBTTagCompound nbttag2 = tempStack2.getTagCompound();
							
							if (found && nbttag2.hasKey("entityTags") && nbttag2.hasKey("entityId") && found && space)
							{
								player.inventory.consumeInventoryItem(Item.coal.shiftedIndex);
								
								player.inventory.setInventorySlotContents(spot2, null);
								if (part)
								{
									ItemStack tempStack5 = new ItemStack(CompactMobsItems.mobHolder,player.inventory.getStackInSlot(spot).stackSize+1);
									player.inventory.setInventorySlotContents(spot, tempStack5);
								}
								else
								{
									ItemStack tempStack5 = new ItemStack(CompactMobsItems.mobHolder,1);
									player.inventory.setInventorySlotContents(spot, tempStack5);
								}
								
	                    		
								
								NBTTagCompound newCompound = (NBTTagCompound) nbttag2.getTag("entityTags");
			                    int id = nbttag2.getInteger("entityId");
			                    entity = (EntityLiving) EntityList.createEntityByID(id, world);
			                    
			                    	entity.readFromNBT(newCompound);
			                    
			                    Random random = new Random();
			                    
			                    entity.setPosition(player.posX + xChange, player.posY, player.posZ +zChange);
			                    CompactMobsCore.instance.proxy.spawnParticle("explode", player.posX + xChange, player.posY + .5D, player.posZ +zChange, -xVel, 0, -zVel, 10);
			                    //areItemStacksEqual
			                    if (!world.isRemote)
			                    	world.spawnEntityInWorld(entity);
			                    //CompactMobsCore.proxy.spawnMob(entity);
			                    nbttag.setBoolean("targeted", false);
		    					stack.setTagCompound(nbttag);
		    					stack.setItemDamage(0);
							}
						}
					}
				}
			}
		}
        return stack;
    }

	
}