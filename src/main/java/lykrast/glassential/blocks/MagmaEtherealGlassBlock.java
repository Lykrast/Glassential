package lykrast.glassential.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class MagmaEtherealGlassBlock extends EtherealGlassBlock {

	public MagmaEtherealGlassBlock(Block.Properties properties, boolean collidePlayers) {
		this(properties, collidePlayers, null);
	}

	public MagmaEtherealGlassBlock(Block.Properties properties, boolean collidePlayers, String third) {
		super(properties, collidePlayers, "tooltip.glassential.magma", third);
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
}
