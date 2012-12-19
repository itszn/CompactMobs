package compactMobs.Items;

import java.util.Random;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityList;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

import compactMobs.CompactMobsCore;
import compactMobs.DefaultProps;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class HandheldDecompactor extends Item{
	public HandheldDecompactor(int par1) {
        super(par1);
        this.setMaxDamage(250);
        maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    @Override
    public String getItemDisplayName(ItemStack stack) {
        return "Handheld Decompactor";
    }

    public String getTextureFile() {
        return DefaultProps.ITEM_TEXTURES + "/items.png";
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
					if (player.inventory.hasItemStack(tempStack))
					{
						xVel = (double)(MathHelper.cos((player.rotationYaw+90) / 180.0F * (float)Math.PI) * -.05F);
				    	zVel = (double)(MathHelper.sin((player.rotationYaw+90) / 180.0F * (float)Math.PI) * -.05F);
				    	
						ItemStack tempStack2 = null;
						
						boolean found = false;
						
						for (int i = 0;i<player.inventory.getSizeInventory();i++)
	                    {
	                    	if (tempStack.isItemEqual(player.inventory.getStackInSlot(i)))
	                    	{
	                    		tempStack2 = player.inventory.getStackInSlot(i);
	                    		player.inventory.setInventorySlotContents(i, null);
	                    		found = true;
	                    		
	                    		break;
	                    	}
	                    }
						
						NBTTagCompound nbttag2 = tempStack2.getTagCompound();
						
						if (found && nbttag2.hasKey("entityTags")&& nbttag2.hasKey("entityId"))
						{
							NBTTagCompound newCompound = (NBTTagCompound) nbttag2.getTag("entityTags");
		                    int id = nbttag2.getInteger("entityId");
		                    entity = (EntityLiving) EntityList.createEntityByID(id, world);
		                    
		                    	entity.readFromNBT(newCompound);
		                    
		                    Random random = new Random();
		                    
		                    entity.setPosition(player.posX + xChange, player.posY, player.posZ +zChange);
		                    CompactMobsCore.instance.proxy.spawnParticle("explode", player.posX + xChange, player.posY + .5D, player.posZ +zChange, -xVel, 0, -zVel, 10);
		                    //areItemStacksEqual
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
        return stack;
    }

	
}