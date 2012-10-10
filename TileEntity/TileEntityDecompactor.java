package compactMobs.TileEntity;



import java.io.DataInput;
import java.util.Iterator;
import java.util.List;

import compactMobs.CompactMobsCore;
import compactMobs.Items.CompactMobsItems;
import compactMobs.Items.FullMobHolder;

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
import net.minecraft.src.EntityList;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntitySheep;
import net.minecraft.src.EntityVillager;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class TileEntityDecompactor extends TileEntity implements IInventory, IPowerReceptor
{
	
	public ItemStack[] ItemStacks;
	
	IPowerProvider provider;

	
	public TileEntityDecompactor() {
		ItemStacks = new ItemStack[30];
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
		/*if (ItemStacks[0] != null)
		{
			if (ItemStacks[0].getItem() == Item.redstone)
			{
				for (int var3 = 1; var3 < 29; ++var3)
		        {
					if (ItemStacks[var3] != null)
					{
						if (ItemStacks[var3].getItem() == Item.paper){
							if(ItemStacks[var3].stackSize < 64){
								ItemStacks[var3] = new ItemStack(Item.paper, ItemStacks[var3].stackSize+1);
								break;
							}
						}
					} else
					{
						ItemStacks[var3] = new ItemStack(Item.paper, 1);
						break;
					}
		        }
				
				if (ItemStacks[0].stackSize > 1)
				{
					ItemStacks[0] = new ItemStack(ItemStacks[0].getItem(), ItemStacks[0].stackSize-1);
				} else 
				{
					ItemStacks[0] = null;
				}
			}
		}*/
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
		//double radius = 3.0D;
		//List list1 = world.getEntitiesWithinAABB(EntityCreature.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)this.xCoord-radius, (double)this.yCoord-1.0D, (double)this.zCoord-radius, (double)this.xCoord+radius, (double)this.yCoord+1.0D, (double)this.zCoord+radius));
		
		//EntityCreature entity = null;
        //double var6 = Double.MAX_VALUE;
        //Iterator entitys = list1.iterator();

        /*while (entitys.hasNext())
        {
            EntityCreature var9 = (EntityCreature)entitys.next();

            
            double var10 = this.getDistanceSqToEntity(var9);

            if (var10 <= var6)
            {
                entity = var9;
                var6 = var10;
            }
            
        }*/
		ItemStack stack = null;
		int stackNum;
		for (stackNum = 0; stackNum<27; stackNum++)
		{
			if (ItemStacks[stackNum] != null)
			{
				if (ItemStacks[stackNum].getItem() == CompactMobsItems.fullMobHolder)
				{
					stack = ItemStacks[stackNum];
					break;
				}
			}
		}
        
		if (stack != null)
		{
			NBTTagCompound ntbtag = stack.getTagCompound();
			if (ntbtag != null)
			{
				EntityCreature entity = null;
				if (ntbtag.hasKey("entityId"))
				{
					int id = ntbtag.getInteger("entityId");
					entity = (EntityCreature)EntityList.createEntityByID(id, world);
				} else { return; }
				int dir = world.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
				CompactMobsCore.instance.cmLog.info("dir: "+String.valueOf(dir));
				if (dir == 2)
				{
					entity.setPosition(this.xCoord+.5D, this.yCoord, this.zCoord-.5D);
				}
				else if (dir == 3)
				{
					entity.setPosition(this.xCoord+.5D, this.yCoord, this.zCoord+1.5D);
				}
				else if (dir == 4)
				{
					entity.setPosition(this.xCoord-.5D, this.yCoord, this.zCoord+.5D);
				}
				else if (dir == 5)
				{
					entity.setPosition(this.xCoord+1.5D, this.yCoord, this.zCoord+.5D);
				}
				else
				{
					entity.setPosition(this.xCoord+.5D, this.yCoord+1D, this.zCoord+.5D);
				}
				
				if (ntbtag.hasKey("entityHealth"))
				{
					int health = ntbtag.getInteger("entityHealth");
					entity.setEntityHealth(health);
				}
				
				if (entity instanceof EntityAgeable)
				{
					EntityAgeable entityAgeable = (EntityAgeable)entity;
					if (ntbtag.hasKey("entityGrowingAge"))
					{
						int age = ntbtag.getInteger("entityGrowingAge");
						entityAgeable.setGrowingAge(age);
					}
					if (entityAgeable instanceof EntitySheep)
					{
						EntitySheep entitySheep = (EntitySheep)entityAgeable;
						if (ntbtag.hasKey("entitySheared"))
						{
							boolean sheared = ntbtag.getBoolean("entitySheared");
							entitySheep.setSheared(sheared);
						}
						if (ntbtag.hasKey("entityColor"))
						{
							int color = ntbtag.getInteger("entityColor");
							entitySheep.setFleeceColor(color);
						}
						world.spawnEntityInWorld(entitySheep);
						
					} else if (entityAgeable instanceof EntityVillager)
					{
						EntityVillager entityVillager =  (EntityVillager)entityAgeable;
						if (ntbtag.hasKey("entityProfession"))
						{
							int profession = ntbtag.getInteger("entityProfession");
							entityVillager.setProfession(profession);
						}
						world.spawnEntityInWorld(entityVillager);
					} else
					{
						world.spawnEntityInWorld(entityAgeable);
					}
				} else
				{
					world.spawnEntityInWorld(entity);
				}
			}
			//int id = EntityList.getEntityID(entity);
			
			//CompactMobsCore.instance.cmLog.info("1: " + String.valueOf(id));
			
			//var5.getHealth();
			//world.spawnEntityInWorld(newMob);
			if (ItemStacks[stackNum].stackSize > 1)
			{
				ItemStacks[stackNum] = new ItemStack(ItemStacks[stackNum].getItem(), ItemStacks[stackNum].stackSize-1);
			} else 
			{
				ItemStacks[stackNum] = null;
			}
			
		}
		
	}

	@Override
	public int powerRequest() {
		// TODO Auto-generated method stub
		return 25;
	}
	
	public double getDistanceSqToEntity(Entity par1Entity)
    {
        double var2 = this.xCoord - par1Entity.posX;
        double var4 = this.yCoord - par1Entity.posY;
        double var6 = this.zCoord - par1Entity.posZ;
        return var2 * var2 + var4 * var4 + var6 * var6;
    }
	
}
