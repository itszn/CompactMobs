package compactMobs.Containers;

import compactMobs.Items.CompactMobsItems;

import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class EmptyMobHolderSlot extends Slot{

	public EmptyMobHolderSlot(IInventory par1iInventory, int par2, int par3,
			int par4) {
		super(par1iInventory, par2, par3, par4);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isItemValid(ItemStack itemStack)
    {
		if (itemStack.getItem() == CompactMobsItems.mobHolder)
			return true;
		return false;
    }

}
