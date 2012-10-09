package compactMobs.Blocks;

import java.util.Random;

import compactMobs.CompactMobsCore;
import compactMobs.DefaultProps;
import compactMobs.Utils;
import compactMobs.TileEntity.TileEntityCompactor;
import compactMobs.TileEntity.TileEntityDecompactor;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityEnchantmentTable;
import net.minecraft.src.World;

public class BlockDecompactor extends BlockContainer{

	public BlockDecompactor(int par1, Material par2Material) 
	{
		super(par1, par2Material);
		super.blockIndexInTexture = 4;
        this.setLightOpacity(10);
        this.setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	@Override
	public boolean renderAsNormalBlock()
    {
        return true;
    }
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		Utils.preDestroyBlock(world, x, y, z);
		super.breakBlock(world, x, y, z, par5, par6);
	}
	
	@Override
	public boolean isOpaqueCube()
    {
        return true;
    }
	
	@Override
	public String getTextureFile()
    {
            return DefaultProps.BLOCK_TEXTURES+"/blocks.png";
    }
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int par1, int par2)
	{
		switch(par1)
		{
			case 0:
				return 1;
			case 1:
				return 3;
			case 2:
				return 2;
			case 3:
				return 2;
			case 4:
				return 2;
			case 5:
				return 2;
		}
		return 0;
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1) 
	{
		return new TileEntityDecompactor();
	}
	
	/**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (par1World.isRemote)
        {
            return true;
        }
        else if(par5EntityPlayer.isSneaking())
        {
        	return true;
        }
        else
        {
        	par5EntityPlayer.openGui(CompactMobsCore.instance, 0, par1World, par2, par3, par4);
    		return true;
        }
    }
    
    @SideOnly(Side.CLIENT)

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        super.randomDisplayTick(par1World, par2, par3, par4, par5Random);
    }
}
