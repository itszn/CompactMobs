package compactMobs.TileEntity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
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

public class TileEntityEquiper extends TileEntity implements IInventory, ISidedInventory {

    public ItemStack[] ItemStacks;
    IPowerProvider provider;
    public String text = "";
    protected float stored;
    protected boolean use = false;
    public boolean armorFlag;
    public boolean newIn = false;
    
    //protected Random rand;

    public TileEntityEquiper() {
        ItemStacks = new ItemStack[8];
    }

    @Override
    public void updateEntity() {

        /*if (this instanceof IPowerReceptor) {
            IPowerReceptor receptor = ((IPowerReceptor) this);
            receptor.getPowerProvider().update(receptor);
        }*/
    	
        World world = worldObj;
        return;
    }
    
    
    public void runNamer() {
    	
        
    }
    
    @Override
    public void onInventoryChanged() {
    	if (ItemStacks[1]!=null) {
    		if (worldObj.getBlockMetadata(xCoord, yCoord, zCoord)!=1)
    			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 2);
    	}
    	else{
    		if (worldObj.getBlockMetadata(xCoord, yCoord, zCoord)!=0)
    			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
    	}
    	armorFlag = false;
    	if(ItemStacks[1]!=null) {
    		ItemStack itemstack = ItemStacks[1];
    		
    		if (itemstack.hasTagCompound()) {
    			NBTTagCompound nbtO = itemstack.getTagCompound();
	    		if (nbtO.hasKey("infoVisable")) {
	    			
	    			if (nbtO.hasKey("entityTags")) {
	    				NBTTagCompound nbttag = nbtO.getCompoundTag("entityTags");
	    				if (!newIn) {
		    				newIn = true;
		    				System.out.println("Refresh from item");
		    				if (nbttag != null) {
		    					if(nbttag.hasKey("Equipment")) { 
		    						NBTTagList list = nbttag.getTagList("Equipment");
		    						armorFlag=true;
		    						for (int i=0; i<5;i++) {
		    							NBTTagCompound tag = (NBTTagCompound) list.tagAt(i);
		    							if(!tag.hasNoTags()) {
		    								if(ItemStack.loadItemStackFromNBT(tag)!=null)
		    									ItemStacks[i+2]=ItemStack.loadItemStackFromNBT(tag).copy();
		    								else
		    									ItemStacks[i+2]=null;
		    							}
		    							else
		    							{
		    								ItemStacks[i+2]=null;
		    							}
		    						}
		    						
		    					}
		    					if (nbttag.hasKey("carried")) {
		    						if (Block.blocksList[nbttag.getShort("carried")]!=null) {
		    							if (nbttag.hasKey("carried")) {
				    						if (Block.blocksList[nbttag.getShort("carried")]!=null)
				    							ItemStacks[2] = new ItemStack(Block.blocksList[nbttag.getShort("carried")],1,nbttag.getShort("carriedData"));
				    					}
		    						}
		    						
		    					}
		    				}
	    				} else {
	    					if (nbttag != null) {
		    					if(nbttag.hasKey("Equipment")) { 
		    						NBTTagList list = nbttag.getTagList("Equipment");
		    						NBTTagList nlist = new NBTTagList();
		    						NBTTagList nlist2 = new NBTTagList();
		    						NBTTagList list2 = nbttag.getTagList("DropChances");
		    						armorFlag=true;
		    						for (int i=0; i<5;i++) {
		    							boolean isBlock = false;
		    							if (i==0 && ItemStacks[2]!=null) {
			    							Block block = (ItemStacks[2].itemID < Block.blocksList.length ? Block.blocksList[ItemStacks[2].itemID] : null);
			    							if (ItemStacks[2]!=null)
			    								isBlock = ItemStacks[2].getItemSpriteNumber() == 0 && block != null && block.blockID!=0;
		    							}
		    							if (i==0&&nbttag.hasKey("carried")&&((ItemStacks[2]!=null&&isBlock)||ItemStacks[2]==null)) {
		    								if (ItemStacks[2]==null) {
		    									nbttag.setShort("carried",(short) 0);
			    								nbttag.setShort("carriedData",(short) 0);
		    								}else {
			    								System.out.println("Added Item");
			    								nbttag.setShort("carried",(short) ItemStacks[2].itemID);
			    								nbttag.setShort("carriedData",(short) ItemStacks[2].getItemDamage());
		    								}
		    								NBTTagFloat tf = (NBTTagFloat) list2.tagAt(i);
		    								nlist2.appendTag(tf);
		    								nlist.appendTag(new NBTTagCompound());
		    							}
		    							else {
			    							ItemStack ols = ItemStack.loadItemStackFromNBT((NBTTagCompound) list.tagAt(i));
			    							NBTTagFloat tf = (NBTTagFloat) list2.tagAt(i);
			    							if (!ItemStack.areItemStacksEqual(ols, ItemStacks[i+2]))
			    								tf.data=2;
			    							nlist2.appendTag(tf);
			    							
			    							if (ItemStacks[i+2]!=null) {
			    								//tag = ItemStacks[i+2].getTagCompound();
			    								NBTTagCompound ntag = new NBTTagCompound();
			    								ItemStacks[i+2].writeToNBT(ntag);
			    								nlist.appendTag(ntag);
			    							}
			    							else {
			    								nlist.appendTag(new NBTTagCompound());
			    								//tag = new NBTTagCompound();
			    							}
		    							}
		    						}
		    						nbttag.setTag("Equipment", nlist);	
		    						nbttag.setTag("DropChances", nlist2);	
		    					}
		    					/*if (nbttag.hasKey("carried")) {
		    						if (Block.blocksList[nbttag.getShort("carried")]!=null) {
		    							if (nbttag.hasKey("carried")) {
				    						if (Block.blocksList[nbttag.getShort("carried")]!=null)
				    							ItemStacks[2] = new ItemStack(Block.blocksList[nbttag.getShort("carried")],1,nbttag.getShort("carriedData"));
				    					}
		    						}
		    						
		    					}*/
		    					nbtO.setTag("entityTags", nbttag);
		    					ItemStacks[1].setTagCompound(nbtO);
		    				}
	    				}
	    			}	
	    		} else {
	    			NBTTagCompound nbttag = ItemStacks[1].getTagCompound();
	                if (nbttag != null) {
	                	if (!nbttag.hasKey("infoVisable")||!nbttag.getBoolean("infoVisable")) {

	    		                nbttag.setBoolean("infoVisable", true);
	    		                ItemStacks[1].setTagCompound(nbttag);

	    	                
	                	}
	                }
	    		}
    		}
    		
    	}
    	else {
    		newIn = false;
    		for (int i=0; i<5;i++) {
					ItemStacks[i+2]=null;
			}
        	
    	}
    	
    		
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
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        NBTTagList var2 = tagCompound.getTagList("equiper");
        this.ItemStacks = new ItemStack[this.getSizeInventory()];
        newIn = tagCompound.getBoolean("newIn");
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
        tagCompound.setBoolean("newIn", newIn);
        for (int i = 0; i < ItemStacks.length; i++) {
            ItemStack stack = ItemStacks[i];
            if (stack != null) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte) i);
                stack.writeToNBT(tag);
                itemList.appendTag(tag);
            }
        }
        tagCompound.setTag("equiper", itemList);
    }

    @Override
    public String getInvName() {
        return "Equiper";
    }

    public double getDistanceSqToEntity(Entity par1Entity) {
        double var2 = this.xCoord - par1Entity.posX;
        double var4 = this.yCoord - par1Entity.posY;
        double var6 = this.zCoord - par1Entity.posZ;
        return var2 * var2 + var4 * var4 + var6 * var6;
    }

    @Override
    public int getStartInventorySide(ForgeDirection side) {
        	return 1;
    }

    @Override
    public int getSizeInventorySide(ForgeDirection side) {
        // TODO Auto-generated method stub
    	return 1;
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
	public void closeChest() {
		// TODO Auto-generated method stub
		
	}
	
	public void clearBeforeBreak() {
		for (int i=0; i<5;i++) {
			ItemStacks[i+2]=null;
		}
	}

}
