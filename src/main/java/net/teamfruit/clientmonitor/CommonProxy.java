package net.teamfruit.clientmonitor;

import javax.annotation.Nonnull;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.teamfruit.clientmonitor.command.CommandClientMonitor;
import net.teamfruit.clientmonitor.network.RequestMessage;

public class CommonProxy {
	public SimpleNetworkWrapper network;

	public void preInit(final @Nonnull FMLPreInitializationEvent event) {
		Config.init(event.getSuggestedConfigurationFile());
		this.network = NetworkRegistry.INSTANCE.newSimpleChannel("clientmonitor");
		this.network.registerMessage(RequestMessage.Handler.class, RequestMessage.class, 0, Side.CLIENT);
	}

	public void init(final @Nonnull FMLInitializationEvent event) {
		new CoreHandler().init();
	}

	public void postInit(final @Nonnull FMLPostInitializationEvent event) {
		Config.getConfig().save();
	}

	public void serverStarting(final @Nonnull FMLServerStartingEvent event) {
		event.registerServerCommand(CommandClientMonitor.instance);
	}
}
