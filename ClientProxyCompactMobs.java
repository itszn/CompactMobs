package compactMobs;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.src.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxyCompactMobs extends CommonProxyCompactMobs
{
	//in charge of textures and rendering
	@Override
    public void registerRenderThings()
    {
     MinecraftForgeClient.preloadTexture(DefaultProps.BLOCK_TEXTURES+"/blocks.png");
     MinecraftForgeClient.preloadTexture(DefaultProps.ITEM_TEXTURES+"/items.png");
    }
	
	@Override
	public void spawnParticle(String type, double x1, double y1, double z1, double x2, double y2, double z2, int number)
	{
		World world = FMLClientHandler.instance().getClient().theWorld;

		if (world == null)
			return;

		for (int i = 0; i < number; i++)
		{
			world.spawnParticle(type, x1, y1, z1, x2, y2, z2);
		}
	}
}
