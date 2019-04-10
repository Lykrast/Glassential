package lykrast.glassential;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.RequiresMcRestart;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Glassential.MODID)
@LangKey("config." + Glassential.MODID + ".title")
public class GlassentialConfig {
	
	@RequiresMcRestart
	@LangKey("config." + Glassential.MODID + ".glass_clear")
	@Comment("Enable Clear Glass")
	public static boolean clear = true;
	
	@RequiresMcRestart
	@LangKey("config." + Glassential.MODID + ".glass_light")
	@Comment("Enable Luminous Glass")
	public static boolean light = true;
	
	@RequiresMcRestart
	@LangKey("config." + Glassential.MODID + ".glass_redstone")
	@Comment("Enable Redstone Glass")
	public static boolean redstone = true;
	
	@RequiresMcRestart
	@LangKey("config." + Glassential.MODID + ".glass_dark")
	@Comment("Enable Dark Glass")
	public static boolean dark = true;

	@Mod.EventBusSubscriber(modid = Glassential.MODID)
	private static class EventHandler {
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(Glassential.MODID)) {
				ConfigManager.sync(Glassential.MODID, Config.Type.INSTANCE);
			}
		}
	}
}
