package compactMobs.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import compactMobs.CompactMobsCore;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class PacketClient implements IPacketHandler {

    public static String channel = "compactMobs";
    protected Packet250CustomPayload internalPacket;

    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        String name = ((EntityPlayer) player).username;
        EntityPlayer playerEntity = FMLClientHandler.instance().getClient().thePlayer;
        recieve(packet, playerEntity, manager);
    }

    public static final void recieve(Packet250CustomPayload packet, EntityPlayer player, INetworkManager manager) {
        if (!packet.channel.equals(channel)) {
            return;
        }

        WorldClient world = Minecraft.getMinecraft().theWorld;

        try {
            DataInputStream stream = new DataInputStream(new ByteArrayInputStream(packet.data));

            int ID = stream.read();

            switch (ID) {
                case 1:
                    (new PacketParticleSpawn()).interpret(stream, new Object[]{world}, Side.CLIENT);
                    break;
                default:
                    CompactMobsCore.instance.cmLog.info("Unknown packet type recived");
                    break;
            }

            stream.close();
        } catch (Exception e) {
            CompactMobsCore.instance.cmLog.info("Error reciving packet");
            e.printStackTrace();
        }
    }

    public void interpret(DataInputStream stream, Object[] extradata, Side side) {
    }

    public byte getPacketID() {
        return 0;
    }
}
