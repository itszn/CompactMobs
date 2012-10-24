package compactMobs.TileEntity;



import java.io.DataInput;
import java.util.Iterator;
import java.util.List;

import compactMobs.CompactMobsCore;
import compactMobs.Utils;
import compactMobs.Vect;
import compactMobs.Items.CompactMobsItems;
import compactMobs.Items.FullMobHolder;

import buildcraft.api.core.Orientations;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerFramework;
import buildcraft.api.power.PowerProvider;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityAgeable;
import net.minecraft.src.EntityCreature;
import net.minecraft.src.EntityDragonBase;
import net.minecraft.src.EntityList;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntitySheep;
import net.minecraft.src.EntitySlime;
import net.minecraft.src.EntityVillager;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class TileEntityBreeder extends TileEntity implements IInventory, IPowerReceptor
{
	
	
	
	public ItemStack[] ItemStacks;
	
	IPowerProvider provider;

	
	public TileEntityBreeder() {
		ItemStacks = new ItemStack[48];
		if (PowerFramework.currentFramework != null)
			provider = PowerFramework.currentFramework.createPowerProvider();
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
		if (ItemStacks[18] != null && ItemStacks[20] == null)
		{
			override = ItemStacks[18];
			oneIn = true;
		}
		else if (ItemStacks[20] != null && ItemStacks[18] == null)
		{
			override = ItemStacks[20];
			oneIn = true;
		}
			
		int i1 = 0, j1 = 0, i2 = 0, j2=0;
		
		for (i1 = 0; i1 < 2; i1++)
		{
			for (j1 = 0; j1 < 9; j1++)
			{
				if (ItemStacks[i1*9+j1] != null)
				{
					if (ItemStacks[i1*9+j1].getItem() == Item.wheat)
					{
						if (ItemStacks[19]== null)
						{
							ItemStacks[19] = ItemStacks[i1*9+j1];
							ItemStacks[i1*9+j1] = null;
						}
						else if (ItemStacks[19].stackSize < 64)
						{
							int overflow = ItemStacks[i1*9+j1].stackSize - (64 - ItemStacks[19].stackSize);
							if (overflow < 0) overflow = 0;
							ItemStacks[19].stackSize = ItemStacks[19].stackSize + ItemStacks[i1*9+j1].stackSize - overflow;
							if (overflow == 0)
								ItemStacks[i1*9+j1] = null;
							else
								ItemStacks[i1*9+j1].stackSize = overflow;
							
							//ItemStacks[19].stackSize = ItemStacks[19].stackSize + ItemStacks[i1*9+j1].stackSize - overflow;
							continue;
						}
						
					}
				}
				else if (oneIn)
				{
					parent1Stack = override;
				}
				else
				{
					parent1Stack = ItemStacks[i1*9+j1];
				}
				if (parent1Stack != null)
				{
					if (parent1Stack.getTagCompound() != null)
					{
						//CompactMobsCore.instance.cmLog.info("***");
						//CompactMobsCore.instance.cmLog.info("0: "+String.valueOf(parent1Stack.getTagCompound().hasKey("entityId")));
						if (parent1Stack.getTagCompound().hasKey("entityId") && parent1Stack.getTagCompound().hasKey("entityGrowingAge"))
						{
							for (i2 = 0; i2 < 2; i2++)
							{
								for (j2 = 0; j2 < 9; j2++)
								{
									if (!(i1==i2 && j1==j2))
									{
										parent2Stack = ItemStacks[i2*9+j2];
										if (parent2Stack != null)
										{
											if (parent2Stack.getTagCompound() != null)
											{
												if (parent2Stack.getTagCompound().hasKey("entityId")&&parent2Stack.getTagCompound().hasKey("entityGrowingAge"))
												{
													//parent2 = (EntityLiving)EntityList.createEntityByID(parent2Stack.getTagCompound().getInteger("entityId"), worldObj);
													//CompactMobsCore.instance.cmLog.info("2: "+parent2.toString());
													//CompactMobsCore.instance.cmLog.info("3: "+String.valueOf(parent1.getClass()));
													//CompactMobsCore.instance.cmLog.info("4: "+String.valueOf(parent2.getClass()));
													//CompactMobsCore.instance.cmLog.info("5: "+String.valueOf(parent2.getClass().equals(parent1.getClass())));
													if (parent1Stack.getTagCompound().getInteger("entityId") == parent2Stack.getTagCompound().getInteger("entityId"))
													{
														parents = true;
														break;
													}
												}	
											}
										}
									}
								}
								if (parents)
									break;
							}
							if (parents)
								break;
						}
					}
				}
			}
			if (parents)
				break;
		}
		
		if (parents)
		{
			if (oneIn)
			{
				if (ItemStacks[18] == null)
				{
					ItemStacks[18] = ItemStacks[i2*9+j2];
					ItemStacks[i2*9+j2] = null;
				}
				if(ItemStacks[20] == null)
				{
					ItemStacks[20] = ItemStacks[i2*9+j2];
					ItemStacks[i2*9+j2] = null;
				}
			}
			else
			{
				if (ItemStacks[18] == null)
				{
					ItemStacks[18] = ItemStacks[i1*9+j1];
					ItemStacks[i1*9+j1] = null;
				}
				if(ItemStacks[20] == null)
				{
					ItemStacks[20] = ItemStacks[i2*9+j2];
					ItemStacks[i2*9+j2] = null;
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
            return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this &&
            player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
    }
    
    

    @Override
    public void openChest() {}

    @Override
    public void closeChest() {}
    
    @Override
    public void readFromNBT(NBTTagCompound tagCompound) 
    {
    	super.readFromNBT(tagCompound);
        NBTTagList var2 = tagCompound.getTagList("compactor");
        this.ItemStacks = new ItemStack[this.getSizeInventory()];
        System.out.println(var2.tagCount());
        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            byte var5 = var4.getByte("Slot");

            if (var5 >= 0 && var5 < this.ItemStacks.length)
            {
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
		if (provider.useEnergy(25, 25, true) < 25)
			return;
		World world = worldObj;
		
		ItemStack wheat = ItemStacks[19];
		ItemStack parent1 = ItemStacks[18];
		ItemStack parent2 = ItemStacks[17];
		NBTTagCompound nbttag1 = null;
		NBTTagCompound nbttag2 = null;
		
		if (wheat != null)
		{
			if (parent1.getItem() == CompactMobsItems.fullMobHolder && parent2.getItem() == CompactMobsItems.fullMobHolder)
			{
				if (parent1.getTagCompound() != null && parent2.getTagCompound() != null)
				{
					nbttag1 = parent1.getTagCompound();
					nbttag2 = parent2.getTagCompound();
					if (nbttag1.hasKey("entityGrowingAge") && nbttag2.hasKey("entityGrowingAge"))
					{
						if (nbttag1.getInteger("entityId") != 120 && nbttag1.getInteger("entityId") ==nbttag2.getInteger("entityId"))
						{
							NBTTagCompound nbttag3 = null;
							nbttag1.setInteger("entityGrowingAge", 6000);
							nbttag2.setInteger("entityGrowingAge", 6000);
							nbttag3.setInteger("entityGrowingAge", -24000);
							
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
	
	public void dumpItems()
	{
		
		Orientations[] pipes = Utils.getPipeDirections(this.worldObj, new Vect(this.xCoord, this.yCoord, this.zCoord), Orientations.XNeg);
	    if (pipes.length > 0) {
	      dumpToPipe(pipes);
	    } else {
	      IInventory[] inventories = Utils.getAdjacentInventories(this.worldObj, new Vect(this.xCoord, this.yCoord, this.zCoord));
	      dumpToInventory(inventories);
	    }
	}
	
	private void dumpToPipe(Orientations[] pipes)
	{
		Orientations[] filtered;
		filtered = Utils.filterPipeDirections(pipes, new Orientations[] { Orientations.YNeg, Orientations.YPos});
		
		for (int i = 21; i < 48; i++) {
			if (ItemStacks[i]!=null)
			{
				while ((ItemStacks[i].stackSize>0) && (filtered.length > 0)) {
					Utils.putFromStackIntoPipe(this, filtered, ItemStacks[i]);
					
				}
				if (this.ItemStacks[i].stackSize <= 0)
					this.ItemStacks[i] = null;
			}
		}
	}
	 
	public void dumpToInventory(IInventory[] inventories)
	{
	for (int i = 8; i < 57; i++) {
		if (ItemStacks[i]!=null && ItemStacks[i].stackSize>0)
		{
			
		 
			for (int j = 0; j < inventories.length; j++)
			{
				if (inventories[j] != null)
				{
					IInventory inventory = Utils.getChest(inventories[j]);
				
				
					Utils.stowInInventory(ItemStacks[i], inventory, true);
					if (this.ItemStacks[i].stackSize <= 0)
						this.ItemStacks[i] = null;
					}
				}
			}
		}
	}


	
	public double getDistanceSqToEntity(Entity par1Entity)
    {
        double var2 = this.xCoord - par1Entity.posX;
        double var4 = this.yCoord - par1Entity.posY;
        double var6 = this.zCoord - par1Entity.posZ;
        return var2 * var2 + var4 * var4 + var6 * var6;
    }
	
}
