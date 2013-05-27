package compactMobs;

import compactMobs.TileEntity.TileEntityNamer;
import compactMobs.network.PacketNamerText;
import compactMobs.network.PacketParticleSpawn;

import net.minecraft.entity.EntityLiving;
import net.minecraft.network.packet.Packet24MobSpawn;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
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
    
    
    @SideOnly(Side.CLIENT)
    @Override
    public boolean isRenderWorld(World world) {
        return false;//world.isRemote;
    }
    
    @Override
    public void registerSoundHandler() {

        MinecraftForge.EVENT_BUS.register(new SoundHandler());
    }
    
    @Override
    public void updateNamerText(String text, TileEntityNamer te) {
    	int x = te.xCoord;
    	int y = te.yCoord;
    	int z = te.zCoord;
    	System.out.println("Sending Packet for "+text);
        PacketDispatcher.sendPacketToServer(PacketNamerText.buildNamerTextPacket(text, x, y, z));
    }
}
