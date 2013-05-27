package compactMobs.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import compactMobs.CompactMobsCore;
import compactMobs.TileEntity.TileEntityNamer;

public class PacketNamerText extends PacketClient {

    public static final byte packetID = 2;
    
    @SideOnly(Side.CLIENT)
    public static Packet buildNamerTextPacket(String text, int x, int y, int z) {
        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = channel;
        System.out.println("Building Packet for "+text);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(bytes);
        try {
            data.write(packetID);
            data.writeUTF(text);
            data.writeInt(x);
            data.writeInt(y);
            data.writeInt(z);

            packet.data = bytes.toByteArray();
            packet.length = packet.data.length;

            data.close();
            bytes.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packet;
    }

    @Override
    public void interpret(DataInputStream stream, Object[] extradata, Side side) {
        if (side.equals(Side.SERVER)) {
            interpretCommon(stream, extradata);
        } else {
            CompactMobsCore.instance.cmLog.info("Received particle packet on client! Doing nothing");
        }
    }
    @SideOnly(Side.SERVER)
    public void interpretCommon(DataInputStream stream, Object[] extradata) {
        try {
            String text = stream.readUTF();
            int x = stream.readInt();
            int y = stream.readInt();
            int z = stream.readInt();
            TileEntityNamer te = (TileEntityNamer) ((World) extradata[0]).getBlockTileEntity(x,y,z);
            te.updateText(text);
            System.out.println("Packet Recieved for "+ text);
        } catch (Exception e) {
            CompactMobsCore.instance.cmLog.info("Error reading packet or changing namer text");
            e.printStackTrace();
        }
    }

    @Override
    public byte getPacketID() {
        return packetID;
    }
}
