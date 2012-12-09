package compactMobs;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.client.FMLClientHandler;

public class CompactMobsClient extends CompactMobsCore {
	public static Minecraft minecraft = FMLClientHandler.instance().getClient();
	public static void tick()
	{
		if (minecraft.thePlayer == null)
			return;
		
	}
	

}
