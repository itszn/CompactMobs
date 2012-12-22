package compactMobs.TileEntity;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
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

import compactMobs.CompactMobsCore;
import compactMobs.Utils;
import compactMobs.Vect;
import compactMobs.Items.CompactMobsItems;

public class TileEntityBreeder extends TileEntity implements IInventory, IPowerReceptor, ISidedInventory {

    public ItemStack[] ItemStacks;
    IPowerProvider provider;
    //protected Random rand;

    public TileEntityBreeder() {
        ItemStacks = new ItemStack[48];
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
        ItemStack parent1Stack = null;
        ItemStack parent2Stack = null;
        ItemStack override = null;
        EntityLiving parent1 = null;
        EntityLiving parent2 = null;
        boolean parents = false;
        boolean oneIn = false;
        if (ItemStacks[18] != null && ItemStacks[20] == null) {
            override = ItemStacks[18];
            oneIn = true;
        } else if (ItemStacks[20] != null && ItemStacks[18] == null) {
            override = ItemStacks[20];
            oneIn = true;
        }
        //CompactMobsCore.instance.cmLog.info("***");
        //CompactMobsCore.instance.cmLog.info("0: " + String.valueOf(oneIn));

        int i1 = 0, j1 = 0, i2 = 0, j2 = 0;

        for (i1 = 0; i1 < 18; i1++) {
            if (ItemStacks[i1] != null) {
                if (ItemStacks[i1].getItem() == CompactMobsItems.mobHolder) {
                    if (ItemStacks[19] == null) {
                        ItemStacks[19] = ItemStacks[i1];
                        ItemStacks[i1] = null;
                    } else if (ItemStacks[19].stackSize < 64) {
                        int overflow = ItemStacks[i1].stackSize - (64 - ItemStacks[19].stackSize);
                        if (overflow < 0) {
                            overflow = 0;
                        }
                        ItemStacks[19].stackSize = ItemStacks[19].stackSize + ItemStacks[i1].stackSize - overflow;
                        if (overflow == 0) {
                            ItemStacks[i1] = null;
                        } else {
                            ItemStacks[i1].stackSize = overflow;
                        }

                        //ItemStacks[19].stackSize = ItemStacks[19].stackSize + ItemStacks[i1*9+j1].stackSize - overflow;
                        continue;
                    }

                } else if (oneIn) {
                    parent1Stack = override;
                } else {
                    parent1Stack = ItemStacks[i1];
                }
                if (parent1Stack != null) {
                    if (parent1Stack.getItem() == CompactMobsItems.fullMobHolder) {
                        if (parent1Stack.getTagCompound() != null) {
                            //CompactMobsCore.instance.cmLog.info("0: "+String.valueOf(parent1Stack.getTagCompound().hasKey("entityId")));
                            if (parent1Stack.getTagCompound().hasKey("entityId") && parent1Stack.getTagCompound().hasKey("entityGrowingAge")) {
                                for (i2 = 0; i2 < 18; i2++) {
                                    if (!(i1 == i2) || oneIn) {
                                        parent2Stack = ItemStacks[i2];
                                        if (parent2Stack != null) {
                                            if (parent2Stack.getItem() == CompactMobsItems.fullMobHolder) {
                                                if (parent2Stack.getTagCompound() != null) {
                                                    if (parent2Stack.getTagCompound().hasKey("entityId") && parent2Stack.getTagCompound().hasKey("entityGrowingAge")) {
                                                        if (parent1Stack.getTagCompound().getInteger("entityGrowingAge") == 0 && parent2Stack.getTagCompound().getInteger("entityGrowingAge") == 0) {
                                                            if (parent1Stack.getTagCompound().getInteger("entityId") == parent2Stack.getTagCompound().getInteger("entityId")) {
                                                                parents = true;
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                if (parents) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }

        }

        if (parents) {
            if (oneIn) {
                if (ItemStacks[18] == null) {
                    ItemStacks[18] = ItemStacks[i2];
                    ItemStacks[i2] = null;
                }
                if (ItemStacks[20] == null) {
                    ItemStacks[20] = ItemStacks[i2];
                    ItemStacks[i2] = null;
                }
            } else {
                if (ItemStacks[18] == null) {
                    ItemStacks[18] = ItemStacks[i1];
                    ItemStacks[i1] = null;
                }
                if (ItemStacks[20] == null) {
                    ItemStacks[20] = ItemStacks[i2];
                    ItemStacks[i2] = null;
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

        ItemStack wheat = ItemStacks[19];
        ItemStack parent1 = ItemStacks[18];
        ItemStack parent2 = ItemStacks[20];
        ItemStack child = null;
        NBTTagCompound nbttag1 = null;
        NBTTagCompound nbttag2 = null;

        int stackNum1 = -1, stackNum2 = -1, stackNum3 = -1;
        for (int i = 21; i < 48; i++) {
            if (ItemStacks[i] == null) {
                if (stackNum1 == -1) {
                    stackNum1 = i;
                } else if (stackNum2 == -1) {
                    stackNum2 = i;
                } else if (stackNum3 == -1) {
                    stackNum3 = i;
                } else {
                    break;
                }
            }
        }
        //CompactMobsCore.instance.cmLog.info("***");
        //CompactMobsCore.instance.cmLog.info(String.valueOf(stackNum1)+" "+String.valueOf(stackNum2)+" "+String.valueOf(stackNum3));
        //CompactMobsCore.instance.cmLog.info(String.valueOf(wheat != null)+" "+String.valueOf(parent1 != null)+" "+String.valueOf(parent2 != null));
        if (wheat != null && wheat.getItem() == CompactMobsItems.mobHolder && stackNum1 >= 0 && stackNum2 >= 0 && stackNum3 >= 0 && parent1 != null && parent2 != null) {
            if (parent1.getItem() == CompactMobsItems.fullMobHolder && parent2.getItem() == CompactMobsItems.fullMobHolder) {
                if (parent1.getTagCompound() != null && parent2.getTagCompound() != null) {
                    nbttag1 = parent1.getTagCompound();
                    nbttag2 = parent2.getTagCompound();
                    //CompactMobsCore.instance.cmLog.info("1: "+String.valueOf(nbttag1.hasKey("entityGrowingAge")));
                    //CompactMobsCore.instance.cmLog.info("2: "+String.valueOf(nbttag2.hasKey("entityGrowingAge")));
                    if (nbttag1.hasKey("entityGrowingAge") && nbttag2.hasKey("entityGrowingAge")) {
                        if (nbttag1.getInteger("entityGrowingAge") == 0 && nbttag1.getInteger("entityGrowingAge") == 0) {
                            //CompactMobsCore.instance.cmLog.info("3: "+String.valueOf(nbttag1.getInteger("entityId")));
                            //CompactMobsCore.instance.cmLog.info("4: "+String.valueOf(nbttag2.getInteger("entityId")));
                            if (nbttag1.getInteger("entityId") != 120 && nbttag1.getInteger("entityId") == nbttag2.getInteger("entityId")) {
                                NBTTagCompound nbttag3 = new NBTTagCompound();
                                nbttag1.setInteger("entityGrowingAge", 6000);
                                nbttag2.setInteger("entityGrowingAge", 6000);
                                nbttag3.setInteger("entityGrowingAge", -24000);
                                nbttag3.setInteger("entityId", nbttag1.getInteger("entityId"));
                                nbttag3.setString("name", nbttag1.getString("name"));
                                if (nbttag1.getInteger("entityId") == 91) {
                                    if (worldObj.rand.nextBoolean()) {
                                        nbttag3.setInteger("entityColor", nbttag1.getInteger("entityColor"));
                                    } else {
                                        nbttag3.setInteger("entityColor", nbttag2.getInteger("entityColor"));
                                    }
                                }
                                parent1.setTagCompound(nbttag1);
                                parent2.setTagCompound(nbttag2);
                                child = new ItemStack(CompactMobsItems.fullMobHolder, 1, nbttag1.getInteger("entityId"));
                                child.setTagCompound(nbttag3);
                                ItemStacks[stackNum1] = parent1;
                                ItemStacks[stackNum2] = parent2;
                                ItemStacks[stackNum3] = child;
                                if (ItemStacks[19].stackSize > 1) {
                                    ItemStacks[19].stackSize = ItemStacks[19].stackSize - 1;
                                } else {
                                    ItemStacks[19] = null;
                                }
                                ItemStacks[18] = null;
                                ItemStacks[20] = null;
                                Random random = new Random();

                                CompactMobsCore.instance.proxy.spawnParticle("heart", this.xCoord + (random.nextDouble() / 2) + .25D, this.yCoord + 1.5D, this.zCoord + (random.nextDouble() / 2) + .25D, 0, 0, -.1, 3);
                                CompactMobsCore.instance.proxy.spawnParticle("heart", this.xCoord + (random.nextDouble() / 2) + .25D, this.yCoord + 1.5D, this.zCoord + (random.nextDouble() / 2) + .25D, 0, 0, -.1, 3);
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

        for (int i = 21; i < 48; i++) {
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
        for (int i = 27; i < 48; i++) {
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
        return 18;
    }
}
