package lykrast.glassential.blocks;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MagmaGlassBlock extends GlassBlock {
	private String extraTooltip;
	
	public MagmaGlassBlock(Block.Properties properties) {
		this(properties, null);
	}

	public MagmaGlassBlock(Block.Properties properties, String extraTooltip) {
		super(properties);
		this.extraTooltip = extraTooltip;
	}

	@Override
	public void stepOn(Level level, BlockPos pos, BlockState blockstate, Entity entity) {
		//match magma block
		if (!entity.isSteppingCarefully() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
			entity.hurt(level.damageSources().hotFloor(), 1);
		}
		super.stepOn(level, pos, blockstate, entity);
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		//match how magma blocks only damage living entities
		//that makes it different from cactus but eeeh we'll see
		if (entity instanceof LivingEntity) entity.hurt(level.damageSources().hotFloor(), 1);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		if (extraTooltip != null) tooltip.add(Component.translatable(extraTooltip).withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("tooltip.glassential.magma").withStyle(ChatFormatting.GRAY));
	}
}
