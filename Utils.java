package compactMobs;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import buildcraft.api.core.Orientations;
import buildcraft.api.core.Position;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.transport.IPipeEntry;
import net.minecraft.src.EntityItem;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryLargeChest;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.World;

public class Utils {
	public static void preDestroyBlock(World world, int i, int j, int k) {
		TileEntity tile = world.getBlockTileEntity(i, j, k);

		if (tile instanceof IInventory && !CompactMobsCore.proxy.isRenderWorld(world))
			if (!(tile instanceof IDropControlInventory) || ((IDropControlInventory) tile).doDrop())
				dropItems(world, (IInventory) tile, i, j, k);

		//if (tile instanceof TileEntity)
			//((TileEntity) tile).destroy();
	}
	
	public static void dropItems(World world, ItemStack stack, int i, int j, int k) {
		if(stack.stackSize <= 0)
			return;

		float f1 = 0.7F;
		double d = (world.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
		double d1 = (world.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
		double d2 = (world.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
		EntityItem entityitem = new EntityItem(world, i + d, j + d1, k + d2, stack);
		entityitem.delayBeforeCanPickup = 10;

		world.spawnEntityInWorld(entityitem);
	}
	
	public static void dropItems(World world, IInventory inventory, int i, int j, int k) {
		for (int l = 0; l < inventory.getSizeInventory(); ++l) {
			ItemStack items = inventory.getStackInSlot(l);

			if (items != null && items.stackSize > 0)
				dropItems(world, inventory.getStackInSlot(l).copy(), i, j, k);
		}
	}
	
	public static Orientations get2dOrientation(Position pos1, Position pos2) {
		double Dx = pos1.x - pos2.x;
		double Dz = pos1.z - pos2.z;
		double angle = Math.atan2(Dz, Dx) / Math.PI * 180 + 180;

		if (angle < 45 || angle > 315)
			return Orientations.XPos;
		else if (angle < 135)
			return Orientations.ZPos;
		else if (angle < 225)
			return Orientations.XNeg;
		else
			return Orientations.ZNeg;
	}
	
	public static Orientations[] getPipeDirections(World world, Vect blockPos, Orientations from)
	  {
	    LinkedList possiblePipes = new LinkedList();

	    for (int j = 0; j < 6; j++) {
	      Position posPipe = new Position(blockPos.x, blockPos.y, blockPos.z, Orientations.values()[j]);
	      posPipe.moveForwards(1.0D);

	      TileEntity pipeEntry = world.getBlockTileEntity((int)posPipe.x, (int)posPipe.y, (int)posPipe.z);

	      if (((pipeEntry instanceof IPipeEntry)) && (((IPipeEntry)pipeEntry).acceptItems())) {
	        possiblePipes.add(Orientations.values()[j]);
	      }
	    }
	    return (Orientations[])possiblePipes.toArray(new Orientations[possiblePipes.size()]);
	  }

	public static Orientations[] filterPipeDirections(Orientations[] all, Orientations[] exclude)
	  {
	    LinkedList filtered = new LinkedList();
	    ArrayList excludeList = new ArrayList(Arrays.asList(exclude));
	    for (int i = 0; i < all.length; i++) {
	      if (!excludeList.contains(all[i]))
	        filtered.add(all[i]);
	    }
	    return (Orientations[])filtered.toArray(new Orientations[filtered.size()]);
	  }
	
	public static void putFromStackIntoPipe(TileEntity tile, Orientations[] pipes, ItemStack stack)
	  {
	    if (stack == null)
	      return;
	    if (stack.stackSize <= 0)
	      return;
	    if (pipes.length <= 0) {
	      return;
	    }
	    int choice = tile.worldObj.rand.nextInt(pipes.length);

	    Position itemPos = new Position(tile.xCoord, tile.yCoord, tile.zCoord, pipes[choice]);

	    itemPos.x += 0.5D;
	    itemPos.y += 0.25D;
	    itemPos.z += 0.5D;
	    itemPos.moveForwards(0.5D);

	    Position pipePos = new Position(tile.xCoord, tile.yCoord, tile.zCoord, pipes[choice]);
	    pipePos.moveForwards(1.0D);

	    IPipeEntry pipe = (IPipeEntry)tile.worldObj.getBlockTileEntity((int)pipePos.x, (int)pipePos.y, (int)pipePos.z);

	    ItemStack payload = stack.splitStack(1);

	    pipe.entityEntering(payload, itemPos.orientation);
	  }
	
	public static IInventory getChest(IInventory inventory) {
	    if (!(inventory instanceof TileEntityChest)) {
	      return inventory;
	    }
	    TileEntityChest chest = (TileEntityChest)inventory;

	    Vect[] adjacent = { new Vect(chest.xCoord + 1, chest.yCoord, chest.zCoord), new Vect(chest.xCoord - 1, chest.yCoord, chest.zCoord), new Vect(chest.xCoord, chest.yCoord, chest.zCoord + 1), new Vect(chest.xCoord, chest.yCoord, chest.zCoord - 1) };

	    for (Vect pos : adjacent) {
	      TileEntity otherchest = chest.worldObj.getBlockTileEntity(pos.x, pos.y, pos.z);
	      if ((otherchest instanceof TileEntityChest)) {
	        return new InventoryLargeChest("", chest, (TileEntityChest)otherchest);
	      }
	    }
	    return inventory;
	  }
	
	public static boolean stowInInventory(ItemStack itemstack, IInventory inventory, boolean doAdd)
	{
		return stowInInventory(itemstack, inventory, doAdd, 0, inventory.getSizeInventory());
	}
	
	public static boolean stowInInventory(ItemStack itemstack, IInventory inventory, boolean doAdd, int slot1, int count)
	  {
	    boolean added = false;

	    for (int i = slot1; i < slot1 + count; i++) {
	      ItemStack inventoryStack = inventory.getStackInSlot(i);

	      if (inventoryStack == null) {
	        if (doAdd) {
	          inventory.setInventorySlotContents(i, itemstack.copy());
	          itemstack.stackSize = 0;
	        }
	        return true;
	      }

	      if (inventoryStack.stackSize >= inventoryStack.getMaxStackSize())
	      {
	        continue;
	      }
	      if (!inventoryStack.isItemEqual(itemstack)) {
	        continue;
	      }
	      int space = inventoryStack.getMaxStackSize() - inventoryStack.stackSize;

	      if (space > itemstack.stackSize) {
	        if (doAdd) {
	          inventoryStack.stackSize += itemstack.stackSize;
	          itemstack.stackSize = 0;
	        }
	        return true;
	      }

	      if (doAdd) {
	        inventoryStack.stackSize = inventoryStack.getMaxStackSize();
	        itemstack.stackSize -= space;
	      }
	      added = true;
	    }

	    return added;
	  }
	
	 public static IInventory[] getAdjacentInventories(World world, Vect blockPos)
	  {
	    ArrayList inventories = new ArrayList();

	    TileEntity entity = world.getBlockTileEntity(blockPos.x - 1, blockPos.y, blockPos.z);
	    if ((entity != null) && 
	      ((entity instanceof IInventory)) && 
	      (!(entity instanceof IPowerReceptor))) {
	      inventories.add((IInventory)entity);
	    }
	    entity = world.getBlockTileEntity(blockPos.x + 1, blockPos.y, blockPos.z);
	    if ((entity != null) && 
	      ((entity instanceof IInventory)) && 
	      (!(entity instanceof IPowerReceptor))) {
	      inventories.add((IInventory)entity);
	    }
	    entity = world.getBlockTileEntity(blockPos.x, blockPos.y, blockPos.z - 1);
	    if ((entity != null) && 
	      ((entity instanceof IInventory)) && 
	      (!(entity instanceof IPowerReceptor))) {
	      inventories.add((IInventory)entity);
	    }
	    entity = world.getBlockTileEntity(blockPos.x, blockPos.y, blockPos.z + 1);
	    if ((entity != null) && 
	      ((entity instanceof IInventory)) && 
	      (!(entity instanceof IPowerReceptor))) {
	      inventories.add((IInventory)entity);
	    }
	    return (IInventory[])inventories.toArray(new IInventory[inventories.size()]);
	  }
}
