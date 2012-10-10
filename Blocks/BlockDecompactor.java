package compactMobs.Blocks;

import java.util.Random;

import buildcraft.api.core.Orientations;
import buildcraft.api.core.Position;

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
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityEnchantmentTable;
import net.minecraft.src.World;

public class BlockDecompactor extends BlockContainer{
	
	int textureFront, textureSides, textureBack, textureTop;
	
	public BlockDecompactor(int par1, Material par2Material) 
	{
		super(par1, par2Material);
		super.blockIndexInTexture = 4;
        this.setLightOpacity(10);
        this.setCreativeTab(CreativeTabs.tabRedstone);
        textureFront = 3; //3
		textureSides = 5; //5
		textureBack = 6; //6
		textureTop = 4; //4
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
	public int getBlockTextureFromSideAndMetadata(int i, int j)
	{
		if (j == 0 && i == 3) {
			return textureFront;
		}

		if (i == 1) {
			return textureTop;
		} else if (i == 0) {
			return textureBack;
		} else if (i == j) {
			return textureFront;
		} else if (j >= 0 && j < 6 && Orientations.values()[j].reverse().ordinal() == i) {
			return textureBack;
		} else {
			return textureSides;
		}
	}
	
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) {
		Orientations orientation = Utils.get2dOrientation(new Position(entityliving.posX, entityliving.posY, entityliving.posZ),
				new Position(i, j, k));

		world.setBlockMetadataWithNotify(i, j, k, orientation.reverse().ordinal());
		CompactMobsCore.instance.cmLog.info(String.valueOf(world.getBlockMetadata(i,j,k)));
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1) 
	{
		return new TileEntityDecompactor();
	}
	
	/**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World world, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
    	
        if (world.isRemote)
        {
            return true;
        }
        else if(par5EntityPlayer.isSneaking())
        {
        	return false;
        }
        else
        {
        	par5EntityPlayer.openGui(CompactMobsCore.instance, 1, world, par2, par3, par4);
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
