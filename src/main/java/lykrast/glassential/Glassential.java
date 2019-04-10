package lykrast.glassential;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;

@Mod(modid = Glassential.MODID, 
	name = Glassential.NAME, 
	version = Glassential.VERSION, 
	acceptedMinecraftVersions = "[1.12, 1.13)")
public class Glassential {
	public static final String MODID = "glassential";
	public static final String NAME = "Glassential";
	public static final String VERSION = "@VERSION@";

	public static Logger logger = LogManager.getLogger(MODID);
}
