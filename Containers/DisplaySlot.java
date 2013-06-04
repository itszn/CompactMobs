package compactMobs.Containers;

import compactMobs.Items.CompactMobsItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class DisplaySlot extends Slot{
	
	public DisplaySlot(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canTakeStack(EntityPlayer par1EntityPlayer)
    {
        return false;
    }
	
	@Override
	public boolean isItemValid(ItemStack itemStack)
    {
		return false;
		
    }
}
