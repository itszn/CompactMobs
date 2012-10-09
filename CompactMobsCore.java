package compactMobs;

import java.util.logging.Logger;

import compactMobs.Items.CompactMobsItems;
import compactMobs.Blocks.BlockCompactor;
import compactMobs.Blocks.BlockDecompactor;
import compactMobs.TileEntity.TileEntityCompactor;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraftforge.common.DungeonHooks;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
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
	public static Block blockDecompactor;
	
	public static Logger cmLog = Logger.getLogger("CompactMobs");
	
	
	@SidedProxy(clientSide = "compactMobs.ClientProxyCompactMobs", serverSide = "compactMobs.CommonProxyCompactMobs")
	public static CommonProxyCompactMobs proxy;
	
	
	@PreInit
	public void loadConfiguration(FMLPreInitializationEvent evt) {
		cmLog.setParent(FMLLog.getLogger());
		cmLog.info("Starting CompactMobs v1.0.0");
		
	}
	
	@Init
	public void load(FMLInitializationEvent event)
	{
		
		CompactMobsItems.createInstance();
		
		NetworkRegistry.instance().registerGuiHandler(this, this.proxy);
		
		blockCompactor = new BlockCompactor(BlockID, Material.iron).setStepSound(Block.soundMetalFootstep).setHardness(3F).setResistance(1.0F).setBlockName("blockCompactor");
		GameRegistry.registerBlock(blockCompactor);
		LanguageRegistry.addName(blockCompactor, "Compactor"); 
		blockDecompactor = new BlockDecompactor(BlockID+1, Material.iron).setStepSound(Block.soundMetalFootstep).setHardness(3F).setResistance(1.0F).setBlockName("blockDecompactor");
		GameRegistry.registerBlock(blockDecompactor);
		LanguageRegistry.addName(blockDecompactor, "Decompactor");
		
		GameRegistry.registerTileEntity(TileEntityCompactor.class,"tileEntityCompactor");
		
		CompactMobsItems.getInstance().instantiateItems();
		CompactMobsItems.getInstance().nameItems();
		
	}
	
}
