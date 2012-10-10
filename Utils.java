package compactMobs;



import buildcraft.api.core.Orientations;
import buildcraft.api.core.Position;
import net.minecraft.src.EntityItem;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
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

}
