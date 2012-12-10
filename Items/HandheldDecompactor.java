package compactMobs.Items;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

import compactMobs.CompactMobsCore;
import compactMobs.DefaultProps;

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
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
    	if (player.isSneaking())
    	{
    		player.openGui(CompactMobsCore.instance, 5, world, 0, 0, 0);
    	}
    	else
    	{
	    	double xChange, zChange;
	    	xChange = (double)(MathHelper.cos((player.rotationYaw+90) / 180.0F * (float)Math.PI) * 1F);
	    	zChange = (double)(MathHelper.sin((player.rotationYaw+90) / 180.0F * (float)Math.PI) * 1F);
    	}
    	
        return stack;
    }

	
}