package compactMobs.Containers;

import compactMobs.CompactMobsCore;
import compactMobs.TileEntity.TileEntityBreeder;
import compactMobs.TileEntity.TileEntityCompactor;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraft.src.TileEntity;


public class ContainerBreeder extends Container{

	int inventorySize;
	public ContainerBreeder(InventoryPlayer par1InventoryPlayer, TileEntityBreeder tileEntity)
    {
		inventorySize = tileEntity.getSizeInventory();
		
        //this.addSlotToContainer(new Slot(te, 1, 44, 51));
        //this.addSlotToContainer(new Slot(te, 2, 116, 51));
        //this.addSlotToContainer(new SlotInbuener(par1InventoryPlayer.player, te, 3, 80, 51));
        
        for (int var3 = 0; var3 < 2; ++var3)
        {
            for (int var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(tileEntity, var4 + var3 * 9, 8 + var4 * 18, 6+18 + var3 * 18));
            }
        }
        this.addSlotToContainer(new Slot(tileEntity, 18, 44, 65));
        this.addSlotToContainer(new Slot(tileEntity, 19, 80, 65));
        this.addSlotToContainer(new Slot(tileEntity, 20, 116, 65));
        for (int var3 = 0; var3 < 3; ++var3)
        {
            for (int var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(tileEntity, 21+var4 + var3 * 9, 8 + var4 * 18, 89 + var3 * 18));
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
	public ItemStack transferStackInSlot(int i) {
		ItemStack itemstack = null;
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
		return itemstack;
	}
}