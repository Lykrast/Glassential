package lykrast.glassential;

import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lykrast.glassential.blocks.DarkEtherealGlassBlock;
import lykrast.glassential.blocks.EtherealGlassBlock;
import lykrast.glassential.blocks.RedstoneGlassBlock;
import lykrast.glassential.blocks.TooltipGlassBlock;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(Glassential.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Glassential.MODID)
public class Glassential {
	public static final String MODID = "glassential";

	public static final Logger LOGGER = LogManager.getLogger();

	public Glassential() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		BLOCKS.register(bus);
		ITEMS.register(bus);
	}

	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	
	static {
		makeBlock("glass_dark_ethereal", () -> new DarkEtherealGlassBlock(glassProp().noCollission(), false), CreativeModeTab.TAB_BUILDING_BLOCKS);
		makeBlock("glass_dark_ethereal_reverse", () -> new DarkEtherealGlassBlock(glassProp().noCollission(), true), CreativeModeTab.TAB_BUILDING_BLOCKS);
		makeBlock("glass_ethereal", () -> new EtherealGlassBlock(glassProp().noCollission(), false), CreativeModeTab.TAB_BUILDING_BLOCKS);
		makeBlock("glass_ethereal_reverse", () -> new EtherealGlassBlock(glassProp().noCollission(), true), CreativeModeTab.TAB_BUILDING_BLOCKS);
		makeBlock("glass_ghostly", () -> new TooltipGlassBlock(glassProp().noCollission(), "tooltip.glassential.ghostly"), CreativeModeTab.TAB_BUILDING_BLOCKS);
		makeBlock("glass_light", () -> new TooltipGlassBlock(glassProp().lightLevel((b) -> 15), "tooltip.glassential.light"), CreativeModeTab.TAB_BUILDING_BLOCKS);
		makeBlock("glass_redstone", () -> new RedstoneGlassBlock(glassProp()), CreativeModeTab.TAB_REDSTONE);
	}

	@SubscribeEvent
	public static void clientStuff(final FMLClientSetupEvent event) {
		BLOCKS.getEntries().forEach(b -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.translucent()));
	}

	private static RegistryObject<Block> makeBlock(String name, Supplier<Block> block, CreativeModeTab creativeTab) {
		RegistryObject<Block> regged = BLOCKS.register(name, block);
		ITEMS.register(name, () -> new BlockItem(regged.get(), (new Item.Properties()).tab(creativeTab)));
		return regged;
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
