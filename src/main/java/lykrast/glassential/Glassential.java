package lykrast.glassential;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lykrast.glassential.blocks.DarkEtherealGlassBlock;
import lykrast.glassential.blocks.DarkGlassBlock;
import lykrast.glassential.blocks.EtherealGlassBlock;
import lykrast.glassential.blocks.MagmaEtherealGlassBlock;
import lykrast.glassential.blocks.MagmaGlassBlock;
import lykrast.glassential.blocks.RedstoneGlassBlock;
import lykrast.glassential.blocks.TooltipGlassBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
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
		bus.addListener(Glassential::makeCreativeTab);
	}

	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	private static List<RegistryObject<Item>> itemsForCreative = new ArrayList<>();
	private static RegistryObject<Item> stackForIcon;
	
	public static void makeCreativeTab(RegisterEvent event) {
		event.register(Registries.CREATIVE_MODE_TAB, helper -> {
			helper.register(ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(MODID, "glassential")),
					CreativeModeTab.builder()
					.title(Component.translatable("itemGroup.glassential.items"))
					.icon(() -> new ItemStack(stackForIcon.get()))
					.displayItems((parameters, output) -> itemsForCreative.forEach(i -> output.accept(i.get())))
					.build());
		});
	}
	
	private static final String GHOSTLY = "tooltip.glassential.ghostly", LIGHT = "tooltip.glassential.light", OBSIDIAN = "tooltip.glassential.obsidian";
	
	static {
		makeBlock("glass_redstone", () -> new RedstoneGlassBlock(glassProp()));
		makeBlock("glass_ghostly", () -> new TooltipGlassBlock(ghostProp(), GHOSTLY));
		makeBlock("glass_ethereal", () -> new EtherealGlassBlock(ghostProp(), false));
		makeBlock("glass_ethereal_reverse", () -> new EtherealGlassBlock(ghostProp(), true));
		makeBlock("glass_dark_ghostly", () -> new DarkGlassBlock(ghostProp(), GHOSTLY));
		makeBlock("glass_dark_ethereal", () -> new DarkEtherealGlassBlock(ghostProp(), false));
		makeBlock("glass_dark_ethereal_reverse", () -> new DarkEtherealGlassBlock(ghostProp(), true));
		makeBlock("glass_light", () -> new TooltipGlassBlock(glassProp().lightLevel((b) -> 15), LIGHT));
		makeBlock("glass_light_ghostly", () -> new TooltipGlassBlock(ghostProp().lightLevel((b) -> 15), GHOSTLY, LIGHT));
		makeBlock("glass_light_ethereal", () -> new EtherealGlassBlock(ghostProp().lightLevel((b) -> 15), false, LIGHT));
		makeBlock("glass_light_ethereal_reverse", () -> new EtherealGlassBlock(ghostProp().lightLevel((b) -> 15), true, LIGHT));
		makeBlock("glass_magma", () -> new MagmaGlassBlock(glassProp().lightLevel((b) -> 3)));
		makeBlock("glass_magma_ghostly", () -> new MagmaGlassBlock(ghostProp().lightLevel((b) -> 3), GHOSTLY));
		makeBlock("glass_magma_ethereal", () -> new MagmaEtherealGlassBlock(ghostProp().lightLevel((b) -> 3), false));
		makeBlock("glass_magma_ethereal_reverse", () -> new MagmaEtherealGlassBlock(ghostProp().lightLevel((b) -> 3), true));

		makeBlock("glass_obsidian", () -> new TooltipGlassBlock(obsidian(), OBSIDIAN));
		makeBlock("glass_obsidian_redstone", () -> new RedstoneGlassBlock(obsidian(), OBSIDIAN));
		makeBlock("glass_obsidian_ghostly", () -> new TooltipGlassBlock(ghostbsidian(), GHOSTLY));
		makeBlock("glass_obsidian_ethereal", () -> new EtherealGlassBlock(ghostbsidian(), false, OBSIDIAN));
		makeBlock("glass_obsidian_ethereal_reverse", () -> new EtherealGlassBlock(ghostbsidian(), true, OBSIDIAN));
		makeBlock("glass_obsidian_dark", () -> new DarkGlassBlock(obsidian(), null, OBSIDIAN));
		makeBlock("glass_obsidian_dark_ghostly", () -> new DarkGlassBlock(ghostbsidian(), GHOSTLY, OBSIDIAN));
		makeBlock("glass_obsidian_dark_ethereal", () -> new DarkEtherealGlassBlock(ghostbsidian(), false, OBSIDIAN));
		makeBlock("glass_obsidian_dark_ethereal_reverse", () -> new DarkEtherealGlassBlock(ghostbsidian(), true, OBSIDIAN));
		makeBlock("glass_obsidian_light", () -> new TooltipGlassBlock(obsidian().lightLevel((b) -> 15), LIGHT, OBSIDIAN));
		makeBlock("glass_obsidian_light_ghostly", () -> new TooltipGlassBlock(ghostbsidian().lightLevel((b) -> 15), GHOSTLY, LIGHT, OBSIDIAN));
		makeBlock("glass_obsidian_light_ethereal", () -> new EtherealGlassBlock(ghostbsidian().lightLevel((b) -> 15), false, LIGHT, OBSIDIAN));
		makeBlock("glass_obsidian_light_ethereal_reverse", () -> new EtherealGlassBlock(ghostbsidian().lightLevel((b) -> 15), true, LIGHT, OBSIDIAN));
		//it's a lil bit of a mess how I ordered the tooltips, hence the extra null
		makeBlock("glass_obsidian_magma", () -> new MagmaGlassBlock(obsidian().lightLevel((b) -> 3), null, OBSIDIAN));
		makeBlock("glass_obsidian_magma_ghostly", () -> new MagmaGlassBlock(ghostbsidian().lightLevel((b) -> 3), GHOSTLY, OBSIDIAN));
		makeBlock("glass_obsidian_magma_ethereal", () -> new MagmaEtherealGlassBlock(ghostbsidian().lightLevel((b) -> 3), false, OBSIDIAN));
		makeBlock("glass_obsidian_magma_ethereal_reverse", () -> new MagmaEtherealGlassBlock(ghostbsidian().lightLevel((b) -> 3), true, OBSIDIAN));
	}

	private static RegistryObject<Block> makeBlock(String name, Supplier<Block> block) {
		RegistryObject<Block> regged = BLOCKS.register(name, block);
		itemsForCreative.add(ITEMS.register(name, () -> new BlockItem(regged.get(), (new Item.Properties()))));
		//too lazy to do it smart
		if (name.equals("glass_ethereal_reverse")) stackForIcon = itemsForCreative.get(itemsForCreative.size()-1);
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
	
	private static Block.Properties ghostProp() {
		return glassProp().forceSolidOn().noCollission();
	}
	
	private static Block.Properties obsidian() {
		return glassProp().strength(0.5f, 1200);
	}
	
	private static Block.Properties ghostbsidian() {
		return ghostProp().strength(0.5f, 1200);
	}
	
	//Private predicates from Blocks, no need to AT something like that
	private static Boolean neverAllowSpawn(BlockState state, BlockGetter reader, BlockPos pos, EntityType<?> entity) {
		return false;
	}
	private static boolean isntSolid(BlockState state, BlockGetter reader, BlockPos pos) {
		return false;
	}
}
