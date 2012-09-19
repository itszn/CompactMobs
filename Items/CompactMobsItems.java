package compactMobs.Items;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.src.Item;

public class CompactMobsItems 
{
	//Item variables
	//public Item veriniteChunk;

	
	private CompactMobsItems()
	{
		
	}
	
	private static CompactMobsItems instance;
	public static void createInstance()
	{
		instance = new CompactMobsItems();
	}
	
	public static CompactMobsItems getInstance()
	{
		return instance;
	}
	
	public void instantiateItems()
	{
		//veriniteChunk = new VeriniteChunk(550).setIconIndex(0).setItemName("VeriniteChunk");
	}
	
	public void nameItems()
	{
		//LanguageRegistry.addName(veriniteChunk, "Verinite Chunk");
	}
	
	
}
