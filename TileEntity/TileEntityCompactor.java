package compactMobs.TileEntity;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
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

public class TileEntityCompactor extends TileEntity implements IInventory, IPowerReceptor, ISidedInventory {

    public ItemStack[] ItemStacks;
    IPowerProvider provider;
    boolean drawParticle;
    Entity lastEntity = null;

    public TileEntityCompactor() {
        ItemStacks = new ItemStack[28];
        if (PowerFramework.currentFramework != null) {
            provider = PowerFramework.currentFramework.createPowerProvider();
        }
        provider.configure(50, 1, 25, 25, 100);
    }
    
    
    @Override
    public void updateEntity() {

        if (this instanceof IPowerReceptor) {
            IPowerReceptor receptor = ((IPowerReceptor) this);

            receptor.getPowerProvider().update(receptor);
        }
        //worldObj.spawnParticle("smoke", this.xCoord+1.5D, this.yCoord+.5D, this.zCoord+.5, -.1, 0, 0);
        //this.worldObj.spawnParticle("smoke", this.xCoord+1.5D, this.yCoord+.5D, this.zCoord+.5, -.1, 0, 0);

        /*
         * if (ItemStacks[0] != null) { if (ItemStacks[0].getItem() ==
         * Item.redstone) { for (int var3 = 1; var3 < 29; ++var3) { if
         * (ItemStacks[var3] != null) { if (ItemStacks[var3].getItem() ==
         * Item.paper){ if(ItemStacks[var3].stackSize < 64){ ItemStacks[var3] =
         * new ItemStack(Item.paper, ItemStacks[var3].stackSize+1); break; } } }
         * else { ItemStacks[var3] = new ItemStack(Item.paper, 1); break; } }
         *
         * if (ItemStacks[0].stackSize > 1) { ItemStacks[0] = new
         * ItemStack(ItemStacks[0].getItem(), ItemStacks[0].stackSize-1); } else
         * { ItemStacks[0] = null; } }
		}
         */
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
    
    
    
    //@Override
	public void doWork() {
        World world = worldObj;
        double radius = 3.0D;
        List list1 = world.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox((double) this.xCoord - radius, (double) this.yCoord - 1.0D, (double) this.zCoord - radius, (double) this.xCoord + radius, (double) this.yCoord + 1.0D, (double) this.zCoord + radius));

        EntityLiving entity = null;
        double var6 = Double.MAX_VALUE;
        Iterator entitys = list1.iterator();
        boolean shard = false;
        String s = "";

        while (entitys.hasNext()) {

            EntityLiving var9 = (EntityLiving) entitys.next();

            
            if (!(var9 instanceof EntityPlayer) && !(var9 instanceof EntityDragon) /*&& !var9.equals(lastEntity)*/) {
                double var10 = this.getDistanceSqToEntity(var9);

                if (var10 <= var6 && EntityList.getEntityID(var9)!=0) {
                    entity = var9;
                    var6 = var10;
                    //System.out.println(EntityList.getEntityString(var9));
                }
                else if (EntityList.getEntityID(var9)==0 && var9.getEntityData() != null)
                {
                	if (var9.getEntityData().hasKey("mobcage"))
                	{
                		s = EntityList.getEntityString(var9);
                		//System.out.println(s);
                		if (s.startsWith("SoulShards.Spawned"))
                		{
                			s = s.split("SoulShards.Spawned")[1];
                			shard = true;
                			NBTTagCompound shardtag = var9.getEntityData();
                			shardtag.setString("name", s);
                			
                			entity = var9;
                            var6 = var10;
                			//System.out.println(s);
                		}
                	}
                	
                }
            }
        }
        if (ItemStacks[0] != null && entity != null) {
        	
            int id = EntityList.getEntityID(entity);
            //entity.isDead=true;
            	

            //CompactMobsCore.instance.cmLog.info("1: " + String.valueOf(id));

            //var5.getHealth();
            //world.spawnEntityInWorld(newMob);
            if (ItemStacks[0].getItem() == CompactMobsItems.mobHolder);
            {
                int empty = 0;
                int var3;
                for (var3 = 1; var3 < 28; var3++) {
                	if(ItemStacks[var3]==null)
                	{
                        empty++;
                        break;
                	}
                	else
                	{
                		if (ItemStacks[var3].stackSize == 0) {
	                        empty++;
	                        break;
	                    }
                	}
                }
                if (empty > 0) {
                	float test = provider.useEnergy(25, 25, true);
                	System.out.println(test);
                	if (test==25)//provider.useEnergy(25, 25, true)==25)
                	{

	                    ItemStack holder = new ItemStack(CompactMobsItems.fullMobHolder, 1);
	                    //FullMobHolder holder = new FullMobHolder(CompactMobsItems.fullMobHolder.shiftedIndex);
	                    NBTTagCompound nbttag = holder.stackTagCompound;
	                    if (nbttag == null) {
	                        nbttag = new NBTTagCompound();
	                    }
	                    nbttag.setInteger("entityId", id);
	                    
	                    if (entity instanceof EntityAgeable) {
	                        EntityAgeable entityAge = (EntityAgeable) entity;
	                        nbttag.setInteger("entityGrowingAge", entityAge.getGrowingAge());
	                    }
	
	                    if (entity instanceof EntitySheep) {
	                        EntitySheep entitySheep = (EntitySheep) entity;
	                        nbttag.setBoolean("entitySheared", entitySheep.getSheared());
	                        nbttag.setInteger("entityColor", entitySheep.getFleeceColor());
	                        
	                    }

	                    world.removeEntity(entity);
	                    
	                 
	                    /*nbttag.setInteger("entityHealth", entity.getHealth());
	
	                    if (entity instanceof EntitySlime) {
	                        EntitySlime entitySlime = (EntitySlime) entity;
	                        nbttag.setInteger("entitySize", entitySlime.getSlimeSize());
	                    }
	                    if (entity instanceof EntityAgeable) {
	                        EntityAgeable entityAge = (EntityAgeable) entity;
	                        nbttag.setInteger("entityGrowingAge", entityAge.getGrowingAge());
	                    }
	
	                    if (entity instanceof EntitySheep) {
	                        EntitySheep entitySheep = (EntitySheep) entity;
	                        nbttag.setBoolean("entitySheared", entitySheep.getSheared());
	                        nbttag.setInteger("entityColor", entitySheep.getFleeceColor());
	                        
	                    }
	                    if (entity instanceof EntityVillager) {
	                        EntityVillager entityVillager = (EntityVillager) entity;
	                        nbttag.setInteger("entityProfession", entityVillager.getProfession());
	                        //nbttag.setCompoundTag("Offers", entityVillager.get)
	                    }*/
	                    if (CompactMobsCore.instance.useFullTagCompound) {
	                    	NBTTagCompound entityTags = new NBTTagCompound();
	                    	//entityTags=entity.;
	                    	
	                    	NBTTagCompound var2 = new NBTTagCompound();
	                        entity.writeToNBT(var2);
	                    	nbttag.setCompoundTag("entityTags", var2);
	                    	//CompactMobsCore.instance.cmLog.info(var2.toString());
	                    }
	                    world.removeEntity(entity);
	                    String name = entity.getEntityName();
	                    nbttag.setString("name", name);
	                    holder.setItemDamage(id);
	                   // lastEntity = entity;
	
	                    spawnParticles(world, entity.posX, entity.posY, entity.posZ);
	                    //spawnParticles(world, entity.posX, entity.posY, entity.posZ);
	
	
	                    holder.setTagCompound(nbttag);
	                    System.out.println("Slot: "+var3);
	                    ItemStacks[var3] = holder;



                    /*
                     * if (true)//holder.hasTagCompound()) { NBTTagCompound
                     * nbttag = new NBTTagCompound();
                     * nbttag.setInteger("entityId", id);
                     * holder.writeToNBT(nbttag);//func_77983_a("tag",nbttag);
                     * //CompactMobsCore.instance.cmLog.info("2: " +
                     * String.valueOf(holder.hasTagCompound())); //nbttag =
                     * holder.getTagCompound();
                     *
                     * NBTBase test =
                     * holder.getTagCompound().getTag("entityId");
                     *
                     *
                     * //setId(EntityList.getEntityID(var5)); ItemStacks[var3] =
                     * holder; CompactMobsCore.instance.cmLog.info("2: " +
                     * String.valueOf(test));
					}
                     */
	                    if (ItemStacks[0].stackSize > 1) {
	                        ItemStacks[0] = new ItemStack(ItemStacks[0].getItem(), ItemStacks[0].stackSize - 1);
	                    } else {
	                        ItemStacks[0] = null;
	                    }
                	}
                }

                /*
                 * for (int var3 = 1; var3 < 29; ++var3) { if (ItemStacks[var3]
                 * != null) { if (ItemStacks[var3].getItem() == Item.paper){
                 * if(ItemStacks[var3].stackSize < 64){ ItemStacks[var3] = new
                 * ItemStack(Item.paper, ItemStacks[var3].stackSize+1); break; }
                 * } } else { ItemStacks[var3] = new ItemStack(Item.paper, 1);
                 * break; }
		        }
                 */
                

                
            }
        }
        if (CompactMobsCore.doAutoOutput)
        	dumpItems();
        //worldObj.spawnParticle("smoke", this.xCoord+1.5D, this.yCoord+.5D, this.zCoord+.5, -.1, 0, 0);
        //spawnParticles(this.worldObj, this.xCoord+1, this.yCoord, this.zCoord);
        //spawnParticles(this.worldObj, this.xCoord+1, this.yCoord, this.zCoord);
        //this.worldObj.spawnParticle("smoke", this.xCoord+1.5D, this.yCoord+.5D, this.zCoord+.5, -.1, 0, 0);
    }

