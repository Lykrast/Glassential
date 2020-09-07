package lykrast.glassential.blocks;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.GlassBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TooltipGlassBlock extends GlassBlock {
	//Used for Light and Ghostly glass that don't need their own classes for their properties
	private String tooltip;

	public TooltipGlassBlock(Properties properties, String tooltip) {
		super(properties);
		this.tooltip = tooltip;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add((new TranslationTextComponent(this.tooltip)).mergeStyle(TextFormatting.GRAY));
	}

}
