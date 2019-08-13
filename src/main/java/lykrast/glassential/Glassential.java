package lykrast.glassential;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lykrast.glassential.blocks.DarkEtherealGlassBlock;
import lykrast.glassential.blocks.DarkGlassBlock;
import lykrast.glassential.blocks.EtherealGlassBlock;
import lykrast.glassential.blocks.RedstoneGlassBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.GlassBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod(Glassential.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Glassential.MODID)
public class Glassential {
	public static final String MODID = "glassential";
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	public Glassential() {
		//Config will have to go here one day
	}
	
	private static List<Item> blockitems;
	
	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
		blockitems = new ArrayList<>();
		event.getRegistry().registerAll(
				makeBlock("glass_dark", new DarkGlassBlock(Block.Properties.from(Blocks.GLASS)), ItemGroup.BUILDING_BLOCKS),
				makeBlock("glass_dark_ethereal", new DarkEtherealGlassBlock(Block.Properties.from(Blocks.GLASS).doesNotBlockMovement(), false), ItemGroup.BUILDING_BLOCKS),
				makeBlock("glass_dark_ethereal_reverse", new DarkEtherealGlassBlock(Block.Properties.from(Blocks.GLASS).doesNotBlockMovement(), true), ItemGroup.BUILDING_BLOCKS),
				makeBlock("glass_ethereal", new EtherealGlassBlock(Block.Properties.from(Blocks.GLASS).doesNotBlockMovement(), false), ItemGroup.BUILDING_BLOCKS),
				makeBlock("glass_ethereal_reverse", new EtherealGlassBlock(Block.Properties.from(Blocks.GLASS).doesNotBlockMovement(), true), ItemGroup.BUILDING_BLOCKS),
				makeBlock("glass_ghostly", new GlassBlock(Block.Properties.from(Blocks.GLASS).doesNotBlockMovement()), ItemGroup.BUILDING_BLOCKS),
				makeBlock("glass_light", new GlassBlock(Block.Properties.from(Blocks.GLASS).lightValue(15)), ItemGroup.BUILDING_BLOCKS),
				makeBlock("glass_redstone", new RedstoneGlassBlock(Block.Properties.from(Blocks.GLASS)), ItemGroup.REDSTONE)
				);
	}
	
	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> reg = event.getRegistry();
		blockitems.forEach(reg::register);
		blockitems = null;
	}
	
	private static Block makeBlock(String name, Block block, ItemGroup creativeTab) {
		block.setRegistryName(MODID, name);
		blockitems.add(new BlockItem(block, ((new Item.Properties()).group(creativeTab))).setRegistryName(MODID, name));
		return block;
	}
}
