package lykrast.glassential.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.GlassBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class DarkGlassBlock extends GlassBlock {

    public DarkGlassBlock(Block.Properties properties) {
        super(properties);
    }

    @Override
    public int getOpacity(BlockState state, IBlockReader world, BlockPos pos) {
        return world.getMaxLightLevel();
    }
}
