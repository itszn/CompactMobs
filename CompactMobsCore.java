package compactMobs;

import compactMobs.Items.CompactMobsItems;
import compactMobs.Blocks.BlockCompactor;
import compactMobs.TileEntity.TileEntityCompactor;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraftforge.common.DungeonHooks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.src.Material;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.LanguageRegistry;


@NetworkMod(clientSideRequired = true, serverSideRequired = false)
@Mod(modid = "CM", name = "CompactMobs", version = "1.0.0")
public class CompactMobsCore {
	
	@Instance 
	public static CompactMobsCore instance;
	
	//hardcoded id of block
	public static int BlockID = 3391;
	
	public static Block blocks;
	public static Block blockCompactor;
	
	
	@SidedProxy(clientSide = "compactMobs.ClientProxyCompactMobs", serverSide = "compactMobs.CommonProxyCompactMobs")
	public static CommonProxyCompactMobs proxy;
	
	
	@Init
	public void load(FMLInitializationEvent event)
	{
		proxy.registerRenderThings();
		CompactMobsItems.createInstance();
		
		NetworkRegistry.instance().registerGuiHandler(this, this.proxy);
		
		blockCompactor = new BlockCompactor(BlockID, Material.iron).setStepSound(Block.soundStoneFootstep).setHardness(3F).setResistance(1.0F).setBlockName("orbBlock");;
		GameRegistry.registerBlock(blockCompactor);
		LanguageRegistry.addName(blockCompactor, "Compactor"); 
		
		GameRegistry.registerTileEntity(TileEntityCompactor.class,"tileEntityCompactor");
		
		CompactMobsItems.getInstance().instantiateItems();
		CompactMobsItems.getInstance().nameItems();
	
		
		
	}
	
}
