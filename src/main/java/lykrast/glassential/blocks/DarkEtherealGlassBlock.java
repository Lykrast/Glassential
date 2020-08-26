package lykrast.glassential.blocks;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DarkEtherealGlassBlock extends EtherealGlassBlock {

	public DarkEtherealGlassBlock(Block.Properties properties, boolean collidePlayers) {
		super(properties, collidePlayers);
	}

	@Override
	public int getOpacity(BlockState state, IBlockReader world, BlockPos pos) {
		return world.getMaxLightLevel();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		ITextComponent added = new TranslationTextComponent("tooltip.glassential.dark");
		added.getStyle().applyFormatting(TextFormatting.GRAY);
		tooltip.add(added);
	}
}
