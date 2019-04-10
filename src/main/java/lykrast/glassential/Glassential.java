package lykrast.glassential;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Glassential.MODID, 
	name = Glassential.NAME, 
	version = Glassential.VERSION, 
	acceptedMinecraftVersions = "[1.12, 1.13)")
public class Glassential {
	public static final String MODID = "glassential";
	public static final String NAME = "Glassential";
	public static final String VERSION = "@VERSION@";

	public static Logger logger = LogManager.getLogger(MODID);

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger.info("We got preinit'd bois!");
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
	}
}
