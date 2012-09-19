package compactMobs;

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
}