    public void spawnParticles(World world, double x, double y, double z) {
        //CompactMobsCore.instance.cmLog.info("Got");
        double xv, yv, zv;
        if (this.yCoord - y > 0) {
            yv = (this.yCoord - y) / 10;
        } else {
            yv = 0;
        }
        if (this.xCoord - x > 0) {
            xv = (this.xCoord - x) / 10;
        } else if (this.xCoord - x < 0) {
            xv = (this.xCoord - x) / 10;
        } else {
            xv = 0;
        }
        if (this.zCoord - z > 0) {
            zv = (this.zCoord - z) / 10;
        } else if (this.zCoord - z < 0) {
            zv = (this.zCoord - z) / 10;
        } else {
            zv = 0;
        }
        CompactMobsCore.instance.proxy.spawnParticle("explode", x + .5D, y + .5D, z + .5D, xv, yv, zv, 10);
        //this.worldObj.spawnParticle("smoke", this.xCoord+1.5D, this.yCoord+.5D, this.zCoord+.5, -.1, 0, 0);
        //world.spawnParticle("smoke", x+.5D, y+.5D, z+.5D, xv, yv, zv);
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

        for (int i = 1; i < 28; i++) {
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
        for (int i = 1; i < 28; i++) {
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
        if (side==ForgeDirection.DOWN || side==ForgeDirection.UP)
        	return 0;
        if (side==ForgeDirection.NORTH||side==ForgeDirection.EAST||side==ForgeDirection.WEST||side==ForgeDirection.SOUTH)
        	return 1;
        return 0;
    }

    @Override
    public int getSizeInventorySide(ForgeDirection side) {
        // TODO Auto-generated method stub
    	if (side==ForgeDirection.DOWN || side==ForgeDirection.UP)
        	return 1;
        if (side==ForgeDirection.NORTH||side==ForgeDirection.EAST||side==ForgeDirection.WEST||side==ForgeDirection.SOUTH)
        	return 27;
        return 0;
    }


	@Override
	public int powerRequest(ForgeDirection from) {
		// TODO Auto-generated method stub
		return 25;
	}


	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return false;
	}





	/*@Override
	public void doWork() {
		// TODO Auto-generated method stub
		
	}*/
}
