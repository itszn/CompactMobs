package compactMobs.Containers;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import compactMobs.Items.CompactMobsItems;
import compactMobs.TileEntity.TileEntityCompactor;
import compactMobs.TileEntity.TileEntityExaminer;

public class ContainerExaminer extends Container {

    int inventorySize;

    public ContainerExaminer(InventoryPlayer par1InventoryPlayer, TileEntityExaminer te) {
        inventorySize = te.getSizeInventory();
        //this.addSlotToContainer(new FullMobHolderSlot(te, 0, 47, 21));
        this.addSlotToContainer(new FullMobHolderSlot(te, 1, 80, 21));
        for (int i=0; i<4;i++)
        	this.addSlotToContainer(new DisplaySlot(te, 6-i, 10, 39+18*i));
        this.addSlotToContainer(new DisplaySlot(te, 2, 10, 121));

        for (int var3 = 0; var3 < 3; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 146 + var3 * 18));
            }
        }

        for (int var3 = 0; var3 < 9; ++var3) {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 204));
        }
    }

    public ContainerExaminer(InventoryPlayer par1, World par2World, int par3,
			int par4, int par5, EntityClientPlayerMP thePlayer) {
		// TODO Auto-generated constructor stub
	}

	@Override
    public boolean canInteractWith(EntityPlayer var1) {
        return true;
    }


	 @Override
	    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	    {
	        ItemStack itemstack = null;
	        Slot slot = (Slot)this.inventorySlots.get(par2);

	        if (slot != null && slot.getHasStack())
	        {
	            ItemStack itemstack1 = slot.getStack();
	            itemstack = itemstack1.copy();
	            
	            if (par2 >= 6 && par2 < 42)
	            {
	                if (!this.mergeItemStack(itemstack1, 0, 1, false))
	                {
	                    return null;
	                }
	            }
	            else if (!this.mergeItemStack(itemstack1, 6, 42, false))
	            {
	                return null;
	            }

	            if (itemstack1.stackSize == 0)
	            {
	                slot.putStack((ItemStack)null);
	            }
	            else
	            {
	                slot.onSlotChanged();
	            }

	            if (itemstack1.stackSize == itemstack.stackSize)
	            {
	                return null;
	            }

	            slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
	        }

	        return itemstack;
	    }
    
}
