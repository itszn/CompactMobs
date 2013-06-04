package compactMobs.Items;

import net.minecraft.item.Item;

import compactMobs.CompactMobsCore;

public class CompactMobsItems {
    //Item variables

    public static Item mobHolder;
    public static Item fullMobHolder;
    public static Item handCompactor;
    public static Item handDecompactor;
    public static Item catalystCore;
    public static Item upgrade;

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
        mobHolder = new MobHolder(CompactMobsCore.instance.emptyMobHolderId.getInt());
        fullMobHolder = new FullMobHolder(CompactMobsCore.instance.fullMobHolderId.getInt());
        handCompactor = new HandheldCompactor(CompactMobsCore.instance.handCompactorId.getInt());
        handDecompactor = new HandheldDecompactor(CompactMobsCore.instance.handDecompactorId.getInt());
        catalystCore = new CatalystCore(CompactMobsCore.instance.catalystCoreId.getInt());
        upgrade = new Upgrade(CompactMobsCore.instance.catalystUpgradeId.getInt());
    }

    public void nameItems() {
        //LanguageRegistry.addName(mobHolder, "Empty Mob Holder");
        //LanguageRegistry.addName(fullMobHolder, "Full Mob Holder");
    }
}
