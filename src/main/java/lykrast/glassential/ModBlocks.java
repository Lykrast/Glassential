package lykrast.glassential;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class ModBlocks {
	public static Block clear, light, redstone, dark;
	private static List<Item> itemBlocks;

	private static Block registerBlock(IForgeRegistry<Block> reg, Block block, String name, CreativeTabs tab) {
		block.setRegistryName(name);
		block.setTranslationKey(Glassential.MODID + "." + name);
		if (tab != null)
			block.setCreativeTab(tab);

		reg.register(block);

		Item item = new ItemBlock(block);
		item.setRegistryName(block.getRegistryName());
		itemBlocks.add(item);

		return block;
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();
		itemBlocks = new ArrayList<>();

		clear = registerBlock(registry, new BlockCustomGlass(), "glass_clear", CreativeTabs.BUILDING_BLOCKS);
		light = registerBlock(registry, new BlockCustomGlass().setLightLevel(1.0F), "glass_light", CreativeTabs.BUILDING_BLOCKS);
		redstone = registerBlock(registry, new BlockCustomGlass() {
			@Override
			public boolean canProvidePower(IBlockState state) {
				return true;
			}

			@Override
			public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
				return 15;
			}
		}, "glass_redstone", CreativeTabs.REDSTONE);
		dark = registerBlock(registry, new BlockCustomGlass().setLightOpacity(255), "glass_dark", CreativeTabs.BUILDING_BLOCKS);
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> reg = event.getRegistry();
		for (Item i : itemBlocks)
			reg.register(i);
		
		registerOreDict();
	}
	
	private static void registerOreDict() {
		OreDictionary.registerOre("blockGlass", clear);
		OreDictionary.registerOre("blockGlass", light);
		OreDictionary.registerOre("blockGlass", redstone);
		OreDictionary.registerOre("blockGlass", dark);
	}
	
	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		GameRegistry.addSmelting(Blocks.GLASS, new ItemStack(clear), 0.1F);
	}

	private static void initModel(Block b) {
		initModel(Item.getItemFromBlock(b));
	}

	private static void initModel(Item i) {
		ModelLoader.setCustomModelResourceLocation(i, 0, new ModelResourceLocation(i.getRegistryName(), "inventory"));
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void registerModels(ModelRegistryEvent evt) {
		initModel(clear);
		initModel(light);
		initModel(redstone);
		initModel(dark);

		for (Item i : itemBlocks)
			initModel(i);
		
		//Let it be garbage collected to save a whole nothing of memory!
		itemBlocks = null;
	}

}
