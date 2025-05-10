package vcfix.config;

import fermiumbooter.annotations.MixinConfig;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vcfix.VCFix;

@Config(modid = VCFix.MODID)
@SuppressWarnings("unused")
public class VCFixConfigHandler {
	
	@Config.Comment("Server-Side Options")
	@Config.Name("Server Options")
	@MixinConfig.SubInstance
	public static final ServerConfig server = new ServerConfig();

	@Config.Comment("Client-Side Options")
	@Config.Name("Client Options")
	@MixinConfig.SubInstance
	public static final ClientConfig client = new ClientConfig();

	public static class ServerConfig {

		@Config.Comment("Shift clicking on an item inside the hotbar will try to move it first into the crafting grid of the carpentry bench.")
		@Config.Name("Bench - Better Shift Click - From Hotbar")
		@MixinConfig.LateMixin(name = "mixins.vcfix.vc.shiftclickhotbar.json")
		public boolean shiftClickHotBarToBench = true;

		@Config.Comment("Shift clicking on an item inside the player inventory will try to move it first into the crafting grid of the carpentry bench.")
		@Config.Name("Bench - Better Shift Click - From Inventory")
		@MixinConfig.LateMixin(name = "mixins.vcfix.vc.shiftclickinventory.json")
		public boolean shiftClickInventoryToBench = true;

		@Config.Comment("Exiting out of the Carpentry Bench GUI will now first try to place the items inside the crafting grid back into the players inventory before dropping onto the ground.")
		@Config.Name("Bench - Stop Drop On GUI Exit")
		@MixinConfig.LateMixin(name = "mixins.vcfix.vc.stopdrop.json")
		public boolean stopDroppingOnExit = true;

		@Config.Comment("JEI sends warnings when big recipes are registered that won't fit the normal crafting table. This disables those warnings if the recipe fits into the carpentry bench.")
		@Config.Name("JEI - Stop Log Warn Spam")
		@MixinConfig.LateMixin(name = "mixins.vcfix.jei.stoplogspam.json")
		public boolean stopLogSpamBigRecipes = true;
	}

	public static class ClientConfig {

		@Config.Comment("Enable the normal number key behavior inside the carpentry bench GUI. Number keys will swap the stack in the currently hovered-over slot with the stack in the selected number key hotbar slot.")
		@Config.Name("Bench - GUI Allow Number Hotkeys")
		@MixinConfig.LateMixin(name = "mixins.vcfix.vc.numberhotkeysingui.json")
		public boolean allowNumberKeysBenchGUI = true;
	}

	@Mod.EventBusSubscriber(modid = VCFix.MODID)
	private static class EventHandler{
		@SubscribeEvent
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if(event.getModID().equals(VCFix.MODID)) {
				ConfigManager.sync(VCFix.MODID, Config.Type.INSTANCE);
			}
		}
	}
}