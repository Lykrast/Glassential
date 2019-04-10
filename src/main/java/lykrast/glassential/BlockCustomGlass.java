package lykrast.glassential;

import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockCustomGlass extends BlockGlass {

	public BlockCustomGlass() {
		//Match base properties of glass
		//NOTE: this sets creative tabs (but luckily we're setting it back later anyway)
		super(Material.GLASS, false);
		setHardness(0.3F);
		setSoundType(SoundType.GLASS);
	}

}
