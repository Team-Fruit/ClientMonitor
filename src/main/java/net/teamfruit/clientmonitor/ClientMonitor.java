package net.teamfruit.clientmonitor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY)
public class ClientMonitor {
	@Instance(Reference.MODID)
	public static @Nullable ClientMonitor instance;

	@SidedProxy(serverSide = Reference.PROXY_SERVER, clientSide = Reference.PROXY_CLIENT)
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(final @Nonnull FMLPreInitializationEvent event) {
		if (proxy!=null)
			proxy.preInit(event);
	}

	@EventHandler
	public void init(final @Nonnull FMLInitializationEvent event) {
		if (proxy!=null)
			proxy.init(event);
	}

	@EventHandler
	public void postInit(final @Nonnull FMLPostInitializationEvent event) {
		if (proxy!=null)
			proxy.postInit(event);
	}

	@EventHandler
	public void serverStarting(final @Nonnull FMLServerStartingEvent event) {
		if (proxy!=null)
			proxy.serverStarting(event);
	}
}