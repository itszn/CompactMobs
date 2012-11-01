package compactMobs.Containers;

import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Slot;

public class IncubatorSlot extends Slot{

	public IncubatorSlot(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onPickupFromSlot(ItemStack stack)
	{
		if (stack != null)
		{
			NBTTagCompound nbttag = stack.getTagCompound();
			if (nbttag != null)
			{
				nbttag.setBoolean("inIncubator", false);
				stack.setTagCompound(nbttag);
			}
		}
		this.onSlotChanged();
	}

}
