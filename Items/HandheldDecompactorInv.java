package compactMobs.Items;

import compactMobs.Utils;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;

public class HandheldDecompactorInv implements IInventory {

	EntityPlayer player;
	ItemStack[] itemStacks;
	ItemStack stack;
	
	
	public HandheldDecompactorInv(ItemStack stack, EntityPlayer player)
	{
		this.player = player;
		this.itemStacks = new ItemStack[this.getSizeInventory()];
		this.stack = stack;
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		this.readFromNBT(stack.getTagCompound());
		
	}
	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if( 0 <= slot && slot < this.getSizeInventory() )
			return itemStacks[slot];
		return null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int count) {
		if (itemStacks[slot] == null)
			return null;
		if (itemStacks[slot].stackSize > count)
			return itemStacks[slot].splitStack(count);

		ItemStack retValue = itemStacks[slot];
		itemStacks[slot] = null;
        return retValue;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		//ItemStack retValue = getStackInSlot(slot);
		//setInventorySlotContents(slot, null);
		//return retValue;
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		NBTTagCompound nbttag = stack.getTagCompound();
		this.writeToNBT(nbttag);
		stack.setTagCompound(nbttag);
		return itemStacks[slot];
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		if( 0 <= slot && slot < this.getSizeInventory())
			itemStacks[slot] = stack;
		
	}

	@Override
	public String getInvName() {
		// TODO Auto-generated method stub
		return "HandDecompactor";
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void onInventoryChanged() {
		for(int i=0; i<this.getSizeInventory(); i++) {
			ItemStack stack = this.getStackInSlot(i);
			if( stack != null && stack.stackSize == 0 )
				this.setInventorySlotContents(i, null);
		}
		
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void openChest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeChest() {
		// TODO Auto-generated method stub
		
	}

	public void readFromNBT(NBTTagCompound compound) {
		NBTTagCompound c = ((NBTTagCompound)compound.getTag("inv"));
		if( c == null ) return;

        NBTTagList list = c.getTagList("inventoryContents");
        for (int i=0; i<list.tagCount(); i++) {
            NBTTagCompound tag = (NBTTagCompound) list.tagAt(i);
            int index = tag.getInteger("index");
            itemStacks[index] = Utils.readStackFromNBT(tag);
        }
    }

    public void writeToNBT(NBTTagCompound compound) {
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < itemStacks.length; i++) {
            if ( itemStacks[i] != null && itemStacks[i].stackSize > 0) {
                NBTTagCompound tag = new NBTTagCompound();
                list.appendTag(tag);
                tag.setInteger("index", i);
                itemStacks[i].writeToNBT(tag);
            }
        }
		NBTTagCompound ownTag = new NBTTagCompound();
			ownTag.setTag("inventoryContents", list);
		compound.setTag("inv", ownTag);
    }
	
}
