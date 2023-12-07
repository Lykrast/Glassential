package lykrast.glassential;

import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lykrast.glassential.blocks.DarkEtherealGlassBlock;
import lykrast.glassential.blocks.EtherealGlassBlock;
import lykrast.glassential.blocks.RedstoneGlassBlock;
import lykrast.glassential.blocks.TooltipGlassBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
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
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerTabItems);
		BLOCKS.register(bus);
		ITEMS.register(bus);
	}

	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	
	public static final RegistryObject<Item> GLASS_DARK_ETHEREAL = makeBlock("glass_dark_ethereal", () -> new DarkEtherealGlassBlock(glassProp().noCollission(), false));
	public static final RegistryObject<Item> GLASS_DARK_ETHEREAL_REVERSE = makeBlock("glass_dark_ethereal_reverse", () -> new DarkEtherealGlassBlock(glassProp().noCollission(), true));
	public static final RegistryObject<Item> GLASS_ETHEREAL = makeBlock("glass_ethereal", () -> new EtherealGlassBlock(glassProp().noCollission(), false));
	public static final RegistryObject<Item> GLASS_ETHEREAL_REVERSE = makeBlock("glass_ethereal_reverse", () -> new EtherealGlassBlock(glassProp().noCollission(), true));
	public static final RegistryObject<Item> GLASS_GHOSTLY = makeBlock("glass_ghostly", () -> new TooltipGlassBlock(glassProp().noCollission(), "tooltip.glassential.ghostly"));
	public static final RegistryObject<Item> GLASS_LIGHT = makeBlock("glass_light", () -> new TooltipGlassBlock(glassProp().lightLevel((b) -> 15), "tooltip.glassential.light"));
	public static final RegistryObject<Item> GLASS_REDSTONE = makeBlock("glass_redstone", () -> new RedstoneGlassBlock(glassProp()));

	private static RegistryObject<Item> makeBlock(String name, Supplier<Block> block) {
		RegistryObject<Block> regged = BLOCKS.register(name, block);
		RegistryObject<Item> item = ITEMS.register(name, () -> new BlockItem(regged.get(), (new Item.Properties())));
		return item;
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

	public void registerTabItems(final BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
			event.accept(GLASS_DARK_ETHEREAL);
			event.accept(GLASS_DARK_ETHEREAL_REVERSE);
			event.accept(GLASS_ETHEREAL);
			event.accept(GLASS_ETHEREAL_REVERSE);
			event.accept(GLASS_GHOSTLY);
			event.accept(GLASS_LIGHT);
		}

		if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
			event.accept(GLASS_REDSTONE);
		}
	}
}
