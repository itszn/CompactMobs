package compactMobs.Containers;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import compactMobs.Items.Upgrade;
import compactMobs.Items.CompactMobsItems;

public class UpgradeSlot extends Slot{
	int[] valid;
	
	public UpgradeSlot(IInventory par1iInventory, int par2, int par3,
			int par4, int[] validUpgrades) {
		super(par1iInventory, par2, par3, par4);
		valid=validUpgrades;
	}

	@Override
	public boolean isItemValid(ItemStack itemStack)
    {
		if (itemStack != null)
			if (itemStack.getItem() == CompactMobsItems.upgrade)
				for (int i: valid)
					if(itemStack.getItemDamage()==i)
						return true;
		if (itemStack == null)
			return true;
		return false;
		
    }


}
