package lykrast.glassential.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class DarkEtherealGlassBlock extends EtherealGlassBlock {

	public DarkEtherealGlassBlock(Block.Properties properties, boolean collidePlayers) {
		super(properties, collidePlayers, "tooltip.glassential.dark");
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter world, BlockPos pos) {
		return world.getMaxLightLevel();
	}
}
