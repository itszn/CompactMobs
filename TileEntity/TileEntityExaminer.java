package compactMobs.TileEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerFramework;

import compactMobs.CompactMobsCore;
import compactMobs.Utils;
import compactMobs.Vect;
import compactMobs.Items.CompactMobsItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityExaminer extends TileEntity implements IInventory, ISidedInventory {

    public ItemStack[] ItemStacks;
    public String text = "";
    protected float stored;
    protected boolean use = false;
    
    //protected Random rand;

    public TileEntityExaminer() {
        ItemStacks = new ItemStack[7];
        
    }

    @Override
    public void updateEntity() {
    	


    	if (ItemStacks[1] != null) {
            NBTTagCompound nbttag = ItemStacks[1].getTagCompound();
            if (nbttag != null) {
            	if (!nbttag.hasKey("infoVisable")||!nbttag.getBoolean("infoVisable")) {
		                nbttag.setBoolean("infoVisable", true);
		                ItemStacks[1].setTagCompound(nbttag);
	                
            	}
            }
        }
		
        return;
    }
    
    
    public void runNamer() {
    	if (ItemStacks[1]!=null) {
    		if (worldObj.getBlockMetadata(xCoord, yCoord, zCoord)!=1)
    			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 2);
    	}
    	else{
    		if (worldObj.getBlockMetadata(xCoord, yCoord, zCoord)!=0)
    			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
    	}
        
    }
    
    @Override
    public void onInventoryChanged() {
    	
    }
    
   
    

    @Override
    public int getSizeInventory() {
        return ItemStacks.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return ItemStacks[slot];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        ItemStacks[slot] = stack;
        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public ItemStack decrStackSize(int slot, int amt) {
        ItemStack stack = getStackInSlot(slot);
        if (stack != null) {
            if (stack.stackSize <= amt) {
                setInventorySlotContents(slot, null);
            } else {
                stack = stack.splitStack(amt);
                if (stack.stackSize == 0) {
                    setInventorySlotContents(slot, null);
                }
            }
        }
        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        ItemStack stack = getStackInSlot(slot);
        if (stack != null) {
            setInventorySlotContents(slot, null);
        }
        return stack;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this
                && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
    }

    @Override
    public void openChest() {
    }

    @Override
    public void closeChest() {
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        NBTTagList var2 = tagCompound.getTagList("namer");
        this.ItemStacks = new ItemStack[this.getSizeInventory()];
        //this.text = tagCompound.getString("text");
        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
            byte var5 = var4.getByte("Slot");

            if (var5 >= 0 && var5 < this.ItemStacks.length) {
                this.ItemStacks[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        NBTTagList itemList = new NBTTagList();
        //tagCompound.setString("text", this.text);
        for (int i = 0; i < ItemStacks.length; i++) {
            ItemStack stack = ItemStacks[i];
            if (stack != null) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte) i);
                stack.writeToNBT(tag);
                itemList.appendTag(tag);
            }
        }
        tagCompound.setTag("namer", itemList);
    }

    @Override
    public String getInvName() {
        return "Namer";
    }

   

    public double getDistanceSqToEntity(Entity par1Entity) {
        double var2 = this.xCoord - par1Entity.posX;
        double var4 = this.yCoord - par1Entity.posY;
        double var6 = this.zCoord - par1Entity.posZ;
        return var2 * var2 + var4 * var4 + var6 * var6;
    }




	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		if (itemstack.getItem() == CompactMobsItems.fullMobHolder && i==1)
			return true;
		return false;
	}

	

	@Override
	public int getStartInventorySide(ForgeDirection side) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side) {
		// TODO Auto-generated method stub
		return 1;
	}
}
