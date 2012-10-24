package compactMobs;

import compactMobs.Containers.ContainerBreeder;
import compactMobs.Containers.ContainerCompactor;
import compactMobs.Containers.ContainerDecompactor;
import compactMobs.GUI.GuiBreeder;
import compactMobs.GUI.GuiCompactor;
import compactMobs.GUI.GuiDecompactor;
import compactMobs.TileEntity.TileEntityBreeder;
import compactMobs.TileEntity.TileEntityCompactor;
import compactMobs.TileEntity.TileEntityDecompactor;

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
			switch (ID){
			case 0:		
				return new ContainerCompactor(player.inventory, (TileEntityCompactor) tileEntity);
			case 1:
				return new ContainerDecompactor(player.inventory, (TileEntityDecompactor) tileEntity);
			case 2:
				return new ContainerBreeder(player.inventory, (TileEntityBreeder) tileEntity);
			}
        }
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity != null)
        {
			switch (ID){
			case 0:
				return new GuiCompactor(player.inventory, ((TileEntityCompactor)tileEntity));
			case 1:
				return new GuiDecompactor(player.inventory, ((TileEntityDecompactor)tileEntity));
			case 2:
				return new GuiBreeder(player.inventory, ((TileEntityBreeder)tileEntity));
			}
        }

		return null;
	}
	
	public boolean isRenderWorld(World world) {
		return world.isRemote;
	}
}
