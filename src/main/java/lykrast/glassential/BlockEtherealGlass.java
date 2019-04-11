package lykrast.glassential;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockEtherealGlass extends BlockCustomGlass {
	private boolean reverse;
	
	public BlockEtherealGlass(boolean reverse) {
		this.reverse = reverse;
	}
	
	private boolean isPlayer(Entity entity) {
		return entity != null && entity instanceof EntityPlayer;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
		if (isPlayer(entityIn) == reverse) super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, isActualState);
	}

}
