package compactMobs.network;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import compactMobs.CompactMobsCore;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.WorldServer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketCommon implements IPacketHandler{
	
	public static String channel = "compactMobs";
	protected Packet250CustomPayload internalPacket;
	
	@Override
	public void onPacketData(NetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		String name = ((EntityPlayer) player).username;
		EntityPlayer playerEntity = FMLCommonHandler.instance().getSidedDelegate().getServer().getConfigurationManager().getPlayerForUsername(name);
		
		recieve(packet, playerEntity, manager);
	}
	
	public static final void recieve(Packet250CustomPayload packet, EntityPlayer player, NetworkManager manager)
	{
		if (!packet.channel.equals(channel))
			return;
		WorldServer world = (WorldServer) player.worldObj;
		
		try
		{
			DataInputStream stream = new DataInputStream(new ByteArrayInputStream(packet.data));
			int ID = stream.read();
			
			switch(ID)
			{
			case 1: (new PacketParticleSpawn()).interpret(stream, new Object[] {world}, Side.SERVER); break;
			default: CompactMobsCore.instance.cmLog.info("Unknown packet type recieved"); break;
			}
			
			stream.close();
		}
		catch(Exception e)
		{
			CompactMobsCore.instance.cmLog.info("Error reciving packet");
			e.printStackTrace();
		}
	}
	
	public void interpret(DataInputStream stream, Object[] extradata, Side side){}
	
	public byte getPacketID()
	{
		return 0;
	}
}
