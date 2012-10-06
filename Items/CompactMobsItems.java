package compactMobs.Items;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.src.Item;

public class CompactMobsItems 
{
	//Item variables
	public static Item mobHolder;
	public static Item fullMobHolder;

	
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
		mobHolder = new MobHolder(3352).setIconIndex(0).setItemName("MobHolder");
		fullMobHolder = new FullMobHolder(3353).setIconIndex(1).setItemName("FullMobHolder");
	}
	
	public void nameItems()
	{
		LanguageRegistry.addName(mobHolder, "Empty Mob Holder");
		LanguageRegistry.addName(fullMobHolder, "Full Mob Holder");

	}
	
	
}
