package compactMobs.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import compactMobs.Items.CompactMobsItems;
import compactMobs.TileEntity.TileEntityBreeder;

public class ContainerBreeder extends Container {

    int inventorySize;

    public ContainerBreeder(InventoryPlayer par1InventoryPlayer, TileEntityBreeder tileEntity) {
        inventorySize = tileEntity.getSizeInventory();

        //this.addSlotToContainer(new Slot(te, 1, 44, 51));
        //this.addSlotToContainer(new Slot(te, 2, 116, 51));
        //this.addSlotToContainer(new SlotInbuener(par1InventoryPlayer.player, te, 3, 80, 51));

        for (int var3 = 0; var3 < 2; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) {
                this.addSlotToContainer(new Slot(tileEntity, var4 + var3 * 9, 8 + var4 * 18, 6 + 18 + var3 * 18));
            }
        }
        this.addSlotToContainer(new FullMobHolderSlot(tileEntity, 18, 44, 65));
        this.addSlotToContainer(new EmptyMobHolderSlot(tileEntity, 19, 80, 65));
        this.addSlotToContainer(new FullMobHolderSlot(tileEntity, 20, 116, 65));
        for (int var3 = 0; var3 < 3; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) {
                this.addSlotToContainer(new ClosedSlot(tileEntity, 21 + var4 + var3 * 9, 8 + var4 * 18, 89 + var3 * 18));
            }
        }
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

            if (par2 >= 0 && par2 < 48) {
                if (!this.mergeItemStack(var4, 48, 84, true)) {
                    return null;
                }

                var3.onSlotChange(var4, var2);
            } else if (par2 >= 48) {
                if (var4.getItem() == CompactMobsItems.fullMobHolder) {
                    if (!this.mergeItemStack(var4, 0, 18, false)) {
                        return null;
                    }
                } else if (var4.getItem() == CompactMobsItems.mobHolder) {
                    if (!this.mergeItemStack(var4, 0, 18, false)) {
                        return null;
                    }
                } else if (par2 >= 48 && par2 < 75) {
                    if (!this.mergeItemStack(var4, 75, 84, false)) {
                        return null;
                    }
                } else if (par2 >= 75 && par2 < 84 && !this.mergeItemStack(var4, 48, 75, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(var4, 48, 84, false)) {
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
