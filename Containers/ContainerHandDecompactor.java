package compactMobs.Containers;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

import compactMobs.Items.CompactMobsItems;
import compactMobs.Items.HandheldDecompactorInv;
import compactMobs.TileEntity.TileEntityDecompactor;

public class ContainerHandDecompactor extends Container {

    int inventorySize;

    public ContainerHandDecompactor(InventoryPlayer par1InventoryPlayer, HandheldDecompactorInv te) {
        inventorySize = te.getSizeInventory();
        //this.addSlotToContainer(new Slot(te, 0, 49, 34));
        //this.addSlotToContainer(new Slot(te, 1, 44, 51));
        //this.addSlotToContainer(new Slot(te, 2, 116, 51));
        //this.addSlotToContainer(new SlotInbuener(par1InventoryPlayer.player, te, 3, 80, 51));

        //62, 105
        this.addSlotToContainer(new FullMobHolderSlot(te, 0, 62, 105));

        for (int var3 = 0; var3 < 3; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 153 + var3 * 18));
            }
        }

        for (int var3 = 0; var3 < 9; ++var3) {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 211));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer var1) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        /*
         * ItemStack itemstack = null; int inventorySize1 = 27; Slot slot =
         * (Slot) inventorySlots.get(i); if (slot != null && slot.getHasStack())
         * { ItemStack itemstack1 = slot.getStack(); itemstack =
         * itemstack1.copy(); if (i < inventorySize) { if
         * (!mergeItemStack(itemstack1, inventorySize1, inventorySlots.size(),
         * true)) return null; } else if (!mergeItemStack(itemstack1, 0,
         * inventorySize1, false)) return null; if (itemstack1.stackSize == 0)
         * slot.putStack(null); else slot.onSlotChanged(); } return itemstack;
         */
        ItemStack var2 = null;
        Slot var3 = (Slot) this.inventorySlots.get(par2);

        if (var3 != null && var3.getHasStack()) {
            ItemStack var4 = var3.getStack();
            var2 = var4.copy();

            if (par2 >= 0 && par2 < 1) {
                if (!this.mergeItemStack(var4, 1, 36, true)) {
                    return null;
                }

                var3.onSlotChange(var4, var2);
            } else if (par2 >= 1) {
                if (var4.getItem() == CompactMobsItems.fullMobHolder) {
                    if (!this.mergeItemStack(var4, 0, 1, false)) {
                        return null;
                    }
                } else if (par2 >= 1 && par2 < 27) {
                    if (!this.mergeItemStack(var4, 27, 36, false)) {
                        return null;
                    }
                } else if (par2 >= 27 && par2 < 36 && !this.mergeItemStack(var4, 1, 36, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(var4, 1, 26, false)) {
                return null;
            }

            if (var4.stackSize == 0) {
                var3.putStack((ItemStack) null);
            } else {
                var3.onSlotChanged();
            }

            if (var4.stackSize == var2.stackSize) {
                return null;
            }

            var3.onPickupFromSlot(par1EntityPlayer, var4);
        }

        return var2;
    }
}