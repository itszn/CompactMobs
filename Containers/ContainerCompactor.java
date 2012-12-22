package compactMobs.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import compactMobs.Items.CompactMobsItems;
import compactMobs.TileEntity.TileEntityCompactor;

public class ContainerCompactor extends Container {

    int inventorySize;

    public ContainerCompactor(InventoryPlayer par1InventoryPlayer, TileEntityCompactor te) {
        inventorySize = te.getSizeInventory();
        this.addSlotToContainer(new EmptyMobHolderSlot(te, 0, 49, 34));

        for (int var3 = 0; var3 < 3; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) {
                this.addSlotToContainer(new ClosedSlot(te, var4 + var3 * 9 + 1, 8 + var4 * 18, 85 + var3 * 18));
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

    /*
     * @Override public boolean mergeItemStack(ItemStack par1ItemStack, int
     * par2, int par3, boolean par4) { boolean var5 = false; int var6 = par2;
     *
     * if (par4) { var6 = par3 - 1; }
     *
     * Slot var7; ItemStack var8;
     *
     * if (par1ItemStack.isStackable()) { while (par1ItemStack.stackSize > 0 &&
     * (!par4 && var6 < par3 || par4 && var6 >= par2)) { var7 =
     * (Slot)this.inventorySlots.get(var6); var8 = var7.getStack(); if (var6 >=
     * 0 && var6 < 1) { if (var8 != null && var8.itemID == par1ItemStack.itemID
     * && (!par1ItemStack.getHasSubtypes() || par1ItemStack.getItemDamage() ==
     * var8.getItemDamage()) && ItemStack.func_77970_a(par1ItemStack, var8)) {
     * int var9 = var8.stackSize + par1ItemStack.stackSize;
     *
     * if (var9 <= par1ItemStack.getMaxStackSize()) { par1ItemStack.stackSize =
     * 0; var8.stackSize = var9; var7.onSlotChanged(); var5 = true; } else if
     * (var8.stackSize < par1ItemStack.getMaxStackSize()) {
     * par1ItemStack.stackSize -= par1ItemStack.getMaxStackSize() -
     * var8.stackSize; var8.stackSize = par1ItemStack.getMaxStackSize();
     * var7.onSlotChanged(); var5 = true; } } }
     *
     * if (par4) { --var6; } else { ++var6; } } }
     *
     * if (par1ItemStack.stackSize > 0) { if (par4) { var6 = par3 - 1; } else {
     * var6 = par2; }
     *
     * while (!par4 && var6 < par3 || par4 && var6 >= par2) { var7 =
     * (Slot)this.inventorySlots.get(var6); var8 = var7.getStack();
     *
     * if (var6 >= 0 && var6 < 1) { if (var8 == null) {
     * var7.putStack(par1ItemStack.copy()); var7.onSlotChanged();
     * par1ItemStack.stackSize = 0; var5 = true; break; } }
     *
     * if (par4) { --var6; } else { ++var6; } } }
     *
     * return var5; }
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        /*
         * ItemStack itemstack = null; int inventorySize1 = 1; Slot slot =
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

            if (par2 >= 0 && par2 < 28) {
                if (!this.mergeItemStack(var4, 28, 64, true)) {
                    return null;
                }

                var3.onSlotChange(var4, var2);
            } else if (par2 >= 28) {
                if (var4.getItem() == CompactMobsItems.mobHolder) {
                    if (!this.mergeItemStack(var4, 0, 1, false)) {
                        return null;
                    }
                } else if (par2 >= 28 && par2 < 55) {
                    if (!this.mergeItemStack(var4, 55, 64, false)) {
                        return null;
                    }
                } else if (par2 >= 55 && par2 < 64 && !this.mergeItemStack(var4, 28, 55, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(var4, 28, 64, false)) {
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
