package lykrast.glassential.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class DarkEtherealGlassBlock extends EtherealGlassBlock {

	public DarkEtherealGlassBlock(Block.Properties properties, boolean collidePlayers) {
		this(properties, collidePlayers, null);
	}

	public DarkEtherealGlassBlock(Block.Properties properties, boolean collidePlayers, String third) {
		super(properties, collidePlayers, "tooltip.glassential.dark", third);
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter world, BlockPos pos) {
		return world.getMaxLightLevel();
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
		return false;
	}
}
