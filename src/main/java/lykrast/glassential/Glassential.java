package lykrast.glassential;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lykrast.glassential.blocks.DarkEtherealGlassBlock;
import lykrast.glassential.blocks.EtherealGlassBlock;
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
	
	static {
		makeBlock("glass_light", () -> new TooltipGlassBlock(glassProp().lightLevel((b) -> 15), "tooltip.glassential.light"));
		makeBlock("glass_redstone", () -> new RedstoneGlassBlock(glassProp()));
		makeBlock("glass_ghostly", () -> new TooltipGlassBlock(glassProp().noCollission(), "tooltip.glassential.ghostly"));
		makeBlock("glass_ethereal", () -> new EtherealGlassBlock(glassProp().noCollission(), false));
		makeBlock("glass_ethereal_reverse", () -> new EtherealGlassBlock(glassProp().noCollission(), true));
		makeBlock("glass_dark_ethereal", () -> new DarkEtherealGlassBlock(glassProp().noCollission(), false));
		makeBlock("glass_dark_ethereal_reverse", () -> new DarkEtherealGlassBlock(glassProp().noCollission(), true));
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
	
	//Private predicates from Blocks, no need to AT something like that
	private static Boolean neverAllowSpawn(BlockState state, BlockGetter reader, BlockPos pos, EntityType<?> entity) {
		return false;
	}
	private static boolean isntSolid(BlockState state, BlockGetter reader, BlockPos pos) {
		return false;
	}
}
