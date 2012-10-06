package compactMobs;

import compactMobs.Containers.ContainerCompactor;
import compactMobs.GUI.GuiCompactor;
import compactMobs.TileEntity.TileEntityCompactor;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxyCompactMobs implements IGuiHandler
{
	//not used for much as of now.
	public void registerRenderThings()
    {
        
    }

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity != null)
        {
			return new ContainerCompactor(player.inventory, (TileEntityCompactor) tileEntity);
        }
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity != null)
        {
			return new GuiCompactor(player.inventory, ((TileEntityCompactor)tileEntity));
        }

		return null;
	}
	
	public boolean isRenderWorld(World world) {
		return world.isRemote;
	}
}
