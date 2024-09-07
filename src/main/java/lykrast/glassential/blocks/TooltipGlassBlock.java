package lykrast.glassential.blocks;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TooltipGlassBlock extends GlassBlock {
	//Used for Light and Ghostly glass that don't need their own classes for their properties
	private String tooltip, second, third;
	
	public TooltipGlassBlock(Properties properties, String tooltip) {
		this(properties, tooltip, null, null);
	}
	
	public TooltipGlassBlock(Properties properties, String tooltip, String second) {
		this(properties, tooltip, second, null);
	}

	public TooltipGlassBlock(Properties properties, String tooltip, String second, String third) {
		super(properties);
		this.tooltip = tooltip;
		this.second = second;
		this.third = third;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		if (this.tooltip != null) tooltip.add(Component.translatable(this.tooltip).withStyle(ChatFormatting.GRAY));
		if (second != null) tooltip.add(Component.translatable(second).withStyle(ChatFormatting.GRAY));
		if (third != null) tooltip.add(Component.translatable(third).withStyle(ChatFormatting.GRAY));
	}

}
