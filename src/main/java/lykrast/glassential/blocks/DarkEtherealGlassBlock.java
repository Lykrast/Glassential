package lykrast.glassential.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class DarkEtherealGlassBlock extends EtherealGlassBlock {

    public DarkEtherealGlassBlock(Block.Properties properties, boolean collideOnSneaking) {
        super(properties, collideOnSneaking);
    }

    @Override
    public int getOpacity(BlockState state, IBlockReader world, BlockPos pos) {
        return world.getMaxLightLevel();
    }
}
