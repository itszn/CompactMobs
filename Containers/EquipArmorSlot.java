package compactMobs.Containers;

import compactMobs.CompactMobsCore;
import compactMobs.Items.CompactMobsItems;
import compactMobs.TileEntity.TileEntityEquiper;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EquipArmorSlot extends Slot{

	public int armorType;
	TileEntityEquiper te;

	public EquipArmorSlot(IInventory par1iInventory, int par2, int par3, int par4, int par6, TileEntityEquiper te) {
		super(par1iInventory, par2, par3, par4);
		this.te = te;
        this.armorType = par6;
		// TODO Auto-generated constructor stub
	}
	
	public boolean isItemValid(ItemStack par1ItemStack)
    {
        Item item = (par1ItemStack == null ? null : par1ItemStack.getItem());
        boolean flag = item != null && (item.isValidArmor(par1ItemStack, armorType)||armorType==-1)&&te.newIn;
        boolean isBlock = false;	
        Block block = (par1ItemStack.itemID < Block.blocksList.length ? Block.blocksList[par1ItemStack.itemID] : null);
		if (par1ItemStack!=null)
			isBlock = par1ItemStack.getItemSpriteNumber() == 0 && block != null && block.blockID!=0;
		boolean upgrade = te.ItemStacks[7]!=null&&te.ItemStacks[7].getItem()==CompactMobsItems.upgrade&&te.ItemStacks[7].getItemDamage()==2;
		flag = flag||(isBlock&&this.slotNumber==1&&upgrade);
        return flag;
    }

}
