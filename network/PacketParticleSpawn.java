package compactMobs.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import compactMobs.CompactMobsCore;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import net.minecraft.src.Packet;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.World;

public class PacketParticleSpawn extends PacketCommon {

    public static final byte packetID = 1;

    public static Packet buildParticleSpawnPacket(String type, double x, double y, double z, double vx, double vy, double vz, int number) {
        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = channel;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(bytes);
        try {
            data.write(packetID);
            data.writeDouble(x);
            data.writeDouble(y);
            data.writeDouble(z);
            data.writeDouble(vx);
            data.writeDouble(vy);
            data.writeDouble(vz);
            data.writeDouble(number);
            data.writeUTF(type);

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
        if (side.equals(Side.CLIENT)) {
            interpretClient(stream, extradata);
        } else {
            CompactMobsCore.instance.cmLog.info("Received particle packet on server! Doing nothing");
        }
    }

    @SideOnly(Side.CLIENT)
    public void interpretClient(DataInputStream stream, Object[] extradata) {
        try {
            double x1 = stream.readDouble();
            double y1 = stream.readDouble();
            double z1 = stream.readDouble();
            double x2 = stream.readDouble();
            double y2 = stream.readDouble();
            double z2 = stream.readDouble();
            int number = stream.read();
            String type = stream.readUTF();

            for (int i = 0; i < number; i++) {
                ((World) extradata[0]).spawnParticle(type, x1, y1, z1, x2, y2, z2);
            }

        } catch (Exception e) {
            CompactMobsCore.instance.cmLog.info("Error reading packet or spawning particles");
            e.printStackTrace();
        }
    }

    @Override
    public byte getPacketID() {
        return packetID;
    }
}
