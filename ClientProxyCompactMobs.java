package compactMobs;

import net.minecraft.entity.EntityLiving;
import net.minecraft.network.packet.Packet24MobSpawn;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ClientProxyCompactMobs extends CommonProxyCompactMobs {
    //in charge of textures and rendering

    @Override
    public void registerRenderThings() {
        MinecraftForgeClient.preloadTexture(DefaultProps.BLOCK_TEXTURES + "/blocks.png");
        MinecraftForgeClient.preloadTexture(DefaultProps.ITEM_TEXTURES + "/items.png");
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void spawnParticle(String type, double x1, double y1, double z1, double x2, double y2, double z2, int number) {
        World world = FMLClientHandler.instance().getClient().theWorld;

        if (world == null) {
            return;
        }

        for (int i = 0; i < number; i++) {
            world.spawnParticle(type, x1, y1, z1, x2, y2, z2);
        }
    }
    
    
    
}
