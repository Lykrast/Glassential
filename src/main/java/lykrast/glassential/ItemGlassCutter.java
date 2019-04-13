package lykrast.glassential;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Glassential.MODID)
public class ItemGlassCutter extends Item {

	public ItemGlassCutter(int durability) {
        setMaxStackSize(1);
        setMaxDamage(durability);
	}
	
	//Borrowed from Botania's Vitreous Pickaxe
	//https://github.com/Vazkii/Botania/blob/master/src/main/java/vazkii/botania/common/item/equipment/tool/ItemGlassPick.java
	@SubscribeEvent
	public static void onBlockDrops(HarvestDropsEvent event) {
		if (event.getHarvester() != null
				&& event.getState() != null
				&& event.getDrops() != null
				&& event.getDrops().isEmpty()
				&& !event.getHarvester().getHeldItemMainhand().isEmpty()
				&& event.getHarvester().getHeldItemMainhand().getItem() instanceof ItemGlassCutter
				&& event.getState().getMaterial() == Material.GLASS
				&& event.getState().getBlock().canSilkHarvest(event.getWorld(), event.getPos(), event.getState(), event.getHarvester()))
			event.getDrops().add(new ItemStack(event.getState().getBlock(), 1, event.getState().getBlock().getMetaFromState(event.getState())));
	}
	
	@Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        if (!worldIn.isRemote) stack.damageItem(1, entityLiving);
        return state.getMaterial() == Material.GLASS ? true : super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }

	@Override
	public boolean canHarvestBlock(IBlockState blockIn) {
		return blockIn.getMaterial() == Material.GLASS;
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		return state.getMaterial() == Material.GLASS ? 5.0F : super.getDestroySpeed(stack, state);
	}
}
