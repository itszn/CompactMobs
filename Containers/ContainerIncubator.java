package compactMobs.Containers;

import compactMobs.CompactMobsCore;
import compactMobs.Items.CompactMobsItems;
import compactMobs.TileEntity.TileEntityBreeder;
import compactMobs.TileEntity.TileEntityCompactor;
import compactMobs.TileEntity.TileEntityIncubator;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraft.src.TileEntity;


public class ContainerIncubator extends Container{

	int inventorySize;
	public ContainerIncubator(InventoryPlayer par1InventoryPlayer, TileEntityIncubator tileEntity)
    {
		inventorySize = tileEntity.getSizeInventory();
		
        //this.addSlotToContainer(new Slot(te, 1, 44, 51));
        //this.addSlotToContainer(new Slot(te, 2, 116, 51));
        //this.addSlotToContainer(new SlotInbuener(par1InventoryPlayer.player, te, 3, 80, 51));
        
        for (int var3 = 0; var3 < 3; ++var3)
        {
            for (int var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new IncubatorSlot(tileEntity, var4 + var3 * 9, 8 + var4 * 18, 6+18 + var3 * 18));
            }
        }
        for (int var3 = 0; var3 < 3; ++var3)
        {
            for (int var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new ClosedIncubatorSlot(tileEntity, 27+var4 + var3 * 9, 8 + var4 * 18, 89 + var3 * 18));
            }
        }
        for (int var3 = 0; var3 < 3; ++var3)
        {
            for (int var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 153 + var3 * 18));
            }
        }

        for (int var3 = 0; var3 < 9; ++var3)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 211));
        }
    }
	
	@Override
	public boolean canInteractWith(EntityPlayer var1) 
	{
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(int par1) {
		/*ItemStack itemstack = null;
		int inventorySize1 = 27;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i < inventorySize) {
				if (!mergeItemStack(itemstack1, inventorySize1, inventorySlots.size(), true))
					return null;
			} else if (!mergeItemStack(itemstack1, 0, inventorySize1, false))
				return null;
			if (itemstack1.stackSize == 0)
				slot.putStack(null);
			else
				slot.onSlotChanged();
		}
		return itemstack;*/
		ItemStack var2 = null;
        Slot var3 = (Slot)this.inventorySlots.get(par1);

        if (var3 != null && var3.getHasStack())
        {
            ItemStack var4 = var3.getStack();
            var2 = var4.copy();

            if (par1 >= 0 && par1 < 54)
            {
                if (!this.mergeItemStack(var4, 54, 90, true))
                {
                    return null;
                }

                var3.onSlotChange(var4, var2);
            }
            else if (par1 >= 54)
            {
                if (var4.getItem() == CompactMobsItems.fullMobHolder)
                {
                    if (!this.mergeItemStack(var4, 0, 27, false))
                    {
                        return null;
                    }
                }
                else if (par1 >= 54 && par1 < 81)
                {
                    if (!this.mergeItemStack(var4, 81, 90, false))
                    {
                        return null;
                    }
                }
                else if (par1 >= 81 && par1 < 90 && !this.mergeItemStack(var4, 54, 81, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var4, 54, 90, false))
            {
                return null;
            }

            if (var4.stackSize == 0)
            {
                var3.putStack((ItemStack)null);
            }
            else
            {
                var3.onSlotChanged();
            }

            if (var4.stackSize == var2.stackSize)
            {
                return null;
            }

            var3.onPickupFromSlot(var4);
        }

        return var2;
	}
	
	@Override
	public void putStackInSlot(int slot, ItemStack stack)
	{
		if (getSlot(slot).isItemValid(stack))
			this.getSlot(slot).putStack(stack);
	}
}
