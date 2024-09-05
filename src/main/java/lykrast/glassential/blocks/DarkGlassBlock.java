package lykrast.glassential.blocks;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DarkGlassBlock extends GlassBlock {
	private String extraTooltip;

	public DarkGlassBlock(Block.Properties properties, String extraTooltip) {
		super(properties);
		this.extraTooltip = extraTooltip;
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter world, BlockPos pos) {
		return world.getMaxLightLevel();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		if (extraTooltip != null) tooltip.add(Component.translatable(extraTooltip).withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("tooltip.glassential.dark").withStyle(ChatFormatting.GRAY));
	}
}
