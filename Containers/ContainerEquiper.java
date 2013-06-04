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
import compactMobs.TileEntity.TileEntityEquiper;
import compactMobs.TileEntity.TileEntityExaminer;

public class ContainerEquiper extends Container {

    int inventorySize;

    public ContainerEquiper(InventoryPlayer par1InventoryPlayer, TileEntityEquiper te) {
        inventorySize = te.getSizeInventory();
        //this.addSlotToContainer(new FullMobHolderSlot(te, 0, 47, 21));
        this.addSlotToContainer(new FullMobHolderSlot(te, 1, 80, 21));
        for (int i=0; i<4;i++)
        	this.addSlotToContainer(new EquipArmorSlot(te, 6-i, 44, 49+18*i,i,te));
        this.addSlotToContainer(new EquipArmorSlot(te, 2, 62, 67,-1,te));
        this.addSlotToContainer(new UpgradeSlot(te,7,116,67,new int[]{2}));

        for (int var3 = 0; var3 < 3; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 146 + var3 * 18));
            }
        }

        for (int var3 = 0; var3 < 9; ++var3) {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 204));
        }
    }

    public ContainerEquiper(InventoryPlayer par1, World par2World, int par3,
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
	            
	            if (par2 >= 7 && par2 < 43)
	            {
	                if (!this.mergeItemStack(itemstack1, 0, 1, false))
	                {
	                    return null;
	                }
	            }
	            else if (!this.mergeItemStack(itemstack1, 7, 43, false))
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
