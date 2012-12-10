package compactMobs;

import compactMobs.Containers.ContainerBreeder;
import compactMobs.Containers.ContainerCompactor;
import compactMobs.Containers.ContainerDecompactor;
import compactMobs.Containers.ContainerHandDecompactor;
import compactMobs.Containers.ContainerIncubator;
import compactMobs.GUI.GuiBreeder;
import compactMobs.GUI.GuiCompactor;
import compactMobs.GUI.GuiDecompactor;
import compactMobs.GUI.GuiHandDecompactor;
import compactMobs.GUI.GuiIncubator;
import compactMobs.Items.HandheldDecompactor;
import compactMobs.Items.HandheldDecompactorInv;
import compactMobs.TileEntity.TileEntityBreeder;
import compactMobs.TileEntity.TileEntityCompactor;
import compactMobs.TileEntity.TileEntityDecompactor;
import compactMobs.TileEntity.TileEntityIncubator;
import compactMobs.network.PacketParticleSpawn;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.PacketDispatcher;

public class CommonProxyCompactMobs implements IGuiHandler {
    //not used for much as of now.

    public void registerRenderThings() {
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID != 5)
        {
			TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		    if (tileEntity != null) {
		        switch (ID) {
		            case 0:
		                return new ContainerCompactor(player.inventory, (TileEntityCompactor) tileEntity);
		            case 1:
		                return new ContainerDecompactor(player.inventory, (TileEntityDecompactor) tileEntity);
		            case 2:
		                return new ContainerBreeder(player.inventory, (TileEntityBreeder) tileEntity);
		            case 3:
		                return new ContainerIncubator(player.inventory, (TileEntityIncubator) tileEntity);
		        }
		    }
        }
        else
        {
        	if (ID == 5)
        	{
        		HandheldDecompactorInv inv = new HandheldDecompactorInv(player.inventory.getCurrentItem(), player);
        		return new ContainerHandDecompactor(player.inventory, inv);
        	}
        }
        return null;
    }
    
    public void doTickStuff()
	{
		// overriden in client
	}

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID != 5)
        {
	    	TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
	
	        if (tileEntity != null) {
	            switch (ID) {
	                case 0:
	                    return new GuiCompactor(player.inventory, ((TileEntityCompactor) tileEntity));
	                case 1:
	                    return new GuiDecompactor(player.inventory, ((TileEntityDecompactor) tileEntity));
	                case 2:
	                    return new GuiBreeder(player.inventory, ((TileEntityBreeder) tileEntity));
	                case 3:
	                    return new GuiIncubator(player.inventory, ((TileEntityIncubator) tileEntity));
	            }
	        }
        }
        else
        {
        	if (ID == 5)
        	{
        		HandheldDecompactorInv inv = new HandheldDecompactorInv(player.inventory.getCurrentItem(), player);
        		return new GuiHandDecompactor(player.inventory, inv);
        	}
        }

        return null;
    }

    public boolean isRenderWorld(World world) {
        return world.isRemote;
    }

    public void spawnParticle(String type, double x, double y, double z, double vx, double vy, double vz, int number) {
        PacketDispatcher.sendPacketToAllPlayers(PacketParticleSpawn.buildParticleSpawnPacket(type, x, y, z, vx, vy, vz, number));
    }
}
