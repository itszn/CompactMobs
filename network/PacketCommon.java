package compactMobs.network;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.WorldServer;

import compactMobs.CompactMobsCore;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketCommon implements IPacketHandler {

    public static String channel = "compactMobs";
    protected Packet250CustomPayload internalPacket;

    @Override
    public void onPacketData(INetworkManager manager,
            Packet250CustomPayload packet, Player player) {
        String name = ((EntityPlayer) player).username;
        EntityPlayer playerEntity = FMLCommonHandler.instance().getSidedDelegate().getServer().getConfigurationManager().getPlayerForUsername(name);

        recieve(packet, playerEntity, manager);
    }

    public static final void recieve(Packet250CustomPayload packet, EntityPlayer player, INetworkManager manager) {
        if (!packet.channel.equals(channel)) {
            return;
        }
        WorldServer world = (WorldServer) player.worldObj;

        try {
            DataInputStream stream = new DataInputStream(new ByteArrayInputStream(packet.data));
            int ID = stream.read();
            System.out.println("Recived Packet ID "+ID);

            switch (ID) {
                case 1:
                    (new PacketParticleSpawn()).interpret(stream, new Object[]{world}, Side.SERVER);
                    break;
                case 2:
                	(new PacketNamerText()).interpret(stream, new Object[]{world}, Side.SERVER);
                	break;
                default:
                    CompactMobsCore.instance.cmLog.info("Unknown packet type recieved");
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
