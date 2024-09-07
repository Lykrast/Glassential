package lykrast.glassential.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EtherealGlassBlock extends TooltipGlassBlock {
    private final boolean collidePlayers;
    
    public EtherealGlassBlock(Block.Properties properties, boolean collidePlayers) {
    	this(properties, collidePlayers, null, null);
    }
    
    public EtherealGlassBlock(Block.Properties properties, boolean collidePlayers, String second) {
    	this(properties, collidePlayers, second, null);
    }

    public EtherealGlassBlock(Block.Properties properties, boolean collidePlayers, String second, String third) {
        super(properties, collidePlayers ? "tooltip.glassential.ethereal_reverse" : "tooltip.glassential.ethereal", second, third);
        this.collidePlayers = collidePlayers;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
    	//Ty KingLemming for finding that new way for the old behavior
        return (context instanceof EntityCollisionContext && ((EntityCollisionContext)context).getEntity() instanceof Player) == collidePlayers ? state.getShape(world, pos) : Shapes.empty();
    }
}
