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

public class TileEntityNamer extends TileEntity implements IInventory, IPowerReceptor, ISidedInventory {

    public ItemStack[] ItemStacks;
    IPowerProvider provider;
    public String text = "";
    protected float stored;
    protected boolean use = false;
    
    //protected Random rand;

    public TileEntityNamer() {
        ItemStacks = new ItemStack[2];
        if (PowerFramework.currentFramework != null) {
            provider = PowerFramework.currentFramework.createPowerProvider();
        }
        provider.configure(50, 1, 25, 25, 1000);
    }

    @Override
    public void updateEntity() {

        if (this instanceof IPowerReceptor) {
            IPowerReceptor receptor = ((IPowerReceptor) this);
            receptor.getPowerProvider().update(receptor);
        }

        World world = worldObj;
        if (use)
        {
        	if (ItemStacks[0]!=null && ItemStacks[1]==null)
            {
            	if (ItemStacks[0].getItem() == CompactMobsItems.fullMobHolder)
            	{
            		ItemStack in = ItemStacks[0].copy();
            		NBTTagCompound nbt = new NBTTagCompound();
            		if (in.hasTagCompound())
            		{
            			nbt = in.stackTagCompound;
            		}
            		//System.out.println("tag " +nbt.hasKey("entityTags"));
                	
            		if(nbt.hasKey("entityTags"))
            		{

            			//System.out.println(text);
            			if (!text.equals(""))
            			{
            				NBTBase mob = (nbt.getTag("entityTags"));
                			((NBTTagCompound) mob).setString("CustomName", text);
                			nbt.setTag("entityTags", mob);
                			nbt.setString("name", text);
                			//ItemStack out = new ItemStack(in.getItem(),1, in.getItemDamage());
                			in.setTagCompound(nbt);
                			ItemStacks[1] = in;
                			ItemStacks[0]=null;
                			use = false;
            			}
            			
            		}
            		else
            		{
            			System.out.print("No entityTags?");
            		}
            	}
            }
            else
            {
            	//ItemStacks[1]=null;
            }
        }
        

        return;
    }
    
    
    public void runNamer() {
    	
        
    }
    
    @SideOnly(Side.SERVER)
    public void updateText(String s)
    {
    	this.text = s;
    	System.out.println("Recived "+text);
    	/*if (ItemStacks[0]!=null)
        {
        	System.out.println(ItemStacks[0].getItem() == CompactMobsItems.fullMobHolder);
        	if (ItemStacks[0].getItem() == CompactMobsItems.fullMobHolder)
        	{
        		ItemStack in = ItemStacks[0].copy();
        		NBTTagCompound nbt = new NBTTagCompound();
        		if (in.hasTagCompound())
        		{
        			nbt = in.stackTagCompound;
        		}
        		//String text = "John";
        		if(nbt.hasKey("entityTags"))
        		{
        			
        			if (!text.equals(""))
        			{
        				NBTBase mob = (nbt.getTag("entityTags"));
            			System.out.println(text);
            			((NBTTagCompound) mob).setString("CustomName", text);
            			nbt.setTag("entityTags", mob);
            			nbt.setString("name", text);
            			//ItemStack out = new ItemStack(in.getItem(),1, in.getItemDamage());
            			in.setTagCompound(nbt);
            			ItemStacks[1] = in;
        			}
        			
        		}
        		else
        		{
        			System.out.print("No entityTags?");
        		}
        	}
        }
        else
        {
        	ItemStacks[1]=null;
        }*/
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

   

    public double getDistanceSqToEntity(Entity par1Entity) {
        double var2 = this.xCoord - par1Entity.posX;
        double var4 = this.yCoord - par1Entity.posY;
        double var6 = this.zCoord - par1Entity.posZ;
        return var2 * var2 + var4 * var4 + var6 * var6;
    }

    @Override
    public int getStartInventorySide(ForgeDirection side) {
        	return 0;
    }

    @Override
    public int getSizeInventorySide(ForgeDirection side) {
        // TODO Auto-generated method stub
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

	@Override
	public void doWork() {
		stored = provider.getEnergyStored();
		if (!use)
		{
			provider.useEnergy(25, 25, true);
			stored = provider.getEnergyStored();
			use = true;
		}
	}
}
