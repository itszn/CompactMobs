package compactMobs.Items;

import compactMobs.CompactMobsCore;

import net.minecraft.src.Item;

public class CompactMobsItems {
    //Item variables

    public static Item mobHolder;
    public static Item fullMobHolder;
    public static Item handCompactor;
    public static Item handDecompactor;
    public static Item catalystCore;

    private CompactMobsItems() {
    }
    private static CompactMobsItems instance;

    public static void createInstance() {
        instance = new CompactMobsItems();
    }

    public static CompactMobsItems getInstance() {
        return instance;
    }

    public void instantiateItems() {
        mobHolder = new MobHolder(CompactMobsCore.instance.emptyMobHolderId.getInt()).setIconIndex(0).setItemName("MobHolder");
        fullMobHolder = new FullMobHolder(CompactMobsCore.instance.fullMobHolderId.getInt()).setIconIndex(1).setItemName("FullMobHolder");
        handCompactor = new HandheldCompactor(CompactMobsCore.instance.handCompactorId.getInt()).setIconIndex(2).setItemName("HandheldCompactor");
        handDecompactor = new HandheldDecompactor(CompactMobsCore.instance.handDecompactorId.getInt()).setIconIndex(3).setItemName("HandheldDecompactor");
        catalystCore = new CatalystCore(CompactMobsCore.instance.catalystCoreId.getInt()).setIconIndex(5).setItemName("CatalystCore");
    }

    public void nameItems() {
        //LanguageRegistry.addName(mobHolder, "Empty Mob Holder");
        //LanguageRegistry.addName(fullMobHolder, "Full Mob Holder");
    }
}
