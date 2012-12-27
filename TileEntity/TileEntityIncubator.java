package compactMobs.TileEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerFramework;

import compactMobs.Utils;
import compactMobs.Vect;
import compactMobs.Items.CompactMobsItems;

public class TileEntityIncubator extends TileEntity implements IInventory, IPowerReceptor, ISidedInventory {

    public ItemStack[] ItemStacks;
    IPowerProvider provider;
    //protected Random rand;

    public TileEntityIncubator() {
        ItemStacks = new ItemStack[54];
        if (PowerFramework.currentFramework != null) {
            provider = PowerFramework.currentFramework.createPowerProvider();
        }
        provider.configure(50, 25, 25, 25, 1000);
    }

    @Override
    public void updateEntity() {

        if (this instanceof IPowerReceptor) {
            IPowerReceptor receptor = ((IPowerReceptor) this);

            receptor.getPowerProvider().update(receptor);

        }
        for (int i = 0; i < 27; i++) {
            if (ItemStacks[i] != null) {
                NBTTagCompound nbttag = ItemStacks[i].getTagCompound();
                if (nbttag != null) {
                    if (nbttag.hasKey("entityGrowingAge")) {
                        nbttag.setBoolean("inIncubator", true);
                        ItemStacks[i].setTagCompound(nbttag);
                    }
                }
            }
        }

        return;
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
        NBTTagList var2 = tagCompound.getTagList("compactor");
        this.ItemStacks = new ItemStack[this.getSizeInventory()];
        System.out.println(var2.tagCount());
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

        for (int i = 0; i < ItemStacks.length; i++) {
            ItemStack stack = ItemStacks[i];
            if (stack != null) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte) i);
                stack.writeToNBT(tag);
                itemList.appendTag(tag);
            }
        }
        tagCompound.setTag("compactor", itemList);
    }

    @Override
    public String getInvName() {
        return "Compactor";
    }

    @Override
    public void setPowerProvider(IPowerProvider iprovider) {
        // TODO Auto-generated method stub
        provider = iprovider;
    }

    @Override
    public IPowerProvider getPowerProvider() {
        // TODO Auto-generated method stub
        return provider;
    }

    @Override
    public void doWork() {
        if (provider.useEnergy(25, 25, true) < 25) {
            return;
        }
        World world = worldObj;

        /*
         * int stackNum1 = -1,stackNum2 = -1; for (int i = 27; i<54; i++) { if
         * (ItemStacks[i] == null) { if (stackNum1 == -1) stackNum1 = i; else
         * break; }
		}
         */

        for (int i = 0; i < 27; i++) {
            if (ItemStacks[i] != null) {
                if (ItemStacks[i].getItem() == CompactMobsItems.fullMobHolder) {
                    if (ItemStacks[i].getTagCompound() != null) {
                        NBTTagCompound nbttag = ItemStacks[i].getTagCompound();
                        if (nbttag.hasKey("entityGrowingAge")) {
                            int age = nbttag.getInteger("entityGrowingAge");
                            //CompactMobsCore.instance.cmLog.info(String.valueOf(age));
                            if (age != 0) {
                                if (age > 0) {
                                    age = age - 100;
                                    if (age < 0) {
                                        age = 0;
                                    }
                                    nbttag.setInteger("entityGrowingAge", age);
                                    NBTTagCompound var;
                                    var = nbttag.getCompoundTag("entityTags");
                                    var.setInteger("Age", age);
                                    nbttag.setCompoundTag("entityTags",var);
                                } else if (age < 0) {
                                    age = age + 200;
                                    if (age > 0) {
                                        age = 0;
                                    }
                                    nbttag.setInteger("entityGrowingAge", age);
                                    NBTTagCompound var;
                                    var = nbttag.getCompoundTag("entityTags");
                                    var.setInteger("Age", age);
                                    nbttag.setCompoundTag("entityTags",var);
                                }
                                ItemStacks[i].setTagCompound(nbttag);
                            }
                            if (age == 0) {
                                int stackNum1 = -1;
                                for (int i1 = 27; i1 < 54; i1++) {
                                    if (ItemStacks[i1] == null) {
                                        if (stackNum1 == -1) {
                                            stackNum1 = i1;
                                        } else {
                                            break;
                                        }
                                    }
                                }

                                if (stackNum1 >= 0) {
                                    nbttag.setBoolean("inIncubator", false);
                                    ItemStacks[stackNum1] = ItemStacks[i];
                                    ItemStacks[stackNum1].setTagCompound(nbttag);
                                    ItemStacks[i] = null;
                                }
                            }
                        }
                    }
                }
            }
        }
        dumpItems();

    }

    @Override
    public int powerRequest() {
        // TODO Auto-generated method stub
        return 25;
    }

    public void dumpItems() {

        ForgeDirection[] pipes = Utils.getPipeDirections(this.worldObj, new Vect(this.xCoord, this.yCoord, this.zCoord), ForgeDirection.WEST);
        if (pipes.length > 0) {
            dumpToPipe(pipes);
        } else {
            IInventory[] inventories = Utils.getAdjacentInventories(this.worldObj, new Vect(this.xCoord, this.yCoord, this.zCoord));
            dumpToInventory(inventories);
        }
    }

    private void dumpToPipe(ForgeDirection[] pipes) {
        ForgeDirection[] filtered;
        filtered = Utils.filterPipeDirections(pipes, new ForgeDirection[]{ForgeDirection.DOWN, ForgeDirection.UP});

        for (int i = 27; i < 54; i++) {
            if (ItemStacks[i] != null) {
                while ((ItemStacks[i].stackSize > 0) && (filtered.length > 0)) {
                    Utils.putFromStackIntoPipe(this, filtered, ItemStacks[i]);

                }
                if (this.ItemStacks[i].stackSize <= 0) {
                    this.ItemStacks[i] = null;
                }
            }
        }
    }

    public void dumpToInventory(IInventory[] inventories) {
        for (int i = 27; i < 54; i++) {
            if (ItemStacks[i] != null && ItemStacks[i].stackSize > 0) {


                for (int j = 0; j < inventories.length; j++) {
                    if (inventories[j] != null) {
                        IInventory inventory = Utils.getChest(inventories[j]);


                        Utils.stowInInventory(ItemStacks[i], inventory, true);
                        if (this.ItemStacks[i].stackSize <= 0) {
                            this.ItemStacks[i] = null;
                        }
                    }
                }
            }
        }
    }

    public double getDistanceSqToEntity(Entity par1Entity) {
        double var2 = this.xCoord - par1Entity.posX;
        double var4 = this.yCoord - par1Entity.posY;
        double var6 = this.zCoord - par1Entity.posZ;
        return var2 * var2 + var4 * var4 + var6 * var6;
    }

    @Override
    public int getStartInventorySide(ForgeDirection side) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getSizeInventorySide(ForgeDirection side) {
        // TODO Auto-generated method stub
        return 27;
    }
}
