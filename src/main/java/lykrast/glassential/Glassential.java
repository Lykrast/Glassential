package lykrast.glassential;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lykrast.glassential.blocks.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod(Glassential.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Glassential.MODID)
public class Glassential {
	public static final String MODID = "glassential";

	public static final Logger LOGGER = LogManager.getLogger();

	public Glassential() {
		// Config will have to go here one day
	}

	private static List<Block> blocks;
	private static List<Item> blockitems;

	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
		blocks = new ArrayList<>();
		blockitems = new ArrayList<>();
		event.getRegistry().registerAll(makeBlock("glass_dark_ethereal", new DarkEtherealGlassBlock(glassProp().noCollission(), false), CreativeModeTab.TAB_BUILDING_BLOCKS),
				makeBlock("glass_dark_ethereal_reverse", new DarkEtherealGlassBlock(glassProp().noCollission(), true), CreativeModeTab.TAB_BUILDING_BLOCKS),
				makeBlock("glass_ethereal", new EtherealGlassBlock(glassProp().noCollission(), false), CreativeModeTab.TAB_BUILDING_BLOCKS),
				makeBlock("glass_ethereal_reverse", new EtherealGlassBlock(glassProp().noCollission(), true), CreativeModeTab.TAB_BUILDING_BLOCKS),
				makeBlock("glass_ghostly", new TooltipGlassBlock(glassProp().noCollission(), "tooltip.glassential.ghostly"), CreativeModeTab.TAB_BUILDING_BLOCKS),
				makeBlock("glass_light", new TooltipGlassBlock(glassProp().lightLevel((b) -> 15), "tooltip.glassential.light"), CreativeModeTab.TAB_BUILDING_BLOCKS),
				makeBlock("glass_redstone", new RedstoneGlassBlock(glassProp()), CreativeModeTab.TAB_REDSTONE));
	}

	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> reg = event.getRegistry();
		blockitems.forEach(reg::register);
		blockitems = null;
	}

	@SubscribeEvent
	public static void clientStuff(final FMLClientSetupEvent event) {
		blocks.forEach(b -> ItemBlockRenderTypes.setRenderLayer(b, RenderType.translucent()));
		blocks = null;
	}

	private static Block makeBlock(String name, Block block, CreativeModeTab creativeTab) {
		block.setRegistryName(MODID, name);
		blocks.add(block);
		blockitems.add(new BlockItem(block, ((new Item.Properties()).tab(creativeTab))).setRegistryName(MODID, name));
		return block;
	}
	
	private static Block.Properties glassProp() {
		//Turns out "from" doesn't copy everything that glass sets
		return Block.Properties.copy(Blocks.GLASS)
				.isValidSpawn(Glassential::neverAllowSpawn)
				.isRedstoneConductor(Glassential::isntSolid)
				.isSuffocating(Glassential::isntSolid)
				.isViewBlocking(Glassential::isntSolid);
	}
	
	//Private predicates from Blocks, no need to AT something like that
	private static Boolean neverAllowSpawn(BlockState state, BlockGetter reader, BlockPos pos, EntityType<?> entity) {
		return false;
	}
	private static boolean isntSolid(BlockState state, BlockGetter reader, BlockPos pos) {
		return false;
	}
}
