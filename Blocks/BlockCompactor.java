package compactMobs.Blocks;

import java.util.Random;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.core.Position;

import compactMobs.CompactMobsCore;
import compactMobs.DefaultProps;
import compactMobs.Utils;
import compactMobs.TileEntity.TileEntityCompactor;

public class BlockCompactor extends BlockContainer {

    public BlockCompactor(int par1, Material par2Material) {
        super(par1, par2Material);
        super.blockIndexInTexture = 2;
        this.setLightOpacity(10);
        this.setCreativeTab(CreativeTabs.tabRedstone);

    }

    @Override
    public boolean renderAsNormalBlock() {
        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
        Utils.preDestroyBlock(world, x, y, z);
        super.breakBlock(world, x, y, z, par5, par6);
    }

    @Override
    public boolean isOpaqueCube() {
        return true;
    }

    @Override
    public String getTextureFile() {
        return DefaultProps.BLOCK_TEXTURES + "/blocks.png";
    }

    @Override
    public int getBlockTextureFromSide(int i) {
        switch (i) {
            case 0:
                //bottom
                return 1;
            case 1:
                //top
                return 2;
            default:
                //side
                return 0;
        }
    }

    @Override
    public TileEntity createNewTileEntity(World var1) {
        return new TileEntityCompactor();
    }

    @Override
    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) {
        ForgeDirection orientation = Utils.get2dOrientation(new Position(entityliving.posX, entityliving.posY, entityliving.posZ),
                new Position(i, j, k));

        world.setBlockMetadataWithNotify(i, j, k, orientation.getOpposite().ordinal());
        //CompactMobsCore.instance.cmLog.info(String.valueOf(world.getBlockMetadata(i,j,k)));
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
        if (par1World.isRemote) {
            return true;
        } else if (par5EntityPlayer.isSneaking()) {
            return false;
        } else {
            par5EntityPlayer.openGui(CompactMobsCore.instance, 0, par1World, par2, par3, par4);
            return true;
        }
    }

    @SideOnly(Side.CLIENT)
    /**
     * A randomly called display update to be able to add particles or other
     * items for display
     */
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
        super.randomDisplayTick(par1World, par2, par3, par4, par5Random);
    }
}
