package lykrast.glassential;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lykrast.glassential.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
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
		event.getRegistry().registerAll(makeBlock("glass_dark", new DarkGlassBlock(glassProp()), ItemGroup.BUILDING_BLOCKS),
				makeBlock("glass_dark_ethereal", new DarkEtherealGlassBlock(glassProp().doesNotBlockMovement(), false), ItemGroup.BUILDING_BLOCKS),
				makeBlock("glass_dark_ethereal_reverse", new DarkEtherealGlassBlock(glassProp().doesNotBlockMovement(), true), ItemGroup.BUILDING_BLOCKS),
				makeBlock("glass_ethereal", new EtherealGlassBlock(glassProp().doesNotBlockMovement(), false), ItemGroup.BUILDING_BLOCKS),
				makeBlock("glass_ethereal_reverse", new EtherealGlassBlock(glassProp().doesNotBlockMovement(), true), ItemGroup.BUILDING_BLOCKS),
				makeBlock("glass_ghostly", new TooltipGlassBlock(glassProp().doesNotBlockMovement(), "tooltip.glassential.ghostly"), ItemGroup.BUILDING_BLOCKS),
				makeBlock("glass_light", new TooltipGlassBlock(glassProp().setLightLevel((b) -> 15), "tooltip.glassential.light"), ItemGroup.BUILDING_BLOCKS),
				makeBlock("glass_redstone", new RedstoneGlassBlock(glassProp()), ItemGroup.REDSTONE));
	}

	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> reg = event.getRegistry();
		blockitems.forEach(reg::register);
		blockitems = null;
	}

	@SubscribeEvent
	public static void clientStuff(final FMLClientSetupEvent event) {
		blocks.forEach(b -> RenderTypeLookup.setRenderLayer(b, RenderType.getCutout()));
		blocks = null;
	}

	private static Block makeBlock(String name, Block block, ItemGroup creativeTab) {
		block.setRegistryName(MODID, name);
		blocks.add(block);
		blockitems.add(new BlockItem(block, ((new Item.Properties()).group(creativeTab))).setRegistryName(MODID, name));
		return block;
	}
	
	private static Block.Properties glassProp() {
		//Turns out "from" doesn't copy everything that glass sets
		return Block.Properties.from(Blocks.GLASS)
				.setAllowsSpawn(Glassential::neverAllowSpawn)
				.setOpaque(Glassential::isntSolid)
				.setSuffocates(Glassential::isntSolid)
				.setBlocksVision(Glassential::isntSolid);
	}
	
	//Private predicates from Blocks, no need to AT something like that
	private static Boolean neverAllowSpawn(BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> entity) {
		return false;
	}
	private static boolean isntSolid(BlockState state, IBlockReader reader, BlockPos pos) {
		return false;
	}
}
